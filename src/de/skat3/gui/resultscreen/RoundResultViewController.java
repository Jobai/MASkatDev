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
public class RoundResultViewController {

  @FXML
  private Label pointsPlayer1;
  @FXML
  private Label pointsPlayer2;
  @FXML
  private Label pointsPlayer3;
  @FXML
  private Label pointsSoloPlayer;
  @FXML
  private CheckBox cbGrande;
  @FXML
  private CheckBox cbSuit;
  @FXML
  private CheckBox cbNullOvert;
  @FXML
  private Label namePlayer4;
  @FXML
  private Label namePlayer3;
  @FXML
  private Label namePlayer2;
  @FXML
  private Label namePlayer1;
  @FXML
  private Label pointsPlayer4;
  @FXML
  private Label rounds;
  @FXML
  private CheckBox cbNull;



  public void setResult(Result result) {
    
    System.out.println(result.firstPlace.getName());

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


    // rounds.setText(result.);

    // checkboxes
    // TODO

  }

}
