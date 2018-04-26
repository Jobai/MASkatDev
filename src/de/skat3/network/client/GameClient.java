/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 12.03.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */

package de.skat3.network.client;

import de.skat3.gamelogic.Player;
import de.skat3.main.Lobby;
import de.skat3.main.SkatMain;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageChat;
import de.skat3.network.datatypes.MessageConnection;
import de.skat3.network.datatypes.MessageType;
import de.skat3.network.datatypes.SubType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * main client side network handler. Handles connection and messages to and from the server.
 * Necessary for any kind of Skat game (single and mutliplayer!).
 * 
 * @author Jonas Bauer
 *
 */
public class GameClient {

  String hostAdress;
  int port;
  Socket socket;
  ObjectOutputStream toSever;
  ObjectInputStream fromServer;
  Logger logger = Logger.getLogger("de.skat3.network.client");
  StreamListener sl;
  ClientLogicHandler clh;

  Player player;

  String lobbyPassword;

  private ClientLogicController clc;

  public ClientLogicController getClc() {
    return clc;
  }


  /**
   * Constructs a gameclient and automatically connects to the given server.
   * 
   * @author Jonas Bauer
   * @param hostAdress adress of the server you want to connect to.
   * @param port port of the server you want to connect to.
   * @param player the player instance of the connecting user.
   */
  public GameClient(String hostAdress, int port, Player player) {
    this.hostAdress = hostAdress;
    this.port = port;
    this.clh = new ClientLogicHandler(this);
    this.clc = new ClientLogicController(this);
    this.player = player;
    logger.setLevel(Level.ALL);
    this.connect();
  }

  public GameClient(String hostAdress, int port, Player player, String lobbyPassword) {
    this.hostAdress = hostAdress;
    this.port = port;
    this.clh = new ClientLogicHandler(this);
    this.clc = new ClientLogicController(this);
    this.player = player;
    logger.setLevel(Level.ALL);
    this.lobbyPassword = lobbyPassword;
    this.connect();
  }



  private void connect() {
    try {
      socket = new Socket(hostAdress, port);
      toSever = new ObjectOutputStream(socket.getOutputStream());
      fromServer = new ObjectInputStream(socket.getInputStream());
      sl = new StreamListener(this);
      sl.start();
      logger.info("Connection to " + hostAdress + ":" + port + " succesfull!");

      if (lobbyPassword == null || lobbyPassword.isEmpty()) {
        openConnection();
      } else {
        openConnection(lobbyPassword);
      }

    } catch (UnknownHostException e) {
      logger.log(Level.SEVERE, "Host not found! Connection failed!", e);
      handleLostConnection();
      e.printStackTrace();
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Connection failed!  \n" + e.getMessage(), e);
      handleLostConnection();
      e.printStackTrace();
    }

  }

  private void openConnection(String lobbyPassword2) {
    MessageConnection mc = new MessageConnection(MessageType.CONNECTION_OPEN);
    mc.payload = player;
    mc.lobbyPassword = lobbyPassword2;
    sendToServer(mc);

  }


  private void openConnection() {
    MessageConnection mc = new MessageConnection(MessageType.CONNECTION_OPEN);
    mc.payload = player;
    sendToServer(mc);

  }

  /**
   * handles and understands the incomming messages. Relays them to the proper methods for action.
   * 
   * @author Jonas Bauer
   * @param o received message from the server (still as object).
   */
  void clientProtocolHandler(Object o) {
    Object receivedObject = o;
    Message m = (Message) receivedObject;
    MessageType mt = m.getType();
    SubType st = m.getSubType();

    switch (mt) {
      case CONNECTION_OPEN:
        this.handleOpendConnection(m);
        break;
      case CONNECTION_CLOSE:
        this.closeConnection(m);
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
      case STATE_CHANGE:
        this.handleStateChange(m, st);
        break;
      default:
        logger.severe("Message Type not handeld!  " + mt + " --- " + st);
        throw new AssertionError();
    }
  }

