import javafx.animation.Animation.Status;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/* Talha BAYBURTLU 150118066
 * Melik Çaðan ODUNCUOÐLU 150118070
 * 
 * This is a game built with JavaFX. Game purpose is to build pipe's path correctly.
 * Starter,PipeStatic and End tiles can't move and the others can move left-up and vise versa if there is
 * a Empty-Free tile. Tile's can be moved by pressing to it and then dragging-releasing it on 
 * another tile. There are several challenging levels. Player can only play one level at a time.
 * After all levels are successfully completed it finishes by pressing quit button. */

public class MainGame extends Application {
	boolean Screen = false;
	boolean soundOn = true; //This boolean stands for mute and unmute
	Scene scene; //Stands for keeping fullscreen
	@Override
    public void start(Stage stage) throws FileNotFoundException {
		
		StackPane finalPane = new StackPane(); //Stack pane
		//Pictures that used in game
		ImageView name = new ImageView(new Image("images/Name.png"));
		ImageView Htp = new ImageView("images/HTPPicture.png");
		ImageView Names = new ImageView("images/Credits.png");
		ImageView Story = new ImageView("images/Story.png");
		//Button that used in game
		Button startStory = new Button("" , new ImageView(new Image("images/Start.png")));
		Button start = new Button("" , new ImageView(new Image("images/Start.png")));
		Button howToPlay = new Button("" , new ImageView(new Image("images/HowToPlay.png")));
		Button sound = new Button("" , new ImageView(new Image("images/SoundOn.png")));
		Button fullScreen = new Button("" , new ImageView(new Image("images/FullScreen.png")));
		Button quit = new Button("" , new ImageView(new Image("images/Quit.png")));
		Button back = new Button("" , new ImageView(new Image("images/Back.png")));
		Button back2 = new Button("" , new ImageView(new Image("images/Back.png")));
		Button credits = new Button("" , new ImageView(new Image("images/CreditsButton.png")));
		
		//This setStyles clear white borders in buttons
		startStory.setStyle("-fx-background-color: transparent;");
		start.setStyle("-fx-background-color: transparent;");
		howToPlay.setStyle("-fx-background-color: transparent;");
		sound.setStyle("-fx-background-color: transparent;");
		fullScreen.setStyle("-fx-background-color: transparent;");
		quit.setStyle("-fx-background-color: transparent;");
		back.setStyle("-fx-background-color: transparent;");
		back2.setStyle("-fx-background-color: transparent;");
		credits.setStyle("-fx-background-color: transparent;");
	
		
		String musicFile = "music/SandCastle.mp3"; //Music file 

		Media music = new Media(new File(musicFile).toURI().toString()); //Creating media with music file
		MediaPlayer mediaPlayer = new MediaPlayer(music); //Turn media to mediaplayer
		mediaPlayer.play(); //Automatic play
		mediaPlayer.setVolume(0.5); //Volume down defaultly
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Music iterates infinite times.
		
		VBox menu = new VBox(0); //For adding menu items
		VBox Howtoplay = new VBox(0); //For picture and back buttons
		VBox Credits = new VBox(0); //For credits and back buttons
		StackPane StoryBox = new StackPane(); //For adding story picture and start button
		HBox soundAndFullScreen = new HBox(0); //Horizontal box for Sound and fullscreen buttons
		soundAndFullScreen.getChildren().addAll(sound,fullScreen);//Adding buttons to HBox
		menu.setAlignment(Pos.CENTER); //Set position of menu to center
		soundAndFullScreen.setAlignment(Pos.CENTER); //Set position of sound and fullscreen buttons to center
		menu.getChildren().addAll(name,startStory,howToPlay,soundAndFullScreen,credits,quit); //Adding items to menu
		
		menu.prefHeightProperty().bind(finalPane.heightProperty());//Set menu height and width
		menu.prefWidthProperty().bind(finalPane.widthProperty());
		menu.setTranslateY(35); //Set menu's position 
		
		startStory.setOnMouseClicked(e ->  { //When this button event activated, story picture and start game button appears
			Story.fitHeightProperty().bind(finalPane.heightProperty().add(-170));//Set width and height properties
			Story.fitWidthProperty().bind(finalPane.widthProperty());
			start.setTranslateY(finalPane.getHeight() * 4 / 9);
			StoryBox.getChildren().addAll(Story, start); //Add picture and start button to pane
			finalPane.getChildren().add(StoryBox); //Add stack pane to main pane
		
		});
		
		start.setOnAction(e ->  {	//Start game button 
			
			try { // Try-catch for FileNotFound exception
				startGame(stage); //Start game method
				mediaPlayer.stop(); //Stop the menu music
			} 
			catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
		});
		
		howToPlay.setOnAction(e ->  { //HowtoPlay button
			Htp.setFitHeight(620); //Set width and height the picture
			Htp.setFitWidth(900);
			Howtoplay.getChildren().addAll(Htp, back); //Add picture and back button to VBox
			Howtoplay.setAlignment(Pos.CENTER); //Set center position
			finalPane.getChildren().add(Howtoplay); //Add VBox to main pane
			
		});
		
		credits.setOnAction(e ->  { //Credits button
			Names.setFitHeight(620); //Set width and height the picture
			Names.setFitWidth(900);
			Credits.getChildren().addAll(Names,back2); //Add picture and back2 button to VBox
			Credits.setAlignment(Pos.CENTER); //Set center position
			finalPane.getChildren().add(Credits); //Add Vbox to main pane
			
		});
		
		back.setOnAction(e -> { //Howtoplay back button
			
			Howtoplay.getChildren().removeAll(Htp, back); //Remove items in Vbox to avoid duplicate error
			finalPane.getChildren().remove(Howtoplay); //Remove Vbox from main pane
				
		});
		
		
		back2.setOnAction(e -> { //Credits back button
			//Has to be another back button because of duplicate errors
		Credits.getChildren().removeAll(Names, back2); //Remove items in Vbox to avoid duplicate error
		finalPane.getChildren().remove(Credits); //Remove Vbox from main pane
				
		});
		
		fullScreen.setOnMouseClicked(e -> { //Fullscreen button
			if (stage.isFullScreen()) { //If fullscreen is on then off the fullscreen
				stage.setFullScreen(false); //Set fullscreen false
				Screen = false;} //And record the boolean
			else { //Or on the fullscreen
				stage.setFullScreen(true); //Set fullscreen true
				Screen = true; //And record the boolean
			}});
		
		quit.setOnMouseClicked(e -> { //Quit button
			System.exit(0); //Exit the system
		});
		
		sound.setOnAction(e -> { //Sound button
			if(mediaPlayer.isMute() == false) { //If it is unmuted
				sound.setGraphic(new ImageView(new Image("images/SoundOff.png"))); //Set picture
				mediaPlayer.setMute(true); //Mute the song
				soundOn = false; //And record the boolean
			}
			else if(mediaPlayer.isMute() == true) { //If it is muted
				mediaPlayer.play(); //Then play the song
				sound.setGraphic(new ImageView(new Image("images/SoundOn.png"))); //Set picture
				mediaPlayer.setMute(false); //Unmute the song
				soundOn = true; //And record the boolean
			}
		});
		
		finalPane.heightProperty().addListener((obs,oldVal,newVal)-> { // Adding listener for translating story start button when height changed.
			start.setTranslateY(finalPane.getHeight() * 4 / 9);
    	});
		
		ImageView background = new ImageView(new Image("images/Background.png")); //Set background
		background.fitHeightProperty().bind(finalPane.heightProperty()); //Set width and height background
    	background.fitWidthProperty().bind(finalPane.widthProperty());
		
		finalPane.getChildren().addAll(background,menu); //Add menu and background to main pane
		scene = new Scene(finalPane ,1366, 766); //Set scene properties defaultly
        stage.setScene(scene); //Set scene to stage
        stage.setFullScreen(false); //Set fullscreen of defaultly
        stage.setTitle("Snared by Ankh"); //Set title
        stage.getIcons().add(new Image("images/ankh.png")); //Add icon
        stage.show(); //Show the all items
    }
	
