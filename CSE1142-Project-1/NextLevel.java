import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

//This class stands for level up and some updates
public class NextLevel { 
	static Integer[] levels = {1,2,3,4,5};//Levels array
	static int i = 1; //Constant variable
	static Button soundSmall ;
	static MediaPlayer mediaPlayer;
	static boolean soundOn ;
	
	public NextLevel() {}//No arg constructor
	//This method changes level every time
	public static void Change() {
		if(i <= 5) {
	   levels[0] = levels[i];
	   i++;
	   }
	}
	//This method add, mute, unmute sound while playing levels
	public static void setSound() {
		Button sound = new Button();
		sound.setTranslateY(-9);
    	String musicFile = "music/AoM.mp3"; //Music file name
		Media music = new Media(new File(musicFile).toURI().toString()); //Adding music from file
		mediaPlayer = new MediaPlayer(music);
		mediaPlayer.setVolume(0.5); //Volume down
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //Music plays with loop
    	
		//This if else keeps the mute or unmute while level changing
    	if (soundOn) {
    		sound.setGraphic(new ImageView(new Image("images/soundOnSmall.png")));
    		mediaPlayer.play();
    	}
    	else
    		sound.setGraphic(new ImageView(new Image("images/soundOffSmall.png")));
    	
    	sound.setStyle("-fx-background-color: transparent;");
    	soundSmall = sound;
	}
	
	//This if else unmutes or mutes the song and changes button picture
	public static void setOnMouseClicked(){
		soundSmall.setOnMouseClicked(e -> {
    		if (soundOn) {
    			soundSmall.setGraphic(new ImageView(new Image("images/soundOffSmall.png")));
				mediaPlayer.setMute(true);
				soundOn = false;
    		}
    		else {
    			mediaPlayer.play();
				soundSmall.setGraphic(new ImageView(new Image("images/soundOnSmall.png")));
				mediaPlayer.setMute(false);
				soundOn = true;
    		}
    	});
	}
 }
