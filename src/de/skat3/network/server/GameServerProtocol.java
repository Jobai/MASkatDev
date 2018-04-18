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

import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Player;
import de.skat3.main.SkatMain;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageChat;
import de.skat3.network.datatypes.MessageCommand;
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
  
  Player playerProfile;
  public ServerLogicController slc;
  
  private GameLogicHandler glh;
  
  

  public GameServerProtocol(Socket socket, GameController gc) {
    // TODO Auto-generated constructor stub
    
    
    this.socket = socket;

    try {
      toClient = new ObjectOutputStream(socket.getOutputStream());
      fromClient = new ObjectInputStream(socket.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
    glh = new GameLogicHandler(gc);
    

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
            this.openConnection(m);
            break; // TODO
          case CONNECTION_CLOSE:
            this.closeConnection();
            break;
          case CHAT_MESSAGE:
            this.relayChat((MessageChat) m);
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
        logger.log(Level.WARNING, "Connection Error! Possibly ungracefully closed by Client! [Client:" + socket.getInetAddress() + "] \n" + e.getMessage(), e);
        closeConnection();
        
        // TODO KILL THIS THREAD
      }
    }
  }

  private void openConnection(Message m) {
    // TODO Auto-generated method stub
    //TODO DO GUI STUFF
    this.playerProfile = (Player) m.payload;
    this.playerProfile = new Player(null); //FIXME
    SkatMain.mainController.currentLobby.addPlayer(this.playerProfile);
    
  }

  private void handleAnswer(Message m) {
    // TODO Auto-generated method stub
    
    //GameLogicHandler.handleAnswer(m);
	  glh.handleAnswer(m);

  }

  private void relayChat(MessageChat m) {
    // TODO Auto-generated method stub
    logger.log(Level.FINE, "Got ChatMessage: " + m.message);
    
    MessageChat oldM = (MessageChat) m;
    
    // TODO check for command and profanity and set flags acordingly
    MessageChat newM = new MessageChat(oldM.message, oldM.nick);

    newM.setCommand(false);
    newM.setProfanity(false);
    

    for (GameServerProtocol gameServerProtocol : GameServer.threadList) {
      gameServerProtocol.sendMessage(newM);
    }

    

  }
  

  void sendMessage(Message message) {
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
      GameServer.threadList.remove(this);
      logger.info("Server closed a connection");
      this.interrupt();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }



  }
  
  private void cleanUp(){
    GameServer.threadList.remove(this);
    closeConnection();
  }
  
  



  // TODO Functions that handels the different scenarios



}
