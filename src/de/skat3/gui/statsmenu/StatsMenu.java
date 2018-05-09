package de.skat3.gui.statsmenu;

import java.io.IOException;
import java.net.URL;
import de.skat3.gui.Menu;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * Class to manage the stats menu.
 * 
 * @author Timo Straub
 */
public class StatsMenu extends Menu {
  private StatsMenuController controller;

  /**
   * Creates a statistic menu with the design in the corresponding fxml file.
   */
  public StatsMenu() {
    super(3);
    URL u = StatsMenu.class.getResource("StatsMenuView.fxml");
    FXMLLoader loader = new FXMLLoader(u);
    try {
      this.setPane((AnchorPane) loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.controller = loader.getController();
  }

  /**
   * Returns the controller which handles the StatsMenu content.
   * 
   * @return the controllerClass of the corresponding pane
   */
  public StatsMenuController getController() {
    return this.controller;
  }
}
