package de.skat3.gui.matchfield;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.gui.resultscreen.GameResultViewController;
import de.skat3.gui.resultscreen.RoundResultViewController;
import de.skat3.main.SkatMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
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

  public void showSelectionInfos() {
    this.matchfield.overlayController.setTrump(SkatMain.lgs.contract, null);

    this.matchfield.overlayController.extra1LocalClient
        .setText(SkatMain.lgs.getLocalClient().isSolo() ? "Solo" : "Team");
    this.matchfield.overlayController.extraEnemyOne
        .setText(SkatMain.lgs.getEnemyOne().isSolo() ? "Solo" : "Team");
    this.matchfield.overlayController.extraEnemyTwo
        .setText(SkatMain.lgs.getEnemyTwo().isSolo() ? "Solo" : "Team");
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#makeAMove(boolean)
   */
  @Override
  public void makeAMove(boolean value) {
    System.out.println("make a move: " + value);
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
  public void setRemainingTime(int remainingTime) {
    this.matchfield.overlayController.setTimer(remainingTime);
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

  public void showBidRequest(int bid) {
    this.matchfield.overlayController.showBidRequest(bid);

  }

  public void initializePlayers() {
    this.matchfield.overlayController.iniEmemyOne(SkatMain.lgs.getEnemyOne());
    this.matchfield.overlayController.iniEmemyTwo(SkatMain.lgs.getEnemyTwo());
    this.matchfield.overlayController.iniLocalClient(SkatMain.lgs.getLocalClient());
  }

  public void showHandGameRequest() {
    this.matchfield.overlayController.showHandGameRequest();
  }

  public void showContractRequest() {
    this.matchfield.overlayController.showContractRequest();
  }

}
