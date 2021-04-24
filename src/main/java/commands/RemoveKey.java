package commands;

import utility.CollectionManager;

public class RemoveKey extends Command {
    public RemoveKey(Integer intArg) {
        this.intArg = intArg;
    }

    @Override
    public void execute(CollectionManager cm) {
        cm.removeKey(intArg);
    }
}
