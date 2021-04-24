package commands;

import utility.CollectionManager;

public class ExecuteScript extends Command {
    @Override
    public void execute(CollectionManager cm) {
        cm.executeScript();
    }
}
