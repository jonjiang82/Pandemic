import java.util.Scanner;
public class Driver{
	public static void main(String[] args){
		Scanner in = new Scanner (System.in);
		System.out.println("Select an option:");
		System.out.println("1. Run tests");
		String option = in.nextLine();
		if (option.equals("1")) {
			System.out.println("Select difficulty (4-6):");
			int difficulty = Integer.parseInt(in.nextLine());
			Tester tester = new Tester(difficulty);
			tester.runTests();
		}
	}
}