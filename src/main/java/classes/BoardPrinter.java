package classes;

import java.util.Map;

public class BoardPrinter {

	public static void printBoard(Board boardToPrint) {
		int boardSize = boardToPrint.getBoardSize();
		int tilesInRow = boardToPrint.getTilesInRow();
		Map<Integer, SquareTile> squareTilesPositionMap = boardToPrint.getSquareTilesPositionMap();
		String cellUnderLine = "_______";
		while (cellUnderLine.length() < tilesInRow * 7) {
			cellUnderLine += "_______";
		}

		System.out.println();
		System.out.println("Board:");
		System.out.println(cellUnderLine);

		for (int i = 1; i <= boardSize; i++) {
			if (squareTilesPositionMap.containsKey(i)) {
				SquareTile squareTile = squareTilesPositionMap.get(i);
				int value = squareTile.getValue();
				String valueToPrint = value + "";
				while (valueToPrint.length() < 5)
					valueToPrint += " ";
				System.out.print("| " + valueToPrint);
			} else {
				System.out.print("|      ");
			}

			if (i % tilesInRow == 0) {
				System.out.println("|");
				System.out.println(cellUnderLine);
			}
		}

		System.out.println();
	}
}
