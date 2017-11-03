import java.util.ArrayList;
public class Player{

	public enum Role {
		NONE, //test role
		CONTINGENCYPLANNER,
		DISPATCHER,
		MEDIC,
		OPERATIONSEXPERT,
		QUARANTINESPECIALIST,
		RESEARCHER,
		SCIENTIST
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

	public boolean moveLocation(String destination) {
		return moveLocation(destination, null);
	}

	public boolean moveLocation(String destination, PlayerCity cardCost) {
		// fail if you're already there
		if (location.equals(destination)) {
			return false;
		}

		City currentCity = game.getCity(location);
		if (currentCity.getConnectedCities().contains(destination)) { // drive/ferry
		} else if (currentCity.hasResearchStation() && game.getCity(destination).hasResearchStation()) { // shuttle flight
		} else if (cardCost == null) { // fail if cardCost is null
			return false;
		} else if (cardCost.getCity() == destination || cardCost.getCity() == location) { // direct & charter flight
			hand.remove(cardCost); // cardCost assumed to be in player hand
		} else { // else fail
			return false;
		}

		location = destination;
		return true;
	}

	public void addCardToHand(PlayerCard card){
		hand.add(card);
	}

	public void removeCityFromHand(PlayerCity city){
		for (int i = 0; i < hand.size(); i++){
			if (hand.get(i).getCardType() == PlayerCard.Type.CITY){
				if (((PlayerCity)hand.get(i)).getCity().equals(city.getCity())){
					hand.remove(i);
				}
			}
		}
	}

	public void removeEventFromHand(PlayerEvent event){
		for (int i = 0; i < hand.size(); i++){
			if (hand.get(i).getCardType() == PlayerCard.Type.EVENT){
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
		if (cardsUsed.size() != 5) { // 4 for scientist
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