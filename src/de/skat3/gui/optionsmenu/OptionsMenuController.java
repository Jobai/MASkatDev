package de.skat3.gui.optionsmenu;

import de.skat3.gamelogic.Card;
import de.skat3.io.SoundVolumeUtil;
import de.skat3.main.SkatMain;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;

/**
 * Class to control the corresponding view file.
 * 
 * @author Timo Straub
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
  @FXML
  private ComboBox<String> comboCardBack;


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

    ObservableList<String> backside = FXCollections.observableArrayList("Blue", "Silver");
    comboCardBack.setItems(backside);
    comboCardBack.getSelectionModel().selectFirst();
    setBlueback();

    comboCardBack.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        if (comboCardBack.getSelectionModel().getSelectedItem() == "Blue") {
          setBlueback();
        }

        if (comboCardBack.getSelectionModel().getSelectedItem() == "Silver") {
          setSilverback();
        }

      }
    });

  }

  /**
   * Set card background to blue.
   */
  public void setBlueback() {
    Card.designPath = Card.BLUE;
  }

  /**
   * Set card background to silver.
   */
  public void setSilverback() {
    Card.designPath = Card.SILVER;
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
   * Check if user had won one or more Singleplayer Game. If so then archivement "cardback" is
   * unlocked
   */
  public void checkArchivment() {
    if (SkatMain.ioController.getLastUsedProfile().getSinglePlayerTotalGamesWon() >= 1) {
      comboCardBack.setDisable(false);
    } else {
      comboCardBack.setDisable(true);
    }
  }

}
