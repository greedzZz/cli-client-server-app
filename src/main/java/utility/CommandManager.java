package utility;

import commands.*;
import content.*;
import utility.auxiliary.*;

import java.io.File;
import java.net.*;
import java.util.Scanner;

/**
 * User interface of the program.
 * Reads user input.
 */
public class CommandManager {
    private final ElementReader elementReader;
    private final ChapterReader chapterReader;
    private final ScriptReader scriptReader;
    private final Serializer serializer;

    public CommandManager() {
        this.elementReader = new ElementReader();
        this.chapterReader = new ChapterReader();
        this.serializer = new Serializer();
        this.scriptReader = new ScriptReader(elementReader, chapterReader, serializer);
    }

    public void readInput(SocketAddress address, DatagramSocket socket) {
        CommandSender commandSender = new CommandSender(address, socket);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter a command. (Enter \"help\" to get information about available commands)");
        String command = "";
        while (scanner.hasNextLine() && !command.equals("exit")) {
            String[] input = scanner.nextLine().trim().split(" ");
            command = input[0];
            try {
                switch (command) {
                    case "help":
                        Command help = new Help();
                        commandSender.send(serializer.serialize(help));
                        //bytes = serializer.serialize(help);
                        //packet = new DatagramPacket(bytes, bytes.length, address);
                        //socket.send(packet);
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
                            SpaceMarine sm = elementReader.readElement(scanner);
                            Command insert = new Insert(key, sm);
                            commandSender.send(serializer.serialize(insert));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        } catch (NumberFormatException e) {
                            System.out.println("Key value must be integer. Greater than 0.");
                        }
                        break;
                    case "update":
                        try {
                            int id = Integer.parseInt(input[1]);
                            if (id < 0) {
                                throw new NumberFormatException();
                            }
                            SpaceMarine sm = elementReader.readElement(scanner);
                            Command update = new Update(id, sm);
                            commandSender.send(serializer.serialize(update));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        } catch (NumberFormatException e) {
                            System.out.println("ID value must be integer. Greater than 0.");
                        }
                        break;
                    case "remove_key":
                        try {
                            Integer key = Integer.parseInt(input[1]);
                            Command removeKey = new RemoveKey(key);
                            commandSender.send(serializer.serialize(removeKey));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        } catch (NumberFormatException e) {
                            System.out.println("The input argument is not an integer.");
                        }
                        break;
                    case "clear":
                        Command clear = new Clear();
                        commandSender.send(serializer.serialize(clear));
                        break;
//                    case "save":
//                        Command save = new Save();
//                        break;
                    case "execute_script":
                        try {
                            File file = new File(input[1]);
                            scriptReader.addScript(file.getAbsolutePath());
                            Command executeScript = new ExecuteScript();
                            commandSender.send(serializer.serialize(executeScript));
                            scriptReader.readScript(input[1], commandSender);
                            scriptReader.clearScripts();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        }
                        break;
                    case "exit":
                        scanner.close();
                        Command exit = new Exit();
                        commandSender.send(serializer.serialize(exit));
                        System.exit(0);
                        break;
                    case "remove_greater":
                        try {
                            SpaceMarine sm = elementReader.readElement(scanner);
                            Command removeGreater = new RemoveGreater(sm);
                            commandSender.send(serializer.serialize(removeGreater));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "replace_if_greater":
                        try {
                            Integer key = Integer.parseInt(input[1]);
                            SpaceMarine sm = elementReader.readElement(scanner);
                            Command replaceIfGreater = new ReplaceIfGreater(key, sm);
                            commandSender.send(serializer.serialize(replaceIfGreater));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        } catch (NumberFormatException e) {
                            System.out.println("The input argument is not an integer.");
                        }
                        break;
                    case "remove_greater_key":
                        try {
                            Integer key = Integer.parseInt(input[1]);
                            Command removeGreaterKey = new RemoveGreaterKey(key);
                            commandSender.send(serializer.serialize(removeGreaterKey));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        } catch (NumberFormatException e) {
                            System.out.println("The input argument is not an integer.");
                        }
                        break;
                    case "group_counting_by_coordinates":
                        Command groupCountingByCoordinates = new GroupCountingByCoordinates();
                        commandSender.send(serializer.serialize(groupCountingByCoordinates));
                        break;
                    case "filter_by_chapter":
                        try {
                            Chapter chapter = chapterReader.readChapter(scanner);
                            Command filterByChapter = new FilterByChapter(chapter);
                            commandSender.send(serializer.serialize(filterByChapter));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "filter_starts_with_name":
                        try {
                            Command filterStartsWithName = new FilterStartsWithName(input[1]);
                            commandSender.send(serializer.serialize(filterStartsWithName));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        }
                        break;
                    default:
                        throw new Exception("Incorrect command input. Enter \"help\" to get information about available commands.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        scanner.close();
        //collectionManager.exit();
    }
}
