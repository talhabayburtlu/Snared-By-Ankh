import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class CreateLevel extends GridPane {
	// Data fields of CreateLevel
	private ArrayList<String> lines = new ArrayList<String>();
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	CreateLevel(File file) throws FileNotFoundException { // Constructor for CreateLevel
		super.setAlignment(Pos.CENTER); // Setting alignment of GridPane.
		copyLines(file); // Calling method for copying lines from text file.
		sortLines(); // Calling method for sorting lines if they are disorganized.
		fillGrids(); // Calling method for filling indexes of GridPane.
	}
	
	public void copyLines(File file) throws FileNotFoundException { // A method for copying lines.
		ArrayList<String> returnLines = new ArrayList<String>();
		try(Scanner input = new Scanner(file)) {
			while (input.hasNextLine()) {
	    		String nextLine = input.nextLine();
	    		if (!nextLine.equals(""))
	    			returnLines.add(nextLine);
	    	}
		}
		setLines(returnLines); // Setting copied lines.
	}
	
	public void fillGrids() { // A method for filling indexes of GridPane.
		for (int i = 0 ; i < lines.size() ; i++) {
    		
    		String[] words = lines.get(i).split(","); // Splitting line to its tokens.
    		switch (words[1]) { // Switch case for determining which type tile is going to filled with.
    		case "Starter" : // Starter Type
    			if (words[2].equals("Horizontal")) // Horizontal property
    				tiles.add(new Tile("tiles/Starter-Horizontal.png",lines,Integer.parseInt(words[0]) - 1));	
    			else if (words[2].equals("Vertical")) // Vertical property
    				tiles.add(new Tile("tiles/Starter-Vertical.png",lines,Integer.parseInt(words[0]) - 1));
    			break;
    		case "End" : // End Type
    			if (words[2].equals("Horizontal")) // Horizontal property
    				tiles.add(new Tile("tiles/End-Horizontal.png",lines,Integer.parseInt(words[0] ) - 1));
    			else if (words[2].equals("Vertical")) // Vertical property
    				tiles.add(new Tile("tiles/End-Vertical.png",lines,Integer.parseInt(words[0]) - 1));
    			break;
    		case "Empty" : // Empty Type
    			if (words[2].equals("Free")) // Free property
    				tiles.add(new Tile("tiles/Empty-Free.png",lines,Integer.parseInt(words[0]) - 1));
    			else if (words[2].equals("none")) // None property
    				tiles.add(new Tile("tiles/Empty-None.png",lines,Integer.parseInt(words[0]) - 1));
    			break;
    		case "Pipe": // Pipe Type
    			if (words[2].equals("00")) // 00 property
    				tiles.add(new Tile("tiles/Pipe-00.png",lines,Integer.parseInt(words[0]) - 1));
    			else if (words[2].equals("01")) // 01 property
    				tiles.add(new Tile("tiles/Pipe-01.png",lines,Integer.parseInt(words[0]) - 1));
    			else if (words[2].equals("10")) // 10 property
    				tiles.add(new Tile("tiles/Pipe-10.png",lines,Integer.parseInt(words[0]) - 1));
    			else if (words[2].equals("11")) // 11 property
    				tiles.add(new Tile("tiles/Pipe-11.png",lines,Integer.parseInt(words[0]) - 1));
    			else if (words[2].equals("Horizontal")) // Horizontal property
    				tiles.add(new Tile("tiles/Pipe-Horizontal.png",lines,Integer.parseInt(words[0]) - 1));
    			else if (words[2].equals("Vertical")) // Vertical property
    				tiles.add(new Tile("tiles/Pipe-Vertical.png",lines,Integer.parseInt(words[0]) - 1));
    			break;
    		case "PipeStatic": // PipeStatic Type
    			if (words[2].equals("00")) // 00 property
    				tiles.add(new Tile("tiles/PipeStatic-00.png",lines,Integer.parseInt(words[0]) - 1));
    			else if (words[2].equals("01")) // 01 property
    				tiles.add(new Tile("tiles/PipeStatic-01.png",lines,Integer.parseInt(words[0]) - 1));
    			else if (words[2].equals("10")) // 10 property
    				tiles.add(new Tile("tiles/PipeStatic-10.png",lines,Integer.parseInt(words[0]) - 1));
    			else if (words[2].equals("11")) // 11 property
    				tiles.add(new Tile("tiles/PipeStatic-11.png",lines,Integer.parseInt(words[0]) - 1));
    			else if (words[2].equals("Horizontal")) // Horizontal property
    				tiles.add(new Tile("tiles/PipeStatic-Horizontal.png",lines,Integer.parseInt(words[0]) - 1));
    			else if (words[2].equals("Vertical")) // Vertical property
    				tiles.add(new Tile("tiles/PipeStatic-Vertical.png",lines,Integer.parseInt(words[0]) - 1));
    			break;
    		}
    		super.add(new ImageView(tiles.get(i)),((Integer.parseInt(words[0])- 1) % 4),((Integer.parseInt(words[0]) - 1 )/ 4)); // Adding tile to the GridPane.
    	}
	}
	
	public void setAllNotMovable() { // A method for setting all tiles not movable.
		for (Tile tiles : getTiles())
			tiles.setMovable(false); // Setting tile
	}
	
	public void sortLines() { // A method for sorting all lines based on their indexes.
		ArrayList<String> sortedLines = new ArrayList<String>();
		
		for (int i = 1 ; i < lines.size() + 1 ;i++)
			for (int j = 0 ; j < lines.size() ; j++) {
				String line = lines.get(j);
				String[] tokens = line.split(",");
				if (Integer.parseInt(tokens[0]) == i)
					sortedLines.add(lines.get(j));
				
			}
		setLines(sortedLines);	// Setting sorted lines.
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
}
