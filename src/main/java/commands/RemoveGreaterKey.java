package commands;

import utility.CollectionManager;

public class RemoveGreaterKey extends Command {
    public RemoveGreaterKey(Integer intArg) {
        this.intArg = intArg;
    }

    @Override
    public void execute(CollectionManager cm) {
        cm.removeGreaterKey(intArg);
    }
}
