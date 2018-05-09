package de.skat3.gui.matchfield;

import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.main.SkatMain;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Controller class to handle the events of the result screen.
 * 
 * @author tistraub
 */
public class RoundResultViewController {

  @FXML
  private Label pointsSoloPlayer;
  @FXML
  private Label overbid;
  @FXML
  private Label scoringPoints;
  @FXML
  private Label headerLabel;
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
  @FXML
  private Label contract;
  @FXML
  public Button closeButton;
  @FXML
  public AnchorPane root;


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
      if (result.isBierlachs) {
        scoringPoints.setText("Team lost (" + result.scoringPoints + " Points)");
      } else {
        scoringPoints.setText("Won (" + result.scoringPoints + " Points)");
      }
    } else {
      if (result.isBierlachs) {
        scoringPoints.setText("Solo lost (" + result.scoringPoints + " Points)");
      } else {
        scoringPoints.setText("Lost (" + result.scoringPoints + " Points)");
      }
    }

    pointsSoloPlayer.setText("" + result.pointsSoloPlayer + " / 120");

    declarerName.setText(result.declarerName);

    contract.setText(result.contract.getTitleCase());

    if (result.bidTooHigh) {
      overbid.setText("Overbid");
    } else {
      overbid.setText("");
    }

    if (SkatMain.lgs.localPlayerIsDealer()) {
      headerLabel.setText("Spectator");
    } else {
      if (SkatMain.lgs.getLocalClient().isSolo()) {
        if (result.soloWon) {
          headerLabel.setText("You win!");
        } else {
          headerLabel.setText("You lose!");
        }
      } else {
        if (!result.soloWon) {
          headerLabel.setText("You win!");
        } else {
          headerLabel.setText("You lose!");
        }
      }
    }



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
