//POJO representing a City

import java.util.ArrayList;
public class City{
	ArrayList<String> connectedCities;
	Color color;
	int yellowInfects;
	int redInfects;
	int blueInfects;
	int blackInfects;

	public City(){
		yellowInfects = 0;
		redInfects = 0;
		blueInfects = 0;
		blackInfects = 0;
	}

	public ArrayList<String> getConnectedCities(){
		return connectedCities;
	}

	public addConnectedCity(String city){
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