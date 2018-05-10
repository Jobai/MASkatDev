package de.skat3.gui.matchfield;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.gamelogic.TrainingRoundInstance;
import de.skat3.main.SkatMain;
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

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#initializePlayers()
   */
  @Override
  public void initializePlayers() {
    this.matchfield.overlayController.bindChat();
    if (!SkatMain.lgs.isGameActive()) {
      this.matchfield.overlayController.showInMainInfo(
          SkatMain.mainController.currentLobby.getCurrentNumberOfPlayers() + "/"
              + SkatMain.mainController.currentLobby.getMaximumNumberOfPlayers() + " Ready",
          Duration.INDEFINITE);
    } else {
      this.matchfield.overlayController.showInMainInfo("", Duration.millis(1));
    }
    this.matchfield.overlayController.iniEmemyOne(SkatMain.lgs.getEnemyOne());
    this.matchfield.overlayController.iniEmemyTwo(SkatMain.lgs.getEnemyTwo());
    this.matchfield.overlayController.iniLocalClient(SkatMain.lgs.getLocalClient());
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showText(java.lang.String)
   */
  @Override
  public void showText(String text) {
    this.matchfield.overlayController.showInMainInfo(text, Duration.seconds(3));
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#startRound()
   */
  @Override
  public void startRound() {
    this.matchfield.overlayController.showInMainInfo("", Duration.millis(1));
    this.matchfield.tableController.iniHands();
    this.matchfield.overlayController.iniStartRound();
    if (SkatMain.lgs.localPlayerIsDealer()) {
      this.matchfield.overlayController.setPlayText("Spectating", true, false);
    } else {
      this.matchfield.overlayController.setPlayText("", false, false);
      this.matchfield.tableView.trick.showBidingCards(true);
    }

    this.showSelectedGame(false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#playCard(de.skat3.gamelogic.Player,
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
    // this.showWhoIsPlaying(null);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showMakeAMoveRequest(boolean)
   */
  @Override
  public void showMakeAMoveRequest(boolean value) {
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

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showTutorialMakeAMoveRequest(de.skat3.
   * gamelogic.Card, boolean)
   */
  @Override
  public void showTutorialMakeAMoveRequest(Card card, boolean value) {
    this.matchfield.overlayController.setPlayText(InGameOverlayController.yourMove, value, true);

    Card[] playableRef = new Card[SkatMain.lgs.getLocalClient().getHand().getCards().length];
    for (int i = 0; i < SkatMain.lgs.getLocalClient().getHand().getCards().length; i++) {
      playableRef[i] = SkatMain.lgs.getLocalClient().getHand().getCards()[i].copy();
    }
    Card d = null;
    for (int i = 0; i < playableRef.length; i++) {
      try {
        if (playableRef[i].equals(card)) {
          d = playableRef[i];
          d.setPlayable(true);
        } else {
          playableRef[i].setPlayable(false);
        }
      } catch (Exception e) {
        // not important
      }
    }
    this.matchfield.tableController.setCardsPlayable(true, playableRef);

    this.matchfield.tableController.showPlayableColor(true,
        SkatMain.lgs.getLocalClient().getHand().getCards());

    this.matchfield.tableView.playerHand.getGuiCard(d).setBlingBling(true);

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
   * @see
   * de.skat3.gui.matchfield.InGameControllerInterface#showWhoIsPlaying(de.skat3.gamelogic.Player)
   */
  @Override
  public void showWhoIsPlaying(Player player) {
    if (player == null) {
      this.matchfield.tableView.leftHand.setBlingBling(false);
      this.matchfield.tableView.rightHand.setBlingBling(false);
      return;
    }

    if (player.equals(SkatMain.lgs.getEnemyOne())) {
      this.matchfield.tableView.leftHand.setBlingBling(true);
      this.matchfield.tableView.rightHand.setBlingBling(false);
      return;
    }

    if (player.equals(SkatMain.lgs.getEnemyTwo())) {
      this.matchfield.tableView.leftHand.setBlingBling(false);
      this.matchfield.tableView.rightHand.setBlingBling(true);
      return;
    }

    if (player.equals(SkatMain.lgs.getLocalClient())) {
      this.matchfield.tableView.leftHand.setBlingBling(false);
      this.matchfield.tableView.rightHand.setBlingBling(false);
      return;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showBidRequest(int)
   */
  @Override
  public void showBidRequest(int bid) {
    if (SkatMain.lgs.getTimerInSeconds() > 0) {
      this.matchfield.overlayController.setTimer(SkatMain.lgs.getTimerInSeconds());
    }
    this.matchfield.overlayController.showBidRequest(bid);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showTutorialBidRequest(int, boolean)
   */
  @Override
  public void showTutorialBidRequest(int bid, boolean yes) {
    this.matchfield.overlayController.showBidRequest(bid, yes);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showHandGameRequest()
   */
  @Override
  public void showHandGameRequest() {
    if (SkatMain.lgs.getTimerInSeconds() > 0) {
      this.matchfield.overlayController.setTimer(SkatMain.lgs.getTimerInSeconds());
    }
    this.matchfield.overlayController.showHandGameRequest();
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showContractRequest()
   */
  @Override
  public void showContractRequest() {
    if (SkatMain.lgs.getTimerInSeconds() > 0) {
      this.matchfield.overlayController.setTimer(SkatMain.lgs.getTimerInSeconds());
    }
    this.matchfield.overlayController.showContractRequest();
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showSelectedGame(boolean)
   */
  @Override
  public void showSelectedGame(boolean show) {
    if (show) {
      this.matchfield.overlayController.setRoundInfos(SkatMain.lgs.getContract(),
          SkatMain.lgs.getAdditionalMultipliers());

      if (SkatMain.lgs.getAdditionalMultipliers().isSchwarzAnnounced()
          || SkatMain.lgs.getAdditionalMultipliers().isOpenHand()) {
        this.matchfield.tableController.iniHands();
      }

      this.matchfield.tableView.trick.showBidingCards(false);
    } else {
      this.matchfield.overlayController.setRoundInfos(null, null);
    }

    this.matchfield.tableController.refreshHands();
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showKontraButton(boolean)
   */
  @Override
  public void showKontraButton(boolean show) {
    if (show) {
      this.matchfield.overlayController.annouceContraButton.setVisible(true);
      this.matchfield.overlayController.annouceContraButton.setText("Announce Kontra");
      this.matchfield.overlayController.annouceContraButton.setOnAction(e -> {
        SkatMain.mainController.localKontraAnnounced();
        this.matchfield.overlayController.annouceContraButton.setVisible(false);
      });
    } else {
      this.matchfield.overlayController.annouceContraButton.setVisible(false);
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showReKontraButton()
   */
  @Override
  public void showReKontraButton() {
    this.matchfield.overlayController.annouceContraButton.setVisible(true);
    this.matchfield.overlayController.annouceContraButton.setText("Announce Rekontra");
    this.matchfield.overlayController.annouceContraButton.setOnAction(e -> {
      SkatMain.mainController.localRekontraAnnounced();
      this.matchfield.overlayController.annouceContraButton.setVisible(false);
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showSkatSelectionRequest()
   */
  @Override
  public void showSkatSelectionRequest(boolean show) {
    if (SkatMain.lgs.getTimerInSeconds() > 0 && show) {
      this.matchfield.overlayController.setTimer(SkatMain.lgs.getTimerInSeconds());
    }
    this.matchfield.tableView.trick.showBidingCards(false);
    this.matchfield.tableController.showSkatSelection(show);

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * de.skat3.gui.matchfield.InGameControllerInterface#showTrainingModeInfoText(java.lang.String,
   * int, int)
   */
  @Override
  public void showTrainingModeInfoText(String text, int width, int height,
      TrainingRoundInstance trInstance) {
    this.matchfield.overlayController.showTrainingModeInfoText(text, width, height, trInstance);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.skat3.gui.matchfield.InGameControllerInterface#showResults(de.skat3.gamelogic.Result)
   */
  @Override
  public void showResults(Result results) {
    this.matchfield.overlayController.showRoundResult(results);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * de.skat3.gui.matchfield.InGameControllerInterface#showEndScreen(de.skat3.gamelogic.MatchResult)
   */
  @Override
  public void showEndScreen(MatchResult matchResult) {
    this.matchfield.overlayController.showMatchResult(matchResult);
  }

}
