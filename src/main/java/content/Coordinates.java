package content;

import java.io.Serializable;

/**
 * Class coordinates.
 */
public class Coordinates implements Serializable {
    private int x;
    private Integer y; //Максимальное значение поля: 941, Поле не может быть null

    public Coordinates(Integer x, Integer y) throws IllegalArgumentException {
        if (y == null || y > 941) {
            throw new IllegalArgumentException("Coordinate Y cannot be empty.\n" +
                    "Max value: 941.");
        } else if (x == null) {
            throw new IllegalArgumentException("Coordinate X cannot be empty.");
        } else {
            this.x = x;
            this.y = y;
        }
    }

    public int getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

}
