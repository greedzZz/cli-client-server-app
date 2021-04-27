package utility.auxiliary;

import java.util.Scanner;

public class ClientAsker {

    public int ask() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        System.out.println("Try to connect again? (\"Yes\"/\"No\")");
        while (scanner.hasNextLine()) {
            answer = scanner.nextLine();
            if (answer.equals("Yes") || answer.equals("yes")) {
                return 1;
            } else if (answer.equals("No") || answer.equals("no")) {
                return 0;
            } else {
                System.out.println("Please, write \"yes\" or \"no\".");
            }
        }
        return -1;
    }
}
