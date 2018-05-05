package de.skat3.gui.matchfield;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class TrainingModeTextController {

  @FXML
  public AnchorPane root;
  @FXML
  private TextArea textArea;

  @FXML
  void close(ActionEvent event) {
    this.root.setVisible(false);
  }


  public void setText(String text) {
    this.textArea.setText(text);
  }



}
