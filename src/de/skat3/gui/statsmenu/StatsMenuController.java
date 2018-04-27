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
 * @author tistraub
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
  public void initialize() {
    Profile p = SkatMain.ioController.getLastUsedProfile();

    // Time
    String time = new SimpleDateFormat("mm:ss").format(new Date(p.getPlayerGameTime()));
    gameTime.setText(time + " h");

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

    // Score
    sHighestScore.setText("" + p.getSinglePlayerHighestScore());
    sLowestScore.setText("" + p.getSinglePlayerLowestScore());

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

    // Score
    mHighestScore.setText("" + p.getMultiPlayerHighestScore());
    mLowestScore.setText("" + p.getMultiPlayerLowestScore());

  }

  public void refresh() {
    initialize();
  }

}
