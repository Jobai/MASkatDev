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
import java.util.logging.Logger;

/**
 * Controls the basic server work flow of receiving connections.
 * 
 * @author Jonas Bauer
 *
 */
public class GameServer extends Thread {

  private static Logger logger = Logger.getLogger("de.skat3.network.server");
  public static ArrayList<GameServerProtocol> threadList;
  public static int port;
  private ServerSocket serverSocket;

  public GameServer() {
    threadList = new ArrayList<GameServerProtocol>();
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
        threadList.add(new GameServerProtocol(socket));
        threadList.get(threadList.size() - 1).start();

      }
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }
  
  public void stopServer()
  {
    //TODO
  }

  /**
   * @author Jonas Bauer
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    
    GameServer gs = new GameServer();

  }

}
