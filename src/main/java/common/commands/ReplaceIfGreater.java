package common.commands;

import common.content.SpaceMarine;
import server.CollectionManager;

public class ReplaceIfGreater extends Command {
    public ReplaceIfGreater(Integer intArg, SpaceMarine smArg) {
        this.intArg = intArg;
        this.smArg = smArg;
    }

    @Override
    public String execute(CollectionManager cm) {
        return cm.replaceIfGreater(intArg, smArg);
    }
}
