/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 12.03.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */
package de.skat3.network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageChat;
import de.skat3.network.datatypes.MessageType;
import de.skat3.network.datatypes.SubType;

/**
 * @author Jonas Bauer
 *
 */
public class GameClient {

  String hostAdress = "134.155.206.203";
  //134.155.206.203";
  int port = 2018;
  Socket s;
  ObjectOutputStream toSever;
  ObjectInputStream fromServer;
  Logger logger = Logger.getLogger("de.skat3.network.client");
  StreamListener sl;
  ClientLogicHandler clh;



  /**
   * @author Jonas Bauer
   * @param hostAdress
   * @param port
   */
  public GameClient(String hostAdress, int port) {
    this.hostAdress = hostAdress;
    this.port = port;
    this.clh = new ClientLogicHandler(this);
  }



  private void connect() {
    try {
      s = new Socket(hostAdress, port);
      toSever = new ObjectOutputStream(s.getOutputStream());
      fromServer = new ObjectInputStream(s.getInputStream());
      sl = new StreamListener(this);
      sl.start();
      logger.info("Connection to " + hostAdress + ":" + port + " succesfull!");
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      logger.log(Level.SEVERE, "Host not found! Connection failed!", e);
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      logger.log(Level.SEVERE, "Connection failed!  \n" + e.getMessage(), e);
      e.printStackTrace();
    }

  }

  void clientProtocolHandler(Object o) {
    Object receivedObject = o;
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
        this.handleChatMessage((MessageChat) m);
        break;
      case ANWSER_ACTION:
        throw new AssertionError();
      case COMMAND_ACTION:
        this.handleCommandAction(m, st);
        break;
      case COMMAND_INFO:
        this.handleCommandAction(m, st); // XXX
        break;
      default:
        throw new AssertionError();
    }
  }



  private void handleCommandAction(Message m, SubType st) {
    // TODO Auto-generated method stub


    CommandType ct = (CommandType) st;
    switch (ct) {
      case BID_INFO:
        clh.bidInfoHandler(m);
        break;
      case BID_REDO:
        clh.bidRedoHandler(m);
        break;
      case BID_REQUEST:
        clh.bidRequestHandler(m);
        break;
      case PLAY_INFO:
        clh.playInfoHandler(m);
        break;
      case PLAY_REDO:
        clh.playRedoHandler(m);
        break;
      case PLAY_REQUEST:
        clh.playRequestHandler(m);
        break;
      case TRICK_INFO:
        clh.trickInfoHandler(m);
        break;
      case ROUND_INFO:
        clh.roundInfoHandler(m);
        break;
      case MATCH_INFO:
        clh.matchInfoHandler(m);
        break;
      case GAME_INFO:
        clh.gameInfoHandler(m);
        break;
      default:
        throw new AssertionError();
      
    }

  }

  //
  //
  // private void handleCommandInfo(Message m, SubType st) {
  // // TODO Auto-generated method stub
  //
  // }



  private void handleChatMessage(MessageChat m) {
    // TODO Auto-generated method stub
    
    logger.log(Level.FINE, "Got Chatmessage" + m.message);

  }



  private void closeConnection() {
    // TODO Auto-generated method stub

  }

  public void sendToServer(Message m) {
    try {
      toSever.writeObject(m);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }



  /**
   * @author Jonas Bauer
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    GameClient gc = new GameClient("localhost", 42);
    gc.connect();
    
    for(int i = 0; i < 100; i++)
    {
      gc.clh.sendChatMessage("Test  Message" + i);
    }
  

  }



  public void handleLostConnection() {
    // TODO Auto-generated method stub
    closeConnection();

  }

}
