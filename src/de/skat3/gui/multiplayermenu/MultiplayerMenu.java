package de.skat3.gui.multiplayermenu;

import de.skat3.gui.Menu;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * Class to manage the multiplayer menu.
 * 
 * @author Timo Straub
 */
public class MultiplayerMenu extends Menu {
  private MultiplayerMenuController controller;

  /**
   * Constructor for Multiplayer Menu.
   */
  public MultiplayerMenu() {
    super(2);
    URL u = MultiplayerMenu.class.getResource("MultiplayerMenuView.fxml");
    FXMLLoader loader = new FXMLLoader(u);
    try {
      this.setPane((AnchorPane) loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.controller = loader.getController();
  }

  /**
   * returns the controller which handles the MultiplayerMenu content.
   * 
   * @return the controllerClass of the corresponding Scene
   */
  public MultiplayerMenuController getController() {
    return this.controller;
  }
}
