package common.commands;

import common.content.Chapter;
import common.content.SpaceMarine;
import server.CollectionManager;

import java.io.Serializable;

public abstract class Command implements Serializable {
    protected String strArg;
    protected Integer intArg;
    protected SpaceMarine smArg;
    protected Chapter chapArg;

    public abstract String execute(CollectionManager cm);
}
