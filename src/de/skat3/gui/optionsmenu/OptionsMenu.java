package de.skat3.gui.optionsmenu;

import java.io.IOException;
import java.net.URL;
import de.skat3.gui.Menu;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * Class to manage the options menu.
 * 
 * @author Timo Straub
 */
public class OptionsMenu extends Menu {
  private OptionsMenuController controller;

  /**
   * Creates a new Option Menu with the design described in the corresponding fxml file.
   */
  public OptionsMenu() {
    super(4);
    URL u = OptionsMenu.class.getResource("OptionsMenuView.fxml");

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
   * returns the controller which handles the OptionsMenu content.
   * 
   * @return the controllerClass of the corresponding Scene
   */
  public OptionsMenuController getController() {
    return this.controller;
  }
}
