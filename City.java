//POJO representing a City

import java.util.ArrayList;
public class City{
	ArrayList<City> connectedCities;
	Color color;
	int yellowInfects;
	int redInfects;
	int blueInfects;
	int blackInfects;

	public City(){

	}

	public ArrayList<City> getConnectedCities(){
		return connectedCities;
	}

	public addConnectedCity(City city){
		connectedCities.add(city);
	}

	public setColor(Color color){
		this.color = color;
	}

	public getColor(){
		return color;
	}

	public setYellowInfects(int in){
		yellowInfects = in;
	}

	public getYellowInfects(){
		return yellowInfects;
	}

	public setRedInfects(int in){
		redInfects = in;
	}

	public getRedInfects(){
		return redInfects;
	}

	public setBlueInfects(int in){
		blueInfects = in;
	}

	public getBlueInfects(){
		return blueInfects;
	}

	public setBlackInfects(int in){
		blackInfects = in;
	}

	public getBlackInfects(){
		return blackInfects;
	}
}