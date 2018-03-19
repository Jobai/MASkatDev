/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 19.03.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */
package de.skat3.network;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.MessageCommand;
import de.skat3.network.datatypes.MessageType;
import de.skat3.network.server.GameServerProtocol;

/**
 * @author Jonas Bauer
 *
 */
public class ServerLogicController implements ServerLogicInterface {

  // ServerGameState
  int connectedPlayer;
  int serverMatchMode;
  int maxPlayer;

  //
  GameServerProtocol gsp;



  /**
   * @author Jonas Bauer
   * @param maxPlayer
   * @param gsp
   */
  public ServerLogicController(int maxPlayer, GameServerProtocol gsp) {
    this.maxPlayer = maxPlayer;
    this.gsp = gsp;
    this.connectedPlayer = 0;
    this.serverMatchMode = 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#sendStartHandtoPlayer(de.skat3.gamelogic.Player)
   */
  @Override
  public void sendStartHandtoPlayer(Player player) {
    // TODO Auto-generated method stub
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, player.toString(), CommandType.GAME_INFO);
    mc.gameState = player;
    gsp.sendToPlayer(player, mc);

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#callForBid(de.skat3.gamelogic.Player, int)
   */
  @Override
  public void callForBid(Player player, int biddingValue) {
    // TODO Auto-generated method stub
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_ACTION, player.toString(), CommandType.BID_REQUEST);
    mc.gameState = (Integer) biddingValue;
    gsp.sendToPlayer(player, mc);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#callForPlay(de.skat3.gamelogic.Player)
   */
  @Override
  public void callForPlay(Player player) {
    // TODO Auto-generated method stub
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_ACTION, player.toString(), CommandType.PLAY_REQUEST);

    gsp.sendToPlayer(player, mc);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#broadcastContract(de.skat3.gamelogic.Contract)
   */
  @Override
  public void broadcastContract(Contract contract) {
    // TODO Auto-generated method stub
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_ACTION, "ALL", CommandType.GAME_INFO);

    gsp.broadcastMessage(mc);

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#sendPlayedCard(de.skat3.gamelogic.Player,
   * de.skat3.gamelogic.Card)
   */
  @Override
  public void sendPlayedCard(Player player, Card card) {
    // TODO Auto-generated method stub



  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#broadcastTrickResult(java.lang.Object)
   */
  @Override
  public void broadcastTrickResult(Object oj) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#broadcastRoundResult(java.lang.Object)
   */
  @Override
  public void broadcastRoundResult(Object oj) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#broadcastMatchResult(java.lang.Object)
   */
  @Override
  public void broadcastMatchResult(Object oj) {
    // TODO Auto-generated method stub

  }

}
