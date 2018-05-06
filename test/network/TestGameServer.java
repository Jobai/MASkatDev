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
import de.skat3.main.Lobby;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.MessageCommand;
import de.skat3.network.datatypes.MessageType;
import de.skat3.network.server.GameServer;
import de.skat3.network.server.LobbyServer;
import static org.junit.Assert.*;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Test;


/**
 * @author Jonas Bauer
 *
 */
public class TestGameServer {

  GameServer currentGameServer;


  /**
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
    gs.stopServer();

    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // gs.interrupt();
    // System.out.println(gs.isInterrupted());
    // if (!gs.isInterrupted()) {
    // fail("Not interrupted");
    // }
    assertFalse(gs.isAlive());
  }

  /**
   * Test method for
   * {@link de.skat3.network.server.GameServer#sendToPlayer(de.skat3.gamelogic.Player, de.skat3.network.datatypes.MessageCommand)}.
   */
  @Test
  public final void testSendToPlayer() {
    currentGameServer = new GameServer(new Lobby(), new GameController());
    currentGameServer.sendToPlayer(new Player(),
        new MessageCommand(MessageType.CONNECTION_OPEN, "test", CommandType.GAME_INFO));
  }

  /**
   * Test method for
   * {@link de.skat3.network.server.GameServer#broadcastMessage(de.skat3.network.datatypes.Message)}.
   */
  @Test
  public final void testBroadcastMessage() {
    currentGameServer = new GameServer(new Lobby(), new GameController());
    currentGameServer.broadcastMessage(
        new MessageCommand(MessageType.CONNECTION_OPEN, "test", CommandType.GAME_INFO));
  }

}
