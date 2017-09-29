public class Disease{
	Color color;
	int cubes;
	boolean cured;
	boolean eradicated;

	public Disease(Color color){
		this.color = color;
		cubes = 24;
		cured = false;
		eradicated = false;
	}

	public Color getColor(){
		return color;
	}

	public int getCubes(){
		return cubes;
	}

	public void setCubes(int cubes){
		this.cubes = cubes;
	}

	public boolean getCured(){
		return cured;
	}

	public void setCured(boolean cured){
		this.cured = cured;
	}

	public boolean getEradicated(){
		return eradicated;
	}

	public void setEradicated(boolean eradicated){
		this.eradicated = eradicated;
	}
}