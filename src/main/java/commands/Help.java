package commands;

import utility.CollectionManager;

public class Help extends Command {
    @Override
    public void execute(CollectionManager cm) {
        cm.help();
    }
}
