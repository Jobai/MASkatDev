package de.skat3.gui.matchfield;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Contract;
import de.skat3.main.SkatMain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * @author tistraub
 *
 */
public class ChooseContractController {

  @FXML
  public AnchorPane root;
  @FXML
  private CheckBox cbHandgame;
  @FXML
  private Label iconClub;
  @FXML
  private ToggleButton toggleNullgame;
  @FXML
  private Label iconSpade;
  @FXML
  private CheckBox cbSchneider;
  @FXML
  private Label iconHeart;
  @FXML
  private Label iconDiamond;
  @FXML
  private ToggleButton toggleGrand;
  @FXML
  private CheckBox cbSchwarz;
  @FXML
  private CheckBox cbOuvert;


  private Contract currentContract;


  @FXML
  public void initialize() {

    iconSpade.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        currentContract = Contract.SPADES;

        iconSpade.setStyle("-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;");

        iconHeart.setStyle("");
        iconDiamond.setStyle("");
        iconClub.setStyle("");
      }
    });

    iconHeart.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        currentContract = Contract.HEARTS;

        iconHeart.setStyle("-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;");

        iconSpade.setStyle("");
        iconDiamond.setStyle("");
        iconClub.setStyle("");
      }
    });

    iconDiamond.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        currentContract = Contract.DIAMONDS;

        iconDiamond
            .setStyle("-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;");

        iconSpade.setStyle("");
        iconHeart.setStyle("");
        iconClub.setStyle("");
      }
    });

    iconClub.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        currentContract = Contract.CLUBS;

        iconClub.setStyle("-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;");

        iconSpade.setStyle("");
        iconDiamond.setStyle("");
        iconHeart.setStyle("");
      }
    });

  }

  public void setContract() {

    AdditionalMultipliers additionalMultipliers = new AdditionalMultipliers();
    currentContract = Contract.CLUBS;
    // AdditionalMultipliers additionalMultipliers = new AdditionalMultipliers(schneiderAnnounced,
    // schwarzAnnounced, openHand)
    SkatMain.mainController.contractSelected(currentContract, additionalMultipliers);

    this.root.setVisible(false);
    this.root.setDisable(true);

  }

  public void setHandGame() {

  }

  public void setSchneider() {
    cbHandgame.setSelected(true);
  }

  public void setSchwarz() {
    cbSchneider.setSelected(true);
    cbHandgame.setSelected(true);
  }

  public void setOuvert() {
    cbSchneider.setSelected(true);
    cbHandgame.setSelected(true);
    cbSchwarz.setSelected(true);
  }
}
