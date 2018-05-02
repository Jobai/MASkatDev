package de.skat3.gui.matchfield;

import de.skat3.main.SkatMain;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * @author Aljoscha Domonell
 *
 */
public class ScoreboardController {
  @FXML
  public AnchorPane root;

  @FXML
  private Label nameP14;

  @FXML
  private Label valueP14;

  @FXML
  private Label nameP1;

  @FXML
  private Label valueP1;

  @FXML
  private Label value2P1;

  @FXML
  private Label nameP2;

  @FXML
  private Label valueP2;

  @FXML
  private Label value2P2;

  @FXML
  private Label nameP3;

  @FXML
  private Label valueP3;

  @FXML
  private Label value2P3;

  @FXML
  private Label nameP4;

  @FXML
  private Label valueP4;

  @FXML
  private Label value2P4;

  void setScores() {
    String[][] scores = new String[4][3];

    int t = 0;

    try {
      scores[t][0] = SkatMain.lgs.getLocalClient().getName();
      scores[t][1] = String.valueOf(SkatMain.lgs.getLocalClient().getPoints());
      scores[t][2] = String.valueOf(SkatMain.lgs.getLocalClient().getSeegerPoints());
      t++;
    } catch (NullPointerException e) {
      System.out.println("Could not set Local client score in scoreboard");
    }
    try {
      scores[t][0] = SkatMain.lgs.getEnemyOne().getName();
      scores[t][1] = String.valueOf(SkatMain.lgs.getEnemyOne().getPoints());
      scores[t][2] = String.valueOf(SkatMain.lgs.getEnemyOne().getSeegerPoints());
      t++;
    } catch (NullPointerException e) {
      System.out.println("Could not set enemy one score in scoreboard");
    }
    try {
      scores[t][0] = SkatMain.lgs.getEnemyTwo().getName();
      scores[t][1] = String.valueOf(SkatMain.lgs.getEnemyTwo().getPoints());
      scores[t][2] = String.valueOf(SkatMain.lgs.getEnemyTwo().getSeegerPoints());
      t++;
    } catch (NullPointerException e) {
      System.out.println("Could not set enemy two score in scoreboard");
    }
    try {
      if (SkatMain.lgs.dealer != null) {
        scores[t][0] = SkatMain.lgs.dealer.getName();
        scores[t][1] = String.valueOf(SkatMain.lgs.dealer.getPoints());
        scores[t][2] = String.valueOf(SkatMain.lgs.dealer.getSeegerPoints());
        t++;
      }
    } catch (NullPointerException e) {
      System.out.println("Could not set dealer score in scoreboard");
    }

    int v = 1;

    try {
      if (SkatMain.lgs.getLocalClient().getSeegerPoints() != 0
          && SkatMain.lgs.getEnemyOne().getSeegerPoints() != 0
          && SkatMain.lgs.getEnemyTwo().getSeegerPoints() != 0) {
        v = 2;
      }
    } catch (NullPointerException e) {
      e.getMessage();
    }

    for (int i = 0; i < t; i++) {
      for (int j = 1; j < t - i; j++) {
        if (Integer.parseInt(scores[i][v]) < Integer.parseInt(scores[i + j][v])) {
          String[] temp = scores[i];
          scores[i] = scores[i + j];
          scores[i + j] = temp;
          j = 0;
          continue;
        }
      }
    }

    while (t < 4) {
      scores[t][0] = "/";
      scores[t][1] = "/";
      scores[t][2] = "/";
      t++;
    }

    t = 0;
    this.nameP1.setText(scores[t][0]);
    this.valueP1.setText(String.valueOf(scores[t][1]));
    this.value2P1.setText(String.valueOf(scores[t][2]));
    t++;
    this.nameP2.setText(scores[t][0]);
    this.valueP2.setText(String.valueOf(scores[t][1]));
    this.value2P2.setText(String.valueOf(scores[t][2]));
    t++;
    this.nameP3.setText(scores[t][0]);
    this.valueP3.setText(String.valueOf(scores[t][1]));
    this.value2P3.setText(String.valueOf(scores[t][2]));
    t++;
    this.nameP4.setText(scores[t][0]);
    this.valueP4.setText(String.valueOf(scores[t][1]));
    this.value2P4.setText(String.valueOf(scores[t][2]));
  }


}
