package de.skat3.gui.resultscreen;

import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 * Controller class to handle the events of the result screen.
 * 
 * @author tistraub
 */
public class RoundResultViewController {

  @FXML
  private Label pointsSoloPlayer;
  @FXML
  private Label declarerName;
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

    Player[] players = result.ranks;

    namePlayer1.setText(players[0].getName());
    namePlayer2.setText(players[1].getName());
    namePlayer3.setText(players[2].getName());

    pointsPlayer1.setText("" + players[0].getPoints());
    pointsPlayer2.setText("" + players[1].getPoints());
    pointsPlayer3.setText("" + players[2].getPoints());

    if (players.length == 4) {
      namePlayer4.setText(players[3].getName());
      pointsPlayer4.setText("" + players[3].getPoints());
    }

    if (result.isBierlachs) {
      rounds.setText("" + result.currentRound);
    } else {
      rounds.setText(result.currentRound + "/" + result.maxRounds);
    }

    if (result.soloWon) {
      pointsSoloPlayer.setText("Won (" + result.scoringPoints + " Punkte)");
    } else {
      pointsSoloPlayer.setText("Lost (" + result.scoringPoints + " Punkte)");
    }

    declarerName.setText(result.declarerName);

    // Checkboxes
    cbHandgame.setSelected(result.handGame);
    cbOpenGame.setSelected(result.openGame);
    cbSchneider.setSelected(result.schneider);
    cbSchneiderAnnounced.setSelected(result.schneiderAnnounced);
    cbSchwarz.setSelected(result.schwarz);
    cbSchwarzAnnounced.setSelected(result.schwarzAnnounced);
    cbKontra.setSelected(result.kontra);
    cbRekontra.setSelected(result.rekontra);

  }

}
