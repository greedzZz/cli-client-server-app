package utility.auxiliary;

import utility.CollectionManager;

import java.util.LinkedList;

public class ScriptReader {
    private final CollectionManager collectionManager;
    private final LinkedList<String> otherScripts = new LinkedList<>();

    public ScriptReader(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void readScript(String pathname){

    }

    public void clearScripts() {
        otherScripts.clear();
    }

    public void addScript(String s) {
        otherScripts.add(s);
    }
}
