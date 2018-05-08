package de.skat3.gui.matchfield;

import java.io.File;
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

  private WebEngine webEngine = null;

  @FXML
  public void initialize() {
    webEngine = text.getEngine();
  }

  @FXML
  void close(ActionEvent event) {
    this.root.setVisible(false);
  }

  public void setPath(String path) {
    File f = new File(path);
    System.out.println(f);
    System.out.println(f.toURI().toString());
    webEngine.load(f.toURI().toString());
  }

  public void setSize(int width, int height) {
    this.root.setPrefWidth(width);
    this.root.setPrefHeight(height);
  }



}
