package commands;

import utility.CollectionManager;

public class Exit extends Command {
    @Override
    public String execute(CollectionManager cm) {
        return cm.exit();
    }
}
