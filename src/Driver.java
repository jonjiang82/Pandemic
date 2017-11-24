import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) throws FileNotFoundException{
        Scanner in = new Scanner (System.in);
        System.out.println("Select an option:");
        System.out.println("1. Player 1");
        System.out.println("2. Player 2+");
        System.out.print("> ");
        String option = in.nextLine();
        if (option.equals("1")) {
        	Pandemic.instance = new Pandemic(4, 4);
        } else if (option.equals("2")) {
        	System.out.println("You are player...?")
        	int playNum = Integer.parseInt(in.nextLine());
        	Pandemic.instance = new Pandemic(4, 4);
        }
    }

}
