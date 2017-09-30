import java.util.ArrayList;
public class Player{
	String location;
	ArrayList<PlayerCard> hand;

	public Player(){
		location = "Atlanta";
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
}