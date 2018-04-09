/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 07.03.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */

package de.skat3.network.server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Player;
import de.skat3.network.datatypes.MessageCommand;

/**
 * Controls the basic server work flow of receiving connections.
 * 
 * @author Jonas Bauer
 *
 */
public class GameServer extends Thread {

  private static Logger logger = Logger.getLogger("de.skat3.network.server");
  public static ArrayList<GameServerProtocol> threadList;
  public static int port = 2018;
  private ServerSocket serverSocket;

  private ServerLogicController slc;
  
  public GameController gc;


  public GameServer() {
    logger.setLevel(Level.ALL);
    logger.fine("test fine");
    threadList = new ArrayList<GameServerProtocol>();
    slc = new ServerLogicController(3, this);

    this.start();
  }

  public void run() {
    try (ServerSocket server = new ServerSocket(port)) {
      logger
          .info("Server started on " + Inet4Address.getLocalHost().getHostAddress() + ": " + port);

      this.serverSocket = server;
      Socket socket;

      while (true) {
        socket = server.accept();
        logger.info("New connection!");
        threadList.add(new GameServerProtocol(socket, gc));
        threadList.get(threadList.size() - 1).start();

      }
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }

  public void stopServer() {
    if (!this.serverSocket.isClosed()) {
      try {
        this.serverSocket.close();
        logger.info("Server stopped!");
      } catch (SocketException e) {
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }

  public ServerLogicController getSeverLogicController() {
    return this.slc;

  }



  public void sendToPlayer(Player player, MessageCommand mc) {
    // TODO Auto-generated method stub

    for (GameServerProtocol gameServerProtocol : GameServer.threadList) {
      if (gameServerProtocol.playerProfile.getUuid().equals(player.getUuid()))
      {
        gameServerProtocol.sendMessage(mc);
        logger.info("send succesful");
        return;
      }
    }
    logger.warning("send To Player FAILED!");

  }

  public void broadcastMessage(MessageCommand mc) {
    // TODO Auto-generated method stub
    logger.log(Level.FINE, "Got ChatMessage: ");



    for (GameServerProtocol gameServerProtocol : GameServer.threadList) {
      gameServerProtocol.sendMessage(mc);
    }



  }


  /**
   * @author Jonas Bauer
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    GameServer gs = new GameServer();

  }

public void setGameController(GameController gameController) {
	// TODO Auto-generated method stub
	this.gc = gameController;
}

}
