package common.commands;

import common.content.SpaceMarine;
import server.CollectionManager;

public class RemoveGreater extends Command {
    public RemoveGreater(SpaceMarine smArg) {
        this.smArg = smArg;
    }

    @Override
    public String execute(CollectionManager cm) {
        return cm.removeGreater(smArg);
    }
}
