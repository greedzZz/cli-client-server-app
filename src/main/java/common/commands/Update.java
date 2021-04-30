package common.commands;

import common.content.SpaceMarine;
import server.CollectionManager;

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
