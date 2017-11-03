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

	private City location;
	private Player.Role role;
	private ArrayList<PlayerCard> hand;

	public Player(City startingLocation){
		location = startingLocation;
		hand = new ArrayList<PlayerCard>();
	}

	public City getLocation(){
		return location;
	}

	public void setLocation(City location){
		this.location = location;
	}

	public boolean moveLocation(City destination) {
		return moveLocation(destination, null);
	}

	public boolean moveLocation(City destination, PlayerCity cardCost) {
		// fail if you're already there
		if (location.equals(destination)) {
			return false;
		}

		if (location.getConnectedCities().contains(destination)) { // drive/ferry
		} else if (location.hasResearchStation() && destination.hasResearchStation()) { // shuttle flight
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

	public boolean buildResearchStation() {
		return buildResearchStation(null);
	}

	public boolean buildResearchStation(City destroyStationAt) {
		// if no more research stations left, try to destroy given research station
		if (Pandemic.instance.getResearchStationsLeft() < 1) {
			if (destroyStationAt == null) {
				return false;
			}
			if (destroyStationAt.hasResearchStation()) {
				destroyStationAt.destroyResearchStation();
				Pandemic.instance.adjustResearchStationsLeft(1);
			} else {
				return false; // fail if there is no research station there to destroy
			}
		}

		if (location.hasResearchStation()) {
			return false; // fail if there is already a research station there
		}

		location.buildResearchStation();
		Pandemic.instance.adjustResearchStationsLeft(-1);
		return true;
	}

	//	public void treat() {
	//		treat(game.getCity(location).getDiseaseType());
	//	}

	public void treat(Disease.Type diseaseType) {
		if (location.getInfection(diseaseType) > 0) {
			if (Pandemic.instance.getDisease(diseaseType).getState() == Disease.State.CURED) {
				location.setInfection(diseaseType, 0);
			} else {
				location.setInfection(diseaseType, location.getInfection(diseaseType) - 1);
			}
		}
	}

	// will fail if exact conditions are not met (eg if there are too many cards in cardsUsed)
	public boolean researchCure(Disease.Type diseaseType, ArrayList<PlayerCity> cardsUsed) {
		// check if disease needs to be cured
		if (Pandemic.instance.getDisease(diseaseType).getState() != Disease.State.ACTIVE) {
			return false;
		}

		// check if we have the right amount of cards
		if (cardsUsed.size() != 5) { // 4 for scientist
			return false;
		}

		// check if all cards are the right diseaseType
		for (PlayerCity card : cardsUsed) {
			if (card.getCity().getDiseaseType() != diseaseType) {
				return false;
			}
		}

		hand.removeAll(cardsUsed); // cardsUsed is assume to be a subset of hand
		Pandemic.instance.getDisease(diseaseType).setState(Disease.State.CURED);
		return true;
	}

}