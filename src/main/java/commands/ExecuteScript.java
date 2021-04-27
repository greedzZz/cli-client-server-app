package commands;

import utility.CollectionManager;

public class ExecuteScript extends Command {
    @Override
    public String execute(CollectionManager cm) {
        return cm.executeScript();
    }
}
