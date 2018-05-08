package de.skat3.network.server;

import de.skat3.main.Lobby;
import de.skat3.main.SkatMain;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LobbyServer extends Thread {

  private static Logger logger = Logger.getLogger("de.skat3.network.server");

  private MulticastSocket ms;
  private DatagramSocket ds;

  private Lobby lobby;

  /**
   * @author Jonas Bauer
   * @return the lobby
   */
  public Lobby getLobby() {
    return lobby;
  }

  /**
   * @author Jonas Bauer
   * @param lobby the lobby to set
   */
  public void setLobby(Lobby lobby) {
    this.lobby = lobby;
  }

  private InetAddress inetAdress;
  public final int port = 2011;

  boolean multicast = false;

  public LobbyServer(Lobby lobby) {
    logger.fine("test fine");
    try {
      inetAdress = InetAddress.getByName("239.4.5.6");
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.lobby = lobby;
    this.start();
  }

  public LobbyServer(Lobby lobby, InetAddress iAdress) {
    logger.fine("test fine");
    this.lobby = lobby;
    this.inetAdress = iAdress;
    this.start();
  }


  public void run() {
    Thread.currentThread().setName("LobbyServerThread");

    if (!multicast) {
      logger.info(
          "LobbyServer started on " + inetAdress + ": " + port);

      try (DatagramSocket ds = new DatagramSocket()) {


//        System.out.println(SkatMain.mainController.currentLobby.hashCode());
//        System.out.println(lobby.hashCode());
//        System.out.println(SkatMain.mainController.currentLobby == lobby);
        this.ds = ds;
        ds.setBroadcast(true);
        byte[] buff;

        // buff = lobby.convertToByteArray(lobby);
        // System.out.println(buff.length);
        DatagramPacket packet;
        // packet =
        // new DatagramPacket(buff, buff.length, InetAddress.getByName("255.255.255.255"), port);

        while (!this.isInterrupted()) {
//          System.out.println(SkatMain.mainController.currentLobby.hashCode());
//          System.out.println(lobby.hashCode());
//          System.out.println(SkatMain.mainController.currentLobby == lobby);
//          lobby = SkatMain.mainController.currentLobby;
//          System.out.println(Arrays.toString(lobby.getPlayers()));
          buff = lobby.convertToByteArray(lobby);
          // System.out.println(buff.length);
          if (buff.length > 4000) {
            logger.severe("WARNING: BYTE ARRAY TO BIG! -  CHECK LOBBY CLASS AND LOBBY SERVER!" + buff.length);
          }
          packet =
              new DatagramPacket(buff, buff.length, InetAddress.getByName("255.255.255.255"), port);
          ds.send(packet);
          sleep(5000);

        }
      } catch (SocketException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      } catch (UnknownHostException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InterruptedException e) {
        this.interrupt();
        // silent is ok
      }

    }

    if (multicast) {
      try (MulticastSocket server = new MulticastSocket()) {
        logger.info(
            "LobbyServer started on " + Inet4Address.getLocalHost().getHostAddress() + ": " + port);

        this.ms = server;

        byte[] buff;
        buff = lobby.convertToByteArray(lobby);
        logger.fine("Buffer lenght:"  + buff.length);
        DatagramPacket packet = new DatagramPacket(buff, buff.length, inetAdress, port);

        while (!this.isInterrupted()) {
          ms.send(packet);
          sleep(5000);

        }
      } catch (SocketException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        this.interrupt();
      }
    }


  }


  /**
   * Stops the broadcasting of the lobby in the local network and kills the thread.
   * 
   * @author Jonas Bauer
   */
  public void stopLobbyBroadcast() {
    logger.info("Stopped LobbyServer");
    this.interrupt();
    try {
      ds.close();
      ms.close();
    } catch (NullPointerException e) {
      // silent drop is ok
    }

  }

  public static void main(String[] args) {
    Lobby lb = new Lobby(null, 0, "68689", null, 3, 0, 0, false);
    LobbyServer ls = new LobbyServer(lb);
    // ls.start();
  }



}
