import java.util.HashMap;
public class Pandemic{
	HashMap<String, City> map;
	int infectionRate; //The actual infection rate
	int infectionCounter; //The location of where the counter is, from 1 to 7
	int outbreaks; //Number of outbreaks. Game is lost at 8 outbreaks
	Disease yellowDisease;
	Disease redDisease;
	Disease blueDisease;
	Disease blackDisease;

	public Pandemic(){
		//Initialize Board
		map = new HashMap<String, City>();
		infectionRate = 2;
		infectionCounter = 1;
		outbreaks = 0;

		//Initialize Diseases
		yellowDisease = new Disease(Color.YELLOW);
		redDisease = new Disease(Color.RED);
		blueDisease = new Disease(Color.BLUE);
		blackDisease = new Disease(Color.BLACK);

		//Initialize Cities
		map.put("Atlanta", new City(Color.BLUE));
		map.get("Atlanta").addConnectedCity("Chicago");
		map.get("Atlanta").addConnectedCity("Washington");
		map.get("Atlanta").addConnectedCity("Miami");

		map.put("Chicago", new City(Color.BLUE));
		map.get("Chicago").addConnectedCity("Atlanta");
		// map.get("Chicago").addConnectedCity("Montreal");
		// map.get("Chicago").addConnectedCity("Mexico City");
		// map.get("Chicago").addConnectedCity("Los Angeles");
		// map.get("Chicago").addConnectedCity("San Francisco");

		map.put("Washington", new City(Color.BLUE));
		// map.get("Washington").addConnectedCity("Montreal");
		map.get("Washington").addConnectedCity("Atlanta");
		// map.get("Washington").addConnectedCity("New York");
		map.get("Washington").addConnectedCity("Miami");

		map.put("Miami", new City(Color.YELLOW));
		map.get("Miami").addConnectedCity("Atlanta");
		map.get("Miami").addConnectedCity("Washington");
		// map.get("Miami").addConnectedCity("Mexico City");
		// map.get("Miami").addConnectedCity("Bogota");
	}
}