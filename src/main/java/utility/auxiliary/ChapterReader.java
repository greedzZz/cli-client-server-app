package utility.auxiliary;

import java.util.Scanner;

/**
 * A class that reads Chapter values from input.
 */
public class ChapterReader {
    private boolean fromFile = false;

    public String[] readChapter(Scanner sc) {
        String[] chapter = new String[2];
        if (!fromFile) {
            boolean isCorrect = false;
            String argument = "";
            while (!isCorrect) {
                System.out.println("Please, enter a chapter name.");
                argument = sc.nextLine().trim();
                if (argument.equals("")) {
                    System.out.println("Chapter name cannot be empty word.");
                } else {
                    isCorrect = true;
                }
            }
            chapter[0] = argument;

            isCorrect = false;
            while (!isCorrect) {
                System.out.println("Please, enter a chapter world.");
                argument = sc.nextLine().trim();
                if (argument.equals("")) {
                    System.out.println("Chapter world cannot be empty word.");
                } else {
                    isCorrect = true;
                }
            }
            chapter[1] = argument;
        } else {
            String argument;
            argument = sc.nextLine().trim();
            if (argument.equals("")) {
                throw new IllegalArgumentException("Chapter name cannot be empty word.");
            }
            chapter[0] = argument;

            argument = sc.nextLine().trim();
            if (argument.equals("")) {
                throw new IllegalArgumentException("Chapter world cannot be empty word.");
            }
            chapter[1] = argument;
        }
        fromFile = false;
        return chapter;
    }

    public void setFromFile(boolean fromFile) {
        this.fromFile = fromFile;
    }
}
