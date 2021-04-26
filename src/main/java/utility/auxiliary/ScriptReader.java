package utility.auxiliary;

import commands.*;
import content.*;


import java.io.*;
import java.net.DatagramPacket;
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

    public void readScript(String pathname, CommandSender commandSender) {
        try {
            File file = new File(pathname);
            Scanner scanFile = new Scanner(file);
            String command = "";
            boolean isIncorrect = false;
            while (!isIncorrect && scanFile.hasNextLine() && !command.equals("exit")) {
                byte[] bytes;
                DatagramPacket packet;
                String[] input = scanFile.nextLine().trim().split(" ");
                command = input[0];
                try {
                    switch (command) {
                        case "help":
                            Command help = new Help();
                            commandSender.send(serializer.serialize(help));
                            break;
                        case "info":
                            Command info = new Info();
                            commandSender.send(serializer.serialize(info));
                            break;
                        case "show":
                            Command show = new Show();
                            commandSender.send(serializer.serialize(show));
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
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        case "clear":
                            Command clear = new Clear();
                            commandSender.send(serializer.serialize(clear));
                            break;
//                        case "save":
//                            Command save = new Save();
//                            break;
                        case "execute_script":
                            try {
                                File nextFile = new File(input[1]);
                                if (otherScripts.contains(nextFile.getAbsolutePath())) {
                                    throw new Exception("Recursion detected. Further reading of the script is impossible.");
                                } else {
                                    otherScripts.add(nextFile.getAbsolutePath());
                                    Command executeScript = new ExecuteScript();
                                    commandSender.send(serializer.serialize(executeScript));
                                    readScript(input[1], commandSender);
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
                            System.exit(0);
                            break;
                        case "remove_greater":
                            try {
                                elementReader.setFromFile(true);
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                Command removeGreater = new RemoveGreater(sm);
                                commandSender.send(serializer.serialize(removeGreater));
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
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        case "group_counting_by_coordinates":
                            Command groupCountingByCoordinates = new GroupCountingByCoordinates();
                            commandSender.send(serializer.serialize(groupCountingByCoordinates));
                            break;
                        case "filter_by_chapter":
                            try {
                                chapterReader.setFromFile(true);
                                Chapter chapter = chapterReader.readChapter(scanFile);
                                Command filterByChapter = new FilterByChapter(chapter);
                                commandSender.send(serializer.serialize(filterByChapter));
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
