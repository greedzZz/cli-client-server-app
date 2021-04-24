package utility.auxiliary;

import commands.*;
import content.*;
import utility.CollectionManager;

import java.io.*;
import java.util.*;

public class ScriptReader {
    private final CollectionManager collectionManager;
    private final ElementReader elementReader;
    private final ChapterReader chapterReader;
    private final LinkedList<String> otherScripts = new LinkedList<>();

    public ScriptReader(CollectionManager collectionManager, ElementReader elementReader, ChapterReader chapterReader) {
        this.collectionManager = collectionManager;
        this.elementReader = elementReader;
        this.chapterReader = chapterReader;
    }

    public void readScript(String pathname) {
        try {
            File file = new File(pathname);
            Scanner scanFile = new Scanner(file);
            String command = "";
            boolean isIncorrect = false;
            while (!isIncorrect && scanFile.hasNextLine() && !command.equals("exit")) {
                String[] input = scanFile.nextLine().trim().split(" ");
                command = input[0];
                try {
                    switch (command) {
                        case "help":
                            Command help = new Help();
                            help.execute(collectionManager);
                            //collectionManager.help();
                            break;
                        case "info":
                            Command info = new Info();
                            info.execute(collectionManager);
                            //collectionManager.info();
                            break;
                        case "show":
                            Command show = new Show();
                            show.execute(collectionManager);
                            //collectionManager.show();
                            break;
                        case "insert":
                            try {
                                int key = Integer.parseInt(input[1]);
                                if (key < 0) {
                                    throw new NumberFormatException();
                                }
                                elementReader.setFromFile(true);
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                Command insert = new Insert(key, sm);
                                insert.execute(collectionManager);
                                //collectionManager.insert(key, sm);
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                                elementReader.setFromFile(false);
                            }
                            break;
                        case "update":
                            try {
                                int id = Integer.parseInt(input[1]);
                                if (id < 0) {
                                    throw new NumberFormatException();
                                }
                                elementReader.setFromFile(true);
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                Command update = new Update(id, sm);
                                update.execute(collectionManager);
                                //collectionManager.update(id, sm);
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                                elementReader.setFromFile(false);
                            }
                            break;
                        case "remove_key":
                            try {
                                Integer key = Integer.parseInt(input[1]);
                                Command removeKey = new RemoveKey(key);
                                removeKey.execute(collectionManager);
                                //collectionManager.removeKey(key);
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        case "clear":
                            Command clear = new Clear();
                            clear.execute(collectionManager);
                            //collectionManager.clear();
                            break;
                        case "save":
                            Command save = new Save();
                            save.execute(collectionManager);
                            //collectionManager.save();
                            break;
                        case "execute_script":
                            try {
                                File nextFile = new File(input[1]);
                                if (otherScripts.contains(nextFile.getAbsolutePath())) {
                                    throw new Exception("Recursion detected. Further reading of the script is impossible.");
                                } else {
                                    otherScripts.add(nextFile.getAbsolutePath());
                                    Command executeScript = new ExecuteScript();
                                    executeScript.execute(collectionManager);
                                    //collectionManager.executeScript();
                                    readScript(input[1]);
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        case "exit":
                            scanFile.close();
                            Command exit = new Exit();
                            exit.execute(collectionManager);
                            //collectionManager.exit();
                            break;
                        case "remove_greater":
                            try {
                                elementReader.setFromFile(true);
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                Command removeGreater = new RemoveGreater(sm);
                                removeGreater.execute(collectionManager);
                                //collectionManager.removeGreater(sm);
                            } catch (IllegalArgumentException e) {
                                elementReader.setFromFile(false);
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }

                            break;
                        case "replace_if_greater":
                            try {
                                Integer key = Integer.parseInt(input[1]);
                                elementReader.setFromFile(true);
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                Command replaceIfGreater = new ReplaceIfGreater(key, sm);
                                replaceIfGreater.execute(collectionManager);
                                //collectionManager.replaceIfGreater(key, sm);
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                                elementReader.setFromFile(false);
                            }
                            break;
                        case "remove_greater_key":
                            try {
                                Integer key = Integer.parseInt(input[1]);
                                Command removeGreaterKey = new RemoveGreaterKey(key);
                                removeGreaterKey.execute(collectionManager);
                                //collectionManager.removeGreaterKey(key);
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        case "group_counting_by_coordinates":
                            Command groupCountingByCoordinates = new GroupCountingByCoordinates();
                            groupCountingByCoordinates.execute(collectionManager);
                            //collectionManager.groupCountingByCoordinates();
                            break;
                        case "filter_by_chapter":
                            try {
                                chapterReader.setFromFile(true);
                                Chapter chapter = chapterReader.readChapter(scanFile);
                                Command filterByChapter = new FilterByChapter(chapter);
                                filterByChapter.execute(collectionManager);
                                //collectionManager.filterByChapter(chapter);
                            } catch (IllegalArgumentException e) {
                                elementReader.setFromFile(false);
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        case "filter_starts_with_name":
                            try {
                                Command filterStartsWithName = new FilterStartsWithName(input[1]);
                                filterStartsWithName.execute(collectionManager);
                                //collectionManager.filterStartsWithName(input[1]);
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        default:
                            throw new Exception("The script file is not correct or there are no commands there. Further reading of the script is impossible.");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    isIncorrect = true;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File with the specified pathname does not exist or there is no read permission for this file.\n" +
                    "Enter \"help\" to get information about available commands.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            File file = new File(pathname);
            otherScripts.remove(file.getAbsolutePath());
        }
    }

    public void clearScripts() {
        otherScripts.clear();
    }

    public void addScript(String s) {
        otherScripts.add(s);
    }
}
