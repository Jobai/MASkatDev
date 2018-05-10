package de.skat3.gui.matchfield;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Contract;
import de.skat3.main.SkatMain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Class handels the events of the corresponging view.
 * 
 * @author Timo Straub
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
  @SuppressWarnings("unused")
  private boolean contractSelected = false;

  /**
   * This method initialize the view and set the event handlers.
   */
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
  }

  /**
   * Check if user play handgame and if not so disable Schneider, Schwarz and Overt.
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
   * Saves the selected contract and send it to the other players.
   */
  public void setContract() {

    AdditionalMultipliers additionalMultipliers = new AdditionalMultipliers(
        cbSchneider.isSelected(), cbSchwarz.isSelected(), cbOuvert.isSelected());
    SkatMain.mainController.contractSelected(currentContract, additionalMultipliers);

    SkatMain.guiController.getInGameController().matchfield.overlayController.showTimer(false);
    SkatMain.guiController.getInGameController().matchfield.overlayController.remove(this.root);

  }

  /**
   * Clear checkboxes Schneider, Schwarz, Ouvert.
   */
  private void clear() {
    cbSchneider.setSelected(false);
    cbSchwarz.setSelected(false);
    cbOuvert.setSelected(false);
  }

  /**
   * Select cotract schneider.
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
   * Select contract schwarz.
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
   * Select contract ouvert.
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
   * Rest all gui contract fields (togglebutton, checkboxes) to default.
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
