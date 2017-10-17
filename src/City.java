//POJO representing a City

import java.util.ArrayList;

public class City{

	private String name;
	private ArrayList<String> connectedCities;
	private Disease.Type diseaseType;
	private boolean researchStation;
	private int[] infections;

	public City(String name, Disease.Type diseaseType, ArrayList<String> connectedCities){
		this.name = name;
		this.connectedCities = connectedCities;
		infections = new int[ Disease.Type.values().length];
		this.diseaseType = diseaseType;
		researchStation = false;
	}

	public City(String name, Disease.Type diseaseType){
		this.name = name;
		this.connectedCities = new ArrayList<String>();
		infections = new int[ Disease.Type.values().length];
		this.diseaseType = diseaseType;
		researchStation = false;
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

	public void buildResearchStation() {
		researchStation = true;
	}

	public void destroyResearchStation() {
		researchStation = false;
	}

	public boolean hasResearchStation() {
		return researchStation;
	}
}