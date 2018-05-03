package de.skat3.gui.matchfield;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import de.skat3.main.SkatMain;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * @author adomonel
 *
 */
public class InGameOverlayController {

  public static final String yourMove = "Your Move!";

  private PopUpController popUpController;

  private ScoreboardController scoreboardController;

  private ChooseContractController contractController;

  @FXML
  private Label schwarzInfo;

  @FXML
  Label extraEnemyOne;

  @FXML
  private VBox addBotRightRoot;

  @FXML
  private Label nameLocalClient;

  @FXML
  private Label extra2EnemyTwo;

  @FXML
  private Button annouceContraButton;

  @FXML
  AnchorPane root;

  @FXML
  private Label nameEnemyOne;

  @FXML
  private ImageView imageEnemyTwo;

  @FXML
  private Label timerLabel;

  @FXML
  private Label extra2LocalClient;

  @FXML
  private TextArea chatArea;

  @FXML
  private Label extra2EnemyOne;

  @FXML
  private Button addEasyBotLeftButton;

  @FXML
  private Label trumpInfo;

  @FXML
  private TextField chatField;

  @FXML
  private Button addEasyBotRightButton;

  @FXML
  Label extra1LocalClient;

  @FXML
  private Label handInfo;

  @FXML
  private Label playInfo;

  @FXML
  private Label schneiderInfo;

  @FXML
  private Button addHardBotRightButton;

  @FXML
  private Button addHardBotLeftButton;

  @FXML
  private ImageView imageEnemyOne;

  @FXML
  private VBox addBotLeftRoot;

  @FXML
  private Label nameEnemyTwo;

  @FXML
  Label extraEnemyTwo;

  @FXML
  private Label openInfo;

  private Timeline localTimer;
  // Initializing



  void setRoundInfos(Contract con, AdditionalMultipliers addMulti) {

    if (con != null) {
      this.trumpInfo.setText(con.toString());
      this.trumpInfo.setVisible(true);

      this.extra1LocalClient.setText(SkatMain.lgs.getLocalClient().isSolo() ? "Solo" : "Team");
      this.extraEnemyOne.setText(SkatMain.lgs.getEnemyOne().isSolo() ? "Solo" : "Team");
      this.extraEnemyTwo.setText(SkatMain.lgs.getEnemyTwo().isSolo() ? "Solo" : "Team");

    } else {
      this.trumpInfo.setVisible(false);
      this.extra1LocalClient.setText("");
      this.extraEnemyOne.setText("");
      this.extraEnemyTwo.setText("");
    }

    if (addMulti != null) {
      this.schneiderInfo.setVisible(addMulti.isSchneiderAnnounced());
      this.schwarzInfo.setVisible(addMulti.isSchwarzAnnounced());
      this.openInfo.setVisible(addMulti.isOpenHand());
      this.handInfo.setVisible(addMulti.isHandGame());
    } else {
      this.schneiderInfo.setVisible(false);
      this.schwarzInfo.setVisible(false);
      this.openInfo.setVisible(false);
      this.handInfo.setVisible(false);
    }
  }

  public void handleSendMessage(KeyEvent e) {
    if (e.getCode().equals(KeyCode.ENTER)) {
      SkatMain.mainController.sendMessage(this.chatField.getText().trim());
      this.chatField.clear();
    }
  }

  void iniStartRound() {
    this.root.requestFocus();

    if (SkatMain.lgs.timerInSeconds > 0) {
      this.showTimer(true);
    } else {
      this.showTimer(false);
    }

    this.iniEmemyOne(SkatMain.lgs.getEnemyOne());
    this.iniEmemyTwo(SkatMain.lgs.getEnemyTwo());
    this.iniLocalClient(SkatMain.lgs.getLocalClient());
  }

  void iniComponents() {
    this.root.requestFocus();
    this.iniScoreboard();
    this.iniPopUp();
    this.iniContract();
    this.bindChat();
  }

