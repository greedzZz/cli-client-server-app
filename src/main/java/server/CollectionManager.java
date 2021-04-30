package server;

import common.content.Chapter;
import common.content.SpaceMarine;
import server.utility.ObjectToXMLParser;
import server.utility.SpaceMarineDescriber;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Class that stores and interacts with the collection.
 */
public class CollectionManager {
    private final TreeMap<Integer, SpaceMarine> treeMap;
    private final Date date;
    private final HashMap<String, String> commandPool = new HashMap<>();
    private File file;

    {
        commandPool.put("help", "Displays information on available commands.");
        commandPool.put("info", "Displays information about the collection.");
        commandPool.put("show", "Displays information about elements of the collection.");
        commandPool.put("insert \"key\"", "Adds a new element with the given key.");
        commandPool.put("update \"id\"", "Updates the value of the collection element whose id is equal to the given.");
        commandPool.put("remove_key \"key\"", "Removes a collection element by its key.");
        commandPool.put("clear", "Clears the collection.");
        commandPool.put("execute_script \"file_name\"", "Reads and executes a script from the specified file.");
        commandPool.put("exit", "Ends the program.");
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
    }

    public String help() {
        return commandPool.keySet().stream()
                .map(com -> com + ": " + commandPool.get(com) + "\n")
                .collect(Collectors.joining());
    }

    public String info() {
        return "Collection type: " + treeMap.getClass() + "\n" +
                "Collection initialization date: " + date + "\n" +
                "Collection size: " + treeMap.size() + "\n";
    }

    public String show() {
        try {
            SpaceMarineDescriber smd = new SpaceMarineDescriber();
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            }
            return treeMap.values().stream()
                    .map(smd::describe)
                    .map(str -> str + "\n\n")
                    .collect(Collectors.joining());
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String insert(Integer key, SpaceMarine sm) {
        try {
            sm.setID(key);
            sm.setCreationDate();
            return put(sm);
        } catch (Exception e) {
            return e.getMessage() + "\n";
        }
    }

    public String update(Integer id, SpaceMarine sm) {
        try {
            if (!treeMap.containsKey(id)) {
                throw new Exception("There is no element with such id in the collection.");
            } else {
                sm.setID(id);
                sm.setCreationDate();
                return put(sm) +
                        "Value of element with id " + id + " has been updated.\n";
            }
        } catch (Exception e) {
            return e.getMessage() + "\n";
        }
    }

    public String removeKey(Integer key) {
        try {
            if (!treeMap.containsKey(key)) {
                throw new Exception("There is no such argument in the collection.");
            } else {
                treeMap.remove(key);
                return "Element with " + key + " key has been deleted.\n";
            }
        } catch (Exception e) {
            return e.getMessage() + "\n";
        }
    }

    public String clear() {
        treeMap.clear();
        return "The collection has been cleared.\n";
    }

    public void save() {
        ObjectToXMLParser parser = new ObjectToXMLParser(file);
        parser.parse(treeMap);
    }

    public String executeScript() {
        return "Starting execution of the script.\n";
    }

    public String exit() {
        return "The program is finished.\n";
    }

    public String removeGreater(SpaceMarine sm) {
        try {
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            } else {
                Iterator<SpaceMarine> iterator = treeMap.values().iterator();
                String name;
                StringBuilder sb = new StringBuilder();
                while (iterator.hasNext()) {
                    SpaceMarine next = iterator.next();
                    if (sm.compareTo(next) < 0) {
                        name = next.getName();
                        iterator.remove();
                        sb.append("Space marine ").append(name).append(" has been removed from the collection.\n");
                    }
                }
                return sb.toString();
            }
        } catch (Exception e) {
            return e.getMessage() + "\n";
        }
    }

    public String replaceIfGreater(Integer key, SpaceMarine sm) {
        try {
            if (!treeMap.containsKey(key)) {
                throw new Exception("There is no such argument in the collection.");
            } else {
                if (sm.compareTo(treeMap.get(key)) > 0) {
                    sm.setID(key);
                    sm.setCreationDate();
                    treeMap.put(sm.getID(), sm);
                    return "Element with " + key + " key has been replaced.\n";
                } else {
                    return "Value of the entered element does not exceed the value of the collection element.\n";
                }
            }
        } catch (Exception e) {
            return e.getMessage() + "\n";
        }
    }

    public String removeGreaterKey(Integer key) {
        try {
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            } else {
                Iterator<SpaceMarine> iterator = treeMap.values().iterator();
                StringBuilder sb = new StringBuilder();
                while (iterator.hasNext()) {
                    SpaceMarine next = iterator.next();
                    Integer currentKey = next.getID();
                    if (currentKey > key) {
                        iterator.remove();
                        sb.append("Element with key ").append(currentKey).append(" has been deleted.\n");
                    }
                }
                return sb.toString() + "\n";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String groupCountingByCoordinates() {
        try {
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            } else {
                long first = treeMap.values().stream()
                        .filter(sm -> sm.getCoordinateX() >= 0)
                        .filter(sm -> sm.getCoordinateY() >= 0)
                        .count();
                long second = treeMap.values().stream()
                        .filter(sm -> sm.getCoordinateX() < 0)
                        .filter(sm -> sm.getCoordinateY() >= 0)
                        .count();
                long third = treeMap.values().stream()
                        .filter(sm -> sm.getCoordinateX() < 0)
                        .filter(sm -> sm.getCoordinateY() < 0)
                        .count();
                long fourth = treeMap.values().stream()
                        .filter(sm -> sm.getCoordinateX() >= 0)
                        .filter(sm -> sm.getCoordinateY() < 0)
                        .count();
                return "There are " + first + " space marines in the first coordinate quarter, " +
                        "" + second + " in the second one, " + third +
                        " in the third one, " + fourth + " in the fourth one.\n";
            }
        } catch (Exception e) {
            return e.getMessage() + "\n";
        }
    }

    public String filterByChapter(Chapter chapter) {
        try {
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            }
            String chapterName = chapter.getName();
            String chapterWorld = chapter.getWorld();
            SpaceMarineDescriber smd = new SpaceMarineDescriber();
            String marines = treeMap.values().stream()
                    .filter(sm -> sm.getChapterName().equals(chapterName) && sm.getChapterWorld().equals(chapterWorld))
                    .map(sm -> smd.describe(sm) + "\n\n")
                    .collect(Collectors.joining());
            if (marines.length() > 1) {
                return "Elements whose chapter value is equal to entered value:\n" + marines;
            } else {
                return "There are no elements whose chapter value is equal to entered value.\n";
            }
        } catch (Exception e) {
            return e.getMessage() + "\n";
        }
    }

    public String filterStartsWithName(String name) {
        try {
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            }
            SpaceMarineDescriber smd = new SpaceMarineDescriber();
            String marines = treeMap.values().stream()
                    .filter(sm -> sm.getName().startsWith(name))
                    .map(sm -> smd.describe(sm) + "\n\n")
                    .collect(Collectors.joining());
            if (marines.length() > 1) {
                return "Elements whose starts with entered value:\n" + marines;
            } else {
                return "There are no elements whose starts with entered value.\n";
            }
        } catch (Exception e) {
            return e.getMessage() + "\n";
        }
    }

    public String put(SpaceMarine sm) {
        treeMap.put(sm.getID(), sm);
        return "Space marine " + sm.getName() + " has been added to the collection!" + "\n";
    }

    public void close() {
        save();
        System.exit(0);
    }

    public void setFile(File file) {
        this.file = file;
    }

}
