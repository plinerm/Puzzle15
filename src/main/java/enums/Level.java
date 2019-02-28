package enums;

public enum Level {

	LOW(1, 25), MEDIUM(2, 150), HIGH(3, 300);

	private int levelNumber;
	private int numberOfRandomizedMoves;

	private Level(int levelNumber, int numberOfRandomizedMoves) {
		this.levelNumber = levelNumber;
		this.numberOfRandomizedMoves = numberOfRandomizedMoves;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public int getNumberOfRandomizedMoves() {
		return numberOfRandomizedMoves;
	}

}
