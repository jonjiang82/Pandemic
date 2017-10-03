//POJO representing a City

import java.util.ArrayList;
public class City{

	private ArrayList<String> connectedCities;
	private Disease.Type diseaseType;
	private int[] infections;

	public City(Disease.Type diseaseType){
		connectedCities = new ArrayList<String>();
		infections = new int[ Disease.Type.values().length];
		this.diseaseType = diseaseType;
	}

	public ArrayList<String> getConnectedCities(){
		return connectedCities;
	}

	public void addConnectedCity(String city){
		connectedCities.add(city);
	}

	public Disease.Type getDiseaseType(){
		return diseaseType;
	}

	public int getInfection(Disease.Type type){
		return infections[type.ordinal()];
	}

	public void setInfection(Disease.Type type, int in){
		infections[type.ordinal()] = in;
	}
}