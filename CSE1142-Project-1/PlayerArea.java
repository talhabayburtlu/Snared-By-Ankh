import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

//This class using while swapping tiles
public class PlayerArea {
	private ArrayList<ImageView> ImageArr = new ArrayList<>(); //This image array stands for changing pictures while swapping 
	private ArrayList<Integer> Indexes = new ArrayList<>(); //This array stores indexes while swapping
	private ArrayList<Tile> Tiletemp = new ArrayList<>(); //While swapping tiles, stores first tile
	Integer[] count = {0}; //Every change counts as move in array
	int moves=0;

	public PlayerArea() {} //No-arg constructor
	
	
	public void MouseClicked(CreateLevel currentLevel,MouseEvent e) {
		//This method stores indexes and image of first tile
    		Node source = e.getPickResult().getIntersectedNode();
    		if (GridPane.getColumnIndex(source) != null || GridPane.getRowIndex(source) != null) { //To avoid runtime errors
    			Integer colIndex1 = GridPane.getColumnIndex(source);
	    		Integer rowIndex1 = GridPane.getRowIndex(source);
	    		Indexes.add(colIndex1);
	    		Indexes.add(rowIndex1);
	    	
    			ImageArr.add((ImageView)source);

    		}
	}
	
	public void MouseReleased(CreateLevel currentLevel, BuildPath path,MouseEvent e) {
			//This method changes indexes and images of last tile and first tile
    		Node source = e.getPickResult().getIntersectedNode();
    		if (source != null &&(GridPane.getColumnIndex(source) != null || GridPane.getRowIndex(source) != null) ) {//To avoid runtime errors
    			Integer colIndex2 = GridPane.getColumnIndex(source);
    			Integer rowIndex2 = GridPane.getRowIndex(source);
    			Indexes.add(colIndex2);
    			Indexes.add(rowIndex2);
    			ImageArr.add((ImageView)source);
    			
    			//This ifs manages player to move only 1 index and change only pipes with free tiles if it is moveable
    			if(currentLevel.getTiles().get(Indexes.get(1)*4+Indexes.get(0)).getMovable() == true && 
    					currentLevel.getTiles().get(Indexes.get(3)*4+Indexes.get(2)).getMovable() == true &&
    					((Math.abs((Indexes.get(0)-Indexes.get(2))) == 0 && Math.abs((Indexes.get(1)-Indexes.get(3))) == 1) || 
    					(Math.abs((Indexes.get(0)-Indexes.get(2))) == 1 && Math.abs((Indexes.get(1)-Indexes.get(3))) == 0))) {
    				if(!currentLevel.getTiles().get(Indexes.get(1)*4+Indexes.get(0)).getProperty().equals("Free") &&
    						currentLevel.getTiles().get(Indexes.get(3)*4+Indexes.get(2)).getProperty().equals("Free")) {
		    			SwapTile(); //Call swaptile method
		    			
		    			count[0] = ++moves; //Increase count
		    			
		    			Tiletemp.add(currentLevel.getTiles().get(Indexes.get(3)*4+Indexes.get(2))); //Add changed tile to array
		    			
		    			//Change tiles with new indexes
		    			currentLevel.getTiles().set(Indexes.get(3)*4+Indexes.get(2), currentLevel.getTiles().get(Indexes.get(1)*4+Indexes.get(0)));    			
		    			currentLevel.getTiles().set(Indexes.get(1)*4+Indexes.get(0), Tiletemp.get(0)); 
		    			
		    			path.setTiles(currentLevel.getTiles()); //Update path with new tiles
    				}
    			}
    			//Clear all things after using
    			ImageArr.clear();
    			Indexes.clear();
    			Tiletemp.clear();
    			
    			path.buildPath(); //Build path again
    		}
	}
	
	public void SwapTile() {
		//Swapping tile's indexes
		if (getImageArr().size() == 2) { //images size has to be max 2 for swapping, can't swapping with itself
			//Sets, new indexes for swapping
			GridPane.setRowIndex(ImageArr.get(0), Indexes.get(3));
			GridPane.setColumnIndex(ImageArr.get(0), Indexes.get(2));
			GridPane.setRowIndex(ImageArr.get(1), Indexes.get(1));
			GridPane.setColumnIndex(ImageArr.get(1), Indexes.get(0));
		}
	}

	//GETTERS & SETTERS
	public ArrayList<ImageView> getImageArr() {
		return ImageArr;
	}

	public void setImageArr(ArrayList<ImageView> imageArr) {
		ImageArr = imageArr;
	}


	public ArrayList<Integer> getIndexes() {
		return Indexes;
	}

	public void setIndexes(ArrayList<Integer> indexes) {
		Indexes = indexes;
	}
}

