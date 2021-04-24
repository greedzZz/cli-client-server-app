package commands;

import utility.CollectionManager;

public class GroupCountingByCoordinates extends Command {
    @Override
    public void execute(CollectionManager cm) {
        cm.groupCountingByCoordinates();
    }
}
