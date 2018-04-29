/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 10.04.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */

package de.skat3.network;

import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Player;
import de.skat3.main.Lobby;
import de.skat3.main.SkatMain;
import de.skat3.network.client.GameClient;
import de.skat3.network.client.LobbyDiscover;
import de.skat3.network.server.GameServer;
import de.skat3.network.server.LobbyServer;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


/**
 * Central controller for fundamental network operation by other classes.
 * 
 * @author Jonas Bauer
 *
 */
public class MainNetworkController implements MainNetworkInterface {



  /**
   * Joins the given server (lobby) as a client over the network.
   * 
   * @param lobby the lobby (server) to connect to
   * @return GameClient instance for future handling the connection.
   * @see de.skat3.network.MainNetworkInterface#joinServerAsClient(de.skat3.main.Lobby)
   */
  @Override
  public GameClient joinServerAsClient(Lobby lobby) {
    Inet4Address ip = lobby.getIp();
    GameClient gc;
    if (lobby.getPassword() == null) {
      gc = new GameClient(ip.getHostAddress(), 2018,
          new Player(SkatMain.ioController.getLastUsedProfile()));

    } else {
      gc = new GameClient(ip.getHostAddress(), 2018,
          new Player(SkatMain.ioController.getLastUsedProfile()), lobby.getPassword());
    }

    return gc;

  }



  /**
   * Starts a game server on the local machine.
   * 
   * @param lobbysettings the game / lobby settings for the server.
   * @param gameController gameController supplied by the gamelogic for network <-> gamelogic
   *        communication.
   * @return the created GameServer instance
   * @see de.skat3.network.MainNetworkInterface#startLocalServer(java.lang.Object)
   */
  @Override
  public GameServer startLocalServer(Lobby lobbysettings, GameController gameController) {
    LobbyServer ls = new LobbyServer(lobbysettings);
    GameServer gs = new GameServer(lobbysettings, gameController, ls);
    return gs;
  }


  /**
   * Join the local Server (server on this host) as a client.
   * 
   * @return GameClient instance for future handling of the connection
   */
  @Override
  public GameClient joinLocalServerAsClient() {
    GameClient gc =
        new GameClient("localhost", 2018, new Player(SkatMain.ioController.getLastUsedProfile()));
    return gc;
  }


  /**
   * Adds a AI player to the local server.
   * 
   * @param hardAi determines if the added AI is of hard or easy difficulty (true = hard, false =
   *        easy).
   * @return the created GameClient instance for the AI player
   */
  @Override
  public GameClient addAItoLocalServer(boolean hardAi) {
    // TODO Auto-generated method stub
    GameClient gc = new GameClient("localhost", 2018, new Player(hardAi));
    return gc;


  }



  /**
   * Discovers all open lobbys (GameServers) in the current local network. <br>
   * <b> THIS METHOD BLOCKS FOR 6 SECONDS! </b> <br>
   * This method uses broadcasting for discovery.
   * 
   * @return the ArrayList of discovered lobbys.
   * @see de.skat3.network.MainNetworkInterface#discoverServer()
   */
  @Override
  public ArrayList<Lobby> discoverServer() {
    LobbyDiscover ld = new LobbyDiscover();
    ld.start();
    try {
      TimeUnit.SECONDS.sleep(6);

    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    ld.stopDiscovery();
    String s = Arrays.toString(ld.lobbyList.toArray());
    // System.out.println("FOUND LOBBYS: \n" + s);

    return ld.lobbyList;
  }


  /**
   * Used for starting a single player game. Creates a local GameServer and joins it immediately.
   * <br>
   * <i>Info: Single player games also utilize the network unit, exactly like network games.</i>
   * 
   * @param lobbySettings game / lobbysettings for the game
   * @param gcon GameController for gamelogic <-> network communication.
   * @return Object[] which includes [0] the instance of the GameServer and [1] the instance of the
   *         GameClient.
   * @see de.skat3.network.MainNetworkInterface#leaveLobby()
   */
  @Override
  public Object[] playAndHostSinglePlayer(Lobby lobbySettings, GameController gcon) { // FIXME
    GameServer gs = new GameServer(lobbySettings, gcon);
    GameClient gc =
        new GameClient("localhost", 2018, new Player(SkatMain.ioController.getLastUsedProfile()));
    Object[] ojA = {gs, gc};
    return ojA;
  }



}
