package common.commands;

import server.CollectionManager;

public class Help extends Command {
    @Override
    public String execute(CollectionManager cm) {
        return cm.help();
    }
}
