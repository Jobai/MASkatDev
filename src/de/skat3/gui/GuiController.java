package de.skat3.gui;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Contract;
import de.skat3.gui.matchfield.InGameController;
import de.skat3.gui.multiplayermenu.HostPopupController;
import de.skat3.gui.resultscreen.GameResultViewController;
import de.skat3.gui.resultscreen.RoundResultViewController;
import de.skat3.main.MainController;
import de.skat3.main.SkatMain;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.Pane;


/**
 * Controller for the main gui
 * 
 * @author adomonel, tistraub
 *
 */
public class GuiController implements GuiControllerInterface {
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

  @Override
  public void showWrongPassword() {

    Alert alert = new Alert(AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setTitle("Wrong Password");
    alert.setContentText("The entered password is wrong!");
    alert.showAndWait();

  }

  @Override
  public void showRoundResult() {

    FXMLLoader fxmlLoader =
        new FXMLLoader(getClass().getResource("resultscreen/RoundResultView.fxml"));
    Parent root = null;
    try {
      root = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Stage stage = new Stage();
    stage.setTitle("Round Result");
    stage.setScene(new Scene(root));

    RoundResultViewController roundResultViwController = fxmlLoader.getController();
    stage.show();
  }

  @Override
  public void showGameResult() {

    FXMLLoader fxmlLoader =
        new FXMLLoader(getClass().getResource("resultscreen/GameResultView.fxml"));
    Parent root = null;
    try {
      root = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Stage stage = new Stage();
    stage.setTitle("Game Result");
    stage.setScene(new Scene(root));

    GameResultViewController gameResultViewController = fxmlLoader.getController();
    stage.show();
  }
}
