//POJO representing a City

import java.util.ArrayList;

public class City{

	private String name;
	private ArrayList<City> connectedCities;
	private Disease.Type diseaseType;
	private float coordX;
	private float coordY;

	private boolean researchStation;
	private int[] infections;

	public City(String name, Disease.Type diseaseType, float coordX, float coordY){
		this.name = name;
		this.connectedCities = new ArrayList<City>();
		infections = new int[ Disease.Type.values().length];
		this.diseaseType = diseaseType;
		researchStation = false;
		this.coordX = coordX;
		this.coordY = coordY;
	}

	public ArrayList<City> getConnectedCities(){
		return connectedCities;
	}

	public void addConnectedCity(City city){
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

	public float getX() {
		return coordX;
	}

	public float getY() {
		return coordY;
	}

	public String getName(){
		return name;
	}
}