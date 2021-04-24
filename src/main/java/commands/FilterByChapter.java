package commands;

import content.Chapter;
import utility.CollectionManager;

public class FilterByChapter extends Command {
    public FilterByChapter(Chapter chapArg) {
        this.chapArg = chapArg;
    }

    @Override
    public void execute(CollectionManager cm) {
        cm.filterByChapter(chapArg);
    }
}
