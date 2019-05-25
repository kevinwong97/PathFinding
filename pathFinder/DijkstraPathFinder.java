package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.*;

public class DijkstraPathFinder implements PathFinder
{
    // TODO: You might need to implement some attributes
	private ArrayList<Coordinate> visited;
	private List<Coordinate> unvisited;
	private Map<Coordinate, ArrayList<Coordinate>> weight;
	private PathMap map;

    public DijkstraPathFinder(PathMap map) {
		this.map = map;	
		visited = new ArrayList<Coordinate>();
		weight = new HashMap<Coordinate, ArrayList<Coordinate>>();
		unvisited = new ArrayList<Coordinate>();
    } // end of DijkstraPathFinder()



    @Override
    public List<Coordinate> findPath() {
        // You can replace this with your favourite list, but note it must be a
        // list type
        List<Coordinate> path = new ArrayList<Coordinate>();
		
		Coordinate mapCells[][] = map.getCells();
		List<Coordinate> originCells = map.getOrigins();
		List<Coordinate> destCells = map.getDestinations();
		
		for (int i = 0; i < mapCells.length; i++) {
			for (int j = 0; j < mapCells[i].length; j++) {
				// System.out.println(mapCells[i][j].getRow() + ", " + mapCells[i][j].getColumn());
				if (mapCells[i][j].getImpassable() == false) {
					unvisited.add(mapCells[i][j]);
				}
			}
		}
		
		for (Coordinate coord : unvisited) {
			System.out.println("Row: " + coord.getRow() + ", Column: " + coord.getColumn());
		}
		
		System.out.println("the origin is: " + originCells.get(0).getRow() + ", " + originCells.get(0).getColumn());
		System.out.println("the destination is: " + destCells.get(0).getRow() + ", " + destCells.get(0).getColumn());
		
		// Main loop.
		// Get neighbours of current cell.
		// Find neighbour with lowest weight.
		// Add to visited list with cumulative weight.
		// Remove current cell from unvisited.
		// Move to neighbour with lowest weight.
		// Add to cumulative weight?
		
		Coordinate currentCell = originCells.get(0);
		ArrayList<Coordinate> tempPath = new ArrayList<Coordinate>();
		tempPath.add(currentCell);
		System.out.println(tempPath);
		weight.put(currentCell, new ArrayList<Coordinate>(tempPath));
		
		
		while (unvisited.isEmpty() == false && weight.containsKey(destCells.get(0)) == false) {
			
			visited.add(currentCell);
			
			ArrayList<Coordinate> neighbours = new ArrayList<Coordinate>();
			if (tempPath.size() == 1) {
				neighbours = getNeighbours(mapCells, currentCell.getRow(), currentCell.getColumn());
			} else {
				for (Coordinate coord : tempPath) {
					ArrayList<Coordinate> tempNeighbours = new ArrayList<Coordinate>();
					tempNeighbours = getNeighbours(mapCells, coord.getRow(), coord.getColumn());
					for (Coordinate neighbour : tempNeighbours) {
						if (neighbours.contains(neighbour) == false && tempPath.contains(neighbour) == false) {
							neighbours.add(neighbour);
						}
					}
				}
			}
			
			System.out.println("Curr Cell Row: " + currentCell.getRow() + ", Column: " + currentCell.getColumn());
			System.out.println("\n" + "Known Weights");
			
			for (Coordinate coord : weight.keySet()) {
				System.out.println("Row: " + coord.getRow() + ", Column: " + coord.getColumn() + ", Shortest Path: " + costOfPath(weight.get(coord)));
			}
			
			System.out.println();
			
			visited.add(currentCell);
			int indexToDelete = unvisited.indexOf(currentCell);
			unvisited.remove(indexToDelete);
			
			for (Coordinate coord : neighbours) {
				ArrayList<Coordinate> pathToNeighbour = new ArrayList<Coordinate>(tempPath);
				pathToNeighbour.add(coord);
				System.out.println(pathToNeighbour);
				if (weight.containsKey(coord) == false) {
					weight.put(coord, new ArrayList<Coordinate>(pathToNeighbour));
					System.out.println("Not in map, adding...");
				} else {
					if (weight.containsKey(coord) == true) {
						if (costOfPath(pathToNeighbour) < costOfPath(weight.get(coord))) {
							System.out.println(costOfPath(pathToNeighbour) + " < " + costOfPath(weight.get(coord)));
							weight.put(coord, new ArrayList<Coordinate>(pathToNeighbour));
							System.out.println("Is in map, updating...");
						}
					} else {
						System.out.println("Already in map!");
					}
				}
			}
			
			int lowestWeight = 10000000;
			Coordinate lowestNeighbour = null;
			
			for (Coordinate coord : neighbours) {
				ArrayList<Coordinate> pathToNeighbour = new ArrayList<Coordinate>(tempPath);
				pathToNeighbour.add(coord);
				if (visited.contains(coord) == false && costOfPath(pathToNeighbour) < lowestWeight) {
					lowestWeight = coord.getTerrainCost();
					lowestNeighbour = coord;
				}
			}
			
			tempPath.add(lowestNeighbour);
			currentCell = lowestNeighbour;
				
			
		} // end main loop
		
		
		System.out.println("End of loop");
		
		for (Coordinate coord : weight.keySet()) {
			System.out.println("Row: " + coord.getRow() + ", Column: " + coord.getColumn() + ", Shortest Path: " + costOfPath(weight.get(coord)));
			System.out.println(weight.get(coord));
		}
		
		path = weight.get(destCells.get(0));
			
        return path;
    } // end of findPath()


    @Override
    public int coordinatesExplored() {
        // TODO: Implement (optional)

        // placeholder
        return 0;
    } // end of cellsExplored()
	
	private ArrayList<Coordinate> getNeighbours(Coordinate mapCells[][], int originRow, int originCol) {
		ArrayList<Coordinate> neighbours = new ArrayList<Coordinate>();
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
		if (currentCellRow >= 0 && currentCellRow < map.getRowSize() - 1) {
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
	
	int costOfPath(ArrayList<Coordinate> path) {
		int pathWeight = 0;
		
		if (path.size() > 0) {
			for (int i = 0; i < path.size() - 1; i++) {
				pathWeight += path.get(i).getTerrainCost();
			}
		}
		
		return pathWeight;
	}



} // end of class DijsktraPathFinder
