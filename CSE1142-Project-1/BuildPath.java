import javafx.geometry.Point2D;
import javafx.scene.shape.*;
import javafx.scene.shape.Path;
import java.util.ArrayList;

public class BuildPath extends Path {
	// Data fields of BuildPath
	private ArrayList<String> lines = new ArrayList<String>();
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private int previousRow , previousColumn , currentRow , currentColumn ;
	private boolean isCompleted;
	private boolean isContinue = true ;
	private int animationCount = 0;
	
	BuildPath(ArrayList<String> lines,ArrayList<Tile> tiles) { // Constructor for BuildPath.
		setLines(lines); // Setting lines.
		setTiles(tiles); // Setting tiles.
		buildPath(); // Calling method buildPath for building path.
	}

	public void buildPath() { // A method for building path.
		isContinue = true; // Reapplying continue condition again.
		
		super.getElements().clear(); // Clearing elements.
		setCoordinates(); // Calling method setCoordinates for setting coordinates of tiles.
		
		int starterTileIndex = findStarter(); // Finding starter tile.
		
		previousRow = starterTileIndex / 4; // Determining previous row.
		previousColumn = starterTileIndex  % 4; // Determining previous column.
		currentRow = previousRow; // Determining current row.
		currentColumn = previousColumn; // Determining current column.
		
		
		MoveTo start = new MoveTo(previousColumn * 150 + 75 , previousRow * 150 + 75); // Creating start point for path.
		super.getElements().add(start); // Adding start point to the elements.
		
		if (tiles.get(starterTileIndex).getProperty().equals("Horizontal")) // Checking property, and defining next index.
			currentColumn = previousColumn - 1;
		else
			currentRow = previousRow + 1;
		
		LineTo startLine = new LineTo(tiles.get(previousRow * 4 + previousColumn).getSecondCoordinate().getX() ,tiles.get(previousRow * 4 + previousColumn).getSecondCoordinate().getY()); // Creating start line for path.
		super.getElements().add(startLine); // Adding start line to the elements
		
		while (isContinue) { // A loop for determining and adding remaining lines and arcs to the path.
			
			if (currentColumn < 0 || currentRow < 0 || currentColumn > 3 || currentRow > 3) { // Checking if current index out of borders.
				setCompleted(false);
				break; 
			}
				
			
			if (tiles.get(currentRow * 4 + currentColumn).getType().equals("End")) { // Checking if current index is end tile.
				LineTo end = new LineTo(tiles.get(currentRow * 4 + currentColumn).getFirstCoordinate().getX() ,tiles.get(currentRow * 4 + currentColumn).getFirstCoordinate().getY()); // Creating end line to path.
				super.getElements().add(end); // Adding end line to the elements.
				setCompleted(true);
				break;
			}
				
			
			Point2D currentCoordinate =chooseFinalCoordinate(tiles.get((currentRow) * 4 + currentColumn).getFirstCoordinate() ,tiles.get((currentRow) * 4 + currentColumn).getSecondCoordinate() ,
					(previousRow * 4 + previousColumn) , (currentRow * 4 + previousColumn)); // Calling chooseFinalCoordinate for determining which coordinate should use for creating line or arc.
			
			if (currentCoordinate == null) { // Checking coordinates matched or not.
				setCompleted(false);
				break;
			}
			
			String currentIndexProperty = tiles.get(currentRow * 4 + currentColumn).getProperty(); // Making a string for current index's property.
			switch (currentIndexProperty) { // Switch-case for determining which type of path (arc or line) should created and added.
			case "00" : case "01" : case "10" : case "11" : // Arc Path
				ArcTo arc = new ArcTo(75,75,0,currentCoordinate.getX(),currentCoordinate.getY(),false,false); // Creating arc path with tile's certain coordinates.
				
				previousRow = currentRow; // Updating previousRow.
				previousColumn = currentColumn; // Updating previousColumn.
				
				// Checking way of the path.
				if (currentIndexProperty.equals("00")) { // Case 00
					if(currentCoordinate.equals((Object)tiles.get(currentRow * 4 + currentColumn).getFirstCoordinate())) { // Checking way is to the left
							currentColumn--;
							arc.setSweepFlag(true); // Setting path to reverse way based on symmetry.
					}
					else // else, way is to the up.
						currentRow--;
				}
				else if (currentIndexProperty.equals("01")) { // Case 01
					if(currentCoordinate.equals((Object)tiles.get(currentRow * 4 + currentColumn).getFirstCoordinate())) // Checking way is to the right
						currentColumn++;
					else {// else , way is to the up
						currentRow--;
						arc.setSweepFlag(true); // Setting path to reverse way based on symmetry.
					}
				}
				else if (currentIndexProperty.equals("10")) { // Case 10
					if(currentCoordinate.equals((Object)tiles.get(currentRow * 4 + currentColumn).getFirstCoordinate())) { // Checking way is to the left
						currentColumn--;
					}
					else { // else , way is to the down
						currentRow++;
						arc.setSweepFlag(true); // Setting path to reverse way based on symmetry.
					}
				}
				else if (currentIndexProperty.equals("11")) { // Case 11
					if(currentCoordinate.equals((Object)tiles.get(currentRow * 4 + currentColumn).getFirstCoordinate())) {// Checking way is to the right 
						currentColumn++;
						arc.setSweepFlag(true); // Setting path to reverse way based on symmetry.
					}
					else // else , way is to the down
						currentRow++;
					
				} 
				super.getElements().add(arc); // Adding arc path to the elements.
				break;
			case "Horizontal" : case "Vertical" : // Horizontal or Vertical Path.
				LineTo line = new LineTo(currentCoordinate.getX(),currentCoordinate.getY()); // Creating line path with tile's certain coordinates.
				super.getElements().add(line); // Adding line path to the elements.
				
				previousRow = currentRow; // Updating previousRow.
				previousColumn = currentColumn; // Updating previousColumn.
				
				// Checking way of the path.
				if (currentIndexProperty.equals("Horizontal")) { // Case Horizontal
					if(currentCoordinate.equals((Object)tiles.get(currentRow * 4 + currentColumn).getFirstCoordinate())) // If way is to the left
						currentColumn--;
					else // else , way is to the right
						currentColumn++;
				}
				else if (currentIndexProperty.equals("Vertical")) { // Case Vertical
					if(currentCoordinate.equals((Object)tiles.get(currentRow * 4 + currentColumn).getFirstCoordinate())) // If way is to the up
						currentRow--;
					else // else , way is to the down
						currentRow++;
				} break;
				default : isContinue = false; // If property doesn't match with given cases, make is continue condition as false.
			}
		}
			
	}
	
	
	
