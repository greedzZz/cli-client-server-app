package server.utility;

import common.content.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import server.CollectionManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Parser from XML to SpaceMarine.
 * Sends SpaceMarine objects from a file to the CollectionManager.
 */
public class FileManager {
    private final File file;

    public FileManager(File file) {
        this.file = file;
    }

    public void manageXML(CollectionManager collectionManager) {
        collectionManager.setFile(file);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ScannerXML scannerXML = new ScannerXML(file);
            scannerXML.scan();
            Document document = builder.parse(scannerXML.getBufferedIS());
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("SpaceMarine");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                Node nodeID = element.getElementsByTagName("id").item(0).getFirstChild();
                Integer idSM;
                if (nodeID == null) {
                    idSM = null;
                } else {
                    idSM = Integer.parseInt(nodeID.getNodeValue());
                }

                Node nodeName = element.getElementsByTagName("name").item(0).getFirstChild();
                String nameSM;
                if (nodeName == null) {
                    nameSM = null;
                } else {
                    nameSM = nodeName.getNodeValue();
                }

                String attrX = (((Element) document.getElementsByTagName("coordinates").item(i))).getAttributeNode("x").getValue();
                Integer xSM;
                if (attrX.equals("")) {
                    xSM = null;
                } else {
                    xSM = Integer.parseInt(attrX);
                }

                String attrY = (((Element) document.getElementsByTagName("coordinates").item(i))).getAttributeNode("y").getValue();
                Integer ySM;
                if (attrY.equals("")) {
                    ySM = null;
                } else {
                    ySM = Integer.parseInt(attrY);
                }

                Node nodeCD = element.getElementsByTagName("creationDate").item(0).getFirstChild();
                CharSequence cdSM;
                if (nodeCD == null) {
                    cdSM = null;
                } else {
                    cdSM = nodeCD.getNodeValue().subSequence(0, nodeCD.getNodeValue().length());
                }


                Node nodeHealth = element.getElementsByTagName("health").item(0).getFirstChild();
                Integer healthSM;
                if (nodeHealth == null) {
                    healthSM = null;
                } else {
                    healthSM = Integer.parseInt(nodeHealth.getNodeValue());
                }

                Node nodeCategory = element.getElementsByTagName("category").item(0).getFirstChild();
                AstartesCategory categorySM;
                if (nodeCategory == null) {
                    categorySM = null;
                } else {
                    categorySM = AstartesCategory.valueOf(nodeCategory.getNodeValue());
                }

                Node nodeWeapon = element.getElementsByTagName("weaponType").item(0).getFirstChild();
                Weapon weaponSM;
                if (nodeWeapon == null) {
                    weaponSM = null;
                } else {
                    weaponSM = Weapon.valueOf(nodeWeapon.getNodeValue());
                }

                Node nodeMelee = element.getElementsByTagName("meleeWeapon").item(0).getFirstChild();
                MeleeWeapon meleeWeaponSM;
                if (nodeMelee == null) {
                    meleeWeaponSM = null;
                } else {
                    meleeWeaponSM = MeleeWeapon.valueOf(nodeMelee.getNodeValue());
                }

                String attrChapterName = (((Element) document.getElementsByTagName("chapter").item(i))).getAttributeNode("name").getValue();
                String chapterNameSM;
                if (attrChapterName.equals("")) {
                    chapterNameSM = null;
                } else {
                    chapterNameSM = attrChapterName;
                }

                String attrChapterWorld = (((Element) document.getElementsByTagName("chapter").item(i))).getAttributeNode("world").getValue();
                String chapterWorldSM;
                if (attrChapterWorld.equals("")) {
                    chapterWorldSM = null;
                } else {
                    chapterWorldSM = attrChapterWorld;
                }

                SpaceMarine sm = new SpaceMarine(idSM, nameSM, new Coordinates(xSM, ySM), cdSM, healthSM, categorySM, weaponSM, meleeWeaponSM, new Chapter(chapterNameSM, chapterWorldSM));
                collectionManager.put(sm);
            }
        } catch (FileNotFoundException f) {
            System.out.println(f.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Incorrect data file common.content.\n" +
                    "Further filling of the collection from this source is impossible.\n" +
                    "Continue to fill in the collection manually or restart the program specifying the correct file.");
        }
    }
}
