package de.skat3.network.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.skat3.main.Lobby;

public class LobbyServer extends Thread {

  private static Logger logger = Logger.getLogger("de.skat3.network.server");

  private MulticastSocket ms;

  private Lobby lobby;

  private InetAddress iAdress;
  public int port = 2011;

  public LobbyServer(Lobby lobby) {
    logger.setLevel(Level.ALL);
    logger.fine("test fine");
    try {
      iAdress = InetAddress.getByName("239.4.5.6");
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.lobby = lobby;
    this.start();
  }

  public LobbyServer(Lobby lobby, InetAddress iAdress) {
    logger.setLevel(Level.ALL);
    logger.fine("test fine");
    this.lobby = lobby;
    this.iAdress = iAdress;
    this.start();
  }

  public void run() {
    try (MulticastSocket server = new MulticastSocket()) {
      logger.info(
          "LobbyServer started on " + Inet4Address.getLocalHost().getHostAddress() + ": " + port);

      this.ms = server;

      byte[] buff;
      buff = lobby.convertToByteArray(lobby);
      System.out.println(buff.length);
      DatagramPacket packet = new DatagramPacket(buff, buff.length, iAdress, port);
      // DatagramPacket packet = new DatagramPacket(buff, buff.length);



      while (!this.isInterrupted()) {
        ms.send(packet);
        sleep(5000);

      }
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }


  public void stopLobbyBroadcast() {
    ms.close();
    this.interrupt();
  }

  public static void main(String[] args) {
    Lobby lb = new Lobby(null, 0, "68689", null, 3, 0, 0, false);
    LobbyServer ls = new LobbyServer(lb);
    // ls.start();
  }



}
