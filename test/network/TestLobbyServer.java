/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 06.05.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */

package network;

import de.skat3.main.Lobby;
import de.skat3.network.server.LobbyServer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Test;



public class TestLobbyServer {

  LobbyServer currentLobbyServer;


  /**
   * tear down.
   */
  @After
  public void tearDown() throws Exception {
    if (currentLobbyServer != null) {
      currentLobbyServer.stopLobbyBroadcast();
      TimeUnit.SECONDS.sleep(1);
    }
  }

  /**
   * Test method for {@link de.skat3.network.server.LobbyServer#LobbyServer(de.skat3.main.Lobby)}.
   */
  @Test
  public final void testLobbyServerLobby() {
    currentLobbyServer = new LobbyServer(new Lobby());

  }

  /**
   * Test method for
   * {@link de.skat3.network.server.LobbyServer#LobbyServer
   * (de.skat3.main.Lobby, java.net.InetAddress)}.
   * 
   * 
   */
  @Test
  public final void testLobbyServerLobbyInetAddress() throws UnknownHostException {
    currentLobbyServer = new LobbyServer(new Lobby(), InetAddress.getByName("239.4.5.6"));
  }

  /**
   * Test method for {@link de.skat3.network.server.LobbyServer#stopLobbyBroadcast()}.
   */
  @Test
  public final void testStopLobbyBroadcast() {
    LobbyServer ls = new LobbyServer(new Lobby());
    ls.stopLobbyBroadcast();
  }

}
