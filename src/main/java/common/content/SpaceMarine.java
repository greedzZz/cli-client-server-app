package common.content;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Class SpaceMarine.
 * Objects of this particular class are stored in the main collection of the program.
 */
public class SpaceMarine implements Comparable<SpaceMarine>, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer health; //Поле может быть null, Значение поля должно быть больше 0
    private AstartesCategory category; //Поле может быть null
    private Weapon weaponType; //Поле может быть null
    private MeleeWeapon meleeWeapon; //Поле может быть null
    private Chapter chapter; //Поле не может быть null

    public SpaceMarine(String name,
                       Coordinates coordinates,
                       Integer health,
                       AstartesCategory category,
                       Weapon weaponType,
                       MeleeWeapon meleeWeapon,
                       Chapter chapter) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be empty word.");
        } else if (health != null && health <= 0) {
            throw new IllegalArgumentException("Health value must be greater than 0.");
        }
        try {
            this.name = name;
            this.coordinates = coordinates;
            this.health = health;
            this.category = category;
            this.weaponType = weaponType;
            this.meleeWeapon = meleeWeapon;
            this.chapter = chapter;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public SpaceMarine(Integer id,
                       String name,
                       Coordinates coordinates,
                       CharSequence creationDate,
                       Integer health,
                       AstartesCategory category,
                       Weapon weaponType,
                       MeleeWeapon meleeWeapon,
                       Chapter chapter) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be empty word.");
        } else if (health != null && health <= 0) {
            throw new IllegalArgumentException("Health value must be greater than 0.");
        } else if (id == null) {
            throw new IllegalArgumentException("ID cannot be empty word.");
        } else if (id <= 0) {
            throw new IllegalArgumentException("ID value must be greater than 0");
        } else if (creationDate == null) {
            throw new IllegalArgumentException("Creation date cannot be empty word.");
        }
        try {
            LocalDateTime.parse(creationDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Creation date must be written according to ISO-8601 calendar system, such as 2007-12-03T10:15:30.");
        }
        try {
            this.id = id;
            this.name = name;
            this.coordinates = coordinates;
            this.creationDate = LocalDateTime.parse(creationDate);
            this.health = health;
            this.category = category;
            this.weaponType = weaponType;
            this.meleeWeapon = meleeWeapon;
            this.chapter = chapter;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int compareTo(SpaceMarine sm) {
        return name.compareTo(sm.getName());
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getCoordinateX() {
        return getCoordinates().getX();
    }

    public Integer getCoordinateY() {
        return getCoordinates().getY();
    }

    public String getCreationDate() {
        return creationDate.toString();
    }

    public void setCreationDate() {
        this.creationDate = LocalDateTime.now();
    }

    public Integer getHealth() {
        return this.health;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public common.content.Chapter getChapter() {
        return chapter;
    }

    public String getChapterName() {
        return chapter.getName();
    }

    public String getChapterWorld() {
        return chapter.getWorld();
    }

}
