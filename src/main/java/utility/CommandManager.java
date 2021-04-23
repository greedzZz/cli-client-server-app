package utility;

import content.Chapter;
import content.SpaceMarine;
import utility.auxiliary.ChapterReader;
import utility.auxiliary.ElementReader;

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

    public CommandManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.elementReader = new ElementReader();
        this.chapterReader = new ChapterReader();
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
                            SpaceMarine sm = elementReader.readElement(scanner);
                            collectionManager.insert(key, sm);
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
                            collectionManager.update(id, sm);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        } catch (NumberFormatException e) {
                            System.out.println("ID value must be integer. Greater than 0.");
                        }
                        break;
                    case "remove_key":
                        try {
                            Integer key = Integer.parseInt(input[1]);
                            collectionManager.removeKey(key);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        } catch (NumberFormatException e) {
                            System.out.println("The input argument is not an integer.");
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
                            File file = new File(input[1]);
                            collectionManager.addScript(file.getAbsolutePath());
                            collectionManager.executeScript(input[1]);
                            collectionManager.clearScripts();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        }
                        break;
                    case "exit":
                        scanner.close();
                        collectionManager.exit();
                        break;
                    case "remove_greater":
                        try {
                            SpaceMarine sm = elementReader.readElement(scanner);
                            collectionManager.removeGreater(sm);
                        } catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "replace_if_greater":
                        try {
                            Integer key = Integer.parseInt(input[1]);
                            SpaceMarine sm = elementReader.readElement(scanner);
                            collectionManager.replaceIfGreater(key, sm);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        } catch (NumberFormatException e) {
                            System.out.println("The input argument is not an integer.");
                        }
                        break;
                    case "remove_greater_key":
                        try {
                            Integer key = Integer.parseInt(input[1]);
                            collectionManager.removeGreaterKey(key);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("To execute this command, you must enter the required argument.");
                        } catch (NumberFormatException e) {
                            System.out.println("The input argument is not an integer.");
                        }
                        break;
                    case "group_counting_by_coordinates":
                        collectionManager.groupCountingByCoordinates();
                        break;
                    case "filter_by_chapter":
                        try {
                            Chapter chapter = chapterReader.readChapter(scanner);
                            collectionManager.filterByChapter(chapter);
                        } catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "filter_starts_with_name":
                        try {
                            collectionManager.filterStartsWithName(input[1]);
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