  private void handleOpendConnection(Message m) {
    // TODO Auto-generated method stub

    Player p = (Player) m.payload;
    Lobby l = (Lobby) m.secondPayload;
    logger.info("Player" + p.getUuid() + "joined and was added to local Lobby!");
    SkatMain.mainController.currentLobby = l;
    SkatMain.mainController.currentLobby.addPlayer(p);

  }

  private void handleStateChange(Message m, SubType st) {

    String state = (String) m.payload;
    logger.info("GAME STATE CHANGE REGISTERED:" + state);
    if (state.equals("START")) {
      SkatMain.mainController.initializeLocalGameState();
    }

  }

  private void handleCommandAction(Message m, SubType st) {
    CommandType ct = (CommandType) st;
    logger.info("Handeling received message!" + ct);

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
      case ROUND_START_INFO:
        clh.roundInfoHandler(m);
        break;
      case ROUND_END_INFO:
        clh.roundInfoHandler(m);
        break;
      case MATCH_INFO:
        clh.matchInfoHandler(m);
        break;
      case GAME_INFO:
        clh.gameInfoHandler(m);
        break;
      case HAND_REQUEST:
        clh.handRequestHandler(m);
        break;
      case CONTRACT_REQUEST:
        clh.contractRequestHandler(m);
        break;
      case SKAT_INFO_REQUEST:
        clh.skatRequestHandler(m);
        break;
      case KONTRA_ANNOUNCED_INFO:
        clh.kontraAnnouncedInfoHandler(m);
        break;
      case REKONTRA_ANNOUNCED_INFO:
        clh.reKontraAnnouncedInfoHandler(m);
        break;
      case KONTRA_SHOW_OPTION_INFO:
        clh.KontraShowHandler(m);
        break;
      case REKONTRA_SHOW_OPTION_INFO:
        clh.reKontraShowHandler(m);
        break;
      case KONTRA_HIDE_OPTION_INFO:
        clh.KontraHideHandler(m);
        break;
      case REKONTRA_HIDE_OPTION_INFO:
        clh.reKontraHideHandler(m);
        break;
      case ROUND_RESTART_INFO:
        clh.roundRestartHandler(m);
        break;
      case AUCTION_WINNER_INFO:
        clh.declarerInfoHander(m);
        break;
      case CONTRACT_INFO:
        clh.contractInfoHandler(m);
        break;
      default:
        logger.severe("Message Type not handeld!  " + " --- " + st);
        throw new AssertionError();

    }

  }

  private void handleChatMessage(MessageChat m) {
    logger.log(Level.INFO, "Got Chatmessage" + m.message);
    SkatMain.mainController.receiveMessage(m.nick + ": " + m.message);
  }


  void closeConnection() {
    sl.interrupt();
    try {
      toSever.close();
      fromServer.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void closeConnection(Message m) {

    MessageConnection mc = (MessageConnection) m;
    String reason = mc.reason;
    if (reason != null) {
      SkatMain.mainController.showWrongPassword();
    }
    sl.interrupt();
    try {
      toSever.close();
      fromServer.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void sendToServer(Message m) {
    try {
      toSever.writeObject(m);
      logger.log(Level.INFO, "tried to send" + m.subType);
    } catch (IOException e) {
      e.printStackTrace();
      handleLostConnection();
    }
  }


  /**
   * Test-Main.
   * 
   * @author Jonas Bauer
   * @param args args.
   */
  public static void main(String[] args) {
    GameClient gc = new GameClient("134.155.210.89", 2018, null);
    gc.connect();

    for (int i = 1; i <= 1000000; i++) {

      gc.clh.sendChatMessage("Test  Message" + i);
    }

  }

  void handleLostConnection() {
    logger.log(Level.SEVERE, "Connection to server failed");
    closeConnection();

  }

}
