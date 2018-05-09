/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 06.05.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
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
import de.skat3.network.client.GameClient;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.MessageCommand;
import de.skat3.network.datatypes.MessageType;
import de.skat3.network.server.GameServer;
import de.skat3.network.server.LobbyServer;
import static org.junit.Assert.*;
import java.net.Inet4Address;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Jonas Bauer
 *
 */
public class TestGameServer {

  GameServer currentGameServer;
  Player testPlayer1 = new Player(new Profile("TestPlayer1"));
  Player testPlayer2 = new Player(new Profile("TestPlayer2"));

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
    
    MasterLogger.networkGameServer.setLevel(Level.WARNING);
    MasterLogger.networkGameClient.setLevel(Level.WARNING);
  }

  /**
   * Stops the GameServer if it is not already part of the assertion test.
   * 
   * @author Jonas Bauer
   */
  @After
  public void tearDown() {
    if (currentGameServer != null) {
      currentGameServer.stopServer();
      try {
        TimeUnit.MILLISECONDS.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Test method for
   * {@link de.skat3.network.server.GameServer#GameServer(de.skat3.main.Lobby, de.skat3.gamelogic.GameController)}.
   */
  @Test
  public final void testGameServerLobbyGameController() {
    currentGameServer = new GameServer(new Lobby(), new GameController());
    assertFalse(currentGameServer.failure);
  }

  /**
   * Test method for
   * {@link de.skat3.network.server.GameServer#GameServer(de.skat3.main.Lobby, de.skat3.gamelogic.GameController, de.skat3.network.server.LobbyServer)}.
   */
  @Test
  public final void testGameServerLobbyGameControllerLobbyServer() {
    currentGameServer =
        new GameServer(new Lobby(), new GameController(), new LobbyServer(new Lobby()));
    assertFalse(currentGameServer.failure);
  }

  /**
   * Test method for {@link de.skat3.network.server.GameServer#stopServer()}.
   */
  @Test
  public final void testStopServer() {
    GameServer gs = new GameServer(new Lobby(), new GameController());
    try {
      TimeUnit.MILLISECONDS.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    stopServerAssertion(gs);
  }

  /**
   * Test method for
   * {@link de.skat3.network.server.GameServer#sendToPlayer(de.skat3.gamelogic.Player, de.skat3.network.datatypes.MessageCommand)}.
   */
  @Test
  public final void testSendToPlayerEmpty() {
    currentGameServer = new GameServer(new Lobby(), new GameController());
    currentGameServer.sendToPlayer(new Player(),
        new MessageCommand(MessageType.CONNECTION_OPEN, "test", CommandType.GAME_INFO));
    assertFalse(currentGameServer.failure);
  }

  /**
   * Test method for
   * {@link de.skat3.network.server.GameServer#broadcastMessage(de.skat3.network.datatypes.Message)}.
   */
  @Test
  public final void testBroadcastMessageEmpty() {
    currentGameServer = new GameServer(new Lobby(), new GameController());
    currentGameServer.broadcastMessage(
        new MessageCommand(MessageType.CONNECTION_OPEN, "test", CommandType.GAME_INFO));
    assertFalse(currentGameServer.failure);
  }

  @Test
  public final void testSendToPlayerFull() {
    currentGameServer = new GameServer(new Lobby(), new GameController());
    try {
      TimeUnit.MILLISECONDS.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    addPlayer();

    currentGameServer.sendToPlayer(testPlayer1,
        new MessageCommand(MessageType.CONNECTION_OPEN, "test", CommandType.GAME_INFO));

    assertFalse(currentGameServer.failure);
  }

  /**
   * Test method for
   * {@link de.skat3.network.server.GameServer#broadcastMessage(de.skat3.network.datatypes.Message)}.
   */
  @Test
  public final void testBroadcastMessageFull() {
    currentGameServer = new GameServer(new Lobby(), new GameController());
    try {
      TimeUnit.MILLISECONDS.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    addPlayer();
    currentGameServer.broadcastMessage(
        new MessageCommand(MessageType.CONNECTION_OPEN, "test", CommandType.GAME_INFO));
    assertFalse(currentGameServer.failure);
  }

  @Test
  public final void testStopFullServer() {
    GameServer gs = new GameServer(new Lobby(), new GameController());
    try {
      TimeUnit.MILLISECONDS.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    addPlayer();
    stopServerAssertion(gs);

  }

  /**
   * @author Jonas Bauer
   */
  private void addPlayer() {
    GameClient gc = new GameClient("localhost", 2018, testPlayer1);
    GameClient gc2 = new GameClient("localhost", 2018, testPlayer2);
    try {
      TimeUnit.MILLISECONDS.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * @author Jonas Bauer
   * @param gs
   */
  private void stopServerAssertion(GameServer gs) {
    gs.stopServer();

    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertFalse(gs.isAlive());
  }

}
