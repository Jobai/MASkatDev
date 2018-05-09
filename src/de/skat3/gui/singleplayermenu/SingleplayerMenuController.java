package de.skat3.gui.singleplayermenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import de.skat3.gui.multiplayermenu.HostPopupController;
import de.skat3.main.SkatMain;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class to control the corresponding view file.
 * 
 * @author tistraub
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
