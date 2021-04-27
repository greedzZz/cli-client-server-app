package utility.parsing;

import content.SpaceMarine;

import java.io.*;
import java.util.TreeMap;

/**
 * Parser from SpaceMarine to XML.
 * Saves the values of the collection to a file.
 */
public class ObjectToXMLParser {
    private final File file;

    public ObjectToXMLParser(File file) {
        this.file = file;
    }

    public String parse(TreeMap<Integer, SpaceMarine> treeMap) {
        try (FileOutputStream fos = new FileOutputStream(file, false);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            bos.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes(), 0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes().length);
            bos.write("<Marines>\n".getBytes(), 0, "<Marines>\n".getBytes().length);
            for (SpaceMarine sm : treeMap.values()) {
                bos.write("\t<SpaceMarine>\n".getBytes(), 0, "\t<SpaceMarine>\n".getBytes().length);

                bos.write("\t\t<id>".getBytes(), 0, "\t\t<id>".getBytes().length);
                bos.write(((Integer) sm.getID()).toString().getBytes(), 0, ((Integer) sm.getID()).toString().getBytes().length);
                bos.write("</id>\n".getBytes(), 0, "</id>\n".getBytes().length);

                bos.write("\t\t<name>".getBytes(), 0, "\t\t<name>".getBytes().length);
                bos.write(sm.getName().getBytes(), 0, sm.getName().getBytes().length);
                bos.write("</name>\n".getBytes(), 0, "</name>\n".getBytes().length);

                bos.write("\t\t<coordinates x=\"".getBytes(), 0, "\t\t<coordinates x=\"".getBytes().length);
                bos.write(((Integer) sm.getCoordinateX()).toString().getBytes(), 0, ((Integer) sm.getCoordinateX()).toString().getBytes().length);
                bos.write("\" y=\"".getBytes(), 0, "\" y=\"".getBytes().length);
                bos.write(sm.getCoordinateY().toString().getBytes(), 0, sm.getCoordinateY().toString().getBytes().length);
                bos.write("\"/>\n".getBytes(), 0, "\"/>\n".getBytes().length);

                bos.write("\t\t<creationDate>".getBytes(), 0, "\t\t<creationDate>".getBytes().length);
                bos.write(sm.getCreationDate().getBytes(), 0, sm.getCreationDate().getBytes().length);
                bos.write("</creationDate>\n".getBytes(), 0, "</creationDate>\n".getBytes().length);

                bos.write("\t\t<health>".getBytes(), 0, "\t\t<health>".getBytes().length);
                if (sm.getHealth() == null) {
                    bos.write("".getBytes(), 0, "".getBytes().length);
                } else {
                    bos.write(sm.getHealth().toString().getBytes(), 0, sm.getHealth().toString().getBytes().length);
                }
                bos.write("</health>\n".getBytes(), 0, "</health>\n".getBytes().length);

                bos.write("\t\t<category>".getBytes(), 0, "\t\t<category>".getBytes().length);
                if (sm.getCategory() == null) {
                    bos.write("".getBytes(), 0, "".getBytes().length);
                } else {
                    bos.write(sm.getCategory().toString().getBytes(), 0, sm.getCategory().toString().getBytes().length);
                }
                bos.write("</category>\n".getBytes(), 0, "</category>\n".getBytes().length);

                bos.write("\t\t<weaponType>".getBytes(), 0, "\t\t<weaponType>".getBytes().length);
                if (sm.getWeaponType() == null) {
                    bos.write("".getBytes(), 0, "".getBytes().length);
                } else {
                    bos.write(sm.getWeaponType().toString().getBytes(), 0, sm.getWeaponType().toString().getBytes().length);
                }
                bos.write("</weaponType>\n".getBytes(), 0, "</weaponType>\n".getBytes().length);

                bos.write("\t\t<meleeWeapon>".getBytes(), 0, "\t\t<meleeWeapon>".getBytes().length);
                if (sm.getMeleeWeapon() == null) {
                    bos.write("".getBytes(), 0, "".getBytes().length);
                } else {
                    bos.write(sm.getMeleeWeapon().toString().getBytes(), 0, sm.getMeleeWeapon().toString().getBytes().length);
                }
                bos.write("</meleeWeapon>\n".getBytes(), 0, "</meleeWeapon>\n".getBytes().length);

                bos.write("\t\t<chapter name=\"".getBytes(), 0, "\t\t<chapter name=\"".getBytes().length);
                bos.write(sm.getChapterName().getBytes(), 0, sm.getChapterName().getBytes().length);
                bos.write("\" world=\"".getBytes(), 0, "\" world=\"".getBytes().length);
                bos.write(sm.getChapterWorld().getBytes(), 0, sm.getChapterWorld().getBytes().length);
                bos.write("\"/>\n".getBytes(), 0, "\"/>\n".getBytes().length);

                bos.write("\t</SpaceMarine>\n".getBytes(), 0, "\t</SpaceMarine>\n".getBytes().length);
            }
            bos.write("</Marines>\n".getBytes(), 0, "</Marines>\n".getBytes().length);
            return "Collection data has been successfully saved to file.";
        } catch (IOException e) {
            return "No permission to write to the file";
        }
    }
}
