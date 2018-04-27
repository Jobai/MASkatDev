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


/**
 * Responsible for the discovery of lobbys / GameServers in the local network. This class implements
 * a method via multicasting and via broadcasting. However, the LobbyServer AND the LobbyDiscover
 * need to use the same. Default is broadcasting.
 * 
 * <p>Discovery works by listening for UDP packets repeatedly send out by the LobbyServer on the
 * network.
 * 
 * <p>The <b> Port 2011 </b> is hardcoded for the communication.
 * 
 * @author Jonas Bauer
 *
 */
public class LobbyDiscover extends Thread {



  int port = 2011; // Hardcoded
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
      System.out.println("BROADCAST LOBBY MODE");

      try {
        ds = new DatagramSocket(port, inetAdressBroadcasting);

        ds.setBroadcast(true);



        lobbyList = new ArrayList<Lobby>();
        while (run) {
          buffer = new byte[4096]; // Hardcoded, needs to be big enough for a full lobby class.
          DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
          System.out.println("RECEIVING NEXT");
          ds.receive(dp);
          System.out.println("RECEIVED");
          Lobby lb = new Lobby();
          lb = lb.convertFromByteArray(buffer);
          System.out.println(lb.getName());
          boolean contains = false;
          for (Lobby l : lobbyList) {
            if (l.equals(lb)) {
              contains = true;
              break;
            }
          }
          if (!contains) {
            lobbyList.add(lb);
            System.out.println("Lobby added");
          }
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
        System.out.println("Finnaly LobbyDiscover Broadcasting");
        run = false;
        ds.close();
      }



      if (multicast) {
        try (MulticastSocket client = new MulticastSocket(port)) {
          ms = client;
          ms.joinGroup(inetAdressMulticast);
          lobbyList = new ArrayList<Lobby>();
          while (run) {
            buffer = new byte[4096]; // XXX
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            System.out.println("RECEIVING NEXT");
            ms.receive(dp);
            System.out.println("RECEIVED");
            Lobby lb = new Lobby();
            lb = lb.convertFromByteArray(buffer);
            System.out.println(lb.getName());


            boolean contains = false;
            for (Lobby l : lobbyList) {
              if (l.equals(lb)) {
                contains = true;
                break;
              }
            }
            if (!contains) {
              lobbyList.add(lb);
              System.out.println("Lobby added");
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        } catch (NullPointerException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Stops the discovery listener and kills the thread.
   * 
   * @author Jonas Bauer
   */
  public void stopDiscovery() {
    try {
      System.out.println("STOP DISCOVERY");
      run = false;
      this.interrupt();
      ds.close();
      System.out.println("STOP END");
      ms.leaveGroup(inetAdressMulticast);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      ms.close();

    }
  }



  /**
   * Test Method.
   * 
   * @author Jonas Bauer
   * @param args args.
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    LobbyDiscover ld = new LobbyDiscover();
    ld.start();


  }

}
