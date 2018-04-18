package de.skat3.gui.matchfield;

import java.util.UUID;
import com.sun.javafx.collections.IntegerArraySyncer;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.main.LocalGameState;
import de.skat3.main.SkatMain;
import javafx.util.Duration;

/**
 * @author Aljoscha Domonell
 *
 */
public class InGameController implements InGameControllerInterface {

  private Matchfield matchfield;
  LocalGameState gameState;

  public InGameController(LocalGameState state, Matchfield matchfield) {

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#startRound()
   */
  @Override
  public void startRound() {

    this.matchfield.playerHand.clear();
    this.matchfield.leftHand.clear();
    this.matchfield.leftHand.clear();

    this.matchfield.playerHand.addAll(SkatMain.lgs.localClient.getHand().getCards());
    this.matchfield.leftHand.addAll(SkatMain.lgs.enemyOne.getHand().getCards());
    this.matchfield.leftHand.addAll(SkatMain.lgs.enemyTwo.getHand().getCards());

    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#setRemainingTime(javafx.util.Duration)
   */
  @Override
  public void setRemainingTime(Duration remaningTime) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showRusults(de.skat3.gamelogic.Result)
   */
  @Override
  public void showResults(Result results) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#playCard(java.util.UUID,
   * de.skat3.gamelogic.Card)
   */
  @Override
  public void playCard(Player owner, Card card) {
    GuiHand playingHand = this.matchfield.getHand(owner);
    GuiCard guiCard = playingHand.getGuiCard(card);
    this.matchfield.playCard(playingHand, guiCard);
  }


  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showEndScreen()
   */
  @Override
  public void showEndScreen() {
    // TODO Auto-generated method stub

  }


  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#makeAMove(boolean)
   */
  @Override
  public void makeAMove(boolean value) {



    // TODO Auto-generated method stub

  }


  /*
   * (non-Javadoc)
   * 
   * @see
   * de.skat3.gui.matchfield.InGameControllerInterface#showContract(de.skat3.gamelogic.Contract,
   * de.skat3.gamelogic.AdditionalMultipliers)
   */
  @Override
  public void showContract(Contract contract, AdditionalMultipliers additionalMultipliers) {
    // TODO Auto-generated method stub

  }


  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showAuctionWinner()
   */
  @Override
  public void showAuctionWinner() {
    // TODO Auto-generated method stub

  }

}
