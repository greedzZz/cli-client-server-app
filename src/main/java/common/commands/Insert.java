package common.commands;

import common.content.SpaceMarine;
import server.CollectionManager;

public class Insert extends Command {
    public Insert(Integer intArg, SpaceMarine smArg) {
        this.intArg = intArg;
        this.smArg = smArg;
    }

    @Override
    public String execute(CollectionManager cm) {
        return cm.insert(intArg, smArg);
    }
}
