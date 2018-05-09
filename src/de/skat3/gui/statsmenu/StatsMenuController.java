package de.skat3.gui.statsmenu;

import java.text.SimpleDateFormat;
import java.util.Date;
import de.skat3.gui.Gui;
import de.skat3.gui.GuiController;
import de.skat3.io.profile.Profile;
import de.skat3.main.SkatMain;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Class to control the corresponding view file.
 * 
 * @author Timo Straub
 */
public class StatsMenuController {

  @FXML
  private Label sTotalRoundsSuit;
  @FXML
  private Label mRoundsLostNull;
  @FXML
  private Label sLowestScore;
  @FXML
  private Label sRoundsLostGrand;
  @FXML
  private Label mTotalRoundsGrand;
  @FXML
  private Label sTotalRoundsGrand;
  @FXML
  private Label mHighestScore;
  @FXML
  private Label sRoundsWon;
  @FXML
  private Label sHighestScore;
  @FXML
  private Label mTotalRoundsSuit;
  @FXML
  private Label mRoundsWonNull;
  @FXML
  private Label sRoundsWonGrand;
  @FXML
  private Label mTotalRoundsNull;
  @FXML
  private Label sRoundsWonNull;
  @FXML
  private Label sRoundsWonSuit;
  @FXML
  private Label sRoundsLost;
  @FXML
  private Label sRoundsLostNull;
  @FXML
  private Label mTotalRounds;
  @FXML
  private Label sRoundsLostSuit;
  @FXML
  private Label mRoundsLost;
  @FXML
  private Label sTotalRounds;
  @FXML
  private Label mRoundsWonSuit;
  @FXML
  private Label mLowestScore;
  @FXML
  private Label sTotalRoundsNull;
  @FXML
  private Label mRoundsWon;
  @FXML
  private Label mRoundsLostSuit;
  @FXML
  private Label mRoundsWonGrand;
  @FXML
  private Label mRoundsLostGrand;
  @FXML
  private Label gameTime;
  @FXML
  private Label mTotalGames;
  @FXML
  private Label mTotalGamesWon;
  @FXML
  private Label mTotalGamesLost;
  @FXML
  private Label sTotalGames;
  @FXML
  private Label sTotalGamesWon;
  @FXML
  private Label sTotalGamesLost;


  /**
   * Initialize statistic menu and set values of the last used profile to screen.
   */
  @FXML
  public void initialize() {
    Profile p = SkatMain.ioController.getLastUsedProfile();

    if (p == null) {
      return;
    }

    // Time
    long hours = p.getPlayerGameTime() / 3600;
    long minutes = (p.getPlayerGameTime() % 3600) / 60;

    String hoursForm = String.format("%02d", hours);
    String minutesForm = String.format("%02d", minutes);

    String time = hoursForm + ":" + minutesForm;
    gameTime.setText(time);

    // Total Games
    mTotalGames.setText("" + p.getMultiPlayerTotalGames());
    mTotalGamesWon.setText("" + p.getMultiPlayerTotalGamesWon());
    mTotalGamesLost.setText("" + p.getMultiPlayerTotalGamesLost());
    sTotalGames.setText("" + p.getSinglePlayerTotalGames());
    sTotalGamesWon.setText("" + p.getSinglePlayerTotalGamesWon());
    sTotalGamesLost.setText("" + p.getSinglePlayerTotalGamesLost());

    // Singleplayer
    // Rounds
    sTotalRounds.setText("" + p.getSinglePlayerTotalRounds());
    sTotalRoundsGrand.setText("" + p.getSinglePlayerTotalRoundsGrand());
    sTotalRoundsNull.setText("" + p.getSinglePlayerTotalRoundsNull());
    sTotalRoundsSuit.setText("" + p.getSinglePlayerTotalRoundsSuit());
    sRoundsWon.setText("" + p.getSinglePlayerRoundsWon());
    sRoundsWonGrand.setText("" + p.getSinglePlayerRoundsWonGrand());
    sRoundsWonNull.setText("" + p.getSinglePlayerRoundsWonNull());
    sRoundsWonSuit.setText("" + p.getSinglePlayerRoundsWonSuit());
    sRoundsLost.setText("" + p.getSinglePlayerRoundsLost());
    sRoundsLostGrand.setText("" + p.getSinglePlayerRoundsLostGrand());
    sRoundsLostNull.setText("" + p.getSinglePlayerRoundsLostNull());
    sRoundsLostSuit.setText("" + p.getSinglePlayerRoundsLostSuit());

    // Multiplayer
    // Rounds
    mTotalRounds.setText("" + p.getMultiPlayerTotalRounds());
    mTotalRoundsGrand.setText("" + p.getMultiPlayerTotalRoundsGrand());
    mTotalRoundsNull.setText("" + p.getMultiPlayerTotalRoundsNull());
    mTotalRoundsSuit.setText("" + p.getMultiPlayerTotalRoundsSuit());
    mRoundsWon.setText("" + p.getMultiPlayerRoundsWon());
    mRoundsWonGrand.setText("" + p.getMultiPlayerRoundsWonGrand());
    mRoundsWonNull.setText("" + p.getMultiPlayerRoundsWonNull());
    mRoundsWonSuit.setText("" + p.getMultiPlayerRoundsWonSuit());
    mRoundsLost.setText("" + p.getMultiPlayerRoundsLost());
    mRoundsLostGrand.setText("" + p.getMultiPlayerRoundsLostGrand());
    mRoundsLostNull.setText("" + p.getMultiPlayerRoundsLostNull());
    mRoundsLostSuit.setText("" + p.getMultiPlayerRoundsLostSuit());

  }

  /**
   * Refresh statistic.
   */
  public void refresh() {
    initialize();
  }

}
