package de.skat3.gui.matchfield;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * Class to manage a Matchfield object.
 * 
 * @author adomonel
 */
public class Matchfield {

  private MatchfieldController controller;
  private Scene scene;

  public Matchfield() {
    URL u = Matchfield.class.getResource("MatchfieldView.fxml");
    FXMLLoader loader = new FXMLLoader(u);
    try {
      this.scene = new Scene((AnchorPane) loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.controller = loader.getController();
  }

  /**
   * returns the controller which handles the Matchfield content.
   * 
   * @return the controllerClass of the corresponding Scene
   */
  public MatchfieldController getController() {
    return this.controller;
  }

  /**
   * returns the scene which represents the Matchfield.
   * 
   * @return the scene
   */
  public Scene getScene() {
    return this.scene;
  }
}
