package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.*;

public class DijkstraPathFinder implements PathFinder
{
    // TODO: You might need to implement some attributes
	private Map<Coordinate, Integer> visited;
	private List<Coordinate> unvisited;
	private PathMap map;

    public DijkstraPathFinder(PathMap map) {
		this.map = map;	
		visited = new HashMap<Coordinate, Integer>();
		unvisited = new ArrayList<Coordinate>();
    } // end of DijkstraPathFinder()



    @Override
    public List<Coordinate> findPath() {
        // You can replace this with your favourite list, but note it must be a
        // list type
        List<Coordinate> path = new ArrayList<Coordinate>();
		// Creating two maps to store visited locations.
        visited = new HashMap<Coordinate, Integer>();
		unvisited = new ArrayList<Coordinate>();
		
		Coordinate mapCells[][] = map.getCells();
		List<Coordinate> originCells = map.getOrigins();
		List<Coordinate> destCells = map.getDestinations();
		
		for (int i = 0; i < mapCells.length; i++) {
			for (int j = 0; j < mapCells[i].length; j++) {
				System.out.println(mapCells[i][j].getRow() + ", " + mapCells[i][j].getColumn());
				unvisited.add(mapCells[i][j]);
			}
		}
		
		System.out.println("the origin is: " + originCells.get(0).getRow() + ", " + originCells.get(0).getColumn());
		System.out.println("the destination is: " + destCells.get(0).getRow() + ", " + destCells.get(0).getColumn());
		
		for (Coordinate coord : originCells) {
			// Marking origin cells with weight zero.
			visited.put(coord, 0);
			path.add(coord);
		}		
		
		List<Coordinate> sourceNeighbours = getNeighbours(mapCells, originCells.get(0).getRow(), originCells.get(0).getColumn());
		// Finding neighbour with lowest weight.
		System.out.println("The neighbours of the source vertex are: ");
		
		for (Coordinate coord : sourceNeighbours) {
			System.out.println(coord.getRow() + ", " + coord.getColumn());
		}
		
		int lowestWeight = 1;
		Coordinate nextCoord = null;
		
		for (Coordinate coord : sourceNeighbours) {
			if (coord.getTerrainCost() <= lowestWeight) {
				lowestWeight = coord.getTerrainCost();
				nextCoord = coord;
			}
		}
		
		int costOfPrevious = lowestWeight;
		
		System.out.println("The next place to visit is: " + nextCoord.getRow() + ", " + nextCoord.getColumn());
        return path;
    } // end of findPath()


    @Override
    public int coordinatesExplored() {
        // TODO: Implement (optional)

        // placeholder
        return 0;
    } // end of cellsExplored()
	
	private List<Coordinate> getNeighbours(Coordinate mapCells[][], int originRow, int originCol) {
		List<Coordinate> neighbours = new ArrayList<Coordinate>();
		int currentCellRow = originRow;
		int currentCellCol = originCol;
		
		// Gets right neighbour of cell.
		if (currentCellCol >= 0 && currentCellCol < map.getColSize() - 1) {
			if (mapCells[currentCellRow][currentCellCol + 1].getImpassable() == false) {
				neighbours.add(mapCells[currentCellRow][currentCellCol + 1]);
			}
		}
		
		// Gets left neighbour of cell.
		if (currentCellCol > 0 && currentCellCol <= map.getColSize() - 1) {
			if (mapCells[currentCellRow][currentCellCol - 1].getImpassable() == false) {
				neighbours.add(mapCells[currentCellRow][currentCellCol - 1]);
			}
		}
		
		// Gets above neighbour of cell.
		if (currentCellRow >= 0 && currentCellRow <= map.getRowSize() - 1) {
			if (mapCells[currentCellRow + 1][currentCellCol].getImpassable() == false) {
				neighbours.add(mapCells[currentCellRow + 1][currentCellCol]);
			}
		}
		
		// Gets below neighbour of cell.
		if (currentCellRow > 0 && currentCellRow <= map.getRowSize() - 1) {
			if (mapCells[currentCellRow - 1][currentCellCol].getImpassable() == false) {
				neighbours.add(mapCells[currentCellRow - 1][currentCellCol]);
			}
		}
		
		return neighbours;
	}



} // end of class DijsktraPathFinder
