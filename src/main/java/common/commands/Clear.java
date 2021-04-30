package common.commands;

import server.CollectionManager;

public class Clear extends Command {
    @Override
    public String execute(CollectionManager cm) {
        return cm.clear();
    }
}
