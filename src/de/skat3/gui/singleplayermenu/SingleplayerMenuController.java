package de.skat3.gui.singleplayermenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import de.skat3.gui.multiplayermenu.HostPopupController;
import de.skat3.main.SkatMain;
import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
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

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    ComboBox<String> player1 = new ComboBox<>();
    player1.getItems().addAll(difficulty);
    player1.getSelectionModel().selectFirst();

    ComboBox<String> player2 = new ComboBox<>();
    player2.getItems().addAll(difficulty);
    player2.getSelectionModel().selectFirst();

    ComboBox<String> scoringMode = new ComboBox<>();
    scoringMode.getItems().addAll(modes);
    scoringMode.getSelectionModel().selectFirst();
    TextField scoreValue = new TextField();
    scoreValue.setText(String.valueOf(48));

    CheckBox kontraRekontra = new CheckBox();

    // Set the button types.
    ButtonType startGame = new ButtonType("Start", ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(startGame, ButtonType.CANCEL);

    grid.add(new Label("KI Player 1:"), 0, 0);
    grid.add(player1, 1, 0);
    grid.add(new Label("KI Player 2:"), 0, 1);
    grid.add(player2, 1, 1);

    grid.add(new Label(""), 0, 2);
    grid.add(new Label("Scoring Mode"), 0, 3);
    grid.add(scoringMode, 1, 3);
    grid.add(scoreValue, 1, 4);

    grid.add(new Label(""), 0, 5);
    grid.add(new Label("Kontra/Rekontra"), 0, 6);
    grid.add(kontraRekontra, 1, 6);

    dialog.getDialogPane().setContent(grid);

    final Button okButton = (Button) dialog.getDialogPane().lookupButton(startGame);
    scoreValue.textProperty().addListener((observable, oldValue, newValue) -> {
      okButton.setDisable(scoreValue.getText().isEmpty());
    });

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == startGame) {
        HashMap<String, String> result = new HashMap<>();
        result.put("p1", player1.getValue());
        result.put("p2", player2.getValue());
        result.put("scoreValue", scoreValue.getText());
        result.put("kontraRekontra", "" + kontraRekontra.isSelected());
        return result;
      }
      return null;
    });

    dialog.showAndWait().ifPresent(result -> {
      boolean ai1Hard = false;
      boolean ai2Hard = false;
      int intScoreValue = 0;
      boolean konRekon = false;

      if ((result.get("p1") != null) && (result.get("p1") == "Hard")) {
        ai1Hard = true;
      }

      if ((result.get("p2") != null) && (result.get("p2") == "Hard")) {
        ai2Hard = true;
      }

      if ((result.get("scoreValue") != null)) {
        intScoreValue = Integer.parseInt(result.get("scoreValue"));
      }

      if ((result.get("kontraRekontra") != null)) {
        konRekon = Boolean.parseBoolean(result.get("kontraRekontra"));
      }

      SkatMain.mainController.startSingleplayerGame(ai1Hard, ai2Hard, intScoreValue, konRekon);
    });



  }

  /**
   * .
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
        .bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(mainPane.translateYProperty())
            .add((mainPane.getPrefHeight() / 2)).subtract((p.getPrefHeight() / 2) + 20));

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
