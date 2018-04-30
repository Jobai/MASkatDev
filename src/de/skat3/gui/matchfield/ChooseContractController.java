package de.skat3.gui.matchfield;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Contract;
import de.skat3.main.SkatMain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * 
 * @author tistraub
 *
 */
public class ChooseContractController {

  @FXML
  private Label iconSpade;
  @FXML
  private Label iconHeart;
  @FXML
  private Label iconDiamond;
  @FXML
  private Label iconClub;
  @FXML
  private ToggleButton toggleGrand;
  @FXML
  private ToggleButton toggleNullgame;


  private Contract currentContract;


  @FXML
  public void initialize() {

    iconSpade.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        currentContract = Contract.SPADES;

        iconSpade.setStyle(
            "style=\"-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;\"");

        iconHeart.setStyle("");
        iconDiamond.setStyle("");
        iconClub.setStyle("");
      }
    });

    iconHeart.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        currentContract = Contract.HEARTS;

        iconHeart.setStyle(
            "style=\"-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;\"");

        iconSpade.setStyle("");
        iconDiamond.setStyle("");
        iconClub.setStyle("");
      }
    });

    iconDiamond.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        currentContract = Contract.DIAMONDS;

        iconDiamond.setStyle(
            "style=\"-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;\"");

        iconSpade.setStyle("");
        iconHeart.setStyle("");
        iconClub.setStyle("");
      }
    });

    iconClub.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        currentContract = Contract.CLUBS;

        iconClub.setStyle(
            "style=\"-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;\"");

        iconSpade.setStyle("");
        iconDiamond.setStyle("");
        iconHeart.setStyle("");
      }
    });

  }

  public void setContract() {

    AdditionalMultipliers additionalMultipliers = new AdditionalMultipliers();
    // AdditionalMultipliers additionalMultipliers = new AdditionalMultipliers(schneiderAnnounced,
    // schwarzAnnounced, openHand)
    SkatMain.mainController.contractSelected(currentContract, additionalMultipliers);


  }

}
