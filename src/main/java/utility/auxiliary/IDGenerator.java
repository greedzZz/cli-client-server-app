package utility.auxiliary;

import java.util.HashSet;

/**
 * A class that generates unique id for the SpaceMarine objects.
 */
public class IDGenerator {
    private final HashSet<Integer> idSet;
    private boolean isFilled;
    private int count;

    public IDGenerator() {
        this.idSet = new HashSet<>();
        count = 0;
    }

    public int generateID() throws Exception {
        if (!isFilled) {
            int soughtID = 1;
            while (soughtID != Integer.MAX_VALUE) {
                if (!idSet.contains(soughtID)) {
                    break;
                }
                soughtID += 1;
            }
            if (soughtID == Integer.MAX_VALUE) {
                isFilled = true;
                throw new Exception("Unfortunately, the collection is overflowing. \n" +
                        "To add a new element to it, you need to delete one of the previously added ones.");
            } else {
                return soughtID;
            }
        } else {
            throw new Exception("Unfortunately, the collection is overflowing. \n" +
                    "To add a new element to it, you need to delete one of the previously added ones.");
        }
    }

    public void addID(Integer id) {
        idSet.add(id);
        count += 1;
        if (count == Integer.MAX_VALUE) {
            isFilled = true;
        }
    }

    public void removeID(Integer id) {
        idSet.remove(id);
        count -= 1;
        if (count != Integer.MAX_VALUE) {
            isFilled = false;
        }
    }

    public void clearSet() {
        idSet.clear();
    }
}
