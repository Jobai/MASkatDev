
package de.skat3.main;

import java.net.UnknownHostException;
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
    // SkatMain sk = new SkatMain();
    //sk. new Test();
    Gui.showAndWait();



  }

  private class Test extends Thread {

    public Test() {
      this.start();
    }

    public void run() {
      try {
        Thread.sleep(10000);
        SkatMain.mainController.hostMultiplayerGame("Test", "Test", 3, 0, false, 48);
        SkatMain.mainController.joinMultiplayerGame(SkatMain.mainController.currentLobby);
        SkatMain.mainController.joinMultiplayerGame(SkatMain.mainController.currentLobby);
        SkatMain.mainController.startGame();


      } catch (UnknownHostException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
