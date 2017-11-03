import java.io.FileNotFoundException;
import java.util.ArrayList;
public class Tester {
	public Tester(int numPlayers, int numEpidemics) throws FileNotFoundException{
		Pandemic.instance = new Pandemic(numPlayers, numEpidemics);
	}
	
	public void runTests() {
		System.out.println("testCityList result: " + testCityList());
	}
	
	private boolean testCityList() {
		boolean answer = true;
		ArrayList<String> cityList = Pandemic.instance.getCityNames();
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
