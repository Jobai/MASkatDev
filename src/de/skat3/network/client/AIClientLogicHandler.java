/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 30.04.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */

package de.skat3.network.client;

import java.util.logging.Logger;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.main.SkatMain;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageCommand;

/**
 * @author Jonas Bauer
 *
 */

public class AIClientLogicHandler extends ClientLogicHandler {
  
  Logger logger = Logger.getLogger("de.skat3.network.AIGameClient");
  Player aiPlayer;

  /**
   * @author Jonas Bauer
   * @param gc
   */
  public AIClientLogicHandler(AIGameClient gc) {
    super(gc);
    // TODO Auto-generated constructor stub
  }



  public AIClientLogicHandler(AIGameClient aiGameClient, Player player) {
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
    // TODO Auto-generated method stub
    super.bidInfoHandler(m);
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
    MessageCommand mc = (MessageCommand) m;
    Card c = (Card) mc.gameState;
    Player op = (Player) mc.originSender;
//    SkatMain.aiController.showCardPlayed(op, c, aiPlayer);
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
   * de.skat3.network.client.ClientLogicHandler#trickInfoHandler(de.skat3.network.datatypes.Message)
   */
  @Override
  void trickInfoHandler(Message m) {
    // TODO Auto-generated method stub
    super.trickInfoHandler(m);
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
    //Round stated - start hand is set
    if (mc.getSubType() == CommandType.ROUND_GENERAL_INFO) {
      Player payloadPlayer = (Player) mc.gameState;
      logger.finer("RIH AI:" + payloadPlayer);
      SkatMain.aiController.updatePlayer(payloadPlayer, aiPlayer);
    }
    
    //Round ended - round results are shown. 
    if (mc.getSubType() == CommandType.ROUND_END_INFO) {
      Result result = (Result) mc.payload;
//      SkatMain.aiController.showResults(result, aiPlayer);
      // FIXME
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
    // TODO Auto-generated method stub
    super.matchInfoHandler(m);
  }



  /*
   * (non-Javadoc)
   * 
   * @see
   * de.skat3.network.client.ClientLogicHandler#gameInfoHandler(de.skat3.network.datatypes.Message)
   */
  @Override
  void gameInfoHandler(Message m) {
    // TODO Auto-generated method stub
    super.gameInfoHandler(m);
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
    MessageCommand mc = (MessageCommand) m;
    Contract c = (Contract) mc.payload;
    AdditionalMultipliers am = (AdditionalMultipliers) mc.secondPayload;
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
    // TODO Auto-generated method stub
    super.declarerInfoHander(m);
  }



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#handRequestHandler(de.skat3.network.datatypes.
   * Message)
   */
  @Override
  void handRequestHandler(Message m) {
    // TODO Auto-generated method stub
    MessageCommand mc = (MessageCommand) m;
    Player p = (Player) mc.gameState;
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
    Player p = mc.playerTarget;
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
    // TODO Auto-generated method stub
    super.kontraAnnouncedInfoHandler(m);
  }



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#reKontraAnnouncedInfoHandler(de.skat3.network.
   * datatypes.Message)
   */
  @Override
  void reKontraAnnouncedInfoHandler(Message m) {
    // TODO Auto-generated method stub
    super.reKontraAnnouncedInfoHandler(m);
  }



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#KontraShowHandler(de.skat3.network.datatypes.
   * Message)
   */
  @Override
  void kontraShowHandler(Message m) {
    // TODO Auto-generated method stub
    super.kontraShowHandler(m);
  }



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#reKontraShowHandler(de.skat3.network.datatypes.
   * Message)
   */
  @Override
  void reKontraShowHandler(Message m) {
    // TODO Auto-generated method stub
    super.reKontraShowHandler(m);
  }



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#KontraHideHandler(de.skat3.network.datatypes.
   * Message)
   */




  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#reKontraHideHandler(de.skat3.network.datatypes.
   * Message)
   */




  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#roundRestartHandler(de.skat3.network.datatypes.
   * Message)
   */
  @Override
  void roundRestartHandler(Message m) {
    // TODO Auto-generated method stub
    super.roundRestartHandler(m);
  }



  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.client.ClientLogicHandler#contractInfoHandler(de.skat3.network.datatypes.
   * Message)
   */
  @Override
  void contractInfoHandler(Message m) {
    // TODO Auto-generated method stub
    super.contractInfoHandler(m);
  }


}
