import javafx.scene.image.Image;
import java.util.ArrayList;
import javafx.geometry.Point2D;

public class Tile extends Image{
	// Data fields of Tile.
	private String type ;
	private String property ;
	private int index;
	private Point2D firstCoordinate = new Point2D(0,0);
	private Point2D secondCoordinate = new Point2D(0,0);
	private boolean movable;
	
	public Tile(String directory , ArrayList<String> features, int index) { // Constructor for Tile.
		super(directory); // Setting directory of image.
		setIndex(index); // A method for setting index of tile.
		String[] words = features.get(index).split(","); // Splitting tokens to ,index, type, way.
		setType(words[1]); // Setting type.
		setProperty(words[2]); // Setting property.
		identifyMovable(); // Calling indetifyMovable method for identifying tile is movable or not.
	}
	
	public void identifyMovable() { // A method for identifying tile is movable or not.
		if (getType().equals("Empty") || getType().equals("Pipe")) // Checking it's type empty or pipe.
			setMovable(true);
	}
	
	// Getter and setter methods.
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public Point2D getFirstCoordinate() {
		return firstCoordinate;
	}

	public void setFirstCoordinate(Point2D firstCoordinate) {
		this.firstCoordinate = firstCoordinate;
	}

	public Point2D getSecondCoordinate() {
		return secondCoordinate;
	}

	public void setSecondCoordinate(Point2D secondCoordinate) {
		this.secondCoordinate = secondCoordinate;
	}
	
	public boolean getMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}
}
