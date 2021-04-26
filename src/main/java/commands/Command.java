package commands;

import content.*;
import utility.CollectionManager;

import java.io.Serializable;

public abstract class Command implements Serializable {
    protected String strArg;
    protected Integer intArg;
    protected SpaceMarine smArg;
    protected Chapter chapArg;

    public abstract void execute(CollectionManager cm);
}
