package commands;

import content.Chapter;
import content.SpaceMarine;
import utility.CollectionManager;

import java.io.Serializable;

public abstract class Command implements Serializable {
    protected String strArg;
    protected Integer intArg;
    protected SpaceMarine smArg;
    protected Chapter chapArg;

    public abstract String execute(CollectionManager cm);
}
