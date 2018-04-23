/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 19.03.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */
package de.skat3.network.server;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.main.Lobby;
import de.skat3.main.SkatMain;
import de.skat3.network.ServerLogicInterface;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.MessageCommand;
import de.skat3.network.datatypes.MessageType;

/**
 * Logic > this class > Server Network
 * 
 * I IMPLEMENT [SEND] COMMANDTYPEs
 * 
 * @author Jonas Bauer
 *
 *         I IMPLEMENT [SEND] COMMANDTYPEs
 */
public class ServerLogicController implements ServerLogicInterface {

  // ServerGameState
  int connectedPlayer;
  int serverMatchMode;
  int maxPlayer;

  //
  GameServer gs;

  /**
   * @author Jonas Bauer
   * @param maxPlayer
   * @param gsp
   */
  public ServerLogicController(int maxPlayer, GameServer gs) {
    this.maxPlayer = maxPlayer;
    this.gs = gs;
    this.connectedPlayer = 0;
    this.serverMatchMode = 0;
  }

  public ServerLogicController(Lobby lobbysettings, GameServer gs) {
    this(lobbysettings.getMaximumNumberOfPlayers(), gs);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#sendStartHandtoPlayer(de.skat3. gamelogic.Player)
   */
  @Override
  public void sendStartHandtoPlayer(Player player) {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, player.toString(), CommandType.GAME_INFO); 
    mc.gameState = player;
    gs.sendToPlayer(player, mc);

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#callForBid(de.skat3.gamelogic. Player, int)
   */
  @Override
  public void callForBid(Player player, int biddingValue) {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_ACTION, player.toString(), CommandType.BID_REQUEST);
    mc.gameState = (Integer) biddingValue;
    mc.payload = player;
    gs.sendToPlayer(player, mc);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#callForPlay(de.skat3.gamelogic. Player)
   */
  @Override
  public void callForPlay(Player player) {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_ACTION, player.toString(), CommandType.PLAY_REQUEST);

    gs.sendToPlayer(player, mc);
  }

  /*
   * ckj (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#broadcastContract(de.skat3. gamelogic.Contract)
   */
  @Override
  public void broadcastContract(Contract contract) {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_ACTION, "ALL", CommandType.GAME_INFO);

    gs.broadcastMessage(mc);

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#sendPlayedCard(de.skat3.gamelogic. Player,
   * de.skat3.gamelogic.Card)
   */
  @Override
  public void sendPlayedCard(Player player, Card card) {
    MessageCommand mc = new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.PLAY_INFO);
    mc.gameState = card;
    mc.originSender = player;
    gs.broadcastMessage(mc);

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#broadcastTrickResult(java.lang. Object)
   */
  @Override
  public void broadcastTrickResult(Player trickWinner) {

    MessageCommand mc = new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.TRICK_INFO);
    mc.gameState = trickWinner;

    gs.broadcastMessage(mc);

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#broadcastRoundResult(java.lang. Object)
   */
  @Override
  public void broadcastRoundResult(Result result) {
    // TODO Auto-generated method stub


    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.ROUND_END_INFO);
    mc.payload = result;
    gs.broadcastMessage(mc);
    System.out.println("Round out");
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#broadcastMatchResult(java.lang. Object)
   */
  @Override
  public void broadcastMatchResult(MatchResult mr) {
    MessageCommand mc = new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.MATCH_INFO);
    mc.gameState = mr;
    System.out.println("Match out");
    gs.broadcastMessage(mc);

  }

  @Override
  public void callForHandOption(Player p) {

    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_ACTION, p.toString(), CommandType.HAND_REQUEST);

    gs.sendToPlayer(p, mc);

  }

  @Override
  public void callForContract(Player p) {

    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_ACTION, p.toString(), CommandType.CONTRACT_REQUEST);

    gs.sendToPlayer(p, mc);

  }

  @Override
  public void sendSkat(Player p, Card[] skat) {

    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_ACTION, p.toString(), CommandType.SKAT_INFO_REQUEST);
    mc.gameState = skat;

    gs.sendToPlayer(p, mc);

  }

  public void broadcastBid(boolean bid) {
  System.out.println("broadcasting bid [SERVER / LOGIC] : " + bid);
    
    MessageCommand mc = new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.BID_INFO);
    mc.gameState = bid;

    gs.broadcastMessage(mc);
  }

  @Override
  public void broadcastKontraAnnounced() {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.KONTRA_ANNOUNCED_INFO);
    gs.broadcastMessage(mc);

  }

  @Override
  public void broadcastRekontraAnnounced() {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.REKONTRA_ANNOUNCED_INFO);
    gs.broadcastMessage(mc);

  }

  @Override
  public void KontraRequest(Player[] players) {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.KONTRA_SHOW_OPTION_INFO);

    for (Player p : players) {
      gs.sendToPlayer(p, mc);
    }

  }

  @Override
  public void RekontraRequest(Player player) {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.REKONTRA_SHOW_OPTION_INFO);
    gs.sendToPlayer(player, mc);

  }

  @Override
  public void broadcastKontraRekontraExpired() {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.KONTRA_HIDE_OPTION_INFO);
    gs.broadcastMessage(mc);

  }

  @Override
  public void broadcastRoundRestarted() {
    // TODO Auto-generated method stub

    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.ROUND_RESTART_INFO);
    gs.broadcastMessage(mc);

  }

  @Deprecated
  @Override
  public void broadcastServerStateChange(int ServerState) {
    // TODO Auto-generated method stub

    MessageCommand mc = new MessageCommand(MessageType.STATE_CHANGE, "ALL");
    mc.gameState = ServerState;
    mc.payload = ServerState;
    gs.broadcastMessage(mc);

  }
//
//  public void broadcastDeclarer(Player declarer) {
//    // TODO Auto-generated method stub
//  }

  @Override
  public void broadcastTrickResult(Object oj) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void broadcastMatchResult(Object oj) {
    // TODO Auto-generated method stub
    
  }



}

