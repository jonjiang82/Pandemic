import java.util.*;

public class Pandemic {

	public static Pandemic instance; // when creating a new Pandemic, remember to set this static instance

	private HashMap<String, City> map;
	private ArrayList<PlayerCard> playerDeck;
	private ArrayList<PlayerCity> infectionDeck;
	private int infectionCounter; //The location of where the counter is, from 1 to 7
	private int infectionRate; //The actual infection rate
	private int outbreaks; //Number of outbreaks. Game is lost at 8 outbreaks
	private int researchStationsLeft; //Number of research station not yet placed. Initializes to 6.
	private HashMap<Disease.Type, Disease> diseases;
	private ArrayList<Player> players;
	private long seed;
	private Random rng;

	public Pandemic(int numPlayers, int numEpidemics) {
		rng = new Random();
	
		//Initialize Board
		infectionRate = 2;
		infectionCounter = 1;
		outbreaks = 0;
		researchStationsLeft = 6;

		//Initialize Diseases
		diseases = new HashMap<Disease.Type, Disease>();
		for (Disease.Type t : Disease.Type.values()) {
			diseases.put(t, new Disease(t));
		}

		initializeMap();
		initializePlayerDeck(numEpidemics);
		//Initialize Infection Deck
		infectionDeck = new ArrayList<PlayerCity>();
		for (City city : getCities()) {
			infectionDeck.add(new PlayerCity(city));
		}

		players = new ArrayList<Player>();
		for (int i = 0; i < numPlayers; i++) {
			players.add(new Player(map.get("Atlanta")));
		}
	}

	private void initializeMap() {
		map = new HashMap<String, City>();

		//hardcoded test cities - will be replaced with data importation

		// create all cities first
		map.put("Atlanta", new City("Atlanta", Disease.Type.BLUE));
		map.put("Chicago", new City("Chicago", Disease.Type.BLUE));
		map.put("Washington", new City("Washington", Disease.Type.BLUE));
		map.put("Miami", new City("Miami", Disease.Type.YELLOW));

		//then add all connected cities
		map.get("Atlanta").addConnectedCity(map.get("Chicago"));
		map.get("Atlanta").addConnectedCity(map.get("Washington"));
		map.get("Atlanta").addConnectedCity(map.get("Miami"));

		map.get("Chicago").addConnectedCity(map.get("Atlanta"));
		// map.get("Chicago").addConnectedCity(map.get("Montreal"));
		// map.get("Chicago").addConnectedCity(map.get("Mexico City"));
		// map.get("Chicago").addConnectedCity(map.get("Los Angeles"));
		// map.get("Chicago").addConnectedCity(map.get("San Francisco"));

		// map.get("Washington").addConnectedCity(map.get("Montreal"));
		map.get("Washington").addConnectedCity(map.get("Atlanta"));
		// map.get("Washington").addConnectedCity(map.get("New York"));
		map.get("Washington").addConnectedCity(map.get("Miami"));

		map.get("Miami").addConnectedCity(map.get("Atlanta"));
		map.get("Miami").addConnectedCity(map.get("Washington"));
		// map.get("Miami").addConnectedCity(map.get("Mexico City"));
		// map.get("Miami").addConnectedCity(map.get("Bogota"));
	}

	private void initializePlayerDeck(int numEpidemics) {
		playerDeck = new ArrayList<PlayerCard>();
		for (City city : getCities()) {
			playerDeck.add(new PlayerCity(city));
		}

		//Event Cards
		playerDeck.add(new PlayerEvent(PlayerEventType.AIRLIFT));
		playerDeck.add(new PlayerEvent(PlayerEventType.FORECAST));
		playerDeck.add(new PlayerEvent(PlayerEventType.GOVERNMENT_GRANT));
		playerDeck.add(new PlayerEvent(PlayerEventType.ONE_QUIET_NIGHT));
		playerDeck.add(new PlayerEvent(PlayerEventType.RESILIENT_POPULATION));

		shuffle(playerDeck);

		//TODO: put epidemic cards in evened out places
		for (int i = 0; i < numEpidemics; i++) {
			playerDeck.add(new PlayerEpidemic());
		}
	}

	public void shuffle (ArrayList<PlayerCard> deck){
		int nextCardIndex = 0;
		int cardsToShuffle = deck.size();
		int cardsShuffled = 0;
		while (cardsToShuffle > 0){
			nextCardIndex = rng.nextInt() % cardsToShuffle;
			if (nextCardIndex < 0) nextCardIndex = nextCardIndex*(-1);
			PlayerCard cardRemoved = deck.remove(nextCardIndex + cardsShuffled);
			deck.add(0, cardRemoved);
			cardsShuffled++;
			cardsToShuffle--;
		}
	}

	public City getCity(String cityName) {
		return map.get(cityName);
	}

	public Disease getDisease(Disease.Type diseaseType) {
		return diseases.get(diseaseType);
	}

	public int getResearchStationsLeft() {
		return researchStationsLeft;
	}

	public void setResearchStationsLeft(int researchStationsLeft) {
		this.researchStationsLeft = researchStationsLeft;
	}

	public void adjustResearchStationsLeft(int adjustment) {
		this.researchStationsLeft += adjustment;
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

	int debugGetResearchStationsLeft() {
		return researchStationsLeft;
	}

	void debugSetResearchStationsLeft(int researchStations) {
		this.researchStationsLeft = researchStations;
	}

	// returns ArrayList of cities
	public ArrayList<City> getCities() {
		return new ArrayList<City>(map.values());
	}
	
	public void setSeed(long seed){
		this.seed = seed;
		rng.setSeed(seed);
	}
	
	long debugGetSeed(){
		return seed;
	}
}
