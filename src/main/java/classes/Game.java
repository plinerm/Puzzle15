package classes;

import java.util.Map;
import java.util.Scanner;

import enums.Level;

public class Game {

	private Board board;

	public Game(int tilesInRow) {
		this.board = new Board(tilesInRow);
	}

	public void startGame() {
		Scanner sc = null;
		try {
			sc = new Scanner(System.in);
			System.out.println("Hello there!");
			Level level = setLevel(sc);
			board.initBoard(level);
			playGame(sc);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}

	private void playGame(Scanner sc) {
		boolean gameFinished = false;
		while (!gameFinished) {
			BoardPrinter.printBoard(board);
			boolean moveWasValid = false;
			while (!moveWasValid) {
				System.out.println("Please select a valid tile number:");
				int tileValueToMove = getNumericInput(sc);
				moveWasValid = board.makeMove(tileValueToMove);
			}

			gameFinished = isGameFinished(board);
		}

		BoardPrinter.printBoard(board);
		System.out.println("Congratulations!! you won!!!");
	}

	private int getNumericInput(Scanner sc) {
		int input = -1;
		while (input == -1) {
			try {
				String numericStr=sc.nextLine();
				input = Integer.parseInt(numericStr);
			} catch (NumberFormatException ex) {
				System.out.println("Input is not a valid number");
			}
		}

		return input;
	}

	private Level setLevel(Scanner sc) {
		Level level = null;
		while (level == null) {
			System.out.println("Please select game level: 1 (Low), 2 (Medium), 3 (Hard):");
			int levelInput = getNumericInput(sc);
			switch (levelInput) {
			case 1:
				level = Level.LOW;
				break;
			case 2:
				level = Level.MEDIUM;
				break;
			case 3:
				level = Level.HIGH;
				break;
			default:
				System.out.println("Game level is not valid");
				break;
			}
		}
		
		return level;
	}

	private boolean isGameFinished(Board board) {
		int boardSize = board.getBoardSize();
		Map<Integer, SquareTile> squareTilesPositionMap = board.getSquareTilesPositionMap();
		boolean isSuccess = true;
		for (int i = 1; i < boardSize; i++) {
			if (squareTilesPositionMap.containsKey(i)) {
				SquareTile st = squareTilesPositionMap.get(i);
				int value = st.getValue();
				if (i != value) {
					isSuccess = false;
					break;
				}
			} else {
				isSuccess = false;
				break;
			}
		}

		return isSuccess;
	}
}
