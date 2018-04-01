package de.skat3.network.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LobbyServer extends Thread{
  
  private static Logger logger = Logger.getLogger("de.skat3.network.server");
  public static ArrayList<LobbyServerProtocol> threadList;
  public static int port = 2011;
 // private ServerSocket serverSocket;
  private MulticastSocket ms;

  public LobbyServer() {
    logger.setLevel(Level.ALL);
    logger.fine("test fine");
    threadList = new ArrayList<LobbyServerProtocol>();

    this.start();
  }
  
  public void run() {
    try (MulticastSocket server = new MulticastSocket(port)) {
      logger
          .info("LobbyServer started on " + Inet4Address.getLocalHost().getHostAddress() + ": " + port);

      this.ms = server;
      DatagramPacket p = new DatagramPacket(arg0, arg1)
     

      while (true) {
//        logger.info("New connection!");
    	ms.receive(p);  
    	  
    	  
        threadList.add(new LobbyServerProtocol(server));
        threadList.get(threadList.size() - 1).start();

      }
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }

  
  

}
