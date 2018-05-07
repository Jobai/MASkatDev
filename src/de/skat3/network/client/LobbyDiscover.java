/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 17.04.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */

package de.skat3.network.client;

import de.skat3.main.Lobby;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * Responsible for the discovery of lobbys / GameServers in the local network. This class implements
 * a method via multicasting and via broadcasting. However, the LobbyServer AND the LobbyDiscover
 * need to use the same. Default is broadcasting.
 * 
 * <p>Discovery works by listening for UDP packets repeatedly send out by the LobbyServer on the
 * network.
 * 
 * <p>The <b> Port 2011 </b> and  <b> buffer size 4096 </b> is hardcoded for the communication.
 * 
 * @author Jonas Bauer
 *
 */
public class LobbyDiscover extends Thread {


  Logger logger = Logger.getLogger("de.skat3.network.server.LobbyServer");

  final int port = 2011; // Hardcoded
  final int buffSize = 4096; // Hardcoded, needs to be big enough for a full lobby class.
  String mutlicastAdress;
  
  InetAddress inetAdressMulticast;
  InetAddress inetAdressBroadcasting;
  MulticastSocket ms;
  byte[] buffer;
  boolean multicast = false;
  DatagramSocket ds;

  public boolean run = true;
  public ArrayList<Lobby> lobbyList;


  /**
   * Default construcor for the LobbyDiscover.
   * 
   * <p>Sets the IP for Multicast listening to <b> 239.4.5.6 </b> <br>
   * Sets the IP for Broadcasting listening <b> to 0.0.0.0 </b>
   * 
   * @author Jonas Bauer
   */
  public LobbyDiscover() {
    try {
      inetAdressMulticast = InetAddress.getByName("239.4.5.6");
      inetAdressBroadcasting = InetAddress.getByName("0.0.0.0");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }

  }


  /**
   * The thread method of the LobbyDiscover Class. Handles the listening for UDP packets and
   * conversion to lobbys.
   */
  public void run() {


    if (!multicast) {
      broadcastingDiscovery();
    }

    if (multicast) {
      mutlicastingDiscovery();
    }
  }



  private void broadcastingDiscovery() {
    logger.info("BROADCAST LOBBY MODE");

    try (DatagramSocket dds = new DatagramSocket(port, inetAdressBroadcasting)) {
      ds = dds;

      ds.setBroadcast(true);
      lobbyList = new ArrayList<Lobby>();


      while (run) {
        buffer = new byte[buffSize]; 
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        logger.finer("RECEIVING NEXT");
        ds.receive(dp);
        logger.finer("RECEIVED");

        Lobby lb = new Lobby();
        lb = lb.convertFromByteArray(buffer);
        logger.finer(lb.getName());

        addDiscoveryToLobbyList(lb);



      }
    } catch (SocketException e) {
      if (run) {
        e.printStackTrace();
      }
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      logger.finest("Finnaly LobbyDiscover Broadcasting");
      run = false;
    }



  }


  private void mutlicastingDiscovery() {
    try (MulticastSocket client = new MulticastSocket(port)) {
      ms = client;
      ms.joinGroup(inetAdressMulticast);
      lobbyList = new ArrayList<Lobby>();
      while (run) {
        buffer = new byte[buffSize];
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        logger.finer("RECEIVING NEXT");
        ms.receive(dp);
        logger.finer("RECEIVED");
        Lobby lb = new Lobby();
        lb = lb.convertFromByteArray(buffer);
        logger.finer(lb.getName());

        addDiscoveryToLobbyList(lb);

      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }



  private void addDiscoveryToLobbyList(Lobby lb) {
    boolean contains = false;
    for (Lobby l : lobbyList) {
      if (l.equals(lb)) {
        contains = true;
        break;
      }
    }
    if (!contains) {
      lobbyList.add(lb);
      logger.fine("Lobby added");
    }
  }

  /**
   * Stops the discovery listener and kills the thread.
   * 
   * @author Jonas Bauer
   */
  public void stopDiscovery() {
    try {
      logger.fine("STOP DISCOVERY");
      run = false;
      this.interrupt();
      ds.close();

      ms.leaveGroup(inetAdressMulticast);
      ms.close();
      logger.finer("STOP END");

    } catch (IOException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      // silent drop ok
    } finally {
      logger.finer("FINNALY stop Discovery");
      //
    }
    logger.finer("FINISHED stopDiscovery");
  }



  /**
   * Test Method.
   * 
   * @author Jonas Bauer
   * @param args args.
   */
  public static void main(String[] args) {
    LobbyDiscover ld = new LobbyDiscover();
    ld.start();


  }

}
