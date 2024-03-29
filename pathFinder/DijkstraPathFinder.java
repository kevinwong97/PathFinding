package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.*;

public class DijkstraPathFinder implements PathFinder
{
	private ArrayList<Coordinate> visited;
	private List<Coordinate> unvisited;
	private Map<Coordinate, ArrayList<Coordinate>> weight;
	private PathMap map;

    public DijkstraPathFinder(PathMap map) {
		this.map = map;	
		visited = new ArrayList<Coordinate>();
		weight = new HashMap<Coordinate, ArrayList<Coordinate>>();
		unvisited = new ArrayList<Coordinate>();
    }
    /**
     * Finds shortest path that passes through waypoints ...Short for test D
     * @return returns a list containing the coordinates of the path
     */
	public ArrayList<Coordinate> findPath2()
	{
		ArrayList<Coordinate> path = new ArrayList<Coordinate>();
		
		// Getting elements of the grid. Cells, origins, destinations and waypoints.
		Coordinate mapCells[][] = map.getCells();
		List<Coordinate> originCells = map.getOrigins();
		List<Coordinate> destCells = map.getDestinations();
		List<Coordinate> waypoints = map.getWaypoints();
		
		//find the waypoint that is nearer to the origin
		List<Coordinate> pointsInOrder = new LinkedList<Coordinate>();//Stores the waypoints in order
		Coordinate origin = originCells.get(0);//Assuming the origin is only one in the list
		Coordinate destination = destCells.get(0);//Assume one destination for task D's sake
		Coordinate waypoint = null;
		
		if (waypoints.size() > 0) {
			waypoint = waypoints.get(0); //Say this is the nearest, ?prove me wrong...
		}
		
		Coordinate currentLoc = origin;//Set the current location to origin
		
		///<editor-fold desc="Loop Get waypoints in order" defaultstate="collapsed">
		while(waypoints.size() != 0)
		{
			
			int pathlength=0;
			for(Coordinate wp: waypoints)
			{
				//distance is the diff in x coordianate + diff in y coordinates since no curves or diagonals
				//look whether is impassable
				int diffx = Math.abs(wp.getColumn() - currentLoc.getRow());
				int diffy = Math.abs(wp.getRow() - currentLoc.getRow());
				int len = diffx+diffy;
				if(pathlength == 0)
				{
					//not assigned before, so assign
					waypoint= wp;
					pathlength = len;
				}
				else if(len <pathlength)
				{
					pathlength = len;
					waypoint = wp;
				}
				//else ignore the path its long
			}
			//now got the nearest waypoint
			//remove the waypoint from the list now to ensure not to check it again
			waypoints.remove(waypoint);
			pointsInOrder.add(waypoint); //Add to the new sorted list
			currentLoc = waypoint;
			//check if there's other waypoints
			if(waypoints.size() == 0)
			{
				//stop getting next shortest waypoint and get paths now
				break;
			}
			
				//continue with loop to get next nearest waypoint
			
		}
		///</editor-fold>
		
		//Now get the path from the origin to the dest
		//you can add functionality to divert path on cost being high
		path.add(origin);
		//loop while the currentLoc'ation' is not the destination
		currentLoc = origin;//reset to origin for the start
		
		boolean notfinished = true;
		while(currentLoc != destination || notfinished)
		{
			
			//travel to the next waypoint
			while(pointsInOrder.size() != 0)
			{
				//Get the waypoints in accordance to their distance from the previous point...at the start always origin
				waypoint = pointsInOrder.get(0);
				pointsInOrder.remove(waypoint);
				
				//create points along path iteratively
				Coordinate currPoint = currentLoc;
				
				//Check direction of the next point
				//if next rowpoint is greater than this ones then
				if(waypoint.getRow()>currPoint.getRow())
				while(currPoint.getRow()<waypoint.getRow())
				{
					//increment the row
					//and add to the path
					currPoint = new Coordinate(currPoint.getRow()+1, currPoint.getColumn());
					path.add(currPoint);
				}
				else
				{
					
					if(waypoint.getColumn()>currPoint.getColumn())
						//now increment column
						while(currPoint.getColumn()< waypoint.getColumn())
						{
							currPoint = new Coordinate(currPoint.getRow(),currPoint.getColumn()+1);
							path.add(currPoint); 
						}
					while(currPoint.getRow()>waypoint.getRow())
					{
						//increment the row
						//and add to the path
						currPoint = new Coordinate(currPoint.getRow()-1, currPoint.getColumn());
						path.add(currPoint);
					}
				}
				if(waypoint.getColumn()>currPoint.getColumn())
				//now increment column
				while(currPoint.getColumn()< waypoint.getColumn())
				{
					currPoint = new Coordinate(currPoint.getRow(),currPoint.getColumn()+1);
					path.add(currPoint); 
				}
				else
				{
					if(waypoint.getRow()<currPoint.getRow())
						while(currPoint.getRow()>waypoint.getRow())
						{
							//increment the row
							//and add to the path
							currPoint = new Coordinate(currPoint.getRow()-1, currPoint.getColumn());
							path.add(currPoint);
						}
					while(currPoint.getColumn()> waypoint.getColumn())
					{
						currPoint = new Coordinate(currPoint.getRow(),currPoint.getColumn()-1);
						path.add(currPoint);
					}
				}
				currentLoc = waypoint;//This becomes my new location or 'origin'
			}
			if(notfinished)
			{//now the last point which is the destination
				//Get the waypoints in accordance to their distance from the previous point...at the start always origin
				waypoint = destination;
				//create points along path iteratively
				Coordinate currPoint = currentLoc;
				
				//Check direction of the next point
				//if next rowpoint is greater than this ones then
				if(waypoint.getRow()>currPoint.getRow())
				while(currPoint.getRow()<waypoint.getRow())
				{
					//increment the row
					//and add to the path
					currPoint = new Coordinate(currPoint.getRow()+1, currPoint.getColumn());
					path.add(currPoint);
				}
				else
				{
					
					if(waypoint.getColumn()>currPoint.getColumn())
						//now increment column
						while(currPoint.getColumn()< waypoint.getColumn())
						{
							currPoint = new Coordinate(currPoint.getRow(),currPoint.getColumn()+1);
							path.add(currPoint); 
						}
					while(currPoint.getRow()>waypoint.getRow())
					{
						//increment the row
						//and add to the path
						currPoint = new Coordinate(currPoint.getRow()-1, currPoint.getColumn());
						path.add(currPoint);
					}
				}
				if(waypoint.getColumn()>currPoint.getColumn())
				//now increment column
				while(currPoint.getColumn()< waypoint.getColumn())
				{
					currPoint = new Coordinate(currPoint.getRow(),currPoint.getColumn()+1);
					path.add(currPoint); 
				}
				else
				{
					if(waypoint.getRow()<currPoint.getRow())
						while(currPoint.getRow()>waypoint.getRow())
						{
							//increment the row
							//and add to the path
							currPoint = new Coordinate(currPoint.getRow()-1, currPoint.getColumn());
							path.add(currPoint);
						}
					while(currPoint.getColumn()> waypoint.getColumn())
					{
						currPoint = new Coordinate(currPoint.getRow(),currPoint.getColumn()-1);
						path.add(currPoint);
					}
				}
				currentLoc = waypoint;//This becomes my new location or 'origin'
			}
			if(currentLoc == destination) notfinished = false;
			if(notfinished)
			if(pointsInOrder.size() == 0)
			{
				currentLoc = destination;
				notfinished = false;
			}
			
		}
		path.add(destination);
		return path;
	}
    @Override
    public List<Coordinate> findPath() {
        
        ArrayList<Coordinate> path = new ArrayList<Coordinate>();
		ArrayList<ArrayList<Coordinate>> multiPath = new ArrayList<ArrayList<Coordinate>>();
		
		// Getting elements of the grid. Cells, origins, destinations and waypoints.
		Coordinate mapCells[][] = map.getCells();
		List<Coordinate> originCells = map.getOrigins();
		List<Coordinate> destCells = map.getDestinations();
		List<Coordinate> waypoints = map.getWaypoints();
		
		if (waypoints.size() != 0) {
			System.out.println("there are waypoints");
			path = findPath2();
		} else {
			
			// This loop is relevant to Task C.
			// Loop through all origin cells and all destination cells.
			for (Coordinate sourceCoord : originCells) {
				for (Coordinate destCoord : destCells) {
					
					visited.removeAll(visited);
					weight.clear();
					unvisited.removeAll(unvisited);
					
					// Populating unvisited with cells that can be visited (must not be impassable).
					for (int i = 0; i < mapCells.length; i++) {
						for (int j = 0; j < mapCells[i].length; j++) {
							if (mapCells[i][j].getImpassable() == false) {
								unvisited.add(mapCells[i][j]);
							}
						}
					}
					
					// Set the current cell in the loop as the origin.
					Coordinate currentCell = sourceCoord;
			
					// Make an array list that contains a path to the origin (a path to itself).
					ArrayList<Coordinate> pathToOrigin = new ArrayList<Coordinate>();
					pathToOrigin.add(currentCell);
					
					// Putting the origin in the known weight map.
					weight.put(currentCell, pathToOrigin);
					
					// Will loop until the algorithm finds a path to the destination or it runs out of cells to visit.
					// This loop is relevant to Tasks A and B.
					while (unvisited.isEmpty() == false && weight.containsKey(destCoord) == false) {
						
						// Mark the current cell as visited.
						visited.add(currentCell);
						
						// Remove the cell from unvisited.
						int indexToDelete = unvisited.indexOf(currentCell);
						unvisited.remove(indexToDelete);
						
						// Create an ArrayList to represent the path from the origin to that node.
						ArrayList<Coordinate> tempPath = new ArrayList<Coordinate>(weight.get(currentCell));
						
						// Create an ArrayList to represent neighbours of a given cell.
						ArrayList<Coordinate> neighbours = new ArrayList<Coordinate>();
						// If the path consists of one cell, get the cells that surround that cell.
						if (tempPath.size() == 1) {
							neighbours = getNeighbours(mapCells, currentCell.getRow(), currentCell.getColumn());
						} else {
							// If it contains more than one cell, obtain every neighbour of every cell in the path that has not been visited.
							for (Coordinate coord : visited) {
								ArrayList<Coordinate> tempNeighbours = new ArrayList<Coordinate>();
								tempNeighbours = getNeighbours(mapCells, coord.getRow(), coord.getColumn());
								for (Coordinate neighbour : tempNeighbours) {
									if (neighbours.contains(neighbour) == false && visited.contains(neighbour) == false) {
										neighbours.add(neighbour);
									}
								}
							}
						}
						
						// Debug printing stuff.
						System.out.println("Curr Cell Row: " + currentCell.getRow() + ", Column: " + currentCell.getColumn());
						System.out.println("\n" + "Known Weights");
						
						for (Coordinate coord : weight.keySet()) {
							System.out.println("Row: " + coord.getRow() + ", Column: " + coord.getColumn() + ", Shortest Path: " + costOfPath(weight.get(coord)));
						}
						
						System.out.println();
						
						// For every cell in neighbours list...
						for (Coordinate coord : neighbours) {
							// Shallow copy the path to the current node.
							ArrayList<Coordinate> pathToNeighbour = new ArrayList<Coordinate>(tempPath);
							// Add the neighbour to it.
							pathToNeighbour.add(coord);
							System.out.println(pathToNeighbour);
							// If the weight is new, put it in the map with the pathToNeighbour.
							if (weight.containsKey(coord) == false) {
								weight.put(coord, new ArrayList<Coordinate>(pathToNeighbour));
								System.out.println("Not in map, adding...");
							} else {
								// If there is already a weight to the current neighbour...
								if (weight.containsKey(coord) == true) {
									// Check if the new path is shorter than the stored path.
									if (costOfPath(pathToNeighbour) < costOfPath(weight.get(coord))) {
										System.out.println(costOfPath(pathToNeighbour) + " < " + costOfPath(weight.get(coord)));
										// Put path in map.
										weight.put(coord, new ArrayList<Coordinate>(pathToNeighbour));
										System.out.println("Is in map, updating...");
									}
								} else {
									System.out.println("Already in map!");
								}
							}
						}
						
						// Set the lowest weight to an arbitrary very high number.
						int lowestWeight = 10000000;
						Coordinate lowestNeighbour = null;
						
						// More debug printing stuff.
						System.out.println("Printing neighbours...");
						
						for (Coordinate coord : neighbours) {
							System.out.println(coord.getRow() + ", " + coord.getColumn() + ", Cost: " + weight.get(coord));
						}
						
						// For all neighbours, find the shortest path to a neighbouring coordinate.
						for (Coordinate coord : neighbours) {
							ArrayList<Coordinate> pathToNeighbour = new ArrayList<Coordinate>(tempPath);
							pathToNeighbour.add(coord);
							if (visited.contains(coord) == false && costOfPath(pathToNeighbour) <= lowestWeight) {
								lowestWeight = coord.getTerrainCost();
								lowestNeighbour = coord;
							}
						}
						
						// Add the lowest neighbour to the temporary path.
						tempPath.add(lowestNeighbour);
						
						// Set lowest neighbour to be next to visit.
						currentCell = lowestNeighbour;
							
						
					} // end main loop
					
					
					// Even more debug printing stuff.
					System.out.println("End of loop");
					
					for (Coordinate coord : weight.keySet()) {
						System.out.println("Row: " + coord.getRow() + ", Column: " + coord.getColumn() + ", Shortest Path: " + costOfPath(weight.get(coord)));
						System.out.println(weight.get(coord));
					}
					
					// Get the path to the destination cell and return it.
					multiPath.add(weight.get(destCoord));
				}
			}
			
			// This block is relevant to Task C.
			path = multiPath.get(0);
			
			for (ArrayList<Coordinate> candidatePath : multiPath) {
				if (costOfPath(candidatePath) < costOfPath(path)) {
					path = candidatePath;
				}
			}
		}
		
		
		
			
        return path;
    }


    @Override
    public int coordinatesExplored() {
        return visited.size();
    }
	
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
			// We don't count the last node as it is not traversed.
			for (int i = 1; i < path.size(); i++) {
				if (path.get(i) != null) {
					pathWeight += path.get(i).getTerrainCost();
				}
				
			}
		}
		
		return pathWeight;
	}



} // end of class DijsktraPathFinder
