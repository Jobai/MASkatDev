package de.skat3.gui.menuframe;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * Class to manage the menu frame.
 * 
 * @author Timo Straub
 */
public class MenuFrame {
  private MenuFrameController controller;
  private Scene scene;

  /**
   * Loads menu fram view and initialize the controller.
   */
  public MenuFrame() {
    URL u = MenuFrame.class.getResource("MenuFrameView.fxml");
    FXMLLoader loader = new FXMLLoader(u);
    try {
      this.scene = new Scene((AnchorPane) loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.controller = loader.getController();
    // For the first start.
    this.controller.initialize();
  }

  /**
   * Returns the controller which handles the MenuFrame content.
   * 
   * @return the controllerClass of the corresponding Scene
   */
  public MenuFrameController getController() {
    return controller;
  }

  /**
   * returns the scene which represents the MenuFrame.
   * 
   * @return the scene
   */
  public Scene getScene() {
    return scene;
  }
}
