package de.skat3.gui.matchfield;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Contract;
import de.skat3.main.SkatMain;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
  private Label iconClub;
  @FXML
  private ToggleButton toggleOpengame;
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
  @FXML
  private Button saveButton;


  private Contract currentContract;
  private boolean contractSelected = false;


  @FXML
  public void initialize() {

    iconSpade.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        contractSelected = true;
        currentContract = Contract.SPADES;
        resetAllContracts();
        iconSpade.setStyle("-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;");
      }
    });

    iconHeart.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        contractSelected = true;
        currentContract = Contract.HEARTS;
        resetAllContracts();
        iconHeart.setStyle("-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;");
      }
    });

    iconDiamond.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        contractSelected = true;
        currentContract = Contract.DIAMONDS;
        resetAllContracts();
        iconDiamond
            .setStyle("-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;");
      }
    });

    iconClub.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        contractSelected = true;
        currentContract = Contract.CLUBS;
        resetAllContracts();
        iconClub.setStyle("-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;");
      }
    });

    toggleGrand.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        resetAllContracts();
        contractSelected = true;
        currentContract = Contract.GRAND;
        toggleGrand.setSelected(true);
      }
    });

    toggleOpengame.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        resetAllContracts();
        contractSelected = true;
        currentContract = Contract.NULL;
        toggleOpengame.setSelected(true);
      }
    });

    // saveButton.disableProperty().bind(BooleanExpression.booleanExpression(value));

  }

  /**
   * .
   */
  public void checkIfHandgame() {
    // If handgame disable all checkboxes
    if (!SkatMain.guiController.getInGameController().matchfield.overlayController.isHandgame()) {
      cbSchneider.setDisable(true);
      cbSchwarz.setDisable(true);
      cbOuvert.setDisable(true);
    } else {
      cbSchneider.setDisable(false);
      cbSchwarz.setDisable(false);
      cbOuvert.setDisable(false);
    }
  }


  /**
   * .
   */
  public void setContract() {

    AdditionalMultipliers additionalMultipliers = new AdditionalMultipliers(
        cbSchneider.isSelected(), cbSchwarz.isSelected(), cbOuvert.isSelected());
    SkatMain.mainController.contractSelected(currentContract, additionalMultipliers);

    SkatMain.guiController.getInGameController().matchfield.overlayController.remove(this.root);

  }

  private void clear() {
    cbSchneider.setSelected(false);
    cbSchwarz.setSelected(false);
    cbOuvert.setSelected(false);
  }

  /**
   * .
   */
  public void setSchneider() {


    if (!cbSchneider.isSelected()) {
      clear();
      cbSchneider.setSelected(false);
    } else {
      cbSchneider.setSelected(true);
    }
  }

  /**
   * .
   */
  public void setSchwarz() {

    if (!cbSchwarz.isSelected()) {
      clear();
      cbSchwarz.setSelected(false);
    } else {
      cbSchwarz.setSelected(true);
    }

    cbSchneider.setSelected(true);
  }

  /**
   * .
   */
  public void setOuvert() {

    if (!cbOuvert.isSelected()) {
      clear();
      cbOuvert.setSelected(false);
    } else {
      cbOuvert.setSelected(true);
    }
    cbSchneider.setSelected(true);
    cbSchwarz.setSelected(true);
  }

  /**
   * rest all gui contract fields (togglebutton, checkboxes) to default.
   */
  private void resetAllContracts() {
    iconClub.setStyle("");
    iconSpade.setStyle("");
    iconDiamond.setStyle("");
    iconHeart.setStyle("");

    toggleGrand.setSelected(false);
    toggleOpengame.setSelected(false);
  }
}
