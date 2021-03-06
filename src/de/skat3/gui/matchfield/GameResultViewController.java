package de.skat3.gui.matchfield;

import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.MatchResult.PlayerHistory;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * Class to control the corresponding game result view file.
 * 
 * @author Timo Straub
 */
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
  @FXML
  private ListView<String> listViewHistP1;
  @FXML
  private ListView<String> listViewHistP2;
  @FXML
  private ListView<String> listViewHistP3;
  @FXML
  public Button closeButton;
  @FXML
  public Label headerText;
  @FXML
  public AnchorPane root;

  /**
   * Set result value to screen.
   * 
   * @param matchResult Result values of the match
   */
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
    if (matchResult.isBierlachs()) {
      winner.setText(matchResult.getLoser().getName());
      headerText.setText("Loser");
    } else {
      winner.setText(matchResult.getWinner().getName());
      headerText.setText("Winner");
    }

    // Points histroy

    ObservableList<String> listP1 = FXCollections.observableArrayList();
    ObservableList<String> listP2 = FXCollections.observableArrayList();
    ObservableList<String> listP3 = FXCollections.observableArrayList();
    ArrayList<Integer> arrayList;
    int i;

    arrayList = history[0].getHistory();
    i = 1;
    for (Integer integer : arrayList) {
      listP1.add("Round" + i + ": " + integer.toString());
      i++;
    }

    listViewHistP1.setItems(listP1);


    arrayList = history[1].getHistory();
    i = 1;
    for (Integer integer : arrayList) {
      listP2.add("Round " + i + ": " + integer.toString());
      i++;
    }

    listViewHistP2.setItems(listP2);

    arrayList = history[2].getHistory();
    i = 1;
    for (Integer integer : arrayList) {
      listP3.add("Round " + i + ": " + integer.toString());
      i++;
    }

    listViewHistP3.setItems(listP3);

  }

}
