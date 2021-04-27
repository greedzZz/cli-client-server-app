package commands;

import content.Chapter;
import utility.CollectionManager;

public class FilterByChapter extends Command {
    public FilterByChapter(Chapter chapArg) {
        this.chapArg = chapArg;
    }

    @Override
    public String execute(CollectionManager cm) {
        return cm.filterByChapter(chapArg);
    }
}
