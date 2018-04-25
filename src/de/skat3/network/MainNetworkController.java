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



  // @see de.skat3.network.MainNetworkInterface#joinServerAsClient(de.skat3.main.Lobby)
  @Override
  public GameClient joinServerAsClient(Lobby lobby) {
    Inet4Address ip = lobby.getIp();
    GameClient gc = new GameClient(ip.getHostAddress(), 2018,
        new Player(SkatMain.ioController.getLastUsedProfile()));
    return gc;

  }


  // @see de.skat3.network.MainNetworkInterface#startLocalServer(java.lang.Object)
  @Override
  public GameServer startLocalServer(Lobby lobbysettings, GameController gameController) {
    LobbyServer ls = new LobbyServer(lobbysettings);
    GameServer gs = new GameServer(lobbysettings, gameController, ls);
    return gs;
  }


  @Override
  public GameClient joinLocalServerAsClient() {
    GameClient gc =
        new GameClient("localhost", 2018, new Player(SkatMain.ioController.getLastUsedProfile()));
    return gc;
  }


  @Override
  public void addAItoLocalServer(boolean hardAi) {
    // TODO Auto-generated method stub

  }



  // @see de.skat3.network.MainNetworkInterface#discoverServer()
  @Override
  public ArrayList<Lobby> discoverServer() {
    LobbyDiscover ld = new LobbyDiscover();
    ld.start();
    try {
      TimeUnit.SECONDS.sleep(6);

    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return ld.lobbyList;
  }


  // @see de.skat3.network.MainNetworkInterface#leaveLobby()
  @Override
  public GameServer playAndHostSinglePlayer(Lobby lobbySettings, GameController gcon) { //FIXME
    GameServer gs = new GameServer(lobbySettings, gcon);
    GameClient gc =
        new GameClient("localhost", 2018, new Player(SkatMain.ioController.getLastUsedProfile()));
    return gs;
  }



}
