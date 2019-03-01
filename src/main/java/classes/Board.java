package classes;

import java.text.MessageFormat;
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

	public final static int MAX_TILES_IN_ROW = 25;

	// key is value of tile, value- tile
	private Map<Integer, SquareTile> squareTilesValueMap;

	// key is position of tile, value- tile
	private Map<Integer, SquareTile> squareTilesPositionMap;

	// key is empty position, value is list of neighbors position
	private Map<Integer, List<Integer>> positionsNeighborsMap;

	public Board(int tilesInRow) {

		if (tilesInRow < 0) {
			tilesInRow = -(tilesInRow);
			System.out.println(MessageFormat.format("number of tiles is negative, setting to possitive ({0})", tilesInRow));
		}
		
		if (tilesInRow > MAX_TILES_IN_ROW) {
			System.out.println(MessageFormat.format("number of tiles is higher than allowed, setting to max ({0})", MAX_TILES_IN_ROW));
			tilesInRow = MAX_TILES_IN_ROW;
		}
		this.tilesInRow = tilesInRow;
		this.boardSize = (int) Math.pow(tilesInRow, 2);
		this.squareTilesValueMap = new HashMap<>();
		this.squareTilesPositionMap = new HashMap<>();
		this.positionsNeighborsMap = new HashMap<>();
	}
	
	public Board(Board b) { 
		this.missingPosition=b.missingPosition;
		this.tilesInRow = b.tilesInRow;
		this.boardSize = b.boardSize;
		this.squareTilesValueMap = new HashMap<>(b.squareTilesValueMap);
		this.squareTilesPositionMap = new HashMap<>(b.squareTilesPositionMap);
		this.positionsNeighborsMap =new HashMap<>(b.positionsNeighborsMap);
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
	
	public boolean equals(Board b) {
		
		if(this.missingPosition!=b.missingPosition || this.tilesInRow != b.tilesInRow 
				|| this.boardSize != b.boardSize
				||!(this.squareTilesValueMap).equals(b.squareTilesValueMap)
				||!(this.squareTilesPositionMap).equals(b.squareTilesPositionMap)
				||!(this.positionsNeighborsMap).equals(b.positionsNeighborsMap))
			return false;
		
		return true;
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

		if (level == null)
			level = Level.LOW;

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
				System.out.println(MessageFormat.format("Tile number: ({0}) is not a neighbor of the empty place",tileValueToMove));
				validMove = false;
			}
		} else {
			System.out.println(MessageFormat.format("Tile number: ({0}) is not valid",tileValueToMove));
			validMove = false;
		}

		return validMove;
	}

}
