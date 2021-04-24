package commands;

import content.SpaceMarine;
import utility.CollectionManager;

public class Insert extends Command {
    public Insert(Integer intArg, SpaceMarine smArg) {
        this.intArg = intArg;
        this.smArg = smArg;
    }

    @Override
    public void execute(CollectionManager cm) {
        cm.insert(intArg, smArg);
    }
}