  private void bindCentral(AnchorPane p) {
    p.translateXProperty()
        .bind(this.root.widthProperty().divide(2).subtract(p.widthProperty().divide(2)));
    p.translateYProperty()
        .bind(this.root.heightProperty().divide(2).subtract(p.heightProperty().divide(2)));
  }

  void showTimer(boolean value) {
    if (this.localTimer != null) {
      if (this.localTimer.getStatus().equals(Status.RUNNING)) {
        this.localTimer.setOnFinished(e -> {
        });
        this.localTimer.stop();
      }
    }
    this.timerLabel.setVisible(value);
  }



  void setTimer(int remainingTime) {

    this.timerLabel.setVisible(true);

    Duration finalTime = Duration.seconds(remainingTime);

    DoubleProperty timeLeft = new SimpleDoubleProperty();
    timeLeft.setValue(finalTime.toSeconds());

    StringProperty sp = new SimpleStringProperty();
    sp.set(" Sec");
    this.timerLabel.textProperty().bind(timeLeft.asString("%.2f").concat(sp));

    if (this.localTimer != null) {
      if (this.localTimer.getStatus().equals(Status.RUNNING)) {
        this.localTimer.setOnFinished(e -> {
        });
        this.localTimer.stop();
      }
    }

    this.localTimer = new Timeline();
    this.localTimer.getKeyFrames().add(new KeyFrame(finalTime, new KeyValue(timeLeft, 0)));
    this.localTimer.play();

    this.localTimer.setOnFinished(e -> {
      this.timerLabel.setVisible(false);
      if (SkatMain.guiController.getInGameController().matchfield.tableController.isPlaying) {
        Random rand = new Random();
        int i;
        ArrayList<Card> temp = new ArrayList<Card>();
        for (Card c : SkatMain.lgs.getLocalHand().cards) {
          if (c.isPlayable()) {
            temp.add(c.copy());
          }
        }
        i = rand.nextInt(temp.size());

        SkatMain.mainController.localCardPlayed(temp.get(i));
        SkatMain.guiController.getInGameController().makeAMove(false);
      }
    });

  }

  void showContractRequest() {
    this.contractController.root.setVisible(true);
    this.contractController.root.setDisable(false);
  }

