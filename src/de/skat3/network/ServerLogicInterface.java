/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 18.03.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */

package de.skat3.network;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;

/**
 * Handles all calls of the gamelogic to the server, so clients are notified. called by Logic.
 * 
 * @author Jonas Bauer
 * 
 */
public interface ServerLogicInterface {

  // Hand is in player object

  /**
   * Broadcast to all players that the round started.
   * @author Jonas Bauer
   */
  public void broadcastRoundStarted();

  /**
   *  tells a (remote) player that he has to bid.
   * @author Jonas Bauer
   * @param player target player
   * @param biddingValue .
   */
  public void callForBid(Player player, int biddingValue);

  // 
  /**
   * Tells a (remote) player that he has to play a card.
   * @author Jonas Bauer
   * @param player target player
   */
  public void callForPlay(Player player);

  // 
  /**
   * Tells everyone what the contract is.
   * @author Jonas Bauer
   * @param contract chosen contract
   * @param am AdditionalMultipliers
   */
  void broadcastContract(Contract contract, AdditionalMultipliers am);

  /**
   * Broadcast to all Players the Matchresult (Endresults of the complete game).
   * @author Jonas Bauer
   * @param mr MatchResult
   */
  void broadcastMatchResult(MatchResult mr);

  /**
   * 
   * @author Jonas Bauer
   * @param player
   * @param card
   */
  public void sendPlayedCard(Player player, Card card);

  /**
   * Broadcast to all player who won the last trick.
   * @author Jonas Bauer
   * @param trickWinner .
   */
  void broadcastTrickResult(Player trickWinner);

  /**
   * Ask the declarer if he wants to play a hand game.
   * @author Jonas Bauer
   * @param p targetplayer (declarer)
   */
  public void callForHandOption(Player p);

  /**
   * Ask the declarer what contract (with additional mutlipliers) he wants to play.
   * @author Jonas Bauer
   * @param p targetplayer (declarer)
   */
  public void callForContract(Player p);

  /**
   * Send the skat to the declarer if he plays not a handgame.
   * @author Jonas Bauer
   * @param p delcarer
   * @param skat the skat
   */
  public void sendSkat(Player p, Card[] skat);

  /**
   * Broadcast the roundresults to all players.
   * @author Jonas Bauer
   * @param result roundresults
   */
  public void broadcastRoundResult(Result result);

  /**
   * Ask the team players if they want to announce kontra.
   * @author Jonas Bauer
   * @param players
   */
  public void kontraRequest(Player[] players);

  /**
   * Aks the solo player if he wants to announce rekontra.
   * @author Jonas Bauer
   * @param player
   */
  public void reKontraRequest(Player player);

  /**
   * Broadcast a announced kontra to all players.
   * @author Jonas Bauer
   */
  void broadcastKontraAnnounced();
  
  /**
   * Broadcast a announced rekontra to all players.
   * @author Jonas Bauer
   */
  public void broadcastRekontraAnnounced();

  /**
   * Broacast to all player who the declarer is (who won the bidding).
   * @author Jonas Bauer
   * @param p the declarer
   */
  void broadcastDeclarer(Player p);

  /**
   * 
   * @author Jonas Bauer
   * @param p
   */
  void updateEnemy(Player p);

  /**
   * Broadcast a bid of a player to all players.
   * @author Jonas Bauer
   * @param message the bid as a message to display directly
   * @param p the bidding player
   */
  void broadcastBid(String message, Player p);

  /**
   * Ask the user to play a certain card. Used in the scenarios / tutorial.
   * @author Jonas Bauer
   * @param player the target player
   * @param card the card to be played
   */
  void callForSpecificPlay(Player player, Card card);

  /**
   * Set a player to be the dealer / spectator.
   * @author Jonas Bauer
   * @param dealer player to be the dealer
   */
  void setDealer(Player dealer);

  /**
   * 
   * @author Jonas Bauer
   * @param player
   */
  void updatePlayerDuringRound(Player player);

}

