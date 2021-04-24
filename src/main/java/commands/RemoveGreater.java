package commands;

import content.SpaceMarine;
import utility.CollectionManager;

public class RemoveGreater extends Command {
    public RemoveGreater(SpaceMarine smArg) {
        this.smArg = smArg;
    }

    @Override
    public void execute(CollectionManager cm) {
        cm.removeGreater(smArg);
    }
}