  void iniContract() {
    URL u = InGameOverlayController.class.getResource("ChooseContractView.fxml");
    FXMLLoader loader = new FXMLLoader(u);
    try {
      this.root.getChildren().add((AnchorPane) loader.load());
      this.contractController = loader.getController();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.bindCentral(this.contractController.root);

    this.contractController.root.setVisible(false);
  }

  private void iniScoreboard() {
    URL u = InGameOverlayController.class.getResource("ScoreboardView.fxml");
    FXMLLoader loader = new FXMLLoader(u);
    try {
      this.root.getChildren().add((AnchorPane) loader.load());
      this.scoreboardController = loader.getController();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.bindCentral(this.scoreboardController.root);

    this.scoreboardController.root.setVisible(false);
  }

  private void iniPopUp() {
    URL u = InGameOverlayController.class.getResource("PopUpView.fxml");
    FXMLLoader loader = new FXMLLoader(u);
    try {
      this.root.getChildren().add((AnchorPane) loader.load());
      this.popUpController = loader.getController();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.bindCentral(this.popUpController.root);

    this.popUpController.root.setVisible(false);
    this.popUpController.root.setDisable(true);

  }

  void setPlayText(String text, boolean show) {
    if (show) {
      this.playInfo.setText(text);
      FadeTransition fading = new FadeTransition();
      fading.setNode(this.playInfo);
      fading.setFromValue(1);
      fading.setToValue(0.5);
      fading.setCycleCount(MediaPlayer.INDEFINITE);
      fading.setAutoReverse(true);
      fading.setDuration(Duration.millis(200));
      fading.play();
    }
    this.playInfo.setVisible(show);
  }

  public void handleKeyPressed(KeyEvent e) {

    if (KeyCode.TAB.equals(e.getCode()) && !this.scoreboardController.root.isVisible()) {
      SkatMain.guiController.getInGameController().matchfield.tableController.tableView.table
          .setDisable(true);
      // this.root.setDisable(true);
      this.scoreboardController.setScores();
      this.scoreboardController.root.toFront();
      this.scoreboardController.root.setVisible(true);
      this.root.requestFocus();
    }
  }

  public void handleKeyReleased(KeyEvent e) {

    if (KeyCode.TAB.equals(e.getCode())) {
      SkatMain.guiController.getInGameController().matchfield.tableController.tableView.table
          .setDisable(false);
      this.scoreboardController.root.setVisible(false);

      this.root.requestFocus();
    }

    if (KeyCode.ESCAPE.equals(e.getCode())) {

      boolean old =
          SkatMain.guiController.getInGameController().matchfield.tableController.isPlaying;

      SkatMain.guiController.getInGameController().makeAMove(false);

      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Leave game");
      alert.setHeaderText("Leave game. Are you sure?");

      ButtonType buttonTypeYes = new ButtonType("Yes");
      ButtonType buttonTypeNo = new ButtonType("No");

      alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == buttonTypeYes) {
        SkatMain.mainController.exitGame();
      } else {
        SkatMain.guiController.getInGameController().makeAMove(old);
      }
    }
  }

  void bindChat() {
    /*
     * SkatMain.mainController.chatMessages.addListener(new ListChangeListener<String>() {
     * 
     * @Override public void onChanged(Change<? extends String> c) { StringBuffer newText = new
     * StringBuffer(); c.next(); for (String addedMessage : c.getAddedSubList()) {
     * newText.append(addedMessage); newText.append("\n"); } chatArea.setText(chatArea.getText() +
     * newText.toString()); chatArea.setScrollTop(Double.MAX_VALUE); }
     * 
     * });
     */
  }

  void iniLocalClient(Player player) {
    if (player == null) {
      this.nameLocalClient.setText("");
      this.extra1LocalClient.setText("");
      this.extra2LocalClient.setText("");
    } else {
      try {
        this.nameLocalClient.setText(player.getName());
      } catch (NullPointerException e) {
        System.err.println("LocalClient: No player name given");
      }
      this.extra1LocalClient.setText(String.valueOf(player.getPoints()));
      this.extra2LocalClient.setText("INFO2");
    }
  }

  void iniEmemyOne(Player player) {

    this.addBotLeftRoot.setDisable(true);
    this.addBotLeftRoot.setVisible(false);

    if (player == null) {
      this.nameEnemyOne.setText("");
      this.extraEnemyOne.setText("");
      this.imageEnemyOne.setImage(null);

      if (SkatMain.mainController.isHost) {
        this.addEasyBotLeftButton.setOnAction(e -> {
          SkatMain.mainController.addBot(false);
          this.addBotLeftRoot.setDisable(true);
          this.addBotLeftRoot.setVisible(false);
        });
        this.addHardBotLeftButton.setOnAction(e -> {
          SkatMain.mainController.addBot(true);
          this.addBotLeftRoot.setDisable(true);
          this.addBotLeftRoot.setVisible(false);
        });
        this.addBotLeftRoot.setDisable(false);
        this.addBotLeftRoot.setVisible(true);
      }

    } else {

      try {
        this.nameEnemyOne.setText(player.getName());
      } catch (NullPointerException e) {
        System.err.println("EnemyOne: No player name given.");
      }
      try {
        this.imageEnemyOne.setImage(player.convertToImage());
      } catch (Exception e) {
        System.err.println("EnemyOne: Image Could not be added.");
      }
      this.extraEnemyOne.setText(String.valueOf(player.getPoints()));
    }
  }

  void iniEmemyTwo(Player player) {

    this.addBotRightRoot.setDisable(true);
    this.addBotRightRoot.setVisible(false);

    if (player == null) {
      this.nameEnemyTwo.setText("");
      this.extraEnemyTwo.setText("");
      this.imageEnemyTwo.setImage(null);

      if (SkatMain.mainController.isHost) {
        this.addEasyBotRightButton.setOnAction(e -> {
          SkatMain.mainController.addBot(false);
          this.addBotRightRoot.setDisable(true);
          this.addBotRightRoot.setVisible(false);
        });
        this.addHardBotRightButton.setOnAction(e -> {
          SkatMain.mainController.addBot(true);
          this.addBotRightRoot.setDisable(true);
          this.addBotRightRoot.setVisible(false);
        });
        this.addBotRightRoot.setDisable(false);
        this.addBotRightRoot.setVisible(true);
      }

    } else {

      try {
        this.nameEnemyTwo.setText(player.getName());
      } catch (NullPointerException e) {
        System.err.println("EnemyTwo: No player name given.");
      }
      try {
        this.imageEnemyTwo.setImage(player.convertToImage());
      } catch (Exception e) {
        System.err.println("EnemyTwo: Image Could not be added.");
      }
      this.extraEnemyTwo.setText(String.valueOf(player.getPoints()));
    }
  }

  void showBidRequest(int bid) {
    this.popUpController.yesButton.setText("Bid");

    this.popUpController.yesButton.setOnAction(e -> {
      SkatMain.mainController.localBid(true);
      this.popUpController.root.setVisible(false);
      this.popUpController.root.setDisable(true);
    });

    this.popUpController.noButton.setText("Pass");

    this.popUpController.noButton.setOnAction(e -> {
      SkatMain.mainController.localBid(false);
      this.popUpController.root.setVisible(false);
      this.popUpController.root.setDisable(true);
    });

    this.popUpController.textField.setText("Do you bid more than " + bid + "?");

    this.popUpController.root.setVisible(true);
    this.popUpController.root.setDisable(false);
  }

  void showHandGameRequest() {

    this.popUpController.yesButton.setText("Yes");

    this.popUpController.yesButton.setOnAction(e -> {
      SkatMain.mainController.handGameSelected(true);
      this.popUpController.root.setVisible(false);
      this.popUpController.root.setDisable(true);
    });

    this.popUpController.noButton.setText("NO");

    this.popUpController.noButton.setOnAction(e -> {
      SkatMain.mainController.handGameSelected(false);
      this.popUpController.root.setVisible(false);
      this.popUpController.root.setDisable(true);
    });

    this.popUpController.textField.setText("Play handgame?");

    this.popUpController.root.setVisible(true);
    this.popUpController.root.setDisable(false);

  }

  void showStartButton() {
    Button button = new Button("Start Game");
    button.setFont(Font.font(40));
    button.setPrefSize(300, 100);

    button.setTextFill(Color.WHITE);

    button.setStyle("-fx-background-color: #d60202; -fx-background-radius: 30; "
        + "-fx-border-color: #404040; -fx-border-radius: 30;");

    button.translateXProperty()
        .bind(this.root.widthProperty().divide(2).subtract(button.widthProperty().divide(2)));
    button.translateYProperty()
        .bind(this.root.heightProperty().divide(2).subtract(button.heightProperty().divide(2)));

    button.disableProperty().bind(SkatMain.mainController.numberOfPlayerProperty
        .lessThan(SkatMain.mainController.maxNumberOfPlayerProperty));

    button.setOnAction(e -> {
      this.root.getChildren().remove(button);
      SkatMain.mainController.startGame();
    });

    this.root.getChildren().add(button);
  }


  void toFront() {
    this.chatArea.toFront();
    this.chatField.toFront();
    this.extra1LocalClient.toFront();
    this.extra2LocalClient.toFront();
    this.extraEnemyOne.toFront();
    this.extraEnemyTwo.toFront();
    this.imageEnemyOne.toFront();
    this.imageEnemyTwo.toFront();
    this.nameEnemyOne.toFront();
    this.nameEnemyTwo.toFront();
    this.nameLocalClient.toFront();
  }

}
