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
import javafx.stage.Stage;

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
    this.matchfield.overlayController.setRoundInfos(SkatMain.lgs.contract,
        SkatMain.lgs.additionalMultipliers);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#makeAMove(boolean)
   */
  @Override
  public void makeAMove(boolean value) {
    this.matchfield.overlayController.setPlayText(InGameOverlayController.yourMove, value);
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
  public void startTimer(int time) {
    this.matchfield.overlayController.setTimer(time);
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
    stage.show();

  }


  @Override
  public void showSkatSelectionRequest() {
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

}
