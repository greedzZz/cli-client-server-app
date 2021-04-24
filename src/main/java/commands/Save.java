package commands;

import utility.CollectionManager;

public class Save extends Command {
    @Override
    public void execute(CollectionManager cm) {
        cm.save();
    }
}
