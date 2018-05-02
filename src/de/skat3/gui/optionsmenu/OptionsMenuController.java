package de.skat3.gui.optionsmenu;

import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * Class to control the corresponding view file.
 * 
 * @author tistraub
 */
public class OptionsMenuController {
  @FXML
  private Slider volumeGame;
  @FXML
  private ToggleButton btnSound;
  @FXML
  private Slider volumeMusic;
  @FXML
  private ToggleButton btnMusic;

  private MediaPlayer backgroundMusicPlayer;

  @FXML
  public void initialize() {

    // Music
    volumeMusic.setMax(1);
    volumeMusic.setMin(0);
    volumeMusic.setBlockIncrement(0.1);
    volumeMusic.setValue(0.25);

    // Adding Listener to value property.
    volumeMusic.valueProperty().addListener(new ChangeListener<Number>() {

      @Override
      public void changed(ObservableValue<? extends Number> observable, //
          Number oldValue, Number newValue) {

        backgroundMusicPlayer.setVolume(volumeMusic.getValue());
      }
    });

    // Game Sound
    volumeGame.setMax(1);
    volumeGame.setMin(0);
    volumeGame.setBlockIncrement(0.1);
    volumeGame.setValue(0.25);

    // Adding Listener to value property.
    volumeGame.valueProperty().addListener(new ChangeListener<Number>() {

      @Override
      public void changed(ObservableValue<? extends Number> observable, //
          Number oldValue, Number newValue) {

        // TODO
        // backgroundMusicPlayer.setVolume(volumeMusic.getValue());
      }
    });

  }


  public void handleMusicSwitched() {

    if (btnMusic.isSelected()) {
      // Music on
      btnMusic.setText("ON");

//      String path = "F:/MusicPlayer/src/musicplayer/adcBicycle_-_02_-_poor_economic_policies.mp3";
//      Media media = new Media(new File(path).toURI().toString());
//      backgroundMusicPlayer = new MediaPlayer(media);
//      backgroundMusicPlayer.setAutoPlay(true);
//      backgroundMusicPlayer = new MediaView(mediaPlayer);
      
      
//      String url = getClass().getResource("../../../../music/backgroundMusic.mp3").toString();
//      Media media = new Media(new File(url).toString());
//      backgroundMusicPlayer = new MediaPlayer(media);
//      backgroundMusicPlayer.setVolume(volumeMusic.getValue());
//      backgroundMusicPlayer.play();

    } else {
      // Music off
      btnMusic.setText("OFF");
      backgroundMusicPlayer.stop();

    }

  }

  public void handleGameSoundSwitched() {

    if (btnSound.isSelected()) {
      // Sound on
      btnSound.setText("ON");

      // TODO
      // String url = getClass().getResource("../../../../music/backgroundMusic.mp3").toString();
      // Media hit = new Media(new File(url).toString());
      // backgroundMusicPlayer = new MediaPlayer(hit);
      // backgroundMusicPlayer.setVolume(volumeMusic.getValue());
      // backgroundMusicPlayer.play();

    } else {
      // Sound off
      btnSound.setText("OFF");
      backgroundMusicPlayer.stop();

    }
  }

}
