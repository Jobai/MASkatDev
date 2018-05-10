/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 30.04.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */

package de.skat3.network.client;

import de.skat3.gamelogic.Player;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageType;
import de.skat3.network.datatypes.SubType;
import java.io.IOException;


/**
 * Main AI client side network handler. Handles connection and messages to and from the server for
 * the AI Client. Necessary for any kind of Skat game with AI (single and multiplayer!). <b> Bots
 * can ONLY be used on the local host and should never be connected to a remote host! </b>
 * 
 * @author Jonas Bauer
 *
 */
public class AiGameClient extends GameClient {

  AiClientLogicHandler aiClh;

  /**
   * Constructs a AiGameClient and automatically connects to the given server. <b> only works on
   * local clients. remote bots are NOT supported! </b>
   * 
   * @author Jonas Bauer
   * @param hostAdress address of the server you want to connect to. <b> Must always be localhost!
   *        </b>
   * @param port port of the server you want to connect to.
   * @param player the player (bot) instance of the connecting user.
   */
  public AiGameClient(String hostAdress, int port, Player player) {
    super(hostAdress, port, player);
    aiClh = new AiClientLogicHandler(this, this.player);
  }

  /**
   * Constructs a AiGameClient and automatically connects to the given server <b> using a lobby
   * password </b>. <b> Lobby password must always the masterpassword for bots! </b> <b> Bots only
   * works on local clients. remote bots are NOT supported! </b>
   * 
   * @author Jonas Bauer
   * @param hostAdress Server address to connect to. <b> Must always be localhost! </b>
   * @param port port of the server you want to connect to.
   * @param player the player (bot) instance of the connecting user.
   * @param lobbyPassword the lobbyPassword for the Server. <b> Should always be the master
   *        password! </b>
   */
  public AiGameClient(String hostAdress, int port, Player player, String lobbyPassword) {
    super(hostAdress, port, player, lobbyPassword);
    aiClh = new AiClientLogicHandler(this, this.player);
  }


  /**
   * Handles and understands the incoming messages. Relays them to the proper methods of the <b>
   * AiClientLogicController </b> for action. Info: The AI handels some messages differently or not
   * a all.
   * 
   * @author Jonas Bauer
   * @param o received message from the server (still as object).
   */
  @Override
  void clientProtocolHandler(Object o) {
    logger.fine("AI got a MEssage!");
    Object receivedObject = o;
    Message m = (Message) receivedObject;
    MessageType mt = m.getType();
    SubType st = m.getSubType();


    switch (mt) {
      case CONNECTION_OPEN:
        // Do nothing as bot
        break;
      case CONNECTION_CLOSE:
        this.closeConnection(m);
        break;
      case CONNECTION_INFO:
        // Do nothing as bot
        break;
      case CHAT_MESSAGE:
        // Do nothing as bot
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


  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.GameClient#closeConnection(de.skat3.network.datatypes.Message)
   */
  @Override
  void closeConnection(Message m) {
    sl.interrupt();
    try {
      sl.interrupt();
      toSever.close();
      fromServer.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void handleCommandAction(Message m, SubType st) {
    CommandType ct = (CommandType) st;
    logger.fine("Handeling AI received message!" + ct);
    switch (ct) {
      case BID_INFO:
        aiClh.bidInfoHandler(m);
        break;
      case BID_REQUEST:
        aiClh.bidRequestHandler(m);
        break;
      case PLAY_INFO:
        aiClh.playInfoHandler(m);
        break;
      case PLAY_REQUEST:
        aiClh.playRequestHandler(m);
        break;
      case ROUND_START_INFO:
        aiClh.roundInfoHandler(m);
        break;
      case ROUND_END_INFO:
        aiClh.roundInfoHandler(m);
        break;
      case MATCH_INFO:
        aiClh.matchInfoHandler(m);
        break;
      case GAME_INFO:
        aiClh.gameInfoHandler(m);
        break;
      case HAND_REQUEST:
        aiClh.handRequestHandler(m);
        break;
      case CONTRACT_REQUEST:
        aiClh.contractRequestHandler(m);
        break;
      case SKAT_INFO_REQUEST:
        aiClh.skatRequestHandler(m);
        break;
      case KONTRA_ANNOUNCED_INFO:
        aiClh.kontraAnnouncedInfoHandler(m);
        break;
      case REKONTRA_ANNOUNCED_INFO:
        aiClh.reKontraAnnouncedInfoHandler(m);
        break;
      case KONTRA_SHOW_OPTION_INFO:
        aiClh.kontraShowHandler(m);
        break;
      case REKONTRA_SHOW_OPTION_INFO:
        aiClh.reKontraShowHandler(m);
        break;
      case ROUND_GENERAL_INFO:
        aiClh.roundInfoHandler(m);
        break;
      case AUCTION_WINNER_INFO:
        aiClh.declarerInfoHander(m);
        break;
      case CONTRACT_INFO:
        aiClh.contractInfoHandler(m);
        break;
      case UPDATE_ENEMY_INFO:
        aiClh.updateEnemyInfoHandler(m);
        break;
      case TRAINING_CALL_FOR_SPECIFIC_PLAY:
        aiClh.specificPlayHandler(m);
        break;
      case SET_DEALER:
        aiClh.setDealerHandler(m);
        break;
      case TRICK_INFO:
        // do nothing
        break;
      default:
        logger.severe("Message Type not handeld!  " + " --- " + st);
        throw new AssertionError();

    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.GameClient#showConnectionErro(de.skat3.network.datatypes.Message)
   */
  @Override
  void showConnectionError(Message m) {
    // Don't show ConnectionErrors for the AI in the GUI!
    logger.warning("AI got Disconnected!");
  }


}
