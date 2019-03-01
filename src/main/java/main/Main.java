package main;

import classes.Game;

public class Main {
	
	private final static int DEFAULT_TILES_IN_ROW=4;


	public static void main(String[] args) {
		try {
			Game game = new Game(DEFAULT_TILES_IN_ROW);
			game.startGame();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
