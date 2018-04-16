/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0
 * 10.04.2018
 * 
 * (c) 2018 All Rights Reserved. 
 * -------------------------
 */
package de.skat3.network;

import java.util.ArrayList;
import de.skat3.gamelogic.Player;
import de.skat3.main.Lobby;
import de.skat3.network.client.GameClient;
import de.skat3.network.server.GameServer;

/**
 * @author Jonas Bauer
 *
 */
public class MainNetworkController implements MainNetworkInterface {


 


  /* (non-Javadoc)
   * @see de.skat3.network.MainNetworkInterface#joinServerAsClient(de.skat3.main.Lobby)
   */
  @Override
  public GameClient joinServerAsClient(Lobby lobby) {
    // TODO Auto-generated method stub
    
    lobby.

  }

  /* (non-Javadoc)
   * @see de.skat3.network.MainNetworkInterface#startLocalServer(java.lang.Object)
   */
  @Override
  public GameServer startLocalServer(Lobby lobbysettings) {
    //TODO Add LobbyServer
    
    //GameServer
    
    GameServer gs = new GameServer(lobbysettings);
    
    return gs;
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see de.skat3.network.MainNetworkInterface#joinLocalServerAsClient()
   */
  @Override
  public GameClient joinLocalServerAsClient() {
    GameClient gc = new GameClient("localhost", 2018, new Player()); //FIXME
    return gc;
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see de.skat3.network.MainNetworkInterface#addAItoLocalServer(boolean)
   */
  @Override
  public void addAItoLocalServer(boolean hardAi) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see de.skat3.network.MainNetworkInterface#changeServerMode(int)
   */
  @Override
  public void changeServerMode(int mode) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see de.skat3.network.MainNetworkInterface#discoverServer()
   */
  @Override
  public ArrayList<Lobby> discoverServer() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see de.skat3.network.MainNetworkInterface#leaveLobby()
   */


  @Override
  public GameServer playAndHostSinglePlayer() {
    // TODO Auto-generated method stub
    GameServer gs = new GameServer();
    GameClient gc = new GameClient("localhost", 2018, new Player()); //FIXME
    
    return null;
  }

  
}
