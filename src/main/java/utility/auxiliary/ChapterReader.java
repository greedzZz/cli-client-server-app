package utility.auxiliary;

import content.Chapter;

import java.util.Scanner;

/**
 * A class that reads Chapter values from input.
 */
public class ChapterReader {
    private boolean fromFile = false;

    public Chapter readChapter(Scanner sc) throws IllegalArgumentException {
        String[] fields = new String[2];
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
            fields[0] = argument;

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
            fields[1] = argument;
        } else {
            String argument;
            argument = sc.nextLine().trim();
            if (argument.equals("")) {
                throw new IllegalArgumentException("Chapter name cannot be empty word.");
            }
            fields[0] = argument;

            argument = sc.nextLine().trim();
            if (argument.equals("")) {
                throw new IllegalArgumentException("Chapter world cannot be empty word.");
            }
            fields[1] = argument;
        }
        fromFile = false;
        return new Chapter(fields[0], fields[1]);
    }

    public void setFromFile(boolean fromFile) {
        this.fromFile = fromFile;
    }
}
