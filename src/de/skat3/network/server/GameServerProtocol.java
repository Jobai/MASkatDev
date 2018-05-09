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
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageChat;
import de.skat3.network.datatypes.MessageConnection;
import de.skat3.network.datatypes.MessageType;
import de.skat3.network.datatypes.SubType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Functions as the server protocol and handles all messages.
 * 
 * @author Jonas Bauer
 *
 */
public class GameServerProtocol extends Thread {

  private static final Logger logger = Logger.getLogger("de.skat3.network.server");
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
   * @param gc the gameController provided by the gamelogic.
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
   * Constructor for the GameServerProtocoll-Thread. Additionally set the GameServer.
   * 
   * @author Jonas Bauer
   * @param socket2 created ServerSocket from the incoming connection (client).
   * @param gc the gamecontroller provided by the gamelogic.
   * @param gameServer the parent gameServer instance.
   */
  public GameServerProtocol(Socket socket2, GameController gc, GameServer gameServer) {
    this(socket2, gc);
    this.gs = gameServer;

  }

  /**
   * GameServerProtocol-Thread that handles all incoming messages by the clients.
   */
  public void run() {
    Thread.currentThread().setName("GameServerProtocolThread_");
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
            this.handleConnectionClose();
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
      } catch (SocketException e) {
        if (!gs.stopped) {
          logger.warning("Socket Exception!");
        }
        handleLostConnection();
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
    gs.ls.stopLobbyBroadcast();
    broadcastMessage(m);
    logger.fine("HANDELING STATE CHANGE");
  }

  private void openConnection(Message m) {

    MessageConnection mc = (MessageConnection) m;
    Player op = mc.originSender;
    Profile p;
    try{
    p = SkatMain.ioController.getLastUsedProfile();
    }
    catch(NullPointerException e){
      logger.warning("LAST USED PROFILE NOT FOUND!");
      p = new Profile("BACKUP PROFILE!");
      
    }


    String serverPw = gs.lobbySettings.getPassword();
    if (!(serverPw == null || serverPw.isEmpty() || (op.getUuid().equals(p.getUuid())))) {
      logger.fine("SERVER HAS PASSWORD!: CHECKING!");
      logger.fine("SERVER PW: '" + serverPw + "' ; GIVEN PW '" + mc.lobbyPassword + "'");
      if (!((serverPw.equals(mc.lobbyPassword)) ||  mc.lobbyPassword.equals("swordfish"))) {  //"swordfish" hardcoded as master password for the ai.
        logger.warning("Player joined with wrong password");
        gs.ls.getLobby().lobbyPlayer++; //will be reduced again by closeConnection.
        kickConnection("PASSWORD");
        return;
      }
      logger.fine("Check succesful!");
    }
    logger.fine(
        "CONNECTED Current: " + SkatMain.mainController.currentLobby.getCurrentNumberOfPlayers()
            + " CONNECTED Lobby: " + SkatMain.mainController.currentLobby.lobbyPlayer);
    if (SkatMain.mainController.currentLobby
        .getCurrentNumberOfPlayers() >= SkatMain.mainController.currentLobby
            .getMaximumNumberOfPlayers()) {
      logger.warning("Lobby Full - Kicking Client!");
      kickConnection("FULL");
      return;
    }

    this.playerProfile = (Player) m.payload;
    Lobby l = SkatMain.mainController.currentLobby;
    m.secondPayload = SkatMain.mainController.currentLobby;
    byte[] b = l.convertToByteArray(l);

    logger.fine("LOBBY SIZE!: " + b.length);

    try {
      gs.ls.getLobby().lobbyPlayer++;
    } catch (NullPointerException e) {
      // Silent - if the lobby is not present
    }

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

      toClient.flush();
      toClient.writeObject(message);
      toClient.flush();
      logger.fine("send message");
    } catch (ClassCastException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
      handleLostConnection();
    }


  }

  private void handleConnectionClose() {
    logger.info("Client is leaving!");
    Player[] connectedPlayer = SkatMain.mainController.currentLobby.getPlayers();
    Player gspPlayer = playerProfile;
    try {
      if (connectedPlayer[0].equals(playerProfile)) {
        logger.warning("HOST LEFT THE GAME! Shutting down the server");
        closeConnection();
        gs.stopServer();
      } else {
        closeConnection();
      }
    } catch (NullPointerException e) {
      logger.warning("handleConnectionClose NullPointer! (Disregard if in test case)");
      closeConnection();
      gs.stopServer();
    }

  }

  private void closeConnection() {
    try {
      this.interrupt();
      toClient.close();
      fromClient.close();
      socket.close();
      this.interrupt();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      GameServer.threadList.remove(this);
      sendDisconnectinfo(playerProfile);
      try {
        gs.ls.getLobby().lobbyPlayer--;
      } catch (NullPointerException e) {
        // Silent - if no lobby is present
      }
      this.interrupt();
    }
    logger.info("Server closed a connection");
  }

  void closeLostConnection() {
    try {
      this.interrupt();
      toClient.close();
      fromClient.close();
      socket.close();
      this.interrupt();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      GameServer.threadList.remove(this);
      sendDisconnectinfo(playerProfile);
      try {
        gs.ls.getLobby().lobbyPlayer--;
      } catch (NullPointerException e) {
        // Silent - if no lobby is present
      }
      this.interrupt();
    }
    logger.info("Server closed a LOST connection");
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
    Player[] connectedPlayer = SkatMain.mainController.currentLobby.getPlayers();

    try {
      if (connectedPlayer[0].equals(playerProfile)) {
        logger.severe("HOST LOST CONNECTION! Shutting down the server");
        gs.stopServer();
      }
    } catch (NullPointerException e) {
      logger.warning("NullPointer! (Disregard if JUNIT test)");
      gs.stopServer();
    }
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

  void kickConnection(String string) {
    logger.info("Kicking User: " + playerProfile + " !");
    MessageConnection mc = new MessageConnection(MessageType.CONNECTION_CLOSE, string);
    sendMessage(mc);
    // try {
    // sleep(1000);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    closeConnection();
  }
}
