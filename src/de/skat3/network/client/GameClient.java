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

  boolean closedByServer;

  boolean closedByClient;

  boolean kickedByServer;

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
    this.connect();
  }

  /**
   * Constructs a gameclient and automatically connects to the given server <b> using a lobby
   * password </b>.
   * 
   * @author Jonas Bauer
   * @param hostAdress Server adress to connect to
   * @param port port of the server you want to connect to.
   * @param player the player instance of the connecting user.
   * @param lobbyPassword the lobbyPassword for the Server
   */
  public GameClient(String hostAdress, int port, Player player, String lobbyPassword) {
    this.hostAdress = hostAdress;
    this.port = port;
    this.clh = new ClientLogicHandler(this);
    this.clc = new ClientLogicController(this);
    this.player = player;
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

  /**
   * Sends a "introduction" / OpenConenction Message to the server which includes the player class.
   * This method includes the LobbyPassword and is used for authentication.
   * 
   * @author Jonas Bauer
   * @param lobbyPassword the lobby password
   */
  private void openConnection(String lobbyPassword) {
    MessageConnection mc = new MessageConnection(MessageType.CONNECTION_OPEN);
    mc.payload = player;
    mc.lobbyPassword = lobbyPassword;
    mc.originSender = player;
    sendToServer(mc);

  }

  /**
   * Sends a "introduction" / OpenConenction Message to the server which includes the player class.
   * 
   * @author Jonas Bauer
   */
  private void openConnection() {
    MessageConnection mc = new MessageConnection(MessageType.CONNECTION_OPEN);
    mc.payload = player;
    mc.originSender = player;
    sendToServer(mc);

  }

  /**
   * Handles and understands the incomming messages. Relays them to the proper methods for action.
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
      case CONNECTION_INFO:
        this.handleConnectionInfo(m);
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
        this.handleCommandAction(m, st);
        break;
      case STATE_CHANGE:
        // do nothing
        break;
      default:
        logger.severe("Message Type not handeld!  " + mt + " --- " + st);
        throw new AssertionError();
    }
  }

  /**
   * Handles all ConnectionInfo Messages which include Information about the leaving of another
   * player.
   * 
   * @author Jonas Bauer
   * @param m the network message
   */
  void handleConnectionInfo(Message m) {
    MessageConnection mc = (MessageConnection) m;
    logger.info("removed disconnected player");
    SkatMain.mainController.currentLobby.removePlayer(mc.disconnectingPlayer);

  }


  /**
   * Handels openConnection Messages which are send by all players after connection. Used to add the
   * player to the local lobby.
   * 
   * @author Jonas Bauer
   * @param m the network message
   */
  void handleOpendConnection(Message m) {

    Player p = (Player) m.payload;
    Lobby l = (Lobby) m.secondPayload;
    logger.info("Player" + p.getUuid() + "joined and was added to local Lobby!");
    SkatMain.mainController.currentLobby = l; // Lobby is set (needed for direct connect)
    SkatMain.mainController.currentLobby.addPlayer(p);

  }


  void handleCommandAction(Message m, SubType st) {
    CommandType ct = (CommandType) st;
    logger.finer("Handeling received message!" + ct);

    switch (ct) {
      case BID_INFO:
        clh.bidInfoHandler(m);
        break;
      case BID_REQUEST:
        clh.bidRequestHandler(m);
        break;
      case PLAY_INFO:
        clh.playInfoHandler(m);
        break;
      case PLAY_REQUEST:
        clh.playRequestHandler(m);
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
        clh.kontraShowHandler(m);
        break;
      case REKONTRA_SHOW_OPTION_INFO:
        clh.reKontraShowHandler(m);
        break;
      case ROUND_GENERAL_INFO:
        clh.roundInfoHandler(m);
        break;
      case AUCTION_WINNER_INFO:
        clh.declarerInfoHander(m);
        break;
      case CONTRACT_INFO:
        clh.contractInfoHandler(m);
        break;
      case UPDATE_ENEMY_INFO:
        clh.updateEnemyInfoHandler(m);
        break;
      case TRAINING_CALL_FOR_SPECIFIC_PLAY:
        clh.specificPlayHandler(m);
        break;
      case SET_DEALER:
        clh.setDealerHandler(m);
        break;
      default:
        logger.severe("Message Type not handeld!  " + " --- " + st);
        throw new AssertionError();

    }

  }

  void handleChatMessage(MessageChat m) {
    logger.log(Level.FINE, "Got Chatmessage" + m.message);
    SkatMain.mainController.receiveMessage(m.nick + ": " + m.message);
  }


  void closeConnection() {
    sl.interrupt();
    try {
      sl.interrupt();
      sl.closeStreamListener();
      fromServer.close();
      sl.interrupt();
      toSever.close();
      socket.close();
      logger.info("Client: Closed connection to server!");
    } catch (IOException e) {
      e.printStackTrace();
    }


    SkatMain.mainController.goToMenu();
    if (!closedByClient) {
      SkatMain.mainController.showCustomAlertPormpt("Connection to the server failed",
          "The connection to the server failed. "
              + "Please check that a server is running and try again later");
    }
  }


  void closeConnection(Message m) {

    showConnectionErro(m);
    sl.interrupt();
    logger.fine("GO to menu");
    SkatMain.mainController.goToMenu();

    try {
      sl.interrupt();
      toSever.close();
      fromServer.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void showConnectionErro(Message m) {
    MessageConnection mc = (MessageConnection) m;
    String reason = mc.reason;
    if (reason != null) {
      kickedByServer = true;
      closedByServer = true;
      switch (reason) {
        case "PASSWORD":
          SkatMain.mainController.showWrongPassword();
          break;
        case "FULL":
          SkatMain.mainController.showCustomAlertPormpt("Lobby is full!",
              "Sorry, the selected Lobby is already full. Please select another lobby.");
          break;
        case "KICK":
          SkatMain.mainController.showCustomAlertPormpt("You got kicked!",
              "The server ended you connection. ");
          break;
        case "SHUTDOWN":
          SkatMain.mainController.showCustomAlertPormpt("Server is shuttind down!",
              "The game server is shutting down and closed your connection. \n"
                  + "Please chose a different server.");
          break;
        default:
          SkatMain.mainController.showCustomAlertPormpt("Server closed the connection!",
              "The game server closed your connection. \n"
                  + "You can try again later or chose a different server.");
      }
    } else {
      if (!closedByClient) {
        SkatMain.mainController.showCustomAlertPormpt("Lost connection to the Server!",
            "The connection to the server failed suddenly.\n"
                + "You can try again later or chose a different server.");
      }
    }
  }

  void sendToServer(Message m) {
    try {
      toSever.flush();
      toSever.writeObject(m);
      toSever.flush();
      logger.log(Level.FINE, "tried to send" + m.subType);
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

      gc.clc.sendChatMessage("Test  Message" + i);
    }

  }

  void handleLostConnection() {
    logger.log(Level.SEVERE, "Connection to server failed");
    closeConnection();

  }

}
