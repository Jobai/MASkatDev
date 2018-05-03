/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 07.03.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */

package de.skat3.network.server;

import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Player;
import de.skat3.main.Lobby;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageCommand;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Controls the basic server work flow of receiving connections.
 * 
 * @author Jonas Bauer
 *
 */
public class GameServer extends Thread {

  private static Logger logger = Logger.getLogger("de.skat3.network.server");
  public static List<GameServerProtocol> threadList;
  public static int port = 2018; // HARDCODED
  private ServerSocket serverSocket;

  private ServerLogicController slc;

  public GameController gc;

  int gameServerMode = 0;

  public LobbyServer ls;

  static Lobby lobby;



  /**
   * Constructor only used for testing purposes. DEPRECATED!!!
   * 
   * @author Jonas Bauer
   * @param gc provided by logic.
   */
  @Deprecated
  GameServer(GameController gc) {
    this.gc = gc;
    logger.setLevel(Level.ALL);
    logger.fine("test fine");
    threadList = Collections.synchronizedList(new ArrayList<GameServerProtocol>());
    slc = new ServerLogicController(3, this);

    this.start();
  }

  /**
   * Constructor for creation of a GameServer without a lobby Server. Called by the standard
   * constructor. Directly only used for singleplayergames!
   * 
   * @param lobbysettings settings of the game
   * @param gc gameController provided by the gamelogic
   */
  public GameServer(Lobby lobbysettings, GameController gc) {
    this.gc = gc;
    logger.setLevel(Level.ALL);
    logger.fine("test fine");
    threadList = Collections.synchronizedList(new ArrayList<GameServerProtocol>());
    slc = new ServerLogicController(lobbysettings, this);
    GameServer.lobby = lobbysettings;
    this.start();
  }

  /**
   * Standard constructor for the creation of a GameServer. Calls another constructor.
   * 
   * @author Jonas Bauer
   * @param lobbysettings Game & Lobby Settings
   * @param gameController GameController provided by the GameLogic
   * @param ls LobbyServer that handels the broadcasting. Must be created beforhand!
   */
  public GameServer(Lobby lobbysettings, GameController gameController, LobbyServer ls) {
    this(lobbysettings, gameController);
    this.ls = ls;

  }

  /**
   * GameServer-Thread. Handles all new connections and starts foreach a GameServerProtocol.
   */
  public void run() {
    try (ServerSocket server = new ServerSocket(port)) {
      logger
          .info("Server started on " + Inet4Address.getLocalHost().getHostAddress() + ": " + port);

      this.serverSocket = server;
      Socket socket;

      while (true) {
        socket = server.accept();
        logger.info("New connection!");
        threadList.add(new GameServerProtocol(socket, gc, this));
        threadList.get(threadList.size() - 1).start();

      }
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  /**
   * Ungracefully stops the GameServer and closes the serversocket. All clients still connected lose
   * ungracefully the connection.
   * 
   * @author Jonas Bauer
   */
  public void stopServer() {
    if (!this.serverSocket.isClosed()) {
      try {
        this.serverSocket.close();
        logger.info("Server stopped!");
      } catch (SocketException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  public ServerLogicController getSeverLogicController() {
    return this.slc;

  }



  /**
   * Sends a message to the given player.
   * 
   * @author Jonas Bauer
   * @param player destination for the message.
   * @param mc message to be transmitted.
   */
  public void sendToPlayer(Player player, MessageCommand mc) {
    for (GameServerProtocol gameServerProtocol : GameServer.threadList) {
      if (gameServerProtocol.playerProfile.equals(player)) {
        if (mc.getSubType() == CommandType.ROUND_GENERAL_INFO) {
//          System.out
//              .println("============= send To Player Log [ROUND_GENERAL_INFO] ================");
//
//          System.out.println(mc.gameState);
//          System.out.println(((Player) mc.gameState).getUuid());
//          System.out.println(((Player) mc.gameState).getHand());
//          System.out.println("=========================================");
        }

        gameServerProtocol.sendMessage(mc);



        logger.info("send to:  " + player.getUuid() + "  succesful");
        return;
      }
    }
    logger.warning("send to player: " + player.getUuid() + "  FAILED!");

  }

  /**
   * broadcasts a message to all connected clients (including the original sender and the host!).
   * 
   * @author Jonas Bauer
   * @param mc the message to be broadcasted
   */
  void broadcastMessage(Message mc) {
    synchronized (threadList) {
      logger.log(Level.FINE, "Got ChatMessage: ");
      for (GameServerProtocol gameServerProtocol : GameServer.threadList) {
        gameServerProtocol.sendMessage(mc);
      }
    }
   
  }



  public void setGameController(GameController gameController) {
    this.gc = gameController;
  }

  public void setGameMode(int mode) {
    this.gameServerMode = mode;
  }


  public static void main(String[] args) {
    @SuppressWarnings("unused")
    GameServer gs = new GameServer(null);
  }



}
