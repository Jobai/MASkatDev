

package de.skat3.main;

import java.util.Properties;
import de.skat3.gui.Gui;
import de.skat3.gui.GuiController;
import de.skat3.io.profile.IoController;
import de.skat3.main.AiController;
import de.skat3.main.LocalGameState;
import de.skat3.main.MainController;
import de.skat3.network.MainNetworkController;
import de.skat3.network.client.ClientLogicController;

public class SkatMain {

  public static MainController mainController;
  public static LocalGameState lgs;
  public static ClientLogicController clc;
  public static GuiController guiController;
  public static IoController ioController;
  public static MainNetworkController mainNetworkController;
  public static AiController aiController;
  public static MasterLogger masterLogger;


  /**
   * Main entry point for the MA SKAT game.
   * 
   * @param args not used
   */
  public static void main(String[] args) {

<<<<<<< Updated upstream
    SkatMain.masterLogger = new MasterLogger();
=======
//    System.setProperty("sun.java2d.dpiaware", "false");
    // Properties props = System.getProperties();
    // props.setProperty("-Dsun.java2d.dpiaware=false");

>>>>>>> Stashed changes
    SkatMain.mainController = new MainController();
    SkatMain.guiController = new GuiController();
    SkatMain.ioController = new IoController();
    SkatMain.mainNetworkController = new MainNetworkController();
    SkatMain.aiController = new AiController();
    Gui.showAndWait();



  }
}
