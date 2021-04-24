package commands;

import utility.CollectionManager;

public class Info extends Command {
    @Override
    public void execute(CollectionManager cm) {
        cm.info();
    }
}
