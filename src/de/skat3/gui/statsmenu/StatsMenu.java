package de.skat3.gui.statsmenu;

import java.io.IOException;
import java.net.URL;
import de.skat3.gui.Menu;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * Class to manage the stats menu.
 * 
 * @author tistraub
 */
public class StatsMenu extends Menu {
  private StatsMenuController controller;

  public StatsMenu() {
    super(3);
    URL u = StatsMenu.class.getResource("StatsMenuView.fxml");
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
   * returns the controller which handles the StatsMenu content.
   * 
   * @return the controllerClass of the corresponding pane
   */
  public StatsMenuController getController() {
    return this.controller;
  }
}
