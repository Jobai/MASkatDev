package de.skat3.gui.optionsmenu;

import de.skat3.io.SoundVolumeUtil;
import de.skat3.main.SkatMain;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.media.MediaPlayer;

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

  /**
   * Initialize listeners and preset values.
   */
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

        SoundVolumeUtil.setVolume((float) volumeMusic.getValue());
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

  /**
   * Handles event when user turn on / off the music.
   */
  public void handleMusicSwitched() {

    if (btnMusic.isSelected()) {
      // Music on
      btnMusic.setText("ON");
      SkatMain.soundPlayer.playBackgroundMusic();

    } else {
      // Music off
      btnMusic.setText("OFF");
      SkatMain.soundPlayer.stopBackgroundMusic();

    }

  }

  /**
   * Handles event when user turn on / off game sound.
   */
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
