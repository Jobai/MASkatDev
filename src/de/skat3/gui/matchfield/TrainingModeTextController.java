package de.skat3.gui.matchfield;

import de.skat3.gamelogic.LogicAnswers;
import de.skat3.gamelogic.TrainingRoundInstance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Class handels the events of the corresponing view file.
 * 
 * @author Timo Straub
 *
 */
public class TrainingModeTextController {

  @FXML
  public AnchorPane root;
  @FXML
  private WebView text;

  private WebEngine webEngine = null;
  private TrainingRoundInstance trInstance;

  /**
   * Initialize the screen.
   */
  @FXML
  public void initialize() {
    webEngine = text.getEngine();
  }

  /**
   * Close Popup.
   * 
   * @param event ActionEvent
   */
  @FXML
  void close(ActionEvent event) {
    this.trInstance.notifyRoundInstance(LogicAnswers.BID);
    this.root.setVisible(false);
  }

  /**
   * Loads html file and set it to the popup.
   * 
   * @param path Path
   */
  public void setPath(String path) {
    webEngine.load(TrainingModeTextController.class.getResource(path).toString());
  }

  /**
   * Set size of Popup.
   * 
   * @param width Width
   * @param height Height
   */
  public void setSize(int width, int height) {
    this.root.setPrefWidth(width);
    this.root.setPrefHeight(height);
  }

  public void setIntance(TrainingRoundInstance trInstance) {
    this.trInstance = trInstance;
  }

}
