/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 07.03.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */

package de.skat3.network.server;

import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Player;
import de.skat3.main.SkatMain;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageChat;
import de.skat3.network.datatypes.MessageConnection;
import de.skat3.network.datatypes.MessageType;
import de.skat3.network.datatypes.SubType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


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

  Player playerProfile;
  public ServerLogicController slc;

  private GameLogicHandler glh;


  /**
   * Constructor for the GameServerProtocoll-Thread.
   * 
   * @author Jonas Bauer
   * @param socket created ServerSocket from the incoming connection (client).
   * @param gc the gamecontroller provided by the gamelogic.
   */
  public GameServerProtocol(Socket socket, GameController gc) {
    this.socket = socket;

    try {
      toClient = new ObjectOutputStream(socket.getOutputStream());
      fromClient = new ObjectInputStream(socket.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
      handleLostConnection();
    }
    glh = new GameLogicHandler(gc);


  }

  /**
   * GameServerProtocol-Thread that handles all incoming messages by the clients.
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
            this.openConnection(m);
            break;
          case CONNECTION_CLOSE:
            this.closeConnection();
            break;
          case CHAT_MESSAGE:
            this.relayChat((MessageChat) m);
            break;
          case ANWSER_ACTION:
            this.handleAnswer(m);
            break;
          case STATE_CHANGE:
            this.handleStateChange(m);
            break;
          case COMMAND_ACTION:
            throw new AssertionError();
          case COMMAND_INFO:
            throw new AssertionError();
          default:
            logger.severe("Message Type not handeld!  " + mt + " --- " + st);
            throw new AssertionError();


        }
      } catch (AssertionError e) {
        logger.log(Level.SEVERE, "FAIL + ASSERTION ERROR", e);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        logger.log(Level.WARNING,
            "Connection Error! Possibly ungracefully closed by Client! [Client:"
                + socket.getInetAddress() + "] \n" + e.getMessage(),
            e);
        handleLostConnection();
      }
    }
  }

  private void handleStateChange(Message m) {
    // TODO Auto-generated method stub

    broadcastMessage(m);

  }

  private void openConnection(Message m) {

    MessageConnection mc = (MessageConnection) m;
    if (!(GameServer.lobby.getPassword().equals(mc.lobbyPassword))) {

      kickConnection();
      return;
    }

    
    
    this.playerProfile = (Player) m.payload;
    m.secondPayload = SkatMain.mainController.currentLobby;

    

    
    broadcastMessage(m);
    logger.info("Player" + this.playerProfile.getUuid() + "joined the server!");
  }

  private void handleAnswer(Message m) {
    glh.handleAnswer(m);

  }

  private void relayChat(MessageChat m) {
    logger.log(Level.FINE, "Got ChatMessage: " + m.message);

    MessageChat oldM = (MessageChat) m;
    MessageChat newM = new MessageChat(oldM.message, oldM.nick);

    for (GameServerProtocol gameServerProtocol : GameServer.threadList) {
      gameServerProtocol.sendMessage(newM);
    }



  }


  void sendMessage(Message message) {
    try {
      toClient.writeObject(message);
      logger.fine("send message");
    } catch (IOException e) {
      e.printStackTrace();
      handleLostConnection();
    }


  }


  private void closeConnection() {
    try {
      toClient.close();
      fromClient.close();
      socket.close();
      // GameServer.threadList.remove(this);
      // logger.info("Server closed a connection");
      // this.interrupt();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      GameServer.threadList.remove(this);
      logger.info("Server closed a connection");
      this.interrupt();
    }
  }

  @SuppressWarnings("unused")
  private void cleanUp() {
    GameServer.threadList.remove(this);
    closeConnection();
  }


  /**
   * broadcasts a message to all connected clients (including the original sender and the host!).
   * 
   * @author Jonas Bauer
   * @param mc the message to be broadcasted
   */
  public void broadcastMessage(Message mc) {
    // logger.log(Level.FINE, "Got ChatMessage: ");
    for (GameServerProtocol gameServerProtocol : GameServer.threadList) {
      gameServerProtocol.sendMessage(mc);
    }
  }

  void handleLostConnection() {
    logger.severe("LOST CONNECTION TO CLIENT!  " + playerProfile.getUuid());
    closeConnection();
  }

  private void kickConnection() {
    MessageConnection mc = new MessageConnection(MessageType.CONNECTION_CLOSE, "WRONG_PASSWORD");
    sendMessage(mc);
    try {
      sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    closeConnection();
  }



  // TODO Functions that handels the different scenarios



}
