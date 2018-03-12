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
import java.util.logging.Level;
import java.util.logging.Logger;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageChat;
import de.skat3.network.datatypes.MessageType;
import de.skat3.network.datatypes.SubType;

/**
 * Functions as the server protocol and handles all messages.
 * 
 * @author Jonas Bauer
 *
 */
public class GameServerProtocol extends Thread {

  private static Logger logger = Logger.getLogger("de.skat3.network.server");
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
        SubType st = m.getSubType();



        switch (mt) {
          case CONNECTION_OPEN:
            break; // TODO
          case CONNECTION_CLOSE:
            this.closeConnection();
            break;
          case CHAT_MESSAGE:
            this.relayChat(m);
            break;
          case ANWSER_ACTION:
            this.handleAnswer(m);
            break;
          case COMMAND_ACTION:
            throw new AssertionError();
          case COMMAND_INFO:
            throw new AssertionError();
          default:
            throw new AssertionError();


        }
      } catch (AssertionError e) {
        logger.log(Level.SEVERE, "FAIL", e);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
        // TODO KILL THIS THREAD
      }
    }
  }

  private void handleAnswer(Message m) {
    // TODO Auto-generated method stub

  }

  private void relayChat(Message m) {
    // TODO Auto-generated method stub
    
    MessageChat oldM = (MessageChat) m;
    
    // TODO check for command and profanity and set flags acordingly
    MessageChat newM = new MessageChat(oldM.message, oldM.nick);

    newM.setCommand(false);
    newM.setProfanity(false);
    

    for (GameServerProtocol gameServerProtocol : GameServer.threadList) {
      gameServerProtocol.sendMessage(newM);
    }

    

  }
  

  private void sendMessage(Message message) {
    try {
      toClient.writeObject(message);
      logger.fine("send message");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }

  private Message craftMessage() {
    // TODO Auto-generated method stub
    return null;
  }

  private void closeConnection() {

    try {
      toClient.close();
      fromClient.close();
      socket.close();
      logger.info("Server closed a connection");
      this.interrupt();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }



  }


  // TODO Functions that handels the different scenarios



}
