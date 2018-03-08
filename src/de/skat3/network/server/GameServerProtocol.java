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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageType;

/**
 * Functions as the server protocol and handles all messages.
 * 
 * @author Jonas Bauer
 *
 */
public class GameServerProtocol extends Thread {

  Socket socket;
  private ObjectOutputStream toClient;
  private ObjectInputStream fromClient;

  public GameServerProtocol(Socket socket) {
    // TODO Auto-generated constructor stub
    this.socket = socket;

    try {
      toClient = new ObjectOutputStream(socket.getOutputStream());
      fromClient = new ObjectInputStream(socket.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Real protocol
   */
  public void run() {
    while (!this.isInterrupted()) {
      try {
        Object receivedObject = fromClient.readObject();
        Message m = (Message) receivedObject;
        MessageType mt = m.getType();



        switch (mt) {
          default:
            // do stuff
        }
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
        // TODO KILL THIS THREAD
      }
    }
  }
  
  
  //TODO Functions that handels the different scenarios



}
