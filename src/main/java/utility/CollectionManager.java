package utility;

import content.Chapter;
import content.SpaceMarine;
import utility.auxiliary.*;
import utility.parsing.ObjectToXMLParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class that stores and interacts with the collection.
 */
public class CollectionManager {
    private final IDGenerator idGenerator;
    private final ElementReader elementReader;
    private final ChapterReader chapterReader;
    private final TreeMap<Integer, SpaceMarine> treeMap;
    private final Date date;
    private final HashMap<String, String> commandPool = new HashMap<>();
    private final LinkedList<String> otherScripts = new LinkedList<>();
    private File file;

    {
        commandPool.put("help", "Displays information on available commands.");
        commandPool.put("info", "Displays information about the collection.");
        commandPool.put("show", "Displays information about elements of the collection.");
        commandPool.put("insert \"key\"", "Adds a new element with the given key.");
        commandPool.put("update \"id\"", "Updates the value of the collection element whose id is equal to the given.");
        commandPool.put("remove_key \"key\"", "Removes a collection element by its key.");
        commandPool.put("clear", "Clears the collection.");
        commandPool.put("save", "Saves collection to the file.");
        commandPool.put("execute_script \"file_name\"", "Reads and executes a script from the specified file.");
        commandPool.put("exit", "Ends the program (without saving to file).");
        commandPool.put("remove_greater", "Removes all items from the collection that are greater than the specified one.");
        commandPool.put("replace_if_greater \"key\"", "Replaces the value by key if the new value is greater than the old one.");
        commandPool.put("remove_greater_key \"key\"", "Removes from the collection all elements whose key exceeds the given one.");
        commandPool.put("group_counting_by_coordinates", "Groups the elements of the collection by the value of the coordinates field, display the number of elements in each group.");
        commandPool.put("filter_by_chapter \"chapter\"", "Displays elements whose chapter field is equal to the given.");
        commandPool.put("filter_starts_with_name \"name\"", "Displays elements whose name field value begins with a given substring.");
    }

    public CollectionManager() {
        this.date = new Date();
        this.treeMap = new TreeMap<>();
        this.idGenerator = new IDGenerator();
        this.elementReader = new ElementReader();
        this.chapterReader = new ChapterReader();
    }

    public void help() {
        for (String com : commandPool.keySet()) {
            System.out.println(com + ": " + commandPool.get(com));
        }
    }

    public void info() {
        System.out.println("Collection type: " + treeMap.getClass() + "\n" +
                "Collection initialization date: " + date + "\n" +
                "Collection size: " + treeMap.size());
    }

    public void show() {
        try {
            SpaceMarineDescriber smd = new SpaceMarineDescriber();
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            }
            System.out.println("Elements of the collection:\n");
            for (SpaceMarine spaceMarine : treeMap.values()) {
                smd.describe(spaceMarine);
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(Integer key, SpaceMarine sm) {
        try {
            sm.setID(key);
            put(sm);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Integer id, SpaceMarine sm) {
        try {
            if (!treeMap.containsKey(id)) {
                throw new Exception("There is no element with such id in the collection.");
            } else {
                sm.setID(id);
                put(sm);
                System.out.println("Value of element with id " + id + " has been updated.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void removeKey(Integer key) {
        try {
            if (!treeMap.containsKey(key)) {
                throw new Exception("There is no such argument in the collection.");
            } else {
                treeMap.remove(key);
                idGenerator.removeID(key);
                System.out.println("Element with " + key + " key has been deleted.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clear() {
        treeMap.clear();
        idGenerator.clearSet();
        System.out.println("The collection has been cleared.");
    }

    public void save() {
        ObjectToXMLParser parser = new ObjectToXMLParser(file);
        parser.parse(treeMap);
    }

    public void executeScript(String pathname) {
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
                            help();
                            break;
                        case "info":
                            info();
                            break;
                        case "show":
                            show();
                            break;
                        case "insert":
                            try {
                                int key = Integer.parseInt(input[1]);
                                if (key < 0) {
                                    throw new NumberFormatException();
                                }
                                elementReader.setFromFile(true);
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                insert(key, sm);
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
                                update(id, sm);
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
                                removeKey(key);
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        case "clear":
                            clear();
                            break;
                        case "save":
                            save();
                            break;
                        case "execute_script":
                            try {
                                File file1 = new File(input[1]);
                                if (otherScripts.contains(file1.getAbsolutePath())) {
                                    throw new Exception("Recursion detected. Further reading of the script is impossible.");
                                } else {
                                    otherScripts.add(file.getAbsolutePath());
                                    executeScript(input[1]);
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        case "exit":
                            scanFile.close();
                            exit();
                            break;
                        case "remove_greater":
                            try {
                                elementReader.setFromFile(true);
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                removeGreater(sm);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }

                            break;
                        case "replace_if_greater":
                            try {
                                Integer key = Integer.parseInt(input[1]);
                                elementReader.setFromFile(true);
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                replaceIfGreater(key, sm);
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
                                removeGreaterKey(key);
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                System.out.println("The script file is not correct. Further reading of the script is impossible.\n" +
                                        "Enter \"help\" to get information about available commands.");
                                isIncorrect = true;
                            }
                            break;
                        case "group_counting_by_coordinates":
                            groupCountingByCoordinates();
                            break;
                        case "filter_by_chapter":
                            try {
                                chapterReader.setFromFile(true);
                                Chapter chapter = chapterReader.readChapter(scanFile);
                                filterByChapter(chapter);
                            } catch (Exception e){
                                System.out.println(e.getMessage());
                            }
                            break;
                        case "filter_starts_with_name":
                            try {
                                filterStartsWithName(input[1]);
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

    public void exit() {
        System.out.println("The program is finished.");
        System.exit(0);
    }

    public void removeGreater(SpaceMarine sm) {
        try {
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            } else {
                Iterator<SpaceMarine> iterator = treeMap.values().iterator();
                String name;
                while (iterator.hasNext()) {
                    SpaceMarine next = iterator.next();
                    if (sm.compareTo(next) < 0) {
                        name = next.getName();
                        idGenerator.removeID(next.getID());
                        iterator.remove();
                        System.out.println("Space marine " + name + " has been removed from the collection.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void replaceIfGreater(Integer key, SpaceMarine sm) {
        try {
            if (!treeMap.containsKey(key)) {
                throw new Exception("There is no such argument in the collection.");
            } else {
                if (sm.compareTo(treeMap.get(key)) > 0) {
                    sm.setID(key);
                    treeMap.put(sm.getID(), sm);
                    System.out.println("Element with " + key + " key has been replaced.");
                } else {
                    System.out.println("Value of the entered element does not exceed the value of the collection element.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeGreaterKey(Integer key) {
        try {
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            } else {
                Iterator<SpaceMarine> iterator = treeMap.values().iterator();
                while (iterator.hasNext()) {
                    SpaceMarine next = iterator.next();
                    Integer currentKey = next.getID();
                    if (currentKey > key) {
                        iterator.remove();
                        idGenerator.removeID(currentKey);
                        System.out.println("Element with key " + currentKey + " has been deleted.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void groupCountingByCoordinates() {
        try {
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            } else {
                int first = 0;
                int second = 0;
                int third = 0;
                int fourth = 0;
                for (SpaceMarine sm : treeMap.values()) {
                    if (sm.getCoordinateX() >= 0) {
                        if (sm.getCoordinateY() >= 0) {
                            first += 1;
                        } else {
                            fourth += 1;
                        }
                    } else {
                        if (sm.getCoordinateY() >= 0) {
                            second += 1;
                        } else {
                            third += 1;
                        }
                    }
                }
                System.out.println("There are " + first + " space marines in the first coordinate quarter, " +
                        "" + second + " in the second one, " + third + " in the third one, " + fourth + " in the fourth one.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void filterByChapter(Chapter chapter) {
        try {
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            }
            String chapterName = chapter.getName();
            String chapterWorld = chapter.getWorld();
            SpaceMarineDescriber smd = new SpaceMarineDescriber();
            System.out.println("Elements whose chapter value is equal to entered value:");
            System.out.println();
            int count = 0;
            for (SpaceMarine sm : treeMap.values()) {
                if (sm.getChapterName().equals(chapterName) && sm.getChapterWorld().equals(chapterWorld)) {
                    smd.describe(sm);
                    count += 1;
                }
            }
            if (count == 0) {
                System.out.println("There are no elements whose chapter value is equal to entered value.");
            }
        } catch (Exception e) {
            chapterReader.setFromFile(false);
            System.out.println(e.getMessage());
        }
    }

    public void filterStartsWithName(String name) {
        try {
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            }
            SpaceMarineDescriber smd = new SpaceMarineDescriber();
            int count = 0;
            System.out.println("Elements whose starts with entered value:");
            System.out.println();
            for (SpaceMarine sm : treeMap.values()) {
                if (sm.getName().startsWith(name)) {
                    smd.describe(sm);
                    count += 1;
                }
            }
            if (count == 0) {
                System.out.println("There are no elements whose starts with entered value.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void put(SpaceMarine sm) {
        treeMap.put(sm.getID(), sm);
        System.out.println("Space marine " + sm.getName() + " has been added to the collection!");
        idGenerator.addID(sm.getID());
    }

    public void putWithGeneration(SpaceMarine sm) throws Exception {
        sm.setID(idGenerator.generateID());
        treeMap.put(sm.getID(), sm);
        System.out.println("Space marine " + sm.getName() + " has been added to the collection!");
        idGenerator.addID(sm.getID());
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void clearScripts() {
        otherScripts.clear();
    }

    public void addScript(String s) {
        otherScripts.add(s);
    }
}
