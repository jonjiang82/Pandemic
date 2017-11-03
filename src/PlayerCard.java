//abstract class representing Player cards, to be extended into cities, events, and epidemics.

public abstract class PlayerCard{

	//enum representing player card types
	public enum Type{
		CITY,
		EVENT,
		EPIDEMIC
	}

	PlayerCard.Type cardType;

	public PlayerCard(){
		
	}

	public PlayerCard.Type getCardType(){
		return cardType;
	}
}