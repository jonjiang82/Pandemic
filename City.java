//POJO representing a City

import java.util.ArrayList;
public class City{
	ArrayList<String> connectedCities;
	Color color;
	int yellowInfects;
	int redInfects;
	int blueInfects;
	int blackInfects;

	public City(Color color){
		connectedCities = new ArrayList<String>();
		yellowInfects = 0;
		redInfects = 0;
		blueInfects = 0;
		blackInfects = 0;
		this.color = color;
	}

	public ArrayList<String> getConnectedCities(){
		return connectedCities;
	}

	public void addConnectedCity(String city){
		connectedCities.add(city);
	}

	public Color getColor(){
		return color;
	}

	public void setYellowInfects(int in){
		yellowInfects = in;
	}

	public int getYellowInfects(){
		return yellowInfects;
	}

	public void setRedInfects(int in){
		redInfects = in;
	}

	public int getRedInfects(){
		return redInfects;
	}

	public void setBlueInfects(int in){
		blueInfects = in;
	}

	public int getBlueInfects(){
		return blueInfects;
	}

	public void setBlackInfects(int in){
		blackInfects = in;
	}

	public int getBlackInfects(){
		return blackInfects;
	}
}