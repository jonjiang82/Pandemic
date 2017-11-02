import java.io.FileNotFoundException;
import java.util.ArrayList;
public class Tester {
	Pandemic game;
	
	public Tester(int numPlayers, int numEpidemics) throws FileNotFoundException{
		game = new Pandemic(numPlayers, numEpidemics);
	}
	
	public void runTests() {
		System.out.println("testCityList result: " + testCityList(game));
	}
	
	private boolean testCityList(Pandemic game) {
		boolean answer = true;
		ArrayList<String> cityList = game.getCities();
		System.out.print("City List: ");
		for (String c : cityList) {
			System.out.print(c + " ");
		}
		System.out.println();
		if (!cityList.contains("Atlanta") || !cityList.contains("Chicago") ||
			!cityList.contains("Miami") || !cityList.contains("Washington")) {
			answer = false;
		}
		return answer;
	}
}
