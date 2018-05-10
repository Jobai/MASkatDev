package de.skat3.gui;

import de.skat3.gui.matchfield.InGameController;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * Controller for the main gui.
 * 
 * @author Aljoscha Domonell, Timo Straub
 *
 */
public class GuiController implements GuiControllerInterface {
  private Gui gui;

  private InGameController inGameController;

  public Logger logger = Logger.getLogger("de.skat3.gui");

  public void goInGame() {
    this.inGameController = this.gui.showMatchfield();
  }

  /**
   * Print screen.
   */
  public void print() {
    for (Node n : this.gui.getMainStage().getScene().getRoot().getChildrenUnmodifiable()) {
      int i = 1;
      System.out.println(i + " : " + n);

      if (n.getClass().equals(SubScene.class)) {
        this.printChildren(i + "", ((SubScene) n).getRoot().getChildrenUnmodifiable());
      }

      try {
        if (!((Parent) n).getChildrenUnmodifiable().isEmpty()) {
          this.printChildren(i + "", ((Parent) n).getChildrenUnmodifiable());
        }
      } catch (Exception e) {
        // .
      }
    }
  }

  /**
   * TODO.
   * 
   * @param s
   * @param list
   */
  public void printChildren(String s, ObservableList<Node> list) {
    int i = 1;
    for (Node n : list) {
      System.out.println(s + "." + i++ + " : " + n);

      try {
        if (!((Parent) n).getChildrenUnmodifiable().isEmpty()) {
          this.printChildren(s + "." + i + "", ((Parent) n).getChildrenUnmodifiable());
        }
      } catch (Exception e) {
        // .
      }
    }
  }

  public void goToMenu() {
    this.inGameController = null;
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
