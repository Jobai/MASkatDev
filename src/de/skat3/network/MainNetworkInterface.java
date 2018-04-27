package de.skat3.network;

import de.skat3.gamelogic.GameController;
import de.skat3.main.Lobby;
import de.skat3.network.client.GameClient;
import de.skat3.network.server.GameServer;
import java.util.ArrayList;


/**
 * Functions that are called by the GUI outside the Matchphase.
 * 
 * @author Jonas Bauer
 *
 */
public interface MainNetworkInterface {



  /**
   * Called by GUI. Joins the selected server / lobby;
   * 
   * @author Jonas Bauer
   */
  public GameClient joinServerAsClient(Lobby lobby);

  /**
   * Starts a game server on the local machine.
   * 
   * @param lobbysettings the game / lobby settings for the server.
   * @param gameController gameController supplied by the gamelogic for network <-> gamelogic
   *        communication.
   * @return the created GameServer instance
   * @see de.skat3.network.MainNetworkInterface#startLocalServer(java.lang.Object)
   */
  GameServer startLocalServer(Lobby lobbysettings, GameController gameController);


  /**
   * Join the local Server (server on this host) as a client.
   * 
   * @return GameClient instance for future handling of the connection
   */
  public GameClient joinLocalServerAsClient();

  /**
   * Adds a AI player to the local server.
   * 
   * @param hardAi determines if the added AI is of hard or easy difficulty (true = hard, false =
   *        easy).
   * @return the created GameClient instance for the AI player
   */
  public GameClient addAItoLocalServer(boolean hardAi);

  // Modes: Lobby, Paused, Auction, Gamephase, AfterGameLobby
  // tell every
  // public void changeServerMode(int mode);

  /**
   * Discovers all open lobbys (GameServers) in the current local network. <br>
   * <b> THIS METHOD BLOCKS FOR 6 SECONDS! </b> <br>
   * This method uses broadcasting for discovery.
   * 
   * @return the ArrayList of discovered lobbys.
   * @see de.skat3.network.MainNetworkInterface#discoverServer()
   */
  public ArrayList<Lobby> discoverServer();


  /**
   * Used for starting a single player game. Creates a local GameServer and joins it immediately.
   * <br>
   * <i>Info: Single player games also utilize the network unit, exactly like network games.</i>
   * 
   * @param lobbySettings game / lobbysettings for the game
   * @param gc GameController for gamelogic <-> network communication.
   * @return Object[] which includes [0] the instance of the GameServer and [1] the instance of the
   *         GameClient.
   * @see de.skat3.network.MainNetworkInterface#leaveLobby()
   */
  Object[] playAndHostSinglePlayer(Lobby lobbySettings, GameController gc);



}
