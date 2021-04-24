package commands;

import utility.CollectionManager;

public class Exit extends Command {
    @Override
    public void execute(CollectionManager cm) {
        cm.exit();
    }
}
