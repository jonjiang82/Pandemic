//POJO representing diseases

public class Disease{

	public enum Type {
		YELLOW,
		RED,
		BLUE,
		BLACK
	}

	public enum State {
		ACTIVE,
		CURED,
		ERADICATED
	}

	private Disease.Type type;
	private Disease.State state;
	private int cubes;

	public Disease(Type type){
		this.type = type;
		state = State.ACTIVE;
		cubes = 24;
	}

	public static Type getTypeFromString(String str){
		str = str.toUpperCase();
		switch (str){
			case "YELLOW":
				return Type.YELLOW;
			case "RED":
				return Type.RED;
			case "BLACK":
				return Type.BLACK;
			default:
				return Type.BLUE;
		}
	}

	public Type getDiseaseType(){
		return type;
	}

	public int getCubes(){
		return cubes;
	}

	public void setCubes(int cubes){
		this.cubes = cubes;
	}

	public State getState(){
		return state;
	}

	public void setState(State state){
		this.state = state;
	}
}