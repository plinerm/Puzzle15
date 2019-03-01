package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import enums.Level;

public class Board {

	private int tilesInRow;
	private int boardSize;
	private int missingPosition;
	
	private final static int MAX_TILES_IN_ROW=25;

	// key is value of tile, value- tile
	Map<Integer, SquareTile> squareTilesValueMap;

	// key is position of tile, value- tile
	Map<Integer, SquareTile> squareTilesPositionMap;

	// key is empty position, value is list of neighbors position
	Map<Integer, List<Integer>> positionsNeighborsMap;

	public Board(int tilesInRow) {

		if (tilesInRow < 0) {
			tilesInRow = Math.abs(tilesInRow);
		}
		
		tilesInRow=Math.min(tilesInRow, MAX_TILES_IN_ROW);

		this.tilesInRow = tilesInRow;
		this.boardSize = (int) Math.pow(tilesInRow, 2);
		this.squareTilesValueMap = new HashMap<>();
		this.squareTilesPositionMap = new HashMap<>();
		this.positionsNeighborsMap = new HashMap<>();
	}

	public Map<Integer, SquareTile> getSquareTilesPositionMap() {
		return squareTilesPositionMap;
	}

	public int getTilesInRow() {
		return tilesInRow;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void initBoard(Level level) {
		missingPosition = boardSize;

		for (int i = 1; i <= boardSize; i++) {
			if (i != boardSize) {
				SquareTile st = new SquareTile(i, i);
				squareTilesValueMap.put(i, st);
				squareTilesPositionMap.put(i, st);
			}
			positionsNeighborsMap.put(i, calculatePositionNeighbors(i));
		}
		// BoardPrinter.printBoard(this);
		mixBoard(level);
	}

	private void mixBoard(Level level) {
		
		if(level==null)
			level=Level.LOW;
		
		Random rand = new Random();
		int numberOfMoves = level.getNumberOfRandomizedMoves();
		for (int i = 0; i < numberOfMoves; i++) {

			if (positionsNeighborsMap.containsKey(missingPosition)) {
				List<Integer> possiblePositions = positionsNeighborsMap.get(missingPosition);
				int randomIndex = possiblePositions.get(rand.nextInt(possiblePositions.size()));
				if (squareTilesPositionMap.containsKey(randomIndex)) {
					SquareTile squareTile = squareTilesPositionMap.get(randomIndex);
					int tileValueToMove = squareTile.getValue();
					makeMove(tileValueToMove);
					// BoardPrinter.printBoard(this);
				}
			}
		}

	}

	private List<Integer> calculatePositionNeighbors(int position) {
		List<Integer> positionsToCheck = new ArrayList<Integer>();

		int positionLeft = position - 1;
		if (position % tilesInRow != 1) {
			positionsToCheck.add(positionLeft);
		}

		int positionRight = position + 1;
		if (position % tilesInRow != 0) {
			positionsToCheck.add(positionRight);
		}

		int positionUp = position - tilesInRow;
		int positionDown = position + tilesInRow;

		positionsToCheck.add(positionUp);
		positionsToCheck.add(positionDown);

		return positionsToCheck.stream().filter(positionToCheck -> validatePosition(positionToCheck))
				.collect(Collectors.toList());
	}

	private boolean validatePosition(Integer positionToCheck) {
		return positionToCheck >= 1 && positionToCheck <= boardSize;
	}

	public boolean makeMove(int tileValueToMove) {

		boolean validMove = true;
		if (squareTilesValueMap.containsKey(tileValueToMove)) {
			SquareTile squareTile = squareTilesValueMap.get(tileValueToMove);
			int position = squareTile.getPosition();

			List<Integer> possiblePositionsLst = positionsNeighborsMap.get(missingPosition);
			if (possiblePositionsLst.contains(position)) {
				squareTile.setPosition(missingPosition);
				squareTilesPositionMap.remove(position);
				squareTilesPositionMap.put(squareTile.getPosition(), squareTile);
				missingPosition = position;
			} else {
				System.out.println("Tile number: " + tileValueToMove + ", is not a neighbor of the empty place");
				validMove = false;
			}
		} else {
			System.out.println("Tile number is not valid");
			validMove = false;
		}

		return validMove;
	}

}