	public Point2D chooseFinalCoordinate(Point2D firstCoordinate , Point2D secondCoordinate , int previousIndex , int currentIndex) { // A method for determining which coordinate should use for creating line or arc.
		// Checking previous tile's end coordinate with current's first coordinate. If it match's return second coordinate for building path.
		if (tiles.get(previousIndex).getFirstCoordinate().equals(firstCoordinate) || tiles.get(previousIndex).getSecondCoordinate().equals(firstCoordinate)) 
			return secondCoordinate;
		// Checking previous tile's end coordinate with current's second coordinate. If it match's return first coordinate for building path.
		else if (tiles.get(previousIndex).getFirstCoordinate().equals(secondCoordinate) || tiles.get(previousIndex).getSecondCoordinate().equals(secondCoordinate))
			return firstCoordinate;
		else // If coordinate's does not match, return null.
			return null;
	}
	
	public int findStarter() { // A method for finding starter tile's index.
		for (int i = 0 ; i < tiles.size() ; i++) {
			if (tiles.get(i).getType().equals("Starter"))
				return i;
		}
		return 0;
	}
	
	public int findEnd() { // A method for finding end tile's index.
		for (int i = 0 ; i < tiles.size() ; i++) {
			if (tiles.get(i).getType().equals("End"))
				return i;
		}
		return 0;
	}
	
