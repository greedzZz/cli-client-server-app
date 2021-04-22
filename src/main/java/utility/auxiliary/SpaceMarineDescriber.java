package utility.auxiliary;

import content.SpaceMarine;

/**
 * A class that displays the value of an element in string representation.
 */
public class SpaceMarineDescriber {
    public void describe(SpaceMarine sm) {
        System.out.println("id: " + sm.getID() + "\n" +
                "name: " + sm.getName() + "\n" +
                "coordinate X: " + sm.getCoordinateX() + "\n" +
                "coordinate Y: " + sm.getCoordinateY() + "\n" +
                "creation date: " + sm.getCreationDate() + "\n" +
                "health: " + sm.getHealth() + "\n" +
                "Astartes category: " + sm.getCategory() + "\n" +
                "Weapon: " + sm.getWeaponType() + "\n" +
                "Melee Weapon: " + sm.getMeleeWeapon() + "\n" +
                "Chapter name: " + sm.getChapterName() + "\n" +
                "Chapter world: " + sm.getChapterWorld());
    }
}
