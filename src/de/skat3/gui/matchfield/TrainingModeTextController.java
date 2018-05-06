package de.skat3.gui.matchfield;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class TrainingModeTextController {

  @FXML
  public AnchorPane root;
  @FXML
  private WebView text;

  // final WebEngine webEngine = null;

  @FXML
  void close(ActionEvent event) {
    this.root.setVisible(false);
  }


  public void setText(String text) {
    // webEngine = text.getEngine();
    // webEngine.loadContent(text);
  }



}
