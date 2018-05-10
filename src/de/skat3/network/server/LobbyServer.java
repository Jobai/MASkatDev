package de.skat3.network.server;

import de.skat3.main.Lobby;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Logger;


public class LobbyServer extends Thread {

  private static Logger logger = Logger.getLogger("de.skat3.network.server");

  private MulticastSocket ms;
  private DatagramSocket ds;

  private Lobby lobby;

  private InetAddress inetAdress;
  public final int port = 2011;

  final boolean multicast = false; // never use multicasting, always broadcasting



  /**
   * Creates and starts a lobby server with the hardcoded InetAdress for multicasting in. <b>
   * multicasting mode. </b> Hardcoded mutlicast address is: <b> 239.4.5.6 </b>
   * 
   * @author Jonas Bauer
   * @param lobby lobby to broadcast
   */
  public LobbyServer(Lobby lobby) {
    try {
      inetAdress = InetAddress.getByName("239.4.5.6");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    this.lobby = lobby;
    this.start();
  }

  /**
   * Creates and starts a lobby server with the provided InetAdress for multicasting in <b>
   * multicasting mode </b>.
   * 
   * @author Jonas Bauer
   * @param lobby lobby to broadcast
   */
  public LobbyServer(Lobby lobby, InetAddress inetAdress) {
    this.lobby = lobby;
    this.inetAdress = inetAdress;
    this.start();
  }


  /**
   * The central method of the lobby server. Broadcast the given lobby to all local clients in the
   * local network. Implements Multicasting and Broadcasting, however only broadcasting should be
   * used because of issues in some networks with Mutlicasting.
   */
  public void run() {

    Thread.currentThread().setName("LobbyServerThread");

    if (!multicast) {
      logger.info("LobbyServer started on " + inetAdress + ": " + port);

      try (DatagramSocket ds = new DatagramSocket()) {
        this.ds = ds;
        ds.setBroadcast(true);
        byte[] buff;
        DatagramPacket packet;
        while (!this.isInterrupted()) {
          buff = lobby.convertToByteArray(lobby);
          if (buff.length > 4000) {
            logger.severe(
                "WARNING: BYTE ARRAY TO BIG! -  CHECK LOBBY CLASS AND LOBBY SERVER!" + buff.length);
          }
          packet =
              new DatagramPacket(buff, buff.length, InetAddress.getByName("255.255.255.255"), port);
          ds.send(packet);
          sleep(5000);
        }
      } catch (SocketException e1) {
        e1.printStackTrace();
      } catch (UnknownHostException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        this.interrupt();
        // silent is ok
      }

    }

    // Lobby broadcasting via mutlicasting.
    // Completely functional but not used due to issues in some networks.
    if (multicast) {
      try (MulticastSocket server = new MulticastSocket()) {
        logger.info(
            "LobbyServer started on " + Inet4Address.getLocalHost().getHostAddress() + ": " + port);
        this.ms = server;
        byte[] buff;
        buff = lobby.convertToByteArray(lobby);
        if (buff.length > 4000) {
          logger.severe(
              "WARNING: BYTE ARRAY TO BIG! -  CHECK LOBBY CLASS AND LOBBY SERVER!" + buff.length);
        }
        logger.fine("Buffer lenght:" + buff.length);
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
        // silent is ok

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


  public Lobby getLobby() {
    return lobby;
  }


  public void setLobby(Lobby lobby) {
    this.lobby = lobby;
  }

  /**
   * Testmain.
   * 
   * @author Jonas Bauer
   */
  @Deprecated
  public static void main(String[] args) {
    Lobby lb = new Lobby(null, 0, "68689", null, 3, 0, 0, false);
    LobbyServer ls = new LobbyServer(lb);
    ls.start();
  }
}
