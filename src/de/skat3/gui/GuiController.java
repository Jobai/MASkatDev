package de.skat3.gui;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Result;
import de.skat3.gui.matchfield.GameResultViewController;
import de.skat3.gui.matchfield.InGameController;
import de.skat3.gui.matchfield.RoundResultViewController;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.Pane;


/**
 * Controller for the main gui
 * 
 * @author Aljoscha Domonell, tistraub
 *
 */
public class GuiController implements GuiControllerInterface {
  private Gui gui;

  private InGameController inGameController;

  public Logger logger = Logger.getLogger("de.skat3.gui");

  public void goInGame() {
    this.inGameController = this.gui.showMatchfield();
  }

  public void goToMenu() {
    try {
      this.gui.showMenu();
    } catch (NullPointerException e) {
      System.err.println("NO GUI Present!");
    }
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
  public void showWrongPassword() {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setTitle("Wrong Password");
    alert.setContentText("The entered password is wrong!");
    alert.showAndWait();

  }

  /**
   * Causes the taskbar icon to blink until the windows is back in focus. Used to inform the user
   * about a needed action or completed task.
   * 
   * @author Jonas Bauer
   */
  public void blinkAlert() {
    this.gui.getMainStage().toFront();
  }

  /**
   * Creates and shows a custom alert prompt. Used for informing the user of a failed action.
   * 
   * @author Jonas Bauer
   * 
   * @param title of the alarm prompt.
   * @param prompt text of the alarm prompt.
   */
  public void showCustomAlarmPromt(String title, String prompt) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setTitle(title);
    alert.setContentText(prompt);
    alert.showAndWait();

  }

  @Override
  public void refreshStatistik() {
    this.gui.getMenuFrame().getController().getStatsMenu().getController().refresh();
  }
}
