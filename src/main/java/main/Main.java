package main;

import classes.Game;

public class Main {

	public static void main(String[] args) {
		try {
			Game game = new Game(4);
			game.startGame();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
