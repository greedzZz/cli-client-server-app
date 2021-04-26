package content;

import java.io.Serializable;

/**
 * Class Chapter.
 */
public class Chapter implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String world; //Поле не может быть null

    public Chapter(String name, String world) throws Exception {
        if (name == null) {
            throw new Exception("Chapter name cannot be empty word.");
        } else if (world == null) {
            throw new Exception("Chapter name cannot be empty word.");
        } else {
            this.name = name;
            this.world = world;
        }
    }

    public String getName() {
        return name;
    }

    public String getWorld() {
        return world;
    }

}
