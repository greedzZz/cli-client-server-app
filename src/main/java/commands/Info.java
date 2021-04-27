package commands;

import utility.CollectionManager;

public class Info extends Command {
    @Override
    public String execute(CollectionManager cm) {
        return cm.info();
    }
}
