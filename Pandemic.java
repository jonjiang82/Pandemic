import java.util.HashMap;
import java.util.ArrayList;

public class Pandemic{
	private ArrayList<String> cityList; //A list of strings in alphabetical order, where each string is the name of a city.

	HashMap<String, City> map;
	ArrayList<PlayerCard> playerDeck;
	ArrayList<PlayerCity> infectionDeck;
	int infectionCounter; //The location of where the counter is, from 1 to 7
	int infectionRate; //The actual infection rate
	int outbreaks; //Number of outbreaks. Game is lost at 8 outbreaks
	int researchCenters; //Number of research centers not yet placed. Initializes to 6.
	Disease yellowDisease;
	Disease redDisease;
	Disease blueDisease;
	Disease blackDisease;

	public Pandemic(int difficulty){
		//Initialize List of Cities
		cityList = new ArrayList<String>();
		cityList.add("Atlanta");
		cityList.add("Chicago");
		cityList.add("Miami");
		cityList.add("Washington");

		//Initialize Board
		map = new HashMap<String, City>();
		infectionRate = 2;
		infectionCounter = 1;
		outbreaks = 0;
		researchCenters = 6;

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

		//Initialize Player Deck
		playerDeck = new ArrayList<PlayerCard>();
		for (String cityName : cityList){
			playerDeck.add(new PlayerCity(cityName));
		}

		playerDeck.add(new PlayerEvent(PlayerEventType.AIRLIFT));
		playerDeck.add(new PlayerEvent(PlayerEventType.FORECAST));
		playerDeck.add(new PlayerEvent(PlayerEventType.GOVERNMENT_GRANT));
		playerDeck.add(new PlayerEvent(PlayerEventType.ONE_QUIET_NIGHT));
		playerDeck.add(new PlayerEvent(PlayerEventType.RESILIENT_POPULATION));

		for (int i = 0; i < difficulty; i++){
			playerDeck.add(new PlayerEpidemic());
		}

		//Initialize Infection Deck
		infectionDeck = new ArrayList<PlayerCity>();
		for (String cityName : cityList){
			infectionDeck.add(new PlayerCity(cityName));
		}
	}
}