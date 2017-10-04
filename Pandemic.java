import java.util.*;

public class Pandemic{
	private ArrayList<String> cityList; //A list of strings in alphabetical order, where each string is the name of a city.

	private HashMap<String, City> map;
	private ArrayList<PlayerCard> playerDeck;
	private ArrayList<PlayerCity> infectionDeck;
	private int infectionCounter; //The location of where the counter is, from 1 to 7
	private int infectionRate; //The actual infection rate
	private int outbreaks; //Number of outbreaks. Game is lost at 8 outbreaks
	private int researchCenters; //Number of research centers not yet placed. Initializes to 6.
	private HashMap<Disease.Type, Disease> diseases;

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
		outbreakse = 0;
		researchCenters = 6

		//Initialize Diseases
		diseases = new HashMap<Disease.Type, Disease>();
		Disease.Type[] diseaseTypes = Disease.Type.values();
		for (int i = 0, len = diseaseTypes.length; i < len; i++) {
			diseases.put(diseaseTypes[i], new Disease(diseaseTypes[i]));
		}

		//Initialize Cities
		map.put("Atlanta", new City(Disease.Type.BLUE));
		map.get("Atlanta").addConnectedCity("Chicago");
		map.get("Atlanta").addConnectedCity("Washington");
		map.get("Atlanta").addConnectedCity("Miami");

		map.put("Chicago", new City(Disease.Type.BLUE));
		map.get("Chicago").addConnectedCity("Atlanta");
		// map.get("Chicago").addConnectedCity("Montreal");
		// map.get("Chicago").addConnectedCity("Mexico City");
		// map.get("Chicago").addConnectedCity("Los Angeles");
		// map.get("Chicago").addConnectedCity("San Francisco");

		map.put("Washington", new City(Disease.Type.BLUE));
		// map.get("Washington").addConnectedCity("Montreal");
		map.get("Washington").addConnectedCity("Atlanta");
		// map.get("Washington").addConnectedCity("New York");
		map.get("Washington").addConnectedCity("Miami");

		map.put("Miami", new City(Disease.Type.YELLOW));
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

	int debugGetInfectionCounter() {
		return infectionCounter;
	}

	void debugSetInfectionCounter(int infectionCounter) {
		this.infectionCounter = infectionCounter;
	}

	int debugGetInfectionRate() {
		return infectionRate;
	}

	void debugSetInfectionRate(int infectionRate) {
		this.infectionRate = infectionRate;
	}

	int debugGetOutbreaks() {
		return outbreaks;
	}

	void debugSetOutbreaks(int outbreaks) {
		this.outbreaks = outbreaks;
	}

	int debugGetResearchCenters() {
		return researchCenters;
	}

	void debugSetResearchCenters(int researchCenters) {
		this.researchCenters = researchCenters;
	}
	
	ArrayList<String> debugGetCityList() {
		return cityList;
	}
}
