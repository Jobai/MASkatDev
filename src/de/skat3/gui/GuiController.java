package de.skat3.gui;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Contract;
import de.skat3.gui.matchfield.InGameController;
import de.skat3.main.MainController;
import de.skat3.main.SkatMain;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;


public class GuiController implements GuiInterface {
  private Gui gui;

  private InGameController inGameController;

  public void goInGame() {
  this.inGameController = this.gui.showMatchfield();
  }

  public InGameController getInGameController() {
  return this.inGameController;
  }
  protected void setGui(Gui gui) {
    this.gui = gui;
  }

  public void blockInput(boolean value) {
    this.gui.getMainStage().getScene().getRoot().setDisable(value);
  }

  @Override
  public void bidRequest(int bid) {

    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("New bid");
    alert.setHeaderText(bid + "");
    alert.setContentText("Are you ok with this?");

    ButtonType buttonTypeYes = new ButtonType("Yes");
    ButtonType buttonTypeNo = new ButtonType("No");

    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == buttonTypeYes) {
      SkatMain.mainController.localBid(true);
    } else {
      SkatMain.mainController.localBid(false);
    }

  }

  @Override
  public void contractRequest() {

    List<String> contractList = new ArrayList<>();
    Contract[] allContracts = Contract.values();

    for (Contract contract2 : allContracts) {
      contractList.add(contract2.toString());
    }

    ChoiceDialog<String> dialog = new ChoiceDialog<>("", contractList);
    dialog.setTitle("Choose Contracts");
    dialog.setHeaderText("Choose Contracts");
    dialog.setContentText("Choose your contract:");

    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()) {
      SkatMain.mainController.contractSelected(Contract.valueOf(result.get()),
          new AdditionalMultipliers());
    }


  }

  @Override
  public boolean handGameRequest() {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Handgame");
    alert.setHeaderText("Handgame?");

    ButtonType buttonTypeYes = new ButtonType("Yes");
    ButtonType buttonTypeNo = new ButtonType("No");

    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == buttonTypeYes) {
      SkatMain.mainController.handGameSelected(true);
      return true;
    } else {
      SkatMain.mainController.handGameSelected(false);
      return false;
    }

  }

}
