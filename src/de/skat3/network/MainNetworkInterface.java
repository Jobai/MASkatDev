package de.skat3.network;

import java.util.ArrayList;
import de.skat3.main.Lobby;
import de.skat3.network.server.GameServer;

/**
 * Functions that are called by the GUI outside the Matchphase
 * @author Jonas Bauer
 *
 */
public interface MainNetworkInterface {


  public void switchToGameServerMode();

  /**
   * Called by GUI. Joins the selected server / lobby;
   * 
   * @author Jonas Bauer
   */
  public void joinServerAsClient(Lobby lobby);

  public GameServer startLocalServer(Lobby Lobbysettings);

  public void joinLocalServerAsClient();

  public void addAItoLocalServer(boolean hardAi);

  // Modes: Lobby, Paused, Auction, Gamephase, AfterGameLobby
  //tell every
  public void changeServerMode(int mode);
  
  public ArrayList<Lobby> discoverServer();
  
  public void leaveLobby();
  
  
  
  
  
  


}
