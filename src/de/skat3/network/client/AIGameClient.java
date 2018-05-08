/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 30.04.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */
package de.skat3.network.client;

import java.io.IOException;
import de.skat3.gamelogic.Player;
import de.skat3.main.SkatMain;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageChat;
import de.skat3.network.datatypes.MessageType;
import de.skat3.network.datatypes.SubType;

/**
 * @author Jonas Bauer
 *
 */
public class AIGameClient extends GameClient {

  AIClientLogicHandler aiCLH;

  /**
   * @author Jonas Bauer
   * @param hostAdress
   * @param port
   * @param player
   */
  public AIGameClient(String hostAdress, int port, Player player) {
    super(hostAdress, port, player);
    aiCLH = new AIClientLogicHandler(this, this.player);
  }

  /**
   * @author Jonas Bauer
   * @param hostAdress
   * @param port
   * @param player
   * @param lobbyPassword
   */
  public AIGameClient(String hostAdress, int port, Player player, String lobbyPassword) {
    super(hostAdress, port, player, lobbyPassword);
  }

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
        aiCLH.bidInfoHandler(m);
        break;
      case BID_REQUEST:
        aiCLH.bidRequestHandler(m);
        break;
      case PLAY_INFO:
        aiCLH.playInfoHandler(m);
        break;
      case PLAY_REQUEST:
        aiCLH.playRequestHandler(m);
        break;
      case ROUND_START_INFO:
        aiCLH.roundInfoHandler(m);
        break;
      case ROUND_END_INFO:
        aiCLH.roundInfoHandler(m);
        break;
      case MATCH_INFO:
        aiCLH.matchInfoHandler(m);
        break;
      case GAME_INFO:
        aiCLH.gameInfoHandler(m);
        break;
      case HAND_REQUEST:
        aiCLH.handRequestHandler(m);
        break;
      case CONTRACT_REQUEST:
        aiCLH.contractRequestHandler(m);
        break;
      case SKAT_INFO_REQUEST:
        aiCLH.skatRequestHandler(m);
        break;
      case KONTRA_ANNOUNCED_INFO:
        aiCLH.kontraAnnouncedInfoHandler(m);
        break;
      case REKONTRA_ANNOUNCED_INFO:
        aiCLH.reKontraAnnouncedInfoHandler(m);
        break;
      case KONTRA_SHOW_OPTION_INFO:
        aiCLH.kontraShowHandler(m);
        break;
      case REKONTRA_SHOW_OPTION_INFO:
        aiCLH.reKontraShowHandler(m);
        break;
      case ROUND_GENERAL_INFO:
        aiCLH.roundInfoHandler(m);
        break;
      case AUCTION_WINNER_INFO:
        aiCLH.declarerInfoHander(m);
        break;
      case CONTRACT_INFO:
        aiCLH.contractInfoHandler(m);
        break;
      case UPDATE_ENEMY_INFO:
        aiCLH.updateEnemyInfoHandler(m);
        break;
      case TRAINING_CALL_FOR_SPECIFIC_PLAY:
        aiCLH.specificPlayHandler(m);
        break;
      case SET_DEALER:
        aiCLH.setDealerHandler(m);
        break;
      case TRICK_INFO: 
        //do nothing
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
  void showConnectionErro(Message m) {
    // Don't show ConnectionErrors for the AI in the GUI!
    logger.warning("AI got Disconnected!");
  }


}
