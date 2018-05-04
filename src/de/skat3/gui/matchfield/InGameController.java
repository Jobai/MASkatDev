package de.skat3.gui.matchfield;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
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

  public void showSelectionInfos(boolean value) {
    if (value) {
      this.matchfield.overlayController.setRoundInfos(SkatMain.lgs.contract,
          SkatMain.lgs.additionalMultipliers);

      // if (SkatMain.lgs.contract == Contract.NULL
      // || SkatMain.lgs.additionalMultipliers.isSchwarzAnnounced()
      // || SkatMain.lgs.additionalMultipliers.isOpenHand()) {
      this.matchfield.tableController.iniHands();
      // }

      this.matchfield.tableView.trick.showBidingCards(false);
      this.matchfield.tableController.refreshHands();
    } else {
      this.matchfield.overlayController.setRoundInfos(null, null);
    }
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
    if (SkatMain.lgs.timerInSeconds > 0) {
      this.matchfield.overlayController.showTimer(value);
      if (value) {
        this.matchfield.overlayController.setTimer(SkatMain.lgs.timerInSeconds);
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

    if (SkatMain.lgs.timerInSeconds > 0) {
      this.matchfield.overlayController.showTimer(value);
      if (value) {
        this.matchfield.overlayController.setTimer(SkatMain.lgs.timerInSeconds);
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

    FXMLLoader fxmlLoader =
        new FXMLLoader(getClass().getResource("../resultscreen/GameResultView.fxml"));
    Parent root = null;
    try {
      root = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Stage stage = new Stage();
    stage.setTitle("Game Result");
    stage.setScene(new Scene(root));

    GameResultViewController gameResultViewController = fxmlLoader.getController();
    gameResultViewController.setResult(matchResult);
    stage.show();

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

    FXMLLoader fxmlLoader =
        new FXMLLoader(getClass().getResource("../resultscreen/RoundResultView.fxml"));
    Parent root = null;
    try {
      root = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Stage stage = new Stage();
    stage.setTitle("Round Result");
    stage.setScene(new Scene(root));

    RoundResultViewController roundResultViwController = fxmlLoader.getController();
    roundResultViwController.setResult(results);

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();

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
  }


  /**
   * Is showing a value on the screen which represents a bid which has been set by an other player.
   * 
   * @param player Player who has set this bid.
   * @param bid Bid value the player has set.
   */
  public void showBidActivity(Player player, int bid) {
    if (!player.equals(SkatMain.lgs.getLocalClient())) {
      this.matchfield.overlayController.showInMainInfo(player.getName() + " is biding " + bid,
          Duration.seconds(2));
    }
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
  public void showTrainingModeInfoText(String text) {
    this.matchfield.overlayController.showTrainingModeInfoText(text);
  }

}
