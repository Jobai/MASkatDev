/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0
 * 09.05.2018
 * 
 * (c) 2018 All Rights Reserved. 
 * -------------------------
 */

package network;

import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Player;
import de.skat3.gui.GuiController;
import de.skat3.io.SoundPlayer;
import de.skat3.io.SoundVolumeUtil;
import de.skat3.io.profile.IoController;
import de.skat3.io.profile.Profile;
import de.skat3.main.AiController;
import de.skat3.main.Lobby;
import de.skat3.main.MainController;
import de.skat3.main.MasterLogger;
import de.skat3.main.SkatMain;
import de.skat3.network.MainNetworkController;
import de.skat3.network.client.AiGameClient;
import de.skat3.network.server.GameServer;
import java.net.Inet4Address;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class TestAiGamClient {



  GameServer gs;

  /**
   * sets up neeed things in the SkatMain.
   * @author Jonas Bauer
   * 
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    SkatMain.masterLogger = new MasterLogger();
    SkatMain.soundPlayer = new SoundPlayer();
    SkatMain.soundVolumeUtil = new SoundVolumeUtil();
    SkatMain.mainController = new MainController();
    SkatMain.guiController = new GuiController();
    SkatMain.ioController = new IoController();
    SkatMain.mainNetworkController = new MainNetworkController();
    SkatMain.aiController = new AiController();
    SkatMain.mainController.currentLobby =
        new Lobby((Inet4Address) Inet4Address.getByName("localhost"), 0);
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {}


  /**
   * starts the needed GameServer.
   * @author Jonas Bauer
   * 
   */
  @Before
  public void setUp() throws Exception {
    this.gs = new GameServer(new Lobby(), new GameController());
    TimeUnit.SECONDS.sleep(2);
  }


  @After
  public void tearDown() throws Exception {
    this.gs.stopServer();
    TimeUnit.SECONDS.sleep(2);
  }

  @Test
  public final void testOnePlayer() {
    System.out.println("Join");
    AiGameClient gc = new AiGameClient("localhost", 2018, new Player(new Profile("Host")));
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("leave");
    gc.getClc().leaveGame();
    System.out.println("ENDE");
  }

  @SuppressWarnings("unused")
  @Test
  public final void testTwoPlayer() {
    System.out.println("Join");
    AiGameClient gc = new AiGameClient("localhost", 2018, new Player(new Profile("Host")));
    AiGameClient gc2 = new AiGameClient("localhost", 2018, new Player(new Profile("Name 1")));
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("leave");
    gc.getClc().leaveGame();
    System.out.println("ENDE");
  }


}
