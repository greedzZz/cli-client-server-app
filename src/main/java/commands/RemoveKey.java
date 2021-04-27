package commands;

import utility.CollectionManager;

public class RemoveKey extends Command {
    public RemoveKey(Integer intArg) {
        this.intArg = intArg;
    }

    @Override
    public String execute(CollectionManager cm) {
        return cm.removeKey(intArg);
    }
}
