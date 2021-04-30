package common.commands;

import server.CollectionManager;

public class Show extends Command {
    @Override
    public String execute(CollectionManager cm) {
        return cm.show();
    }
}
