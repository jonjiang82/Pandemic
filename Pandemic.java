import java.util.HashMap;
public class Pandemic{
	HashMap<String, City> map;
	int infectionRate;

	public Pandemic(){
		map.put("Atlanta", new City());
		map.get("Atlanta").addConnectedCity("Chicago");
		map.get("Atlanta").addConnectedCity("Washington");
		map.get("Atlanta").addConnectedCity("Miami");
		map.get("Atlanta").setColor(Color.BLUE);
	}
}