import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import org.json.*;

public class Pandemic {
	public static Pandemic instance; // when creating a new Pandemic, remember to set this static instance

	public Display GameWindow;

	private HashMap<String, City> map;
	private ArrayList<PlayerCard> playerDeck;
	private ArrayList<PlayerCity> infectionDeck;
	private int infectionCounter; //The location of where the counter is, from 1 to 7
	private int infectionRate; //The actual infection rate
	private int outbreaks; //Number of outbreaks. Game is lost at 8 outbreaks
	private int researchStationsLeft; //Number of research station not yet placed. Initializes to 6.
	private HashMap<Disease.Type, Disease> diseases;
	private ArrayList<Player> players;
	private int numPlayers;
	private long seed;
	private Random rng;

	private ArrayList<Image> selectables;
	private Image selectedObject = null;
	private float selectedObjectOffsetX;
	private float selectedObjectOffsetY;

	private Image circle;

	public Pandemic(int numPlayers, int numEpidemics) throws FileNotFoundException{
		InitGame(numPlayers, numEpidemics);

		GameWindow = new Display(this);
		GameWindow.addImage(new Image("./res/pandemicmap.png", 0, 0, 1016, 720));
		selectables = new ArrayList<>();

		Image playerImage1 = new Image("./res/1.png", 320, 180, 64, 64);
		Image playerImage2 = new Image("./res/2.png", 320, 540, 64, 64);
		Image playerImage3 = new Image("./res/3.png", 960, 180, 64, 64);
		Image playerImage4 = new Image("./res/4.png", 960, 540, 64, 64);
		GameWindow.addImage(playerImage1);
		GameWindow.addImage(playerImage2);
		GameWindow.addImage(playerImage3);
		GameWindow.addImage(playerImage4);
		selectables.add(playerImage1);
		selectables.add(playerImage2);
		selectables.add(playerImage3);
		selectables.add(playerImage4);

		circle = new Image("./res/circle.png", 0, 0, 18, 18);
		GameWindow.addImage(circle);
		circle.visible = false;

		GameWindow.run();
	}

	public void InitGame(int numPlayers, int numEpidemics) throws FileNotFoundException {
		rng = new Random();
		this.numPlayers = numPlayers;

		//Initialize Board
		infectionRate = 2;
		infectionCounter = 1;
		outbreaks = 0;
		researchStationsLeft = 6;

		//Initialize Diseases
		diseases = new HashMap<>();
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

	public void Update() {
		// selecting image to drag
		if (GameWindow.GetLeftMouseDown()) {
			for (Image img : selectables) {
				if (GameWindow.GetMouseOver(img)) {
					selectedObject = img;
					selectedObjectOffsetX = (float)GameWindow.GetMouseX() - selectedObject.x;
					selectedObjectOffsetY = (float)GameWindow.GetMouseY() - selectedObject.y;
					break;
				}
			}
		}

		// unselecting image
		if (GameWindow.GetLeftMouseUp()) {
			selectedObject = null;
		}

		// dragging image
		if (GameWindow.GetLeftMouseHeld() && selectedObject != null) {
			selectedObject.x = (float)GameWindow.GetMouseX() - selectedObjectOffsetX;
			selectedObject.y = (float)GameWindow.GetMouseY() - selectedObjectOffsetY;
		}

		// hovering cities
		boolean overCity = false;
		ArrayList<City> cities = getCities();
		for (City city : cities) {
			if (GameWindow.GetMouseOver(city.getX() * 72/85, city.getY() * 72/85, 9)) {
				circle.visible = true;
				circle.x = city.getX() * 72/85 - 9;
				circle.y = city.getY() * 72/85 - 9;
				overCity = true;
				break;
			}
		}

		if (!overCity) {
			circle.visible = false;
		}
	}

	private void initializeMap() throws FileNotFoundException{
		map = new HashMap<>();
		File citiesJSON = new File("res/cities.json");
		FileInputStream citiesJSONinput = new FileInputStream(citiesJSON);
		JSONTokener citiesTokener = new JSONTokener(citiesJSONinput);
		JSONObject root = new JSONObject(citiesTokener);
		JSONArray cities = root.getJSONArray("Cities");
		for (int i = 0; i < cities.length(); i++) {
			JSONObject city = cities.getJSONObject(i);
			JSONObject coords = city.getJSONObject("Coordinates");
			City mapCity = new City(city.getString("Name"), Disease.getTypeFromString(city.getString("DiseaseType")),
												   coords.getFloat("x"), coords.getFloat("y"));
			map.put(city.getString("Name"), mapCity);
		}
		for (int i = 0; i < cities.length();i++){
			JSONObject city = cities.getJSONObject(i);
			City mapCity = map.get(city.getString("Name"));
			JSONArray connectedCities = city.getJSONArray("ConnectedCities");
			for (int j = 0; j < connectedCities.length(); j++){
				mapCity.addConnectedCity(map.get(connectedCities.getString(j)));
			}
		}
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

	//TODO: Put this function where it needs to go
	public void gameLoop(){
		//TODO: Players need to draw cards. Epidemics also happen when they draw cards.
		for (int i = 0; i < numPlayers; i++){
			players.get(i).makeMove();
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

	public ArrayList<String> getCityNames(){
		ArrayList<City> cityCities = getCities();
		ArrayList<String> cityNames = new ArrayList<String>();
		for (City city : cityCities){
			cityNames.add(city.getName());
		}
		return cityNames;
	}
	
	public void setSeed(long seed){
		this.seed = seed;
		rng.setSeed(seed);
	}
	
	long debugGetSeed(){
		return seed;
	}
}
