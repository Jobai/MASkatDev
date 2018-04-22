package de.skat3.gui.matchfield;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.main.SkatMain;
import javafx.util.Duration;

/**
 * InGame Gui controller class.
 * 
 * @author Aljoscha Domonell
 *
 */
public class InGameController implements InGameControllerInterface {

  private Matchfield matchfield;

  public InGameController(Matchfield matchfield) {
    this.matchfield = matchfield;
  }

  public void showSkatSelection() {
    this.matchfield.showSkatSelection();
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
    this.matchfield.playerHand.setPlayer(SkatMain.lgs.localClient);
    this.matchfield.leftHand.addAll(SkatMain.lgs.enemyOne.getHand().getCards());
    this.matchfield.leftHand.setPlayer(SkatMain.lgs.enemyOne);
    this.matchfield.rightHand.addAll(SkatMain.lgs.enemyTwo.getHand().getCards());
    this.matchfield.rightHand.setPlayer(SkatMain.lgs.enemyTwo);
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
    this.matchfield.setCardsPlayable(value);
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
