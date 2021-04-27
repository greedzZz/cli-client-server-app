package commands;

import content.SpaceMarine;
import utility.CollectionManager;

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
