package de.skat3.gui.singleplayermenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import de.skat3.main.SkatMain;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * Class to control the corresponding view file.
 * 
 * @author tistraub
 */
public class SingleplayerMenuController {

  private ScaleTransition imageSizeAnimation;

  /**
   * Show popup to select difficulty of KI players and start a Singleplayer Game
   */
  public void startSinglePlayerGame() {
    List<String> difficulty = new ArrayList<>();
    difficulty.add("Easy");
    difficulty.add("Hard");

    List<String> modes = new ArrayList<>();
    modes.add("Seeger");
    modes.add("Bierlachs");

    Dialog<HashMap<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Start Singleplayer Game");
    dialog.setHeaderText("Please select difficulty of the KI players");

    // Set the button types.
    ButtonType startGame = new ButtonType("Start", ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(startGame, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    ComboBox<String> player1 = new ComboBox<>();
    player1.getItems().addAll(difficulty);

    ComboBox<String> player2 = new ComboBox<>();
    player2.getItems().addAll(difficulty);

    ComboBox<String> player3 = new ComboBox<>();
    player3.getItems().addAll(difficulty);

    ComboBox<String> scoringMode = new ComboBox<>();
    scoringMode.getItems().addAll(modes);
    TextField scoreValue = new TextField();

    CheckBox kontraRekontra = new CheckBox();


    grid.add(new Label("KI Player 1:"), 0, 0);
    grid.add(player1, 1, 0);
    grid.add(new Label("KI Player 2:"), 0, 1);
    grid.add(player2, 1, 1);
    grid.add(new Label("KI Player 3:"), 0, 2);
    grid.add(player3, 1, 2);

    grid.add(new Label(""), 0, 3);
    grid.add(new Label("Scoring Mode"), 0, 4);
    grid.add(scoringMode, 1, 4);
    grid.add(scoreValue, 1, 5);

    grid.add(new Label(""), 0, 6);
    grid.add(new Label("Kontra/Rekontra"), 0, 7);
    grid.add(kontraRekontra, 1, 7);

    dialog.getDialogPane().setContent(grid);

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == startGame) {
        HashMap<String, String> result = new HashMap<>();
        result.put("p1", player1.getValue());
        result.put("p2", player2.getValue());
        result.put("p3", player3.getValue());
        return result;
      }
      return null;
    });

    String player1Value;
    String player2Value;

    dialog.showAndWait().ifPresent(result -> {
      // TODO
    });


    SkatMain.mainController.startSingleplayerGame(false, false, 48, false);
  }

  /**
   * .
   */
  public void startTrainingMode() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setHeaderText(null);
    alert.setTitle("Information Dialog");
    alert.setContentText("TrainingMode started!");

    alert.showAndWait();
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
