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
import de.skat3.io.profile.Profile;
import de.skat3.main.Lobby;
import de.skat3.main.SkatMain;
import de.skat3.network.ObjectSizeFetcher;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageChat;
import de.skat3.network.datatypes.MessageCommand;
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

  private GameServer gs;


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

  public GameServerProtocol(Socket socket2, GameController gc, GameServer gameServer) {
    // TODO Auto-generated constructor stub
    this(socket2, gc);
    this.gs = gameServer;

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


    gs.ls.stopLobbyBroadcast(); // XXX


    broadcastMessage(m);
    logger.info("HANDELING STATE CHANGE");


  }

  private void openConnection(Message m) {

    MessageConnection mc = (MessageConnection) m;
    Player op = mc.originSender;
    Profile p = SkatMain.ioController.getLastUsedProfile();

    String serverPw = GameServer.lobby.getPassword();
    if (!(serverPw == null || serverPw.isEmpty() || (op.getUuid().equals(p.getUuid())))) {
      System.out.println("SERVER HAS PASSWORD!: CHECKING!");
      System.out.println("SERVER PW: '" + serverPw + "' ; GIVEN PW '" + mc.lobbyPassword + "'");
      if (!(serverPw.equals(mc.lobbyPassword))) {
        kickConnection("PASSWORD");
        return;
      }
      System.out.println("Check succesful!");
    }
    System.out.println(
        "CONNECTED Current: " + SkatMain.mainController.currentLobby.getCurrentNumberOfPlayers()
            + " CONNECTED Lobby: " + SkatMain.mainController.currentLobby.lobbyPlayer);
    if (SkatMain.mainController.currentLobby
        .getCurrentNumberOfPlayers() >= SkatMain.mainController.currentLobby
            .getMaximumNumberOfPlayers()) {
      kickConnection("FULL");
      return;
    }



    // Lobby backup = SkatMain.mainController.currentLobby;
    // SkatMain.mainController.currentLobby.addPlayer(op);
    // gs.ls.setLobby(SkatMain.mainController.currentLobby);
    // SkatMain.mainController.currentLobby = backup;

    // gs.ls.getLobby().incrementconnectedPlayerNumberbyHand();

    // gs.ls.setLobby(SkatMain.mainController.currentLobby);
    this.playerProfile = (Player) m.payload;
    Lobby l = SkatMain.mainController.currentLobby;
    m.secondPayload = SkatMain.mainController.currentLobby;
    byte[] b = l.convertToByteArray(l);

    System.out.println("LOBBY SIZE!: " + b.length);

    gs.ls.getLobby().lobbyPlayer++;


    logger.info("Player" + this.playerProfile.getUuid() + "joined the server!");
    broadcastMessage(m);

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
      if (((MessageCommand) message).getSubType() == CommandType.ROUND_GENERAL_INFO) {
        // System.out.println("============= sendMessage [ROUND_GENERAL_INFO] ================");
        //
        // System.out.println(((MessageCommand) message).gameState);
        // System.out.println(((Player) ((MessageCommand) message).gameState));
        // System.out.println(((Player) ((MessageCommand) message).gameState).getHand());
        // System.out.println("=========================================");
      }
    } catch (ClassCastException e) {
      //
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
      sendDisconnectinfo(playerProfile);
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

    gs.broadcastMessage(mc);
  }

  void handleLostConnection() {
    if (playerProfile != null) {
      logger.severe("LOST CONNECTION TO CLIENT!  " + playerProfile.getUuid());
    } else {
      logger.severe("LOST CONNECTION TO UNKNOWN CLIENT!");
    }


    closeConnection();
  }

  void sendDisconnectinfo(Player disconnecting) {
    MessageConnection mc = new MessageConnection(MessageType.CONNECTION_INFO);
    mc.disconnectingPlayer = disconnecting;
    broadcastMessage(mc);
  }

  private void kickConnection(String string) {
    MessageConnection mc = new MessageConnection(MessageType.CONNECTION_CLOSE, string);
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
