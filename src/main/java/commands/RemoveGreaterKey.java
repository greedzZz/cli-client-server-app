package commands;

import utility.CollectionManager;

public class RemoveGreaterKey extends Command {
    public RemoveGreaterKey(Integer intArg) {
        this.intArg = intArg;
    }

    @Override
    public String execute(CollectionManager cm) {
        return cm.removeGreaterKey(intArg);
    }
}
