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
	private Map<Integer, List<Coordinate>> mappath;
	
	private List<Coordinate> mapCoordinates;
	private List<List<Coordinate>> paths;
	

    public DijkstraPathFinder(PathMap map) {
		this.map = map;	
		visited = new ArrayList<Coordinate>();
		weight = new HashMap<Coordinate, ArrayList<Coordinate>>();
		unvisited = new ArrayList<Coordinate>();
		mapCoordinates= this.getAllCoordinates();
		paths = new LinkedList<List<Coordinate>>();
		this.mappath = new HashMap<Integer,List<Coordinate>>();
    }
    /**
     * Finds shortest path that passes through waypoints ...Short for test D
     * @return returns a list containing the coordinates of the path
     */
    
    private List<Coordinate> getAllCoordinates()
    {
    	//flatten the map array into a list
    	List<Coordinate> data = new LinkedList<Coordinate>();
    	Coordinate[][] mapCells = map.getCells();
    	for(int x =0;x<map.getRowSize();x++)
    	{
    		for(int y=0;y<map.getColSize();y++)
    		{
    			data.add(mapCells[x][y]);
    		}
    	}
    	System.out.println(data);
    	return data;
    }
    
    private List<Coordinate> getpathfinal()
    {
    	List<Coordinate> path = new ArrayList<Coordinate>();
		
		// Getting elements of the grid. Cells, origins, destinations and waypoints.
		Coordinate mapCells[][] = map.getCells();
		//System.out.println("map cells");
		//System.out.println(mapCells[0]);
		List<Coordinate> originCells = map.getOrigins();
		List<Coordinate> destCells = map.getDestinations();
		List<Coordinate> list = new LinkedList<Coordinate>();
		//this.getFullPaths1(originCells.get(0), waypoints.get(0), list, Direction.RIGHT, Direction.DOWN);	
		List<Coordinate> fullpath  = new LinkedList<Coordinate>();
		List<Coordinate> waypoints = this.getWayPointsInOrder(originCells.get(0));//Get from the first origin
		for(Coordinate origin: originCells)
		{
			for(Coordinate dest: destCells)
			{
				int waypIndex =0;
				Coordinate startCell = origin;
				if(waypoints != null && waypoints.size()>0)
				{
					while(waypIndex < waypoints.size())
					{
						this.getFullPaths1(startCell, waypoints.get(waypIndex++), list, Direction.RIGHT, Direction.DOWN);
						startCell = waypoints.get(waypIndex-1);//set the current waypoint as the new destination
						//get the shortest path now
						if(paths != null && paths.size()>0)
						{
							List<Coordinate> shortpath = paths.get(0);
							for(List<Coordinate> l : paths)
							{
								if(l.size() < shortpath.size())
								{
									shortpath = l;
								}
							}
							//add the path to the list
							fullpath.addAll(shortpath);
							paths.clear();//remove every item
						}
						//System.out.println("NEW************************************NEW***************************************************************");
					}
				}
				else
				{
					startCell = origin;//set the waypoint as the startcell
				}
				//now link with the destination
				this.getFullPaths1(startCell, dest, list, Direction.RIGHT, Direction.DOWN);
				//get the shortest path now
				 
				if(paths != null && paths.size()>0)
				{
					List<Coordinate> shortpath = paths.get(0);
					for(List<Coordinate> l : paths)
					{
						if(l.size() < shortpath.size())
						{
							shortpath = l;
						}
					}
					fullpath.addAll(shortpath);
					paths.clear();//remove every item
				}
			}
		}
		
		
		System.out.println("........................ok................");
		System.out.println(paths);
		//List<Coordinate> p = paths.get(0);
		System.out.println("paths size: "+fullpath.size());
		this.visited.addAll(fullpath);
		
		return fullpath;//retList;
    	
    }
   
    private void getFullPaths1(Coordinate current, Coordinate destination, List<Coordinate> list,Direction curr, Direction prev)
    {
    	//System.out.println("*************paths****************"+paths.size());
    	if(paths.size()>=1)//later use timers to get shortest path
    	{
    		//System.out.println("pathsize is enoyght: "+paths.size());
    		return;
    	}
    	if(current == null) {
    		//.out.println("blockedk");
    		list = null;return;}
    	Coordinate mapCells[][] = map.getCells();
    	Coordinate left =null,right=null, up=null, down=null;
    	//if(!(current.getColumn()>0 && current.getColumn()< map.getColSize()) || !(current.getRow()>0 && current.getRow()<map.getRowSize()))
    	if(mapCells[current.getRow()][current.getColumn()] == null)
    	{
    		//does not exist
    		//list.clear();
    		//list = null;//clear the list
    		//System.out.println("exists1");
    		return;
    	}
    	if(mapCells[current.getRow()][current.getColumn()].equals(current))
    	{
    		//found it
    		//System.out.println("exists");
    		//System.out.println(current);
    		if(current.getImpassable()) 
			{
    			
    			//System.out.println("impassable");
    			//Change the direction
    		
    			/*if(prev == Direction.LEFTLEVEL)
		    	{
		    		//go to dir2
		    		//this.getFullPaths1(current, destination, list, curr, prev);
		    		if(prev ==  Direction.DOWN)
			    	{
			    		System.out.println("going down");
			    		this.getFullPaths1(down, destination, list,curr, prev);
			    	}
			    	else if(prev == Direction.UP)
			    	{
			    		System.out.println("going up");
			    		this.getFullPaths1(up, destination, list,curr , prev);
			    	}
		    	}
		    	else if(curr == Direction.RIGHLEVEL)
		    	{
		    		//go to dir1
		    		if(curr == Direction.LEFT)
	    			{
	    				System.out.println("going lrft");
	    		    	this.getFullPaths1(left, destination,list,curr , prev);
	    			}
	    			
	    		   	else if(curr==Direction.RIGHT)
	    		   	{
	    		    		System.out.println("going right");
	    		    		this.getFullPaths1(right, destination, list,curr , prev);
	    		   	}
		    	}*/
    			//System.out.println("out of impassable");
				return;
			}
    		else
    		{
    			if(current.equals(destination))
        		{
        			//System.out.println("........................................................finali:");
        			list.add(current);
        			this.paths.add(list);
        			
        			return;
        		}
    			//continue with direction
    			int currCol =current.getColumn(), currRow = current.getRow();
    	    	//Coordinate left =null,right=null, up=null, down=null;
    	    	if(currCol -1 >=0)
    	    	left = mapCells[currRow][current.getColumn()-1];
    	    	if(currCol +1 < map.getColSize())
    	    	right = mapCells[current.getRow()][current.getColumn()+1];
    	    	if(currRow +1 <map.getRowSize())
    	    	up = mapCells[current.getRow()+1][current.getColumn()];
    	    	if(currRow -1>=0)
    	    	down = mapCells[current.getRow()-1][current.getColumn()];
    	    	 
    	    	Direction dir =Direction.RIGHLEVEL;
    	    	Direction dir1 = Direction.LEFTLEVEL;
    	    	if(destination.getColumn()> current.getColumn())
    	    	{
    	    		dir = Direction.RIGHT;
    	    	}
    	    	else if(destination.getColumn()<current.getColumn())
    	    	{
    	    		dir = Direction.LEFT;
    	    	}
    	    	else dir= Direction.RIGHLEVEL;
    	    	if(destination.getRow()>current.getRow())
    	    	{
    	    		dir1 = Direction.UP;
    	    	}
    	    	else if(destination.getRow()<current.getRow())
    	    	{
    	    		dir1 = Direction.DOWN;
    	    	}
    	    	else dir1 = Direction.LEFTLEVEL;
    	    	//System.out.print(dir);System.out.println(dir1);
    	    	//list for storing all traversed
    	    	List<Coordinate> l = new LinkedList<Coordinate>();
    	    	
    	    	l.addAll(list);
    	    	l.add(current);
    			if(dir == Direction.LEFT)
    			{
    				//System.out.println("going lrft");
    		    	this.getFullPaths1(left, destination, l, dir, dir);
    			}
    			
    		   	else if(dir==Direction.RIGHT)
    		   	{
    		    		//System.out.println("going right");
    		    		this.getFullPaths1(right, destination, l, dir, dir1);
    		   	}
		    	if(dir1 ==  Direction.DOWN)
		    	{
		    		//System.out.println("going down");
		    		this.getFullPaths1(down, destination, l,dir1, dir);
		    	}
		    	else if(dir1 == Direction.UP)
		    	{
		    		//System.out.println("going up");
		    		this.getFullPaths1(up, destination, l,dir1 , dir);
		    	}
		    	
		    	if(dir1 == Direction.LEFTLEVEL)
		    	{
		    		//go to dir2
		    		//this.getFullPaths1(current, destination, list, curr, prev);
		    		if(dir1 ==  Direction.DOWN)
			    	{
			    		//System.out.println("going down");
			    		this.getFullPaths1(down, destination, l,dir1, dir);
			    	}
			    	else //if(dir1 == Direction.UP)
			    	{
			    		//System.out.println("going up");
			    		this.getFullPaths1(up, destination, l,dir1 , dir);
			    	}
		    		
		    	}
		    	else if(dir == Direction.RIGHLEVEL)
		    	{
		    		//go to dir1
		    		if(dir == Direction.LEFT)
	    			{
	    				//System.out.println("going lrft");
	    		    	this.getFullPaths1(left, destination, l, dir, dir);
	    			}
	    			
	    		   	else if(dir==Direction.RIGHT)
	    		   	{
	    		    		//System.out.println("going right");
	    		    		this.getFullPaths1(right, destination, l, dir, dir1);
	    		   	}
		    	}
    		    	
    		}
    		//System.out.print("found");
    		//list.add(current);
    		//this.paths.add(list);
    		
    	}
    	//System.out.println("not in  map");
    	//System.out.println(current);
    	//System.out.println("and map");
    	//System.out.println(mapCells[current.getRow()][current.getColumn()]);
    	
    	
    }
    
   
  
    public List<Coordinate> findPath2()
	{	
    	return this.getpathfinal();
    }
    
    private List<Coordinate> getWayPointsInOrder(Coordinate origin)
    {
    	
    	List<Coordinate> path = new ArrayList<Coordinate>();
		
		// Getting elements of the grid. Cells, origins, destinations and waypoints.
		Coordinate mapCells[][] = map.getCells();
		List<Coordinate> originCells = map.getOrigins();
		List<Coordinate> destCells = map.getDestinations();
		List<Coordinate> waypoints = map.getWaypoints();
		
		if(waypoints == null || waypoints.size() ==0) return null; 
		//find the waypoint that is nearer to the origin
		List<Coordinate> pointsInOrder = new LinkedList<Coordinate>();//Stores the waypoints in order
		//Coordinate origin = originCells.get(0);//Assuming the origin is only one in the list
		Coordinate destination = destCells.get(0);//Assume one destination for task D's sake
		Coordinate waypoint = waypoints.get(0); //Say this is the nearest, ?prove me wrong...
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
		return pointsInOrder;
    }

		
		
    
	
    @Override
    public List<Coordinate> findPath() {
        
        List<Coordinate> path = new ArrayList<Coordinate>();
		ArrayList<ArrayList<Coordinate>> multiPath = new ArrayList<ArrayList<Coordinate>>();
		
		// Getting elements of the grid. Cells, origins, destinations and waypoints.
		Coordinate mapCells[][] = map.getCells();
		List<Coordinate> originCells = map.getOrigins();
		List<Coordinate> destCells = map.getDestinations();
		List<Coordinate> waypoints = map.getWaypoints();
		System.out.println("printing");
		for(Coordinate[] cell: mapCells)
		{
			System.out.println(cell);
		}
		// Printing all the unvisited coordinates.
		for (Coordinate coord : unvisited) {
			System.out.println("Row: " + coord.getRow() + ", Column: " + coord.getColumn());
		}
		
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
		ArrayList<Coordinate> shortestPath = multiPath.get(0);
		
		for (ArrayList<Coordinate> candidatePath : multiPath) {
			if (costOfPath(candidatePath) < costOfPath(shortestPath)) {
				shortestPath = candidatePath;
			}
		}
		
			
        return shortestPath;
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

	enum Direction
	{
		UP,
		DOWN,
		RIGHT,
		LEFT,
		RIGHLEVEL,
		LEFTLEVEL
	};

} // end of class DijsktraPathFinder
