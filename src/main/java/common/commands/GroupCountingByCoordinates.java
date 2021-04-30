package common.commands;

import server.CollectionManager;

public class GroupCountingByCoordinates extends Command {
    @Override
    public String execute(CollectionManager cm) {
        return cm.groupCountingByCoordinates();
    }
}
