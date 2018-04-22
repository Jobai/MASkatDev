package de.skat3.gui.matchfield;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import javafx.util.Duration;

/**
 * This Controller Interface difines the methods to manipulate the GUI.
 * 
 * @author adomonel
 *
 */
public interface InGameControllerInterface {

  public void startRound();

  public void setRemainingTime(Duration remaningTime);

  public void showResults(Result results);

  public void showEndScreen();

  /**
   * Player is asked to play a card.
   * 
   * @param value True if the player is allowed to do a move. False if not.
   */
  public void makeAMove(boolean value);

  public void showContract(Contract contract, AdditionalMultipliers additionalMultipliers);

  public void showAuctionWinner();

  // Ich muss kl�ren wie

  /**
   * Play a card of a hand.
   * 
   * @param player UUID of the player who plays this card.
   * @param card Card to be played.
   */
  public void playCard(Player player, Card card);
}
