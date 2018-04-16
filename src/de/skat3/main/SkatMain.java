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


    // GameServer gm = new GameServer();
    // Player kai = new Player();
    // Player bot1 = new Player();
    // Player bot2 = new Player();
    //
    // Player[] players = {kai, bot1, bot2};
    // GameController gameController =
    // new GameController(gm.getSeverLogicController(), players, 0, false, 48);
    // gm.setGameController(gameController); // must happen before any clients connect!!!
    //
    // GameClient gcKai = new GameClient("localhost", 2018, kai);
    // GameClient gcBot1 = new GameClient("localhost", 2018, bot1);
    // GameClient gcBot2 = new GameClient("localhost", 2018, bot2);

  }
}
