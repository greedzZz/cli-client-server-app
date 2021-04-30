package common.commands;

import common.content.Chapter;
import server.CollectionManager;

public class FilterByChapter extends Command {
    public FilterByChapter(Chapter chapArg) {
        this.chapArg = chapArg;
    }

    @Override
    public String execute(CollectionManager cm) {
        return cm.filterByChapter(chapArg);
    }
}
