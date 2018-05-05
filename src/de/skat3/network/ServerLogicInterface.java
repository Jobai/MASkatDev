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

  public void broadcastRoundStarted();

  // tells a (remote) player that he has to bid.
  public void callForBid(Player player, int biddingValue);

  // tells a (remote) player that he has to play a card
  public void callForPlay(Player player);

  // tells everyone what the contract is.
  void broadcastContract(Contract contract, AdditionalMultipliers am);

  void broadcastMatchResult(MatchResult mr);

  public void sendPlayedCard(Player player, Card card);

  void broadcastTrickResult(Player trickWinner);

  public void callForHandOption(Player p);

  public void callForContract(Player p);

  public void sendSkat(Player p, Card[] skat);

  public void broadcastRoundResult(Result result);

  public void broadcastRekontraAnnounced();

  public void kontraRequest(Player[] players);

  public void reKontraRequest(Player player);

  public void broadcastRoundRestarted();

  public void broadcastServerStateChange(int serverState);

  void broadcastKontraAnnounced();

  void broadcastDeclarer(Player p);


  void updateEnemy(Player p);

  void broadcastBid(String message, Player p);

  void callForSpecificPlay(Player player, Card card);

}

