//class representing Player Card Cities

public class PlayerCity extends PlayerCard{
	private City city;

	public PlayerCity(City city){
		super();
		cardType = PlayerCard.Type.CITY;
		this.city = city;
	}

	public City getCity(){
		return city;
	}
}