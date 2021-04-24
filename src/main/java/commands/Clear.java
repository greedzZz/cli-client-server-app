package commands;

import utility.CollectionManager;

public class Clear extends Command {
    @Override
    public void execute(CollectionManager cm) {
        cm.clear();
    }
}
