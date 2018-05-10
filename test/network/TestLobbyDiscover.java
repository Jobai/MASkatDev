/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 09.05.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */

package network;

import static org.junit.Assert.assertTrue;

import de.skat3.main.Lobby;
import de.skat3.network.client.LobbyDiscover;
import de.skat3.network.server.LobbyServer;
import java.net.Inet4Address;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;



public class TestLobbyDiscover {


  static LobbyServer ls;
  LobbyDiscover ld;


  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    Inet4Address inet = (Inet4Address) Inet4Address.getLocalHost();
    ls = new LobbyServer(new Lobby(inet, 0, "JUNIT test lobby", "", 3, 0, 0, false));
  }


  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    ls.stopLobbyBroadcast();
  }



  @After
  public void tearDown() throws Exception {
    ls.stopLobbyBroadcast();
    TimeUnit.SECONDS.sleep(2);
  }

  /**
   * Test method for {@link de.skat3.network.client.LobbyDiscover#LobbyDiscover()}.
   */
  @Test
  public final void testLobbyDiscover() {
    ld = new LobbyDiscover();
    ld.start();
    try {
      TimeUnit.SECONDS.sleep(6);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertTrue(ld.lobbyList.size() != 0);
  }

  /**
   * Test method for {@link de.skat3.network.client.LobbyDiscover#stopDiscovery()}.
   */
  @Test
  public final void testStopDiscovery() {
    ls = new LobbyServer(new Lobby());
    ls.stopLobbyBroadcast();;
  }

}
