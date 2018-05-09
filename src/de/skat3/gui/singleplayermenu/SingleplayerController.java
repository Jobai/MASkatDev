package de.skat3.gui.singleplayermenu;

import java.util.Stack;
import com.sun.prism.paint.Color;
import de.skat3.main.SkatMain;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author tistraub
 *
 */
public class SingleplayerController {

  @FXML
  private AnchorPane root;
  @FXML
  private ComboBox<String> difficultyP1;
  @FXML
  private ComboBox<String> difficultyP2;
  @FXML
  private ChoiceBox<String> mode;
  @FXML
  private TextField modeValue;
  @FXML
  private Tooltip tooltipModeValue;
  @FXML
  private CheckBox kontraRekontra;
  @FXML
  private Button startButton;

  private AnchorPane main;
  private Pane singleplayer;


  @FXML
  public void initialize() {
    ObservableList<String> modeList = FXCollections.observableArrayList("Seeger", "Bierlachs");
    ObservableList<String> difficultyList = FXCollections.observableArrayList("Easy", "Hard");

    difficultyP1.setItems(difficultyList);
    difficultyP1.getSelectionModel().selectFirst();
    difficultyP2.setItems(difficultyList);
    difficultyP2.getSelectionModel().selectFirst();
    mode.setItems(modeList);
    mode.getSelectionModel().selectFirst();
    modeValue.setText(String.valueOf(48));
  }


  @FXML
  void switchMode(ActionEvent event) {
    if (mode.getSelectionModel().getSelectedItem() == "Seeger") {
      modeValue.setText(String.valueOf(48));
    } else {
      modeValue.setText(String.valueOf(-750));
    }
  }

  public void setPanes(AnchorPane main, Pane singleplayer) {
    this.main = main;
    this.singleplayer = singleplayer;
  }


  @FXML
  void close() {
    this.main.getChildren().remove(this.singleplayer);
  }

  @FXML
  void startGame(ActionEvent event) {

    // Check values
    int value = Integer.parseInt(modeValue.getText());
    if (mode.getSelectionModel().getSelectedItem() == "Seeger") {
      if (value % 3 != 0) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("Rounds are not divisible by 3");
        a.setHeaderText(null);
        a.showAndWait();
        return;
      }
    } else if (mode.getSelectionModel().getSelectedItem() == "Bierlachs") {
      if (!(value > -1000 && value < -500)) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("Score is not between -500 and -1000");
        a.setHeaderText(null);
        a.showAndWait();
        return;
      }
    }

    boolean ai1Hard = false;
    boolean ai2Hard = false;
    int intScoreValue = 0;

    intScoreValue = Integer.parseInt(this.modeValue.getText());

    if (this.difficultyP1.getSelectionModel().getSelectedItem() == "Hard") {
      ai1Hard = true;
    }

    if (this.difficultyP2.getSelectionModel().getSelectedItem() == "Hard") {
      ai2Hard = true;
    }

    close();
    startButton.setDisable(true);
    showLoadingScreen();

    SkatMain.mainController.startSingleplayerGame(ai1Hard, ai2Hard, intScoreValue,
        this.kontraRekontra.isSelected());

    startButton.setDisable(false);

  }

  /**
   * Shows a loading screen.
   */
  private void showLoadingScreen() {

    Pane p = new Pane();
    p.setPrefSize(400, 400);
    p.setStyle("-fx-background-color: #404040;");

    // p.translateXProperty()
    // .bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(hostListView.layoutXProperty())
    // .add(hostListView.widthProperty().divide(2)).subtract(100));
    //
    // p.translateYProperty()
    // .bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(hostListView.layoutYProperty())
    // .add(hostListView.heightProperty().divide(2)).subtract(100));

    System.out.println("start");
    Image i1 = new Image("guifiles/loading3.gif");
    ImageView v = new ImageView(i1);
    p.getChildren().add(v);

    Label l = new Label("jkjdhkjhdf");
    l.setFont(new Font(30));
    p.getChildren().add(l);

    this.main.getChildren().add(p);

    PauseTransition pause = new PauseTransition(Duration.seconds(10));
    pause.setOnFinished(event -> {
      this.main.getChildren().remove(p);
      System.out.println("end!");
    });
    pause.play();

  }

}
