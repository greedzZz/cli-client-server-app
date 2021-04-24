package commands;

import utility.CollectionManager;

public class Show extends Command {
    @Override
    public void execute(CollectionManager cm) {
        cm.show();
    }
}
