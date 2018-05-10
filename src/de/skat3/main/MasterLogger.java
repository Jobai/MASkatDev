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
 * Main logger for all console prints.
 * 
 * @author Jonas Bauer
 *
 */
public class MasterLogger {


  public static final Logger networkGameServer = Logger.getLogger("de.skat3.network.server");
  public static final Logger networkGameClient = Logger.getLogger("de.skat3.network.client");
  public static final Logger networkAIGameClient =
      Logger.getLogger("de.skat3.network.AIGameClient");
  public static final Logger networkLobbyServer =
      Logger.getLogger("de.skat3.network.server.LobbyServer");
  public static final Logger guiLogger = Logger.getLogger("de.skat3.gui");
  public static final Logger global = Logger.getGlobal();
  public static final Logger gameLogicLogger = Logger.getLogger("de.skat3.gamelogic");

  /**
   * Creates a logger and initializes all logger to the ConsoleHandlers.
   * 
   * @author Jonas Bauer
   */
  public MasterLogger() {

    Handler handlerObj = new ConsoleHandler();
    handlerObj.setLevel(Level.ALL);

    networkGameServer.addHandler(handlerObj);
    networkGameServer.setLevel(Level.INFO);
    networkGameServer.setUseParentHandlers(false);

    networkGameClient.addHandler(handlerObj);
    networkGameClient.setLevel(Level.FINE);
    networkGameClient.setUseParentHandlers(false);

    networkLobbyServer.addHandler(handlerObj);
    networkLobbyServer.setLevel(Level.ALL);
    networkLobbyServer.setUseParentHandlers(false);

    networkAIGameClient.addHandler(handlerObj);
    networkAIGameClient.setLevel(Level.INFO);
    networkAIGameClient.setUseParentHandlers(false);

    gameLogicLogger.addHandler(handlerObj);
    gameLogicLogger.setLevel(Level.ALL);
    gameLogicLogger.setUseParentHandlers(false);

    guiLogger.addHandler(handlerObj);
    guiLogger.setLevel(Level.ALL);
    guiLogger.setUseParentHandlers(false);

    global.addHandler(handlerObj);
    global.setLevel(Level.INFO);
    global.setUseParentHandlers(false);

    global.info("Logger initalized!");

  }

}
