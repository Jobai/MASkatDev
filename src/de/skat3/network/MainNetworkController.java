/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 10.04.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */
package de.skat3.network;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Player;
import de.skat3.main.Lobby;
import de.skat3.main.SkatMain;
import de.skat3.network.client.GameClient;
import de.skat3.network.client.LobbyDiscover;
import de.skat3.network.server.GameServer;
import de.skat3.network.server.LobbyServer;

/**
 * @author Jonas Bauer
 *
 */
public class MainNetworkController implements MainNetworkInterface {



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.MainNetworkInterface#joinServerAsClient(de.skat3.main.Lobby)
   */
  @Override
  public GameClient joinServerAsClient(Lobby lobby) {
    // TODO Auto-generated method stub

    Inet4Address ip = lobby.getIp();
    GameClient gc = new GameClient(ip.getHostAddress(), 2018,
        new Player(SkatMain.ioController.getLastUsedProfile())); // FIXME
    return gc;

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.MainNetworkInterface#startLocalServer(java.lang.Object)
   */
  @Override
  public GameServer startLocalServer(Lobby lobbysettings, GameController gameController) {
    // TODO Add LobbyServer

    LobbyServer ls = new LobbyServer(lobbysettings);

    // GameServer

    GameServer gs = new GameServer(lobbysettings, gameController, ls);

    return gs;
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.MainNetworkInterface#joinLocalServerAsClient()
   */
  @Override
  public GameClient joinLocalServerAsClient() {
    GameClient gc =
        new GameClient("localhost", 2018, new Player(SkatMain.ioController.getLastUsedProfile()));
    return gc;
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.MainNetworkInterface#addAItoLocalServer(boolean)
   */
  @Override
  public void addAItoLocalServer(boolean hardAi) {
    // TODO Auto-generated method stub

  }


  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.MainNetworkInterface#discoverServer()
   */
  @Override 
  public ArrayList<Lobby> discoverServer() {
    // TODO Auto-generated method stub
    LobbyDiscover ld = new LobbyDiscover();
    ld.start();
    try {
      TimeUnit.SECONDS.sleep(10);

    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return ld.lobbyList;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.MainNetworkInterface#leaveLobby()
   */


  @Override
  public GameServer playAndHostSinglePlayer() {
    // TODO Auto-generated method stub
    GameServer gs = new GameServer(null); // FIXME
    GameClient gc = new GameClient("localhost", 2018, new Player(null)); // FIXME

    return null;
  }

  @Override
  public GameServer startLocalServer(Lobby Lobbysettings) {
    // TODO Auto-generated method stub
    return null;
  }


}
