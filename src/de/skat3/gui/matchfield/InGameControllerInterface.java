package de.skat3.gui.matchfield;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.MatchResult;
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

  /**
   * Player is asked to play a card. The user can now interact with his hand. When chosen the Card
   * will be send to the mainController and makeAMove will be set to false.
   * 
   * @param value True if the player is allowed to do a move. False if not.
   */
  public void makeAMoveRequest(boolean value);

  /**
   * Play a card of a hand.
   * 
   * @param player UUID of the player who plays this card.
   * @param card Card to be played.
   */
  public void playCard(Player player, Card card);

  public void startTimer(int remainingTime);

  public void showAuctionWinner();

  public void showContractRequest();

  public void showEndScreen(MatchResult matchResult);

  public void showBidRequest(int bid);

  public void showResults(Result results);
  
  public void showTrainingModeInfoText(String text);

  public void showHandGameRequest();

  /*
   * Shows the Skat (which has to be stored in SkatMain)!.lgs on the screen. The User can now
   * interact with it. When ready the new Cards in the local hand and the new Skat will be send to
   * the mainController.
   * 
   */
  public void showSkatSelectionRequest();

  // Ich muss kl�ren wie

  /**
   * The GUI will initialize the player hands based on the players in SkatMain.lgs. Changes made to
   * the hand of any player will not effect the hand in the GUI. To play a card use
   * this.playCard(...).
   */
  public void startRound();
}
