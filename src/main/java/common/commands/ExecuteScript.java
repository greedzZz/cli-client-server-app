package common.commands;

import server.CollectionManager;

public class ExecuteScript extends Command {
    @Override
    public String execute(CollectionManager cm) {
        return cm.executeScript();
    }
}
