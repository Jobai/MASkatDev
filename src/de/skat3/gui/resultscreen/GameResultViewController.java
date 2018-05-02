package de.skat3.gui.resultscreen;

import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.MatchResult.PlayerHistory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameResultViewController {

  @FXML
  private Label nameP4;
  @FXML
  private Label nameP3;
  @FXML
  private Label nameP2;
  @FXML
  private Label winner;
  @FXML
  private Label nameP1;
  @FXML
  private Label pointsP1;
  @FXML
  private Label pointsP2;
  @FXML
  private Label pointsP3;
  @FXML
  private Label pointsP4;


  public void setResult(MatchResult matchResult) {

    PlayerHistory[] history = matchResult.getData();

    nameP1.setText(history[0].getName());
    pointsP1.setText("" + history[0].getFinalScore());

    nameP2.setText(history[1].getName());
    pointsP2.setText("" + history[1].getFinalScore());

    nameP3.setText(history[2].getName());
    pointsP3.setText("" + history[2].getFinalScore());

    if (history.length == 4) {
      nameP4.setText(history[3].getName());
      pointsP4.setText("" + history[3].getFinalScore());
    }

    // Determine winner
    int score = 0;
    for (PlayerHistory playerHistory : history) {
      if (playerHistory.getFinalScore() > score) {
        winner.setText(playerHistory.getName());
      }
    }


  }

}
