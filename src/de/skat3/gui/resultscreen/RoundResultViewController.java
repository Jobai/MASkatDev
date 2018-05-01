package de.skat3.gui.resultscreen;

import de.skat3.gamelogic.Result;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 * @author tistraub
 * 
 *         Controller class to handle the events of the result screen.
 *
 */
/**
 * @author Timo
 *
 */
public class RoundResultViewController {

  @FXML
  private Label pointsSoloPlayer;
  @FXML
  private Label rounds;
  @FXML
  private Label namePlayer1;
  @FXML
  private Label namePlayer2;
  @FXML
  private Label namePlayer3;
  @FXML
  private Label namePlayer4;
  @FXML
  private Label pointsPlayer1;
  @FXML
  private Label pointsPlayer2;
  @FXML
  private Label pointsPlayer3;
  @FXML
  private Label pointsPlayer4;
  @FXML
  private CheckBox cbHandgame;
  @FXML
  private CheckBox cbOpenGame;
  @FXML
  private CheckBox cbSchneider;
  @FXML
  private CheckBox cbSchneiderAnnounced;
  @FXML
  private CheckBox cbSchwarz;
  @FXML
  private CheckBox cbSchwarzAnnounced;
  @FXML
  private CheckBox cbKontra;
  @FXML
  private CheckBox cbRekontra;


  /**
   * .
   * 
   * @param result Round Result
   */
  public void setResult(Result result) {

    namePlayer1.setText(result.firstPlace.getName());
    namePlayer2.setText(result.secondPlace.getName());
    namePlayer3.setText(result.thirdPlace.getName());

    pointsPlayer1.setText("" + result.firstPlace.getPoints());
    pointsPlayer2.setText("" + result.secondPlace.getPoints());
    pointsPlayer3.setText("" + result.thirdPlace.getPoints());

    if (result.fourthPlace != null) {
      namePlayer4.setText(result.fourthPlace.getName());
      pointsPlayer4.setText("" + result.fourthPlace.getPoints());
    }

    if (result.isBierlachs) {
      rounds.setText("" + result.currentRound);
    } else {
      rounds.setText(result.currentRound + "/" + result.maxRounds);
    }

    if (result.soloWon) {
      pointsSoloPlayer.setText("Gewonnen (" + result.scoringPoints + "Punkte)");
    } else {
      pointsSoloPlayer.setText("Verloren (" + result.scoringPoints + "Punkte)");
    }


    // checkboxes

    // public boolean handGame;
    // public boolean openGame;
    // public boolean schneider;
    // public boolean schneiderAnnounced;
    // public boolean schwarz;
    // public boolean schwarzAnnounced;
    // public boolean kontra;
    // public boolean rekontra;

  }

}
