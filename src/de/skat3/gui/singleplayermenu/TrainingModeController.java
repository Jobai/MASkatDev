package de.skat3.gui.singleplayermenu;

import de.skat3.main.SkatMain;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * Class to control the corresponding training mode view file.
 * 
 * @author Timo Straub
 */
public class TrainingModeController {
  private AnchorPane main;
  private Pane trainingMode;

  public void setPanes(AnchorPane main, Pane trainingMode) {
    this.main = main;
    this.trainingMode = trainingMode;
  }

  public void startSkatBasic() {
    SkatMain.mainController.startTrainingMode(0);
  }

  public void startTrumpMonopoly() {
    SkatMain.mainController.startTrainingMode(1);
  }

  public void startPressTheSkat() {
    SkatMain.mainController.startTrainingMode(2);
  }

  public void startOfferPointsForTeamPlayer() {
    SkatMain.mainController.startTrainingMode(3);
  }

  public void startPlayShortToFriend() {
    SkatMain.mainController.startTrainingMode(4);
  }

  public void startPlayLongToEnemy() {
    SkatMain.mainController.startTrainingMode(5);
  }

  public void close() {
    this.main.getChildren().remove(this.trainingMode);
  }


}
