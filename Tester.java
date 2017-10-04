import java.util.ArrayList;
public class Tester {
	Pandemic game;
	
	public Tester(int difficulty) {
		game = new Pandemic(difficulty);
	}
	
	public void runTests() {
		System.out.println("testCityList result: " + testCityList(game));
	}
	
	private boolean testCityList(Pandemic game) {
		boolean answer = true;
		ArrayList<String> cityList = game.debugGetCityList();
		if (!cityList.get(0).equals("Atlanta")) {
			answer = false;
		}
		if (!cityList.get(1).equals("Chicago")) {
			answer = false;
		}
		if (!cityList.get(2).equals("Miami")) {
			answer = false;
		}
		if (!cityList.get(3).equals("Washington")) {
			answer = false;
		}
		return answer;
	}
}
