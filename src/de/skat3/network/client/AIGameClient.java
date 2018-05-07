/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0
 * 30.04.2018
 * 
 * (c) 2018 All Rights Reserved. 
 * -------------------------
 */
package de.skat3.network.client;

import de.skat3.gamelogic.Player;
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
    // TODO Auto-generated constructor stub
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
    // TODO Auto-generated constructor stub
  }
  
  @Override
  void clientProtocolHandler(Object o) {
    logger.fine("AI got a MEssage!");
    Object receivedObject = o;
    Message m = (Message) receivedObject;
    MessageType mt = m.getType();
    SubType st = m.getSubType();


    if (st == CommandType.ROUND_GENERAL_INFO) {
      // System.out
      // .println("============= clientProtocolHandler [ROUND_GENERAL_INFO] ================");
      //
      // MessageCommand mc = (MessageCommand) m;
      // System.out.println(mc.gameState);
      // System.out.println(((Player) mc.gameState).getUuid());
      // System.out.println(((Player) mc.gameState).getHand());
      // System.out.println("=========================================");

    }

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
        this.handleStateChange(m, st);
        break;
        
      default:
        logger.severe("Message Type not handeld!  " + mt + " --- " + st);
        throw new AssertionError();
    }
  }


  void handleCommandAction(Message m, SubType st) {
    CommandType ct = (CommandType) st;
    logger.fine("Handeling AI received message!" + ct);

    switch (ct) {
      case BID_INFO:
        aiCLH.bidInfoHandler(m);
        break;
      case BID_REDO:
        aiCLH.bidRedoHandler(m);
        break;
      case BID_REQUEST:
        aiCLH.bidRequestHandler(m);
        break;
      case PLAY_INFO:
        aiCLH.playInfoHandler(m);
        break;
      case PLAY_REDO:
        aiCLH.playRedoHandler(m);
        break;
      case PLAY_REQUEST:
        aiCLH.playRequestHandler(m);
        break;
      case TRICK_INFO:
        aiCLH.trickInfoHandler(m);
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
      case ROUND_RESTART_INFO:
        aiCLH.roundRestartHandler(m);
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
      default:
        logger.severe("Message Type not handeld!  " + " --- " + st);
        throw new AssertionError();

    }

  }

  /* (non-Javadoc)
   * @see de.skat3.network.client.GameClient#showConnectionErro(de.skat3.network.datatypes.Message)
   */
  @Override
  void showConnectionErro(Message m) {
    //Don't show ConnectionErrors for the AI in the GUI!
    logger.warning("AI got Disconnected!");
  }
  
  
}
