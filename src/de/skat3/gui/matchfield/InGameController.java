package de.skat3.gui.matchfield;

import java.awt.Point;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.main.SkatMain;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
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

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#makeAMove(boolean)
   */
  @Override
  public void makeAMove(boolean value) {
    this.matchfield.tableController.setCardsPlayable(value);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#playCard(java.util.UUID,
   * de.skat3.gamelogic.Card)
   */
  @Override
  public void playCard(Player owner, Card card) {
    GuiHand playingHand = this.matchfield.tableController.getHand(owner);
    GuiCard guiCard = playingHand.getGuiCard(card);
    if (guiCard == null) {
      int index = playingHand.getCards().size() / 2;
      playingHand.remove(index);
      guiCard = new GuiCard(card);
      playingHand.add(index, guiCard, false);
    }
    this.matchfield.tableController.playCard(playingHand, guiCard);
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
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showAuctionWinner()
   */
  @Override
  public void showAuctionWinner() {
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
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showEndScreen()
   */
  @Override
  public void showEndScreen() {
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


  @Override
  public void showSkatSelection() {
    this.matchfield.tableController.showSkatSelection();
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#startRound()
   */
  @Override
  public void startRound() {
    this.matchfield.tableController.iniHands();
    this.matchfield.overlayController.iniEmemyOne(SkatMain.lgs.enemyOne);
    this.matchfield.overlayController.iniEmemyTwo(SkatMain.lgs.enemyTwo);
    this.matchfield.overlayController.iniLocalClient(SkatMain.lgs.localClient);
  }

  public void bidRequest(int bid) {
    this.matchfield.overlayController.showBidRequest(bid);

  }

  public void initializePlayers() {
    this.matchfield.overlayController.iniEmemyOne(SkatMain.lgs.enemyOne);
    this.matchfield.overlayController.iniEmemyTwo(SkatMain.lgs.enemyTwo);
    this.matchfield.overlayController.iniLocalClient(SkatMain.lgs.localClient);
  }

}