	public void startGame(Stage stage) throws FileNotFoundException { // A method for starting game.
		
    	File file = new File("level" + NextLevel.levels[0] + ".txt"); // Opening files for levels.
    	
    	CreateLevel currentLevel = new CreateLevel(file); // Creating currentLevel as CreateLevel.
    	BuildPath path = new BuildPath(currentLevel.getLines(),currentLevel.getTiles()); // Creating path as BuildPath.
    	
    	double startX = currentLevel.getTiles().get(path.findStarter()).getFirstCoordinate().getX(); // Taking starter tile's X coordinate.
    	double startY = currentLevel.getTiles().get(path.findStarter()).getFirstCoordinate().getY(); // Taking starter tile's Y coordinate.
    	
   		Circle circle = new Circle(startX,startY,20); // Creating circle.
   		ImagePattern pattern = new ImagePattern(new Image("images/MarbleBall.png"));
   		circle.setFill(pattern); // Setting pattern of circle.
    	
    	StackPane centerPane = new StackPane(currentLevel,circle);
    	centerPane.setTranslateY(50);
    	StackPane.setMargin(circle ,new Insets(0, 600- 2 * startX, 600-2 * startY,0)); // Setting location of circle.
    	
    	PlayerArea area = new PlayerArea(); // Creating area as PlayerArea.
    	PathTransition transition = new PathTransition(); // Creating transition as PathTransition.
    	
    	Button next = new Button(""); // Creating next button for next level.
    	next.setStyle("-fx-background-color: transparent;");
    	
    	Text text = new Text("Level: " + NextLevel.levels[0]); // Creating text for showing which level is the current one.
    	text.setFill(Color.GOLD);
    	text.setFont(new Font(20));
    	text.setWrappingWidth(100);
    	
    	Text counts = new Text("Moves: " + area.count[0]); // Creating counts for counting moves.
    	counts.setFill(Color.WHITE);
    	counts.setFont(new Font(20));
    	counts.setWrappingWidth(130);
    	
    	
    	long currentTime = System.currentTimeMillis(); 
    	Text time = new Text("Time: 00:00");
    	Timer timer = new Timer(); // Creating timer.
    	TimerTask task = new TimerTask() { // Creating task for timer.
    		public void run() {
    			timer(time,currentTime);
    		}
    	};
    	
    	timer.scheduleAtFixedRate(task, 1000, 1000); // Setting task to the timer.
    	
    	time.setFill(Color.DARKRED.brighter());
    	time.setFont(new Font(20));
    	time.setWrappingWidth(140);
    	
    	if (soundOn) // Checking sound on or off, if it is setting NextLevel's soundOn property to true.
    		NextLevel.soundOn = soundOn;
    	
    	if (NextLevel.levels[0] == 1) // Checking current level is level1 or not, if it is set sound.
    		NextLevel.setSound();	
    	
    	Button soundSmall = NextLevel.soundSmall; // Getting sound button from NextLevel.
    	NextLevel.setOnMouseClicked(); // Setting mouse on clicked event for sound button.
    	
    	ImageView bottomSide = new ImageView(new Image("images/BottomSide.png")); // Creating background image for informations.
    	HBox informations = new HBox(text,counts,next,time,soundSmall); // Adding all informative things to the HBox.
    	soundSmall.setTranslateY(5);
    	informations.setAlignment(Pos.CENTER); // Setting alignment HBox.
    	
    	StackPane bottomPane = new StackPane(bottomSide,informations); // Creating bottom pane.
    	bottomPane.setAlignment(Pos.BOTTOM_CENTER);
    	
    	bottomPane.setTranslateY(-20);
    	
    	BorderPane pane = new BorderPane(); // Creating border pane.
    	pane.setCenter(centerPane); // Setting center pane which has currentlevel and circle.
    	pane.setBottom(bottomPane); // Setting bottom pane which has background image and informations.
    	
    	
    	ImageView image = new ImageView(new Image("images/Background.png")); // Creating background image of final pane.
    	StackPane finalPane = new StackPane(image,pane); // Creating final Pane with background image and bordor pane.
    	
    	next.setOnAction(e -> { // Set on action event for next button.         
    		LevelChange(path,stage,next); // Calling method LevelChange for changing level.
    	});
    	
    	currentLevel.setOnMousePressed(e -> { // Set on mouse pressed action for pressing tiles.
    		setOnMousePressed(e, path, area, currentLevel); // Calling method setOnMousePressed for getting info of pressed tile.
    	});
    	
    	currentLevel.setOnMouseReleased(e -> { // Set on mouse released action for releasing tiles.
    		setOnMouseReleased(e, path, area, currentLevel, transition, circle, next, counts , stage, task ,finalPane); // Calling method setOnMouseReleased for operating switching tiles.
    	});
    	
    	// Some custom bindings.
    	bottomSide.fitHeightProperty().bind(finalPane.heightProperty().divide(15).add(-2));
    	
    	bottomPane.prefHeightProperty().bindBidirectional(finalPane.prefHeightProperty());
    	bottomPane.prefWidthProperty().bindBidirectional(finalPane.prefWidthProperty());
    	
    	image.fitHeightProperty().bind(finalPane.heightProperty());
    	image.fitWidthProperty().bind(finalPane.widthProperty());
        
    	
    	stage.getScene().setRoot(finalPane); // Saving settings for FullScreen.
        stage.show();
	}
    
