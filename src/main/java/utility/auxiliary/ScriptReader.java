package utility.auxiliary;

import content.Chapter;
import content.SpaceMarine;
import utility.CollectionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

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

    public void readScript(String pathname){
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
                            collectionManager.help();
                            break;
                        case "info":
                            collectionManager.info();
                            break;
                        case "show":
                            collectionManager.show();
                            break;
                        case "insert":
                            try {
                                int key = Integer.parseInt(input[1]);
                                if (key < 0) {
                                    throw new NumberFormatException();
                                }
                                elementReader.setFromFile(true);
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                collectionManager.insert(key, sm);
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
                                collectionManager.update(id, sm);
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
                                collectionManager.removeKey(key);
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        case "clear":
                            collectionManager.clear();
                            break;
                        case "save":
                            collectionManager.save();
                            break;
                        case "execute_script":
                            try {
                                File file1 = new File(input[1]);
                                if (otherScripts.contains(file1.getAbsolutePath())) {
                                    throw new Exception("Recursion detected. Further reading of the script is impossible.");
                                } else {
                                    otherScripts.add(file.getAbsolutePath());
                                    collectionManager.executeScript();
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
                            collectionManager.exit();
                            break;
                        case "remove_greater":
                            try {
                                elementReader.setFromFile(true);
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                collectionManager.removeGreater(sm);
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
                                collectionManager.replaceIfGreater(key, sm);
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
                                collectionManager.removeGreaterKey(key);
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        case "group_counting_by_coordinates":
                            collectionManager.groupCountingByCoordinates();
                            break;
                        case "filter_by_chapter":
                            try {
                                chapterReader.setFromFile(true);
                                Chapter chapter = chapterReader.readChapter(scanFile);
                                collectionManager.filterByChapter(chapter);
                            } catch (IllegalArgumentException e) {
                                elementReader.setFromFile(false);
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        case "filter_starts_with_name":
                            try {
                                collectionManager.filterStartsWithName(input[1]);
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
