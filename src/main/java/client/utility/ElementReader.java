package client.utility;

import common.content.*;

import java.util.Scanner;

/**
 * A class that reads SpaceMarine objects from input.
 */
public class ElementReader {
    private boolean fromFile = false;

    public SpaceMarine readElement(Scanner sc) throws IllegalArgumentException {
        if (!fromFile) {
            String argument = "";
            boolean isCorrect = false;
            while (!isCorrect) {
                System.out.println("Please, enter a space marine name.");
                argument = sc.nextLine().trim();
                if (argument.equals("")) {
                    System.out.println("Name cannot be empty word.");
                } else {
                    isCorrect = true;
                }
            }
            String nameSM = argument;

            isCorrect = false;
            while (!isCorrect) {
                System.out.println("Please, enter a coordinate X");
                argument = sc.nextLine().trim();
                try {
                    Integer.parseInt(argument);
                    isCorrect = true;
                } catch (NumberFormatException e) {
                    System.out.println("Coordinate X value must be integer.");
                }
            }
            int xSM = Integer.parseInt(argument);

            isCorrect = false;
            while (!isCorrect) {
                System.out.println("Please, enter a coordinate Y");
                argument = sc.nextLine().trim();
                try {
                    Integer y = Integer.parseInt(argument);
                    if (y > 941) {
                        throw new IllegalArgumentException("Coordinate Y max value: 941.");
                    }
                    isCorrect = true;
                } catch (NumberFormatException e) {
                    System.out.println("Coordinate Y value must be integer.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            Integer ySM = Integer.parseInt(argument);

            isCorrect = false;
            while (!isCorrect) {
                System.out.println("Please, enter a health value.");
                argument = sc.nextLine().trim();
                if (argument.equals("")) {
                    isCorrect = true;
                } else {
                    try {
                        Integer health = Integer.parseInt(argument);
                        if (health < 0) {
                            throw new IllegalArgumentException("Health value must be greater than 0.");
                        }
                        isCorrect = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Health value must be integer or empty word.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            Integer healthSM;
            if (argument.equals("")) {
                healthSM = null;
            } else {
                healthSM = Integer.parseInt(argument);
            }

            isCorrect = false;
            while (!isCorrect) {
                System.out.println("Please, enter an Astartes category. (Possible values: ASSAULT, TACTICAL, CHAPLAIN. Or empty word.)");
                argument = sc.nextLine().trim();
                if (argument.equals("")) {
                    isCorrect = true;
                } else {
                    try {
                        AstartesCategory.valueOf(argument);
                        isCorrect = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Possible values: ASSAULT, TACTICAL, CHAPLAIN. Or empty word.");
                    }
                }
            }
            AstartesCategory acSM;
            if (argument.equals("")) {
                acSM = null;
            } else {
                acSM = AstartesCategory.valueOf(argument);
            }

            isCorrect = false;
            while (!isCorrect) {
                System.out.println("Please, enter an Weapon type. (Possible values: BOLTGUN, PLASMA_GUN, COMBI_PLASMA_GUN, GRENADE_LAUNCHER. Or empty word.)");
                argument = sc.nextLine().trim();
                if (argument.equals("")) {
                    isCorrect = true;
                } else {
                    try {
                        Weapon.valueOf(argument);
                        isCorrect = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Possible values: BOLTGUN, PLASMA_GUN, COMBI_PLASMA_GUN, GRENADE_LAUNCHER. Or empty word.");
                    }
                }
            }
            Weapon weaponSM;
            if (argument.equals("")) {
                weaponSM = null;
            } else {
                weaponSM = Weapon.valueOf(argument);
            }

            isCorrect = false;
            while (!isCorrect) {
                System.out.println("Please, enter an Melee weapon type. (Possible values: CHAIN_SWORD, CHAIN_AXE, POWER_BLADE, POWER_FIST. Or empty word.)");
                argument = sc.nextLine().trim();
                if (argument.equals("")) {
                    isCorrect = true;
                } else {
                    try {
                        MeleeWeapon.valueOf(argument);
                        isCorrect = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Possible values: CHAIN_SWORD, CHAIN_AXE, POWER_BLADE, POWER_FIST. Or empty word.");
                    }
                }
            }
            MeleeWeapon meleeSM;
            if (argument.equals("")) {
                meleeSM = null;
            } else {
                meleeSM = MeleeWeapon.valueOf(argument);
            }

            isCorrect = false;
            while (!isCorrect) {
                System.out.println("Please, enter a chapter name.");
                argument = sc.nextLine().trim();
                if (argument.equals("")) {
                    System.out.println("Chapter name cannot be empty word.");
                } else {
                    isCorrect = true;
                }
            }
            String cNameSM = argument;

            isCorrect = false;
            while (!isCorrect) {
                System.out.println("Please, enter a chapter world.");
                argument = sc.nextLine().trim();
                if (argument.equals("")) {
                    System.out.println("Chapter world cannot be empty word.");
                } else {
                    isCorrect = true;
                }
            }
            String cWorldSM = argument;

            return new SpaceMarine(nameSM, new Coordinates(xSM, ySM), healthSM, acSM, weaponSM, meleeSM, new Chapter(cNameSM, cWorldSM));
        } else {
            String argument;
            argument = sc.nextLine().trim();
            if (argument.equals("")) {
                throw new IllegalArgumentException("Name cannot be empty word.");
            }
            String nameSM = argument;

            argument = sc.nextLine().trim();
            try {
                Integer.parseInt(argument);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Coordinate X value must be integer.");
            }

            int xSM = Integer.parseInt(argument);

            argument = sc.nextLine().trim();
            try {
                Integer y = Integer.parseInt(argument);
                if (y > 941) {
                    throw new IllegalArgumentException("Coordinate Y max value: 941.");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Coordinate Y value must be integer.");
            }
            Integer ySM = Integer.parseInt(argument);

            argument = sc.nextLine().trim();
            if (!argument.equals("")) {
                try {
                    Integer health = Integer.parseInt(argument);
                    if (health < 0) {
                        throw new IllegalArgumentException("Health value must be greater than 0.");
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Health value must be integer or empty word.");
                }
            }
            Integer healthSM;
            if (argument.equals("")) {
                healthSM = null;
            } else {
                healthSM = Integer.parseInt(argument);
            }

            argument = sc.nextLine().trim();
            if (!argument.equals("")) {
                try {
                    AstartesCategory.valueOf(argument);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Possible values: ASSAULT, TACTICAL, CHAPLAIN. Or empty word.");
                }
            }
            AstartesCategory acSM;
            if (argument.equals("")) {
                acSM = null;
            } else {
                acSM = AstartesCategory.valueOf(argument);
            }

            argument = sc.nextLine().trim();
            if (!argument.equals("")) {
                try {
                    Weapon.valueOf(argument);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Possible values: BOLTGUN, PLASMA_GUN, COMBI_PLASMA_GUN, GRENADE_LAUNCHER. Or empty word.");
                }
            }
            Weapon weaponSM;
            if (argument.equals("")) {
                weaponSM = null;
            } else {
                weaponSM = Weapon.valueOf(argument);
            }

            argument = sc.nextLine().trim();
            if (!argument.equals("")) {
                try {
                    MeleeWeapon.valueOf(argument);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Possible values: CHAIN_SWORD, CHAIN_AXE, POWER_BLADE, POWER_FIST. Or empty word.");
                }
            }
            MeleeWeapon meleeSM;
            if (argument.equals("")) {
                meleeSM = null;
            } else {
                meleeSM = MeleeWeapon.valueOf(argument);
            }

            argument = sc.nextLine().trim();
            if (argument.equals("")) {
                throw new IllegalArgumentException("Chapter name cannot be empty word.");
            }
            String cNameSM = argument;

            argument = sc.nextLine().trim();
            if (argument.equals("")) {
                throw new IllegalArgumentException("Chapter world cannot be empty word.");
            }
            String cWorldSM = argument;
            fromFile = false;
            return new SpaceMarine(nameSM, new Coordinates(xSM, ySM), healthSM, acSM, weaponSM, meleeSM, new Chapter(cNameSM, cWorldSM));
        }
    }

    public void setFromFile(boolean fromFile) {
        this.fromFile = fromFile;
    }
}
