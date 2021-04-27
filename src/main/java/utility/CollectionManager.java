package utility;

import content.*;
import utility.auxiliary.*;
import utility.parsing.ObjectToXMLParser;

import java.io.File;
import java.util.*;

/**
 * Class that stores and interacts with the collection.
 */
public class CollectionManager {
    //private final IDGenerator idGenerator;
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
        //this.idGenerator = new IDGenerator();
    }

    public String help() {
        StringBuilder sb = new StringBuilder();
        for (String com : commandPool.keySet()) {
            sb.append(com).append(": ").append(commandPool.get(com)).append("\n");
        }
        return sb.toString();
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
            StringBuilder sb = new StringBuilder();
            sb.append("Elements of the collection:\n");
            for (SpaceMarine spaceMarine : treeMap.values()) {
                sb.append(smd.describe(spaceMarine)).append("\n\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String insert(Integer key, SpaceMarine sm) {
        try {
            sm.setID(key);
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
                //idGenerator.removeID(key);
                return "Element with " + key + " key has been deleted.\n";
            }
        } catch (Exception e) {
            return e.getMessage() + "\n";
        }
    }

    public String clear() {
        treeMap.clear();
        //idGenerator.clearSet();
        return "The collection has been cleared.\n";
    }

    public String save() {
        ObjectToXMLParser parser = new ObjectToXMLParser(file);
        return parser.parse(treeMap);
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
                        //idGenerator.removeID(next.getID());
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
                        //idGenerator.removeID(currentKey);
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
            StringBuilder sb = new StringBuilder();
            sb.append("Elements whose chapter value is equal to entered value:").append("\n");
            int count = 0;
            for (SpaceMarine sm : treeMap.values()) {
                if (sm.getChapterName().equals(chapterName) && sm.getChapterWorld().equals(chapterWorld)) {
                    sb.append(smd.describe(sm)).append("\n\n");
                    count += 1;
                }
            }
            if (count == 0) {
                sb.append("There are no elements whose chapter value is equal to entered value.");
            }
            return sb.toString();
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
            int count = 0;
            StringBuilder sb = new StringBuilder();
            sb.append("Elements whose starts with entered value:").append("\n");
            for (SpaceMarine sm : treeMap.values()) {
                if (sm.getName().startsWith(name)) {
                    sb.append(smd.describe(sm)).append("\n\n");
                    count += 1;
                }
            }
            if (count == 0) {
                sb.append("There are no elements whose starts with entered value.\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return e.getMessage() + "\n";
        }
    }

    public String put(SpaceMarine sm) {
        treeMap.put(sm.getID(), sm);
        //idGenerator.addID(sm.getID());
        return "Space marine " + sm.getName() + " has been added to the collection!" + "\n";
    }

//    public void putWithGeneration(SpaceMarine sm) throws Exception {
//        sm.setID(idGenerator.generateID());
//        treeMap.put(sm.getID(), sm);
//        System.out.println("Space marine " + sm.getName() + " has been added to the collection!");
//        idGenerator.addID(sm.getID());
//    }

    public void setFile(File file) {
        this.file = file;
    }

}
