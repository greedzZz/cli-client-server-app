package utility.auxiliary;

import commands.*;
import content.*;

import java.io.*;
import java.util.*;

public class ScriptReader {
    private final ElementReader elementReader;
    private final ChapterReader chapterReader;
    private final LinkedList<String> otherScripts = new LinkedList<>();
    private final Serializer serializer;

    public ScriptReader(ElementReader elementReader, ChapterReader chapterReader, Serializer serializer) {
        this.elementReader = elementReader;
        this.chapterReader = chapterReader;
        this.serializer = serializer;
    }

    public void readScript(String pathname, CommandSender commandSender, AnswerReceiver answerReceiver) {
        try {
            File file = new File(pathname);
            Scanner scanFile = new Scanner(file);
            String command = "";
            boolean isIncorrect = false;
            while (!isIncorrect && scanFile.hasNextLine()) {
                String[] input = scanFile.nextLine().trim().split(" ");
                command = input[0];
                try {
                    switch (command) {
                        case "help":
                            Command help = new Help();
                            commandSender.send(serializer.serialize(help));
                            System.out.println(answerReceiver.receive());
                            break;
                        case "info":
                            Command info = new Info();
                            commandSender.send(serializer.serialize(info));
                            System.out.println(answerReceiver.receive());
                            break;
                        case "show":
                            Command show = new Show();
                            commandSender.send(serializer.serialize(show));
                            System.out.println(answerReceiver.receive());
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
                                commandSender.send(serializer.serialize(insert));
                                System.out.println(answerReceiver.receive());
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
                                commandSender.send(serializer.serialize(update));
                                System.out.println(answerReceiver.receive());
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
                                commandSender.send(serializer.serialize(removeKey));
                                System.out.println(answerReceiver.receive());
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        case "clear":
                            Command clear = new Clear();
                            commandSender.send(serializer.serialize(clear));
                            System.out.println(answerReceiver.receive());
                            break;
                        case "execute_script":
                            try {
                                File nextFile = new File(input[1]);
                                if (otherScripts.contains(nextFile.getAbsolutePath())) {
                                    throw new IllegalArgumentException("Recursion detected. Further reading of the script is impossible.");
                                } else {
                                    otherScripts.add(nextFile.getAbsolutePath());
                                    Command executeScript = new ExecuteScript();
                                    commandSender.send(serializer.serialize(executeScript));
                                    System.out.println(answerReceiver.receive());
                                    readScript(input[1], commandSender, answerReceiver);
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
                            commandSender.send(serializer.serialize(exit));
                            System.out.println(answerReceiver.receive());
                            System.exit(0);
                            break;
                        case "remove_greater":
                            try {
                                elementReader.setFromFile(true);
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                Command removeGreater = new RemoveGreater(sm);
                                commandSender.send(serializer.serialize(removeGreater));
                                System.out.println(answerReceiver.receive());
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
                                commandSender.send(serializer.serialize(replaceIfGreater));
                                System.out.println(answerReceiver.receive());
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
                                commandSender.send(serializer.serialize(removeGreaterKey));
                                System.out.println(answerReceiver.receive());
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        case "group_counting_by_coordinates":
                            Command groupCountingByCoordinates = new GroupCountingByCoordinates();
                            commandSender.send(serializer.serialize(groupCountingByCoordinates));
                            System.out.println(answerReceiver.receive());
                            break;
                        case "filter_by_chapter":
                            try {
                                chapterReader.setFromFile(true);
                                Chapter chapter = chapterReader.readChapter(scanFile);
                                Command filterByChapter = new FilterByChapter(chapter);
                                commandSender.send(serializer.serialize(filterByChapter));
                                System.out.println(answerReceiver.receive());
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
                                commandSender.send(serializer.serialize(filterStartsWithName));
                                System.out.println(answerReceiver.receive());
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        default:
                            throw new IllegalArgumentException("The script file is not correct or there are no commands there. Further reading of the script is impossible.");
                    }
                } catch (IllegalArgumentException e) {
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
