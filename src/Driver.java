import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) throws FileNotFoundException{
        Scanner in = new Scanner (System.in);
        System.out.println("Select an option:");
        System.out.println("1. Run tests");
        System.out.println("2. Test image display");
        String option = in.nextLine();
        if (option.equals("1")) {
            System.out.print("Select number of players (2-4): ");
            int players = Integer.parseInt(in.nextLine());
            System.out.print("Select difficulty (4-6): ");
            int difficulty = Integer.parseInt(in.nextLine());
            Tester tester = new Tester(players, difficulty);
            tester.runTests();
        } else if (option.equals("2")) {
            new Display().run();
        }
    }

}