	public boolean isCompleted() { // A method for determining path is completed or not.
		int endTileIndex = findEnd(); // Calling findEnd method for finding end tile's index.
		return super.contains((endTileIndex/4)*150 + 75 ,(endTileIndex % 4) *150 + 75); // Checking path contains end tile's 
	}
	
	public void setCoordinates() { // A method for setting coordinates of tiles.
		
		for (int i = 0 ; i < tiles.size() ; i++) {
			String[] words = {tiles.get(i).getIndex() + "" , tiles.get(i).getType() , tiles.get(i).getProperty()}; // Creating array with index,type and property.
			switch (words[1]) { // Switch case for tile type.
			
				case "Starter" : // Starter Type
				case "End" : // End Type
					tiles.get(i).setFirstCoordinate(new Point2D((i % 4) * 150 + 75, (i / 4) * 150 + 75)); // Center
					if (words[2].equals("Horizontal"))
						tiles.get(i).setSecondCoordinate(new Point2D((i % 4) * 150, (i / 4) * 150 + 75)); // Left
					else if (words[2].equals("Vertical"))
						tiles.get(i).setSecondCoordinate(new Point2D((i % 4) * 150 + 75,((i / 4) + 1) * 150)); break; // Down
				case "Pipe": // Pipe Type
				case "PipeStatic": // PipeStatic Type
					if (words[2].equals("00")) {
						tiles.get(i).setFirstCoordinate(new Point2D((i % 4) * 150 ,(i / 4) * 150 + 75)); // Left
						tiles.get(i).setSecondCoordinate(new Point2D((i % 4) * 150 + 75, (i / 4) * 150)); // Up
					}
					else if (words[2].equals("01")) {
						tiles.get(i).setFirstCoordinate(new Point2D(((i % 4) + 1) * 150,(i / 4) * 150 + 75)); // Right
						tiles.get(i).setSecondCoordinate(new Point2D((i % 4) * 150 + 75, (i / 4) * 150)); // Up
					}
					else if ( words[2].equals("10")) {
						tiles.get(i).setFirstCoordinate(new Point2D((i % 4) * 150 ,(i / 4) * 150 + 75)); // Left
						tiles.get(i).setSecondCoordinate(new Point2D((i % 4)  * 150 + 75 , ((i / 4) + 1) * 150)); // Down
					}
					else if (words[2].equals("11")) {
						tiles.get(i).setFirstCoordinate(new Point2D(((i % 4) + 1) * 150,(i / 4) * 150 + 75)); // Right
						tiles.get(i).setSecondCoordinate(new Point2D((i % 4)  * 150 + 75 , ((i / 4) + 1) * 150)); // Down
					} 
					else if (words[2].equals("Horizontal")) {
						tiles.get(i).setFirstCoordinate(new Point2D((i % 4) * 150 ,(i / 4) * 150 + 75)); // Left
						tiles.get(i).setSecondCoordinate(new Point2D(((i % 4) + 1) * 150,(i / 4) * 150 + 75)); // Right
					}
					else if (words[2].equals("Vertical")) {
						tiles.get(i).setFirstCoordinate(new Point2D((i % 4) * 150 + 75, (i / 4) * 150)); // Up
						tiles.get(i).setSecondCoordinate(new Point2D((i % 4)  * 150 + 75 , ((i / 4) + 1) * 150)); // Down
					} break;
			}
		}
		
	}
	
	// Getter and setter methods.
	public ArrayList<String> getLines() {
		return lines;
	}

	public void setLines(ArrayList<String> lines) {
		this.lines = lines;
	}
	
	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}

	public boolean getCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public boolean isContinue() {
		return isContinue;
	}

	public void setContinue(boolean isContinue) {
		this.isContinue = isContinue;
	}

	public int getAnimationCount() {
		return animationCount;
	}

	public void setAnimationCount(int animationCount) {
		this.animationCount = animationCount;
	}
}
