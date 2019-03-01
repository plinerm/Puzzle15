package puzzle15;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import classes.Board;
import classes.BoardPrinter;
import enums.Level;

public class Puzzle15Test {

	@Test
	public void printNullBoardTest() {
		try {
			BoardPrinter.printBoard(null);
		} catch (NullPointerException ex) {
			Assert.fail();
		}
	}

	@Test
	public void boardMaxTilesTest() {

		int highTilesInRow = Board.MAX_TILES_IN_ROW + 1;
		Board board = new Board(highTilesInRow);
		Assert.assertNotNull(board);

		int tilesInRow = board.getTilesInRow();
		Assert.assertEquals(Board.MAX_TILES_IN_ROW, tilesInRow);
	}

	@Test
	public void boardNegativeTilesTest() {

		int negativeTilesInRow = -3;
		Board board = new Board(negativeTilesInRow);
		Assert.assertNotNull(board);

		int tilesInRow = board.getTilesInRow();
		Assert.assertEquals(Math.abs(negativeTilesInRow), tilesInRow);
	}

	@Test
	public void boardNullLevelTest() {

		Board board = createValidBoard();

		try {
			board.initBoard(null);
		} catch (NullPointerException ex) {
			Assert.fail();
		}
	}

	@Test
	public void makeMoveFailTest() {

		Board board = createInitializedBoard();

		Board tmp=new Board(board);
		boolean makeMove = board.makeMove(-1);
		Assert.assertFalse(makeMove);
		Assert.assertTrue(board.equals(tmp));

		makeMove = board.makeMove(board.getBoardSize() + 1);
		Assert.assertFalse(makeMove);
		Assert.assertTrue(board.equals(tmp));
	}

	@Test
	public void makeMoveSuccessTest() {

		Board board = createInitializedBoard();
		boolean makeMove = false;
		for (int i = 1; i <= board.getBoardSize(); i++) {
			Board tmp=new Board(board);
			makeMove = board.makeMove(i);
			if (makeMove) {
				makeMove = true;
				Assert.assertFalse(board.equals(tmp));
				break;
			}
			else
			{
				Assert.assertTrue(board.equals(tmp));
			}
		}
		Assert.assertTrue(makeMove);
	}

	private Board createInitializedBoard() {
		Board board = createValidBoard();
		board.initBoard(Level.LOW);
		return board;
	}

	private Board createValidBoard() {
		int randomInt = getRandomValidTilesNumber();
		Board board = new Board(randomInt);
		Assert.assertNotNull(board);
		return board;
	}

	private int getRandomValidTilesNumber() {
		Random rand = new Random();
		int randomInt = rand.nextInt(Board.MAX_TILES_IN_ROW) + 1;
		return randomInt;
	}
}
