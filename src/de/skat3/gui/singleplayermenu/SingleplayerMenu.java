package de.skat3.gui.singleplayermenu;

import java.io.IOException;
import java.net.URL;
import de.skat3.gui.Menu;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * Class to manage the singleplayer menu.
 * 
 * @author Timo Straub
 */
public class SingleplayerMenu extends Menu {
  private SingleplayerMenuController controller;

  /**
   * Initialize the Singleplay Menu with the design in the corresponding fxml file.
   */
  public SingleplayerMenu() {
    super(1);
    URL u = SingleplayerMenu.class.getResource("SingleplayerMenuView.fxml");
    FXMLLoader loader = new FXMLLoader(u);
    try {
      this.setPane((AnchorPane) loader.load());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.controller = loader.getController();
  }

  /**
   * Returns the controller which handles the SinglePlayerMenu content.
   * 
   * @return the controllerClass of the corresponding Scene
   */
  public SingleplayerMenuController getController() {
    return this.controller;
  }
}
