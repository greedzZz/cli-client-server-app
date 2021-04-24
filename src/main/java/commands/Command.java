package commands;

import content.*;
import utility.CollectionManager;

public abstract class Command {
    protected String strArg;
    protected Integer intArg;
    protected SpaceMarine smArg;
    protected Chapter chapArg;

    public abstract void execute(CollectionManager cm);
}
