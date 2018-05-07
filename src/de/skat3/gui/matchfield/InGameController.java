package de.skat3.gui.matchfield;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.gui.resultscreen.GameResultViewController;
import de.skat3.gui.resultscreen.RoundResultViewController;
import de.skat3.main.SkatMain;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * InGame Gui controller class.
 * 
 * @author Aljoscha Domonell
 *
 */
public class InGameController implements InGameControllerInterface {
  Matchfield matchfield;

  public InGameController(Matchfield matchfield) {
    this.matchfield = matchfield;
  }

  public void showKontraButton() {
    this.matchfield.overlayController.annouceContraButton.setVisible(true);
    this.matchfield.overlayController.annouceContraButton.setText("Announce Kontra");
    this.matchfield.overlayController.annouceContraButton.setOnAction(e -> {
      SkatMain.mainController.localKontraAnnounced();
      this.matchfield.overlayController.annouceContraButton.setVisible(false);
    });
  }

  public void showReKontraButton() {
    this.matchfield.overlayController.annouceContraButton.setVisible(true);
    this.matchfield.overlayController.annouceContraButton.setText("Announce Rekontra");
    this.matchfield.overlayController.annouceContraButton.setOnAction(e -> {
      SkatMain.mainController.localRekontraAnnounced();
      this.matchfield.overlayController.annouceContraButton.setVisible(false);
    });
  }

  /**
   * asd.
   * 
   * @param value .
   */
  public void showSelectionInfos(boolean value) {
    if (value) {
      this.matchfield.overlayController.setRoundInfos(SkatMain.lgs.getContract(),
          SkatMain.lgs.getAdditionalMultipliers());

      if (SkatMain.lgs.getAdditionalMultipliers().isSchwarzAnnounced()
          || SkatMain.lgs.getAdditionalMultipliers().isOpenHand()) {
        this.matchfield.tableController.iniHands();
      }
    } else {
      this.matchfield.overlayController.setRoundInfos(null, null);
    }

    this.matchfield.tableView.trick.showBidingCards(false);
    this.matchfield.tableController.refreshHands();
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#makeAMove(boolean)
   */
  @Override
  public void makeAMoveRequest(boolean value) {
    this.matchfield.overlayController.setPlayText(InGameOverlayController.yourMove, value, true);
    this.matchfield.tableController.setCardsPlayable(value,
        SkatMain.lgs.getLocalClient().getHand().getCards());
    if (SkatMain.lgs.getTimerInSeconds() > 0) {
      this.matchfield.overlayController.showTimer(value);
      if (value) {
        this.matchfield.overlayController.setTimer(SkatMain.lgs.getTimerInSeconds());
      }
    }
  }


  /**
   * Is setting only the card which is passed with this method to be playable in the gui. So only
   * this card can be played via click on it.
   * 
   * @param card
   */
  public void singleMakeAMoveRequest(Card card, boolean value) {
    this.matchfield.overlayController.setPlayText(InGameOverlayController.yourMove, value, true);

    Card[] playableRef = SkatMain.lgs.getLocalClient().getHand().getCards().clone();
    for (int i = 0; i < playableRef.length; i++) {
      try {
        if (playableRef[i].equals(card)) {
          playableRef[i].setPlayable(true);
        } else {
          playableRef[i].setPlayable(false);
        }
      } catch (Exception e) {
        // not important
      }
    }
    this.matchfield.tableController.setCardsPlayable(true, playableRef);

    if (SkatMain.lgs.getTimerInSeconds() > 0) {
      this.matchfield.overlayController.showTimer(value);
      if (value) {
        this.matchfield.overlayController.setTimer(SkatMain.lgs.getTimerInSeconds());
      }
    }
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
  @Deprecated
  public void startTimer(int time) {
    // this.matchfield.overlayController.setTimer(time);
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
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showEndScreen()
   */
  @Override
  public void showEndScreen(MatchResult matchResult) {
    this.matchfield.overlayController.showMatchResult(matchResult);
  }

  /*
   * 
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showRusults(de.skat3.gamelogic.Result)
   */
  /**
   * @author tistraub
   */
  @Override
  public void showResults(Result results) {
    this.matchfield.overlayController.showRoundResult(results);
  }


  @Override
  public void showSkatSelectionRequest() {
    this.matchfield.tableView.trick.showBidingCards(false);
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
    this.matchfield.overlayController.iniStartRound();
    this.matchfield.tableView.trick.showBidingCards(true);
    this.showSelectionInfos(false);
  }


  /**
   * Is showing a text on the screen.
   * 
   * @param text Text to be shown.
   */
  public void showText(String text) {
    this.matchfield.overlayController.showInMainInfo(text, Duration.seconds(3));
  }

  @Override
  public void showBidRequest(int bid) {
    this.matchfield.overlayController.showBidRequest(bid);

  }

  public void initializePlayers() {
    this.matchfield.overlayController.iniEmemyOne(SkatMain.lgs.getEnemyOne());
    this.matchfield.overlayController.iniEmemyTwo(SkatMain.lgs.getEnemyTwo());
    this.matchfield.overlayController.iniLocalClient(SkatMain.lgs.getLocalClient());
  }

  @Override
  public void showHandGameRequest() {
    this.matchfield.overlayController.showHandGameRequest();
  }

  @Override
  public void showContractRequest() {
    this.matchfield.overlayController.showContractRequest();
  }

  @Override
  public void showTrainingModeInfoText(String text, int width, int height) {
    this.matchfield.overlayController.showTrainingModeInfoText(text, width, height);
  }

}
