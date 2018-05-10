package de.skat3.gui.singleplayermenu;

import java.io.IOException;
import javafx.animation.ScaleTransition;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Class to control the corresponding view file.
 * 
 * @author Timo Straub
 */
public class SingleplayerMenuController {

  @FXML
  AnchorPane mainPane;

  private ScaleTransition imageSizeAnimation;

  /**
   * Show a popup to select difficulty of KI players and start a Singleplayer Game.
   */
  public void startSinglePlayerGame() {

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SingleplayerView.fxml"));
    Pane p = null;
    try {
      p = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    SingleplayerController c = fxmlLoader.getController();
    c.setPanes(mainPane, p);

    p.translateXProperty()
        .bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(mainPane.translateXProperty())
            .add((mainPane.getPrefWidth() / 2)).subtract((p.getPrefWidth() / 2)));
    p.translateYProperty()
        .bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(mainPane.translateYProperty()).add(20));

    mainPane.getChildren().add(p);

  }

  /**
   * Start training mode.
   */
  public void startTrainingMode() {

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TrainingModeView.fxml"));
    Pane p = null;
    try {
      p = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    TrainingModeController c = fxmlLoader.getController();
    c.setPanes(mainPane, p);

    p.translateXProperty()
        .bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(mainPane.translateXProperty())
            .add((mainPane.getPrefWidth() / 2)).subtract((p.getPrefWidth() / 2)));
    p.translateYProperty()
        .bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(mainPane.translateYProperty()).add(20));

    mainPane.getChildren().add(p);

  }

  /**
   * Is starting the animation when the cursor is over a button.
   * 
   * @param e Event to get the source from.
   */
  public void handleMouseOverEvent(MouseEvent e) {
    ImageView source = (ImageView) e.getSource();
    imageSizeAnimation = new ScaleTransition();
    imageSizeAnimation.setDuration(Duration.millis(1));
    imageSizeAnimation.setNode(source);
    imageSizeAnimation.setToX(1.1);
    imageSizeAnimation.setToY(1.1);
    imageSizeAnimation.play();
  }

  /**
   * Is starting the animation when the cursor is over a button.
   * 
   * @param e Event to get the source from.
   */
  public void handleMouseLeaveEvent(MouseEvent e) {
    ImageView source = (ImageView) e.getSource();
    imageSizeAnimation = new ScaleTransition();
    imageSizeAnimation.setDuration(Duration.millis(1));
    imageSizeAnimation.setNode(source);
    imageSizeAnimation.setToX(1.0);
    imageSizeAnimation.setToY(1.0);
    imageSizeAnimation.play();
  }
}
