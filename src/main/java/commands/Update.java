package commands;

import content.SpaceMarine;
import utility.CollectionManager;

public class Update extends Command {
    public Update(Integer intArg, SpaceMarine smArg) {
        this.intArg = intArg;
        this.smArg = smArg;
    }

    @Override
    public String execute(CollectionManager cm) {
        return cm.update(intArg, smArg);
    }
}
