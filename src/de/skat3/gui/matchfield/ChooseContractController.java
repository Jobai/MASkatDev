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
  private CheckBox cbOpengame;
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


  private Contract currentContract;


  @FXML
  public void initialize() {

    iconSpade.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        currentContract = Contract.SPADES;
        resetAllContracts();
        iconSpade.setStyle("-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;");
      }
    });

    iconHeart.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        currentContract = Contract.HEARTS;
        resetAllContracts();
        iconHeart.setStyle("-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;");
      }
    });

    iconDiamond.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        currentContract = Contract.DIAMONDS;
        resetAllContracts();
        iconDiamond
            .setStyle("-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;");
      }
    });

    iconClub.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        currentContract = Contract.CLUBS;
        resetAllContracts();
        iconClub.setStyle("-fx-border-color: #d60202; -fx-border-radius: 5; -fx-border-width: 2;");
      }
    });

    toggleGrand.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        resetAllContracts();
        currentContract = Contract.GRAND;
        toggleGrand.setSelected(true);
      }
    });

    toggleOpengame.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        resetAllContracts();
        currentContract = Contract.NULL;
        toggleOpengame.setSelected(true);
      }
    });

  }

  public void setContract() {

    AdditionalMultipliers additionalMultipliers = new AdditionalMultipliers(
        cbSchneider.isSelected(), cbSchwarz.isSelected(), cbOpengame.isSelected());
    SkatMain.mainController.contractSelected(currentContract, additionalMultipliers);

    this.root.setVisible(false);
    this.root.setDisable(true);

  }

  public void setOpenGame() {

  }

  public void setSchneider() {
    cbOpengame.setSelected(true);
  }

  public void setSchwarz() {
    cbSchneider.setSelected(true);
    cbOpengame.setSelected(true);
  }

  public void setOuvert() {
    cbSchneider.setSelected(true);
    cbOpengame.setSelected(true);
    cbSchwarz.setSelected(true);
  }

  /**
   * rest all gui contract fields (togglebutton, checkboxes) to default
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
