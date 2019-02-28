package classes;

public class SquareTile {

	private int value;
	private int position;

	public SquareTile(int value, int position) {
		this.value = value;
		this.position = position;
	}

	public int getValue() {
		return value;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
