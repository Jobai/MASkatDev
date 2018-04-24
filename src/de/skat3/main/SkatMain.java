
package de.skat3.main;

import de.skat3.gui.Gui;
import de.skat3.gui.GuiController;
import de.skat3.io.profile.IoController;
import de.skat3.network.MainNetworkController;

public class SkatMain {

  public static MainController mainController;
  public static LocalGameState lgs;
  public static GuiController guiController;
  public static IoController ioController;
  public static MainNetworkController mainNetworkController;

  /**
   * 
   * @param args
   */
  public static void main(String[] args) {

    SkatMain.mainController = new MainController();
    SkatMain.guiController = new GuiController();
    SkatMain.ioController = new IoController();
    SkatMain.mainNetworkController = new MainNetworkController();
    Gui.showAndWait();



  }
}
