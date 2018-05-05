/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 19.03.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */

package de.skat3.network.server;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.main.Lobby;
import de.skat3.network.ServerLogicInterface;
import de.skat3.network.datatypes.CommandType;
import de.skat3.network.datatypes.MessageCommand;
import de.skat3.network.datatypes.MessageType;
import java.util.ArrayList;


/**
 * Receives orders from the GameLogic and handles them to be send over the network to the clients.
 * Logic > this class > Server Network. IMPLEMENTs [SEND] COMMANDTYPEs.
 * 
 * @author Jonas Bauer
 *
 */
public class ServerLogicController implements ServerLogicInterface {

  // ServerGameState
  @Deprecated
  int connectedPlayer;
  @Deprecated
  int serverMatchMode;
  @Deprecated
  int maxPlayer;

  //
  GameServer gs;


  ServerLogicController(int maxPlayer, GameServer gs) {
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
  public void broadcastRoundStarted() {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, null, CommandType.ROUND_START_INFO);
    gs.broadcastMessage(mc);


  }

  public void updatePlayerDuringRound(Player player) {
    // player.secretBackupHand


    player.wonTricks = new ArrayList<Card>();
    // System.out.println("SERVER RECEIVED: " + player.getName() + "CARDS" + player.getHand());
    // System.out.println("UPDATEPLAYER SLC CALL:" + player.getUuid() + "\n \n" + player);
    MessageCommand mc = new MessageCommand(MessageType.COMMAND_INFO, player.toString(),
        CommandType.ROUND_GENERAL_INFO);
    mc.gameState = player;
    for (int i = 0; i < player.getHand().cards.length; i++) {
      player.getHand().cards[i].imageView = null;
    }
    mc.playerTarget = player;
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
    mc.playerTarget = player;
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

    mc.playerTarget = player;
    gs.sendToPlayer(player, mc);
  }

  /*
   * ckj (non-Javadoc)
   * 
   * @see de.skat3.network.ServerLogicInterface#broadcastContract(de.skat3. gamelogic.Contract)
   */
  @Override
  public void broadcastContract(Contract contract, AdditionalMultipliers am) {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.CONTRACT_INFO);

    mc.payload = contract;
    mc.secondPayload = am;

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

    mc.playerTarget = p;
    gs.sendToPlayer(p, mc);


  }

  @Override
  public void callForContract(Player p) {


    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_ACTION, p.toString(), CommandType.CONTRACT_REQUEST);

    mc.playerTarget = p;
    gs.sendToPlayer(p, mc);


  }

  @Override
  public void sendSkat(Player p, Card[] skat) {


    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_ACTION, p.toString(), CommandType.SKAT_INFO_REQUEST);
    mc.gameState = skat;
    mc.playerTarget = p;

    gs.sendToPlayer(p, mc);

  }

  @Override
  public void broadcastBid(String message, Player p) {
    System.out.println("broadcasting bid [SERVER / LOGIC] : " + message);

    MessageCommand mc = new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.BID_INFO);
    mc.gameState = message;
    mc.originSender = p;

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
  public void kontraRequest(Player[] players) {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.KONTRA_SHOW_OPTION_INFO);

    for (Player p : players) {
      gs.sendToPlayer(p, mc);
    }

  }

  @Override
  public void reKontraRequest(Player player) {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.REKONTRA_SHOW_OPTION_INFO);
    gs.sendToPlayer(player, mc);

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
  public void broadcastServerStateChange(int serverState) {
    // TODO Auto-generated method stub

    MessageCommand mc = new MessageCommand(MessageType.STATE_CHANGE, "ALL");
    mc.gameState = serverState;
    mc.payload = serverState;
    gs.broadcastMessage(mc);

  }


  @Override
  public void broadcastDeclarer(Player p) {

    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, "ALL", CommandType.AUCTION_WINNER_INFO);
    mc.gameState = p;
    gs.broadcastMessage(mc);
  }

  @Override
  public void updateEnemy(Player p) {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, "SOME", CommandType.UPDATE_ENEMY_INFO);
    mc.gameState = p;
    gs.broadcastMessage(mc);
  }

  @Override
  public void callForSpecificPlay(Player player, Card card) {
    MessageCommand mc = new MessageCommand(MessageType.COMMAND_ACTION, player.toString(),
        CommandType.TRAINING_CALL_FOR_SPECIFIC_PLAY);
    mc.gameState = card;
    mc.playerTarget = player;
    gs.sendToPlayer(player, mc);
  }

  @Override
  public void setDealer(Player dealer) {
    MessageCommand mc =
        new MessageCommand(MessageType.COMMAND_INFO, dealer.toString(), CommandType.SET_DEALER);
    mc.gameState = dealer;
    gs.broadcastMessage(mc);
    
  }


}