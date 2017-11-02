import java.util.ArrayList;
public class Player{

	public enum Role {
		NONE, //test role
		DISPATCHER
	}

	private Pandemic game;
	private String location;
	private Player.Role role;
	private ArrayList<PlayerCard> hand;

	public Player(Pandemic game, String startingLocation){
		game = game;
		location = startingLocation;
		hand = new ArrayList<PlayerCard>();
	}

	public String getLocation(){
		return location;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public void addCardToHand(PlayerCard card){
		hand.add(card);
	}

	public void removeCityFromHand(PlayerCity city){
		for (int i = 0; i < hand.size(); i++){
			if (hand.get(i).getCardType() == PlayerType.CITY){
				if (((PlayerCity)hand.get(i)).getCity().equals(city.getCity())){
					hand.remove(i);
				}
			}
		}
	}

	public void removeEventFromHand(PlayerEvent event){
		for (int i = 0; i < hand.size(); i++){
			if (hand.get(i).getCardType() == PlayerType.EVENT){
				if (((PlayerEvent)hand.get(i)).getType() == event.getType()){
					hand.remove(i);
				}
			}
		}
	}

	public boolean buildResearchStation(){
		return game.buildResearchStation(location);
	}

	//	public void treat() {
	//		treat(game.getCity(location).getDiseaseType());
	//	}

	public void treat(Disease.Type diseaseType) {
		City city = game.getCity(location);
		if (city.getInfection(diseaseType) > 0) {
			if (game.getDisease(diseaseType).getState() == Disease.State.CURED) {
				city.setInfection(diseaseType, 0);
			} else {
				city.setInfection(diseaseType, city.getInfection(diseaseType) - 1);
			}
		}
	}

	// will fail if exact conditions are not met (eg if there are too many cards in cardsUsed)
	public boolean researchCure(Disease.Type diseaseType, ArrayList<PlayerCity> cardsUsed) {
		// check if disease needs to be cured
		if (game.getDisease(diseaseType).getState() != Disease.State.ACTIVE) {
			return false;
		}

		// check if we have the right amount of cards
		if (cardsUsed.size() != 5) { // 4 for researcher
			return false;
		}

		// check if all cards are the right diseaseType
		for (PlayerCity card : cardsUsed) {
			if (game.getCity(card.getCity()).getDiseaseType() != diseaseType) {
				return false;
			}
		}

		hand.removeAll(cardsUsed); // cardsUsed is assume to be a subset of hand
		game.getDisease(diseaseType).setState(Disease.State.CURED);
		return true;
	}

}