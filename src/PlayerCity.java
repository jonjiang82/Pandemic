//class representing Player Card Cities

public class PlayerCity extends PlayerCard{
	String city;

	public PlayerCity(String city){
		super();
		cardType = PlayerType.CITY;
		this.city = city;
	}

	public String getCity(){
		return city;
	}
}