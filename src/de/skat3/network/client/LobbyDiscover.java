/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 17.04.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */
package de.skat3.network.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import de.skat3.main.Lobby;

/**
 * @author Jonas Bauer
 *
 */
public class LobbyDiscover extends Thread {



  int port = 2011;
  InetAddress iAdress;
  MulticastSocket ms;
  byte[] buffer;

  public ArrayList<Lobby> lobbyList;


  public LobbyDiscover() {
    // TODO Auto-generated constructor stub
    try {
      iAdress = InetAddress.getByName("239.4.5.6");
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }


  public void run() {

    try (MulticastSocket client = new MulticastSocket(port)) {
      ms = client;
      ms.joinGroup(iAdress);

      lobbyList = new ArrayList<Lobby>();
      while (!this.isInterrupted()) {
        buffer = new byte[500];
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        System.out.println("RECEIVING NEXT");
        ms.receive(dp);
        System.out.println("RECEIVED");
        Lobby lb = new Lobby();
        lb = lb.convertFromByteArray(buffer);
        System.out.println(lb.getName());
        if (!lobbyList.contains(lb))
          lobbyList.add(lb);
        System.out.println("Lobby added");

      }



    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public void stopDiscovery() {
    try {
      ms.leaveGroup(iAdress);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    ms.close();
    this.interrupt();
  }



  /**
   * @author Jonas Bauer
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    LobbyDiscover ld = new LobbyDiscover();
    ld.start();


  }

}