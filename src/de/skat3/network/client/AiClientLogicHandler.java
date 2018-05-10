/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 30.04.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */

package de.skat3.network.client;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Player;
import de.skat3.main.SkatMain;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageCommand;
import java.util.logging.Logger;


/**
 * Handles messages For the AI (Mostly Commands sometimes Informatio) from the network and acts on
 * their content. Calls manly methods from the AiMainController to inform the AI of changes in the
 * GameState or a Request to do something. <br>
 * ClientNetwork > this Class > aiController > AI. IMPLEMENTs [UNDERSTAND] COMMANDTYPEs.
 * 
 * @author Jonas
 *
 * 
 */

public class AiClientLogicHandler extends ClientLogicHandler {

  Logger logger = Logger.getLogger("de.skat3.network.AIGameClient");
  Player aiPlayer;


  /**
   * Constructor that sets the ai Player an aiGameClient.
   * 
   * @see de.skat3.network.client.ClientLogicHandler#AiClientLogicHandle(de.skat3.network.client.
   *      AiGameClient, de.skat3.gamelogic. Player)
   * @author Jonas Bauer
   * @param aiGameClient the aiGameClient
   * @param player aiPlayer to be set.
   */
  public AiClientLogicHandler(AiGameClient aiGameClient, Player player) {
    // TODO Auto-generated constructor stub
    super(aiGameClient);
    this.aiPlayer = player;


  }



  /*
   * (non-Javadoc)
   * 
   * @see
   * de.skat3.network.client.ClientLogicHandler#bidInfoHandler(de.skat3.network.datatypes.Message)
   */
  @Override
  void bidInfoHandler(Message m) {
    // do nothing
  }



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#bidRequestHandler(de.skat3.network.datatypes.
   * Message)
   */
  @Override
  void bidRequestHandler(Message m) {
    // TODO Auto-generated method stub
    logger.finer("AI bidRequest received");
    int b = (int) ((MessageCommand) m).gameState;
    SkatMain.aiController.bidRequest(b, aiPlayer);
  }



  /*
   * (non-Javadoc)
   * 
   * @see
   * de.skat3.network.client.ClientLogicHandler#playInfoHandler(de.skat3.network.datatypes.Message)
   */
  @Override
  void playInfoHandler(Message m) {
    // do nothing
  }


  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#playRequestHandler(de.skat3.network.datatypes.
   * Message)
   */
  @Override
  void playRequestHandler(Message m) {
    SkatMain.aiController.playCardRequest(aiPlayer);
  }



  /*
   * (non-Javadoc)
   * 
   * @see
   * de.skat3.network.client.ClientLogicHandler#roundInfoHandler(de.skat3.network.datatypes.Message)
   */
  @Override
  void roundInfoHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    // Round stated - hand is set
    if (mc.getSubType() == CommandType.ROUND_GENERAL_INFO) {
      Player payloadPlayer = (Player) mc.gameState;
      logger.finer("RIH AI:" + payloadPlayer);
      SkatMain.aiController.updatePlayer(payloadPlayer, aiPlayer);
    }
  }



  /*
   * (non-Javadoc)
   * 
   * @see
   * de.skat3.network.client.ClientLogicHandler#matchInfoHandler(de.skat3.network.datatypes.Message)
   */
  @Override
  void matchInfoHandler(Message m) {
    // do nothing
  }



  /*
   * (non-Javadoc)
   * 
   * @see
   * de.skat3.network.client.ClientLogicHandler#gameInfoHandler(de.skat3.network.datatypes.Message)
   */
  @Override
  void gameInfoHandler(Message m) {
    // super.gameInfoHandler(m);
  }



  /*
   * (non-Javadoc)
   * 
   * @see
   * de.skat3.network.client.ClientLogicHandler#contractRequestHandler(de.skat3.network.datatypes.
   * Message)
   */
  @Override
  void contractRequestHandler(Message m) {
    logger.fine("AI CONTRACT REQUEST");
    SkatMain.aiController.contractRequest(aiPlayer);
  }



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#declarerInfoHander(de.skat3.network.datatypes.
   * Message)
   */
  @Override
  void declarerInfoHander(Message m) {
    // do nothing
  }



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#handRequestHandler(de.skat3.network.datatypes.
   * Message)
   */
  @Override
  void handRequestHandler(Message m) {
    SkatMain.aiController.handGameRequest(aiPlayer);
  }



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#skatRequestHandler(de.skat3.network.datatypes.
   * Message)
   */
  @Override
  void skatRequestHandler(Message m) {
    MessageCommand mc = (MessageCommand) m;
    Card[] skat = (Card[]) mc.gameState;
    SkatMain.aiController.selectSkatRequest(skat, aiPlayer);
  }



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#kontraAnnouncedInfoHandler(de.skat3.network.
   * datatypes.Message)
   */
  @Override
  void kontraAnnouncedInfoHandler(Message m) {
    // do nothing
  }



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#reKontraAnnouncedInfoHandler(de.skat3.network.
   * datatypes.Message)
   */
  @Override
  void reKontraAnnouncedInfoHandler(Message m) {
    // do nothing
  }



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#KontraShowHandler(de.skat3.network.datatypes.
   * Message)
   */
  @Override
  void kontraShowHandler(Message m) {
    // do nothing
  }



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#reKontraShowHandler(de.skat3.network.datatypes.
   * Message)
   */
  @Override
  void reKontraShowHandler(Message m) {
    // do nothing
  }


  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#contractInfoHandler(de.skat3.network.datatypes.
   * Message)
   */
  @Override
  void contractInfoHandler(Message m) {
    // do nothing
  }


}