    public void startNextLevel(Stage stage) {
        // Initialization from start method goes here
           try {
			restart(stage);
           } 
           catch (FileNotFoundException e1) {
			e1.printStackTrace();
           }
        stage.show();
    }
    
    public void restart(Stage stage) throws FileNotFoundException { //Restart the game
        startGame(stage);
    }
    
    public void setOnMousePressed(MouseEvent e,BuildPath path,PlayerArea area,CreateLevel currentLevel) { //To call mouse event
    	if (!path.getCompleted())
    		area.MouseClicked(currentLevel,e);
    }
    
    public void setOnMouseReleased(MouseEvent e,BuildPath path,PlayerArea area,CreateLevel currentLevel,PathTransition transition,Circle circle,Button next,Text counts,Stage stage,TimerTask task,StackPane finalPane) {
    	if (!path.getCompleted())	//To call mouse event
    		area.MouseReleased(currentLevel, path,e);
    	
    		path.buildPath(); //Build path when mouse released
    		
    		countsUp(counts, area); //Counts up when after moves
        	transition.setDuration(Duration.millis(5000)); 
        	transition.setCycleCount(1);
        	transition.setPath(path);
        	transition.setNode(circle);
        	transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
    		if (path.getCompleted() && path.getAnimationCount() == 0) { // Checking if animation is played or not, path completed or not.
    			path.setAnimationCount(path.getAnimationCount() + 1); // It increases animation count property by one for being sure that animation will just play only one time.
    			
    			task.cancel(); // Stop's timer after path completed.
    			
        		transition.play();
    			currentLevel.setAllNotMovable(); // Calling method setAllNotMovable for stopping moving tiles after path completed.
        		
        		transition.statusProperty().addListener(new ChangeListener<Status>() { //Add listener while level changing
        		@Override
        		public void changed(ObservableValue<? extends Status> observableValue,Status oldValue, Status newValue) {
        		    if(newValue==Status.STOPPED){
        		    	if(NextLevel.levels[0] < 5) { //If not the end of levels
        		    		finalPane.getChildren().add(next); //Add next button to center
        		        	next.setGraphic(new ImageView(new Image("images/NextLevel.png"))); //next button picture
        		        	
        		        }
        		        else {
        		        	Button quit = new Button("" , new ImageView(new Image("images/Quit.png"))); //Quit button
        		        	quit.setStyle("-fx-background-color: transparent;");
        		        	quit.setOnMouseClicked(e -> { //When mouse pressed exit the system
        		        			System.exit(0);
        		        		});
        		        	finalPane.getChildren().add(quit); //Add quit button
        		        }
        		    }
        		}});
    		}   		
    }
    
