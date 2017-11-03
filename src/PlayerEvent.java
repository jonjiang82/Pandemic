//class representing Player Card Events

public class PlayerEvent extends PlayerCard{
	PlayerEventType type;

	public PlayerEvent(PlayerEventType type){
		super();
		cardType = PlayerCard.Type.EVENT;
		this.type = type;
	}

	public PlayerEventType getType(){
		return type;
	}
}