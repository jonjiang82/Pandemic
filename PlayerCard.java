//abstract class representing Player cards, to be extended into cities, events, and epidemics.

public abstract class PlayerCard{
	PlayerType cardType;

	public PlayerCard(){
		
	}

	public PlayerType getCardType(){
		return cardType;
	}
}