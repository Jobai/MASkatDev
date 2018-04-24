package de.skat3.gui.optionsmenu;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

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


  public void handleMusicSwitched() {

    if (btnMusic.isSelected()) {
      // Music off

    } else {
      // Music on
      System.out.println("ON");
      
      AudioPlayer MGP = AudioPlayer.player;
      AudioStream BGM;
      AudioData MD;
      ContinuousAudioDataStream loop = null;
      try {
        BGM = new AudioStream(new FileInputStream("../../../../backgroundMusic.mp3"));
        MD = BGM.getData();
        loop = new ContinuousAudioDataStream(MD);
      } catch (IOException error) {
        System.out.print("file not found");
      }
      MGP.start(loop);

    }

  }

  public void handleGameSoundSwitched() {

  }

  public void handleVolumeMusicChanged() {

  }

  public void handleVolumeGameSoundChanged() {

  }

}
