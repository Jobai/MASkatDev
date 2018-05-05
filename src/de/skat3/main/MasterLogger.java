/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 05.05.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */
package de.skat3.main;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jonas Bauer
 *
 */
public class MasterLogger {


  public static final Logger networkGameServer = Logger.getLogger("de.skat3.network.server");
  public static final Logger networkGameClient = Logger.getLogger("de.skat3.network.client");
  public static final Logger networkLobbyServer = Logger.getLogger("de.skat3.network.server.LobbyServer");
  public static final Logger global = Logger.getGlobal();

  /**
   * @author Jonas Bauer
   */
  public MasterLogger() {
    
    Handler handlerObj = new ConsoleHandler();
    handlerObj.setLevel(Level.ALL);
    networkGameServer.addHandler(handlerObj);
    networkGameServer.setLevel(Level.ALL);
    networkGameServer.setUseParentHandlers(false);
    
    networkGameClient.addHandler(handlerObj);
    networkGameClient.setLevel(Level.ALL);
    networkGameClient.setUseParentHandlers(false);
    
    networkLobbyServer.addHandler(handlerObj);
    networkLobbyServer.setLevel(Level.ALL);
    networkLobbyServer.setUseParentHandlers(false);
    
    global.addHandler(handlerObj);
    global.setLevel(Level.ALL);
    global.setUseParentHandlers(false);
    
    global.info("Logger initalized!");
    
  }

}