    void countsUp(Text counts, PlayerArea area) { //Count up method to show counts
	 	counts.setText("Moves: " + area.count[0]);
    }
    
    void LevelChange(BuildPath path, Stage stage, Button next) { //Levelchange method to change next level
    	if (path.getCompleted()) { //If path completed
          		NextLevel.Change(); //Call the change method
          		startNextLevel(stage); //call startNextLevel method
          	}
    }
    
    public void timer(Text time, long currentTime) { // A method for setting timer.
    	ArrayList<String> secondAndMinute = new ArrayList<String>();
    	
    	// Checking second and minute for determining should they have an additional 0.
    	if (((System.currentTimeMillis() - currentTime) / 1000) / 60 < 10)
    		secondAndMinute.add(0, ("0" + (((System.currentTimeMillis() - currentTime) / 1000) / 60)));
    	else
    		secondAndMinute.add(0, "" + (((System.currentTimeMillis() - currentTime) / 1000) / 60));
    	if ((((System.currentTimeMillis() - currentTime) / 1000) % 60 ) < 10)
    		secondAndMinute.add(1, "0" + ((((System.currentTimeMillis() - currentTime) / 1000) % 60)));
    	else
    		secondAndMinute.add(1, "" + ((((System.currentTimeMillis() - currentTime) / 1000) % 60)));

    	time.setText("Time: " + secondAndMinute.get(0) + ":" + secondAndMinute.get(1)); // Setting text with certain time.
    	secondAndMinute.clear(); // Clearing arraylist after operation.
    }

    public static void main(String[] args) {
        launch();
    }
}