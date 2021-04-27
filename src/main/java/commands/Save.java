package commands;

import utility.CollectionManager;

public class Save extends Command {
    @Override
    public String execute(CollectionManager cm) {
        return cm.save();
    }
}
