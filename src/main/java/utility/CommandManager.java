package utility;

import commands.*;
import content.*;
import utility.auxiliary.*;

import java.io.File;
import java.util.Scanner;

/**
 * User interface of the program.
 * Reads user input.
 */
public class CommandManager {
    private final CollectionManager collectionManager;
    private final ElementReader elementReader;
    private final ChapterReader chapterReader;
    private final ScriptReader scriptReader;

    public CommandManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.elementReader = new ElementReader();
        this.chapterReader = new ChapterReader();
        this.scriptReader = new ScriptReader(collectionManager, elementReader, chapterReader);
    }

    public void readInput() {
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
                            SpaceMarine sm = elementReader.readElement(scanner);
                            Command insert = new Insert(key, sm);
                            insert.execute(collectionManager);
                            //collectionManager.insert(key, sm);
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
                            update.execute(collectionManager);
                            //collectionManager.update(id, sm);
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
                            removeKey.execute(collectionManager);
                            //collectionManager.removeKey(key);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        } catch (NumberFormatException e) {
                            System.out.println("The input argument is not an integer.");
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
                            File file = new File(input[1]);
                            scriptReader.addScript(file.getAbsolutePath());
                            Command executeScript = new ExecuteScript();
                            executeScript.execute(collectionManager);
                            //collectionManager.executeScript();
                            scriptReader.readScript(input[1]);
                            scriptReader.clearScripts();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        }
                        break;
                    case "exit":
                        scanner.close();
                        Command exit = new Exit();
                        exit.execute(collectionManager);
                        //collectionManager.exit();
                        break;
                    case "remove_greater":
                        try {
                            SpaceMarine sm = elementReader.readElement(scanner);
                            Command removeGreater = new RemoveGreater(sm);
                            removeGreater.execute(collectionManager);
                            //collectionManager.removeGreater(sm);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "replace_if_greater":
                        try {
                            Integer key = Integer.parseInt(input[1]);
                            SpaceMarine sm = elementReader.readElement(scanner);
                            Command replaceIfGreater = new ReplaceIfGreater(key, sm);
                            replaceIfGreater.execute(collectionManager);
                            //collectionManager.replaceIfGreater(key, sm);
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
                            removeGreaterKey.execute(collectionManager);
                            //collectionManager.removeGreaterKey(key);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        } catch (NumberFormatException e) {
                            System.out.println("The input argument is not an integer.");
                        }
                        break;
                    case "group_counting_by_coordinates":
                        Command groupCountingByCoordinates = new GroupCountingByCoordinates();
                        groupCountingByCoordinates.execute(collectionManager);
                        //collectionManager.groupCountingByCoordinates();
                        break;
                    case "filter_by_chapter":
                        try {
                            Chapter chapter = chapterReader.readChapter(scanner);
                            Command filterByChapter = new FilterByChapter(chapter);
                            filterByChapter.execute(collectionManager);
                            //collectionManager.filterByChapter(chapter);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "filter_starts_with_name":
                        try {
                            Command filterStartsWithName = new FilterStartsWithName(input[1]);
                            filterStartsWithName.execute(collectionManager);
                            //collectionManager.filterStartsWithName(input[1]);
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
        collectionManager.exit();
    }
}
