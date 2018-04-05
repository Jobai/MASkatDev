package de.skat3.gui.multiplayermenu;

import java.io.IOException;
import java.net.URL;
import de.skat3.gui.Menu;
import de.skat3.main.SkatMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * Class to manage the multiplayer menu.
 * 
 * @author tistraub
 */
public class MultiplayerMenu extends Menu {
  private MultiplayerMenuController controller;

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
