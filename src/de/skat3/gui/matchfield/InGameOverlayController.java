package de.skat3.gui.matchfield;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.MatchResult;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;
import de.skat3.main.SkatMain;
import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * @author Aljoscha Domonell
 *
 */
public class InGameOverlayController {

  public static final String yourMove = "Your Move!";

  private boolean isHandgame;

  private Timeline localTimer;
  private Timeline mainInfoFader;

  private PopUpController popUpController;
  private ScoreboardController scoreboardController;
  private ChooseContractController contractController;
  private TrainingModeTextController trainingModeTextController;
  private GameResultViewController gameResultcontroller;
  private RoundResultViewController roundResultController;

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
  public Button annouceContraButton;
  @FXML
  public AnchorPane root;
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
  private Label extra3EnemyOne;
  @FXML
  private Label extra3EnemyTwo;
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
  @FXML
  private Label mainInfoLabel;
  @FXML
  public AnchorPane rootEnemyOne;
  @FXML
  public AnchorPane rootEnemyTwo;
  @FXML
  public HBox rootLocalClient;
  // Initializing

  public static final String RED = "#d60202";
  public static final String BLACK = "#404040";
  public static final String BORDER = "-fx-border-color: ";
  public static final String BACKGROUND = "-fx-background-color: ";
  public static final String BACKGROUNDRADIUS = "-fx-background-radius: ";
  public static final String BORDERRADIUS = "-fx-border-radius: ";

  public void handleSendMessage(KeyEvent e) {
    if (e.getCode().equals(KeyCode.ENTER)) {

      switch (this.chatField.getText()) {
        case "&inihands": {
          SkatMain.guiController.getInGameController().matchfield.tableController.iniHands();
          break;
        }

        case "&blinglefton": {
          SkatMain.guiController.getInGameController().matchfield.tableView.leftHand
              .setBlingBling(true);
          break;
        }
        case "&blingleftoff": {
          SkatMain.guiController.getInGameController().matchfield.tableView.leftHand
              .setBlingBling(false);
          break;
        }
        case "&showbidcards": {
          SkatMain.guiController.getInGameController().matchfield.tableView.trick
              .showBidingCards(true);
          break;
        }
        case "&hidebidcards": {
          SkatMain.guiController.getInGameController().matchfield.tableView.trick
              .showBidingCards(false);
          break;
        }
        case "&root": {
          SkatMain.guiController.print();

          break;
        }

        default: {
          SkatMain.mainController.execCommand(this.chatField.getText());

          SkatMain.mainController.sendMessage(this.chatField.getText().trim());
        }
      }

      this.chatField.clear();

    }
  }

  private void loadFXMLFiles() {
    // GameResults
    FXMLLoader fxmlLoader =
        new FXMLLoader(InGameOverlayController.class.getResource("GameResultView.fxml"));
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.gameResultcontroller = fxmlLoader.getController();

    // RoundResults
    fxmlLoader = new FXMLLoader(InGameOverlayController.class.getResource("RoundResultView.fxml"));

    try {
      fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.roundResultController = fxmlLoader.getController();

    // Contract

    fxmlLoader =
        new FXMLLoader(InGameOverlayController.class.getResource("ChooseContractView.fxml"));
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.contractController = fxmlLoader.getController();

    // Scoreboard

    FXMLLoader loader =
        new FXMLLoader(InGameOverlayController.class.getResource("ScoreboardView.fxml"));
    try {
      loader.load();
      this.scoreboardController = loader.getController();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.addAndSetupButton(this.scoreboardController.root, null);
    this.scoreboardController.root.setVisible(false);

    // PopUp

    loader = new FXMLLoader(InGameOverlayController.class.getResource("PopUpView.fxml"));
    try {
      this.root.getChildren().add((AnchorPane) loader.load());
      this.popUpController = loader.getController();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.bindCentral(this.popUpController.root);

    this.popUpController.root.setVisible(false);
    this.popUpController.root.setDisable(true);

    // TrainingModeText

    loader = new FXMLLoader(InGameOverlayController.class.getResource("TrainingModeTextView.fxml"));
    try {
      this.root.getChildren().add((AnchorPane) loader.load());
      this.trainingModeTextController = loader.getController();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.bindCentral(this.trainingModeTextController.root);

    this.trainingModeTextController.root.setVisible(false);

  }

  private void addAndSetupButton(AnchorPane pane, Button closeButton) {
    this.root.getChildren().add(pane);
    if (closeButton != null) {
      closeButton.setOnAction(e -> {
        this.remove(pane);
      });
    }
    this.bindCentral(pane);
  }

  synchronized void remove(Parent p) {
    try {
      this.root.getChildren().remove(p);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  synchronized void showInMainInfo(String text, Duration time) {
    if (this.mainInfoFader == null) {
      this.mainInfoFader = new Timeline();
    }

    if (this.mainInfoFader.getStatus().equals(Status.RUNNING)) {
      this.mainInfoFader.setOnFinished(e -> {
      });
      this.mainInfoFader.stop();
      this.mainInfoFader.getKeyFrames().clear();
      this.showInMainInfo(text, time);
      return;
    } else {
      this.mainInfoLabel.setText(text);
      this.mainInfoLabel.setVisible(true);
      this.mainInfoLabel.setOpacity(1);
      this.mainInfoFader.getKeyFrames().add(new KeyFrame(time, e -> {
      }));
      this.mainInfoFader.setOnFinished(e -> {
        FadeTransition fading = new FadeTransition();
        fading.setNode(this.mainInfoLabel);
        fading.setDuration(Duration.millis(500));
        fading.setFromValue(1);
        fading.setToValue(0);
        fading.play();
      });
      this.mainInfoFader.playFromStart();
    }
  }

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

  void iniStartRound() {
    this.root.requestFocus();

    if (SkatMain.lgs.getTimerInSeconds() > 0) {
      this.showTimer(true);
    } else {
      this.showTimer(false);
    }

    this.iniEmemyOne(SkatMain.lgs.getEnemyOne());
    this.iniEmemyTwo(SkatMain.lgs.getEnemyTwo());
    this.iniLocalClient(SkatMain.lgs.getLocalClient());
  }

  void iniComponents() {
    this.loadFXMLFiles();
    this.root.requestFocus();
  }

  void showRoundResult(Result result) {
    if (result.roundCancelled) {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("RoundCancelledView.fxml"));
      AnchorPane p = null;
      try {
        p = loader.load();
      } catch (IOException e) {
        // .
      }
      Button closeButton = null;
      ObservableList<Node> childs = p.getChildren();
      for (Node node : childs) {
        if (node.getClass() == Button.class) {
          closeButton = (Button) node;
        }
      }

      this.addAndSetupButton(p, closeButton);
      return;
    }

    this.addAndSetupButton(this.roundResultController.root, this.roundResultController.closeButton);

    this.roundResultController.setResult(result);

  }

  void showMatchResult(MatchResult result) {

    this.gameResultcontroller.root.visibleProperty()
        .bind(this.roundResultController.root.parentProperty().isNull());

    this.addAndSetupButton(this.gameResultcontroller.root, null);
    this.gameResultcontroller.closeButton.setOnAction(e -> {
      SkatMain.mainController.exitGame();
    });

    this.gameResultcontroller.setResult(result);
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
    this.timerLabel.textProperty().unbind();
    this.timerLabel.setText("");
    this.timerLabel.setVisible(value);
  }

  void setTimer(int remainingTime) {

    this.showTimer(true);

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
      this.showTimer(false);

      switch (SkatMain.lgs.currentRequestState) {
        case BID: {
          this.popUpController.root.setVisible(false);
          SkatMain.mainController.localBid(false);
          break;
        }
        case CARD:
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

            GuiCard card =
                SkatMain.guiController.getInGameController().matchfield.tableView.playerHand
                    .getGuiCard(temp.get(i));

            SkatMain.guiController.getInGameController().matchfield.tableController.playCard(card);
          }
          break;
        case CONTRACT:
          this.contractController.root.setVisible(false);
          SkatMain.mainController.contractSelected(
              Contract.values()[(int) (Math.random() * (Contract.length - 2))],
              new AdditionalMultipliers());
          break;
        case HANDGAME:
          this.isHandgame = false;
          this.popUpController.root.setVisible(false);
          SkatMain.mainController.handGameSelected(true);
          break;
        case SKAT:
          SkatMain.guiController.getInGameController().matchfield.tableController
              .showSkatSelection(false);
          SkatMain.mainController.skatSelected(SkatMain.lgs.getLocalHand(), SkatMain.lgs.getSkat());
          break;
        default:
          break;
      }
    });
  }

  void showContractRequest() {
    this.contractController.checkIfHandgame();
    this.addAndSetupButton(this.contractController.root, null);
  }

  void setPlayText(String text, boolean show, boolean showAnimation) {
    if (show) {
      this.playInfo.setText(text);
      if (showAnimation) {
        FadeTransition fading = new FadeTransition();
        fading.setNode(this.playInfo);
        fading.setFromValue(1);
        fading.setToValue(0.5);
        fading.setCycleCount(MediaPlayer.INDEFINITE);
        fading.setAutoReverse(true);
        fading.setDuration(Duration.millis(200));
        fading.play();
      }
    }
    this.playInfo.setVisible(show);
  }

  public void handleKeyPressed(KeyEvent e) {
    if (KeyCode.TAB.equals(e.getCode()) && !this.scoreboardController.root.isVisible()) {
      SkatMain.guiController.getInGameController().matchfield.tableController.tableView.table
          .setDisable(true);

      this.scoreboardController.setScores();
      this.scoreboardController.root.toFront();
      this.scoreboardController.root.setVisible(true);
      e.consume();
      this.root.requestFocus();
      return;
    }

    if (KeyCode.TAB.equals(e.getCode())) {
      e.consume();
      return;
    }
  }

  public void handleKeyReleased(KeyEvent e) {

    if (KeyCode.TAB.equals(e.getCode())) {
      SkatMain.guiController.getInGameController().matchfield.tableController.tableView.table
          .setDisable(false);
      this.scoreboardController.root.setVisible(false);
      e.consume();
      this.root.requestFocus();
    }

    if (KeyCode.ESCAPE.equals(e.getCode())) {

      SkatMain.guiController.getInGameController().matchfield.root.setDisable(true);

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
        SkatMain.guiController.getInGameController().matchfield.root.setDisable(false);
      }
    }
  }

  private boolean chatIsBind;

  void bindChat() {
    if (!this.chatIsBind) {
      SkatMain.mainController.chatMessages.addListener(new ListChangeListener<String>() {

        @Override
        public void onChanged(Change<? extends String> c) {
          StringBuffer newText = new StringBuffer("");

          while (c.next()) {
            for (String addedMessage : c.getAddedSubList()) {
              newText.append(addedMessage);
              newText.append("\n");
            }
            chatArea.setText(chatArea.getText() + newText.toString());
            chatArea.setScrollTop(Double.MAX_VALUE);
          }
        }

      });
      this.chatIsBind = true;
    }
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
      this.extra2LocalClient.setText(String.valueOf(player.getPoints()) + " Points");
    }
  }

  void iniEmemyOne(Player player) {

    this.addBotLeftRoot.setDisable(true);
    this.addBotLeftRoot.setVisible(false);

    if (player == null) {
      this.nameEnemyOne.setText("");
      this.extra2EnemyOne.setText("");
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
      this.extra3EnemyOne.setText("");

    } else {

      try {
        this.nameEnemyOne.setText(player.getName());
      } catch (NullPointerException e) {
        System.err.println("EnemyOne: No player name given.");
      }
      try {
        if (player.isBot()) {
          if (player.isHardBot()) {
            this.imageEnemyOne
                .setImage(new Image("profilePictures" + File.separator + "HardKi.jpg"));
          } else {
            this.imageEnemyOne
                .setImage(new Image("profilePictures" + File.separator + "EasyKi.jpg"));
          }
        } else {
          this.imageEnemyOne.setImage(player.convertToImage());
        }
      } catch (Exception e) {
        System.err.println("EnemyOne: Image Could not be added.");
      }
      this.extra2EnemyOne.setText(String.valueOf(player.getPoints()) + " Points");
      if (player.isBot()) {
        this.extra3EnemyOne.setText(player.isHardBot() ? "Hard Bot" : "Easy Bot");
      }
    }
  }

  void iniEmemyTwo(Player player) {

    this.addBotRightRoot.setDisable(true);
    this.addBotRightRoot.setVisible(false);

    if (player == null) {
      this.nameEnemyTwo.setText("");
      this.extra2EnemyTwo.setText("");
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
      this.extra3EnemyTwo.setText("");

    } else {

      try {
        this.nameEnemyTwo.setText(player.getName());
      } catch (NullPointerException e) {
        System.err.println("EnemyTwo: No player name given.");
      }
      try {
        if (player.isBot()) {
          if (player.isHardBot()) {
            this.imageEnemyTwo
                .setImage(new Image("profilePictures" + File.separator + "HardKi.jpg"));
          } else {
            this.imageEnemyTwo
                .setImage(new Image("profilePictures" + File.separator + "EasyKi.jpg"));
          }
        } else {
          this.imageEnemyTwo.setImage(player.convertToImage());
        }
      } catch (Exception e) {
        System.err.println("EnemyTwo: Image Could not be added.");
      }
      this.extra2EnemyTwo.setText(String.valueOf(player.getPoints()) + " Points");

      if (player.isBot()) {
        this.extra3EnemyTwo.setText(player.isHardBot() ? "Hard Bot" : "Easy Bot");
      }
    }
  }

  void showBidRequest(int bid) {
    this.popUpController.yesButton.setText("Bid");

    this.popUpController.yesButton.setOnAction(e -> {
      this.showTimer(false); // XXX
      SkatMain.mainController.localBid(true);
      this.popUpController.root.setVisible(false);
      this.popUpController.root.setDisable(true);
    });

    this.popUpController.noButton.setText("Pass");

    this.popUpController.noButton.setOnAction(e -> {
      this.showTimer(false); // XXX
      SkatMain.mainController.localBid(false);
      this.popUpController.root.setVisible(false);
      this.popUpController.root.setDisable(true);
    });

    this.popUpController.textField.setText("Do you bid more than " + bid + "?");

    this.popUpController.root.setVisible(true);
    this.popUpController.root.setDisable(false);
  }

  void showBidRequest(int bid, boolean yesButton) {
    this.popUpController.yesButton.setText("Bid");

    if (yesButton) {
      this.popUpController.yesButton.setOnAction(e -> {
        SkatMain.mainController.localBid(true);
        this.popUpController.root.setVisible(false);
        this.popUpController.root.setDisable(true);
      });
    } else {
      this.popUpController.noButton.setOnAction(e -> {
        SkatMain.mainController.localBid(false);
        this.popUpController.root.setVisible(false);
        this.popUpController.root.setDisable(true);
      });
    }


    this.popUpController.noButton.setText("Pass");

    this.popUpController.textField.setText("Do you bid more than " + bid + "?");

    this.popUpController.root.setVisible(true);
    this.popUpController.root.setDisable(false);
  }

  void showHandGameRequest() {

    this.popUpController.yesButton.setText("Yes");

    this.popUpController.yesButton.setOnAction(e -> {
      this.showTimer(false);
      isHandgame = true;
      SkatMain.mainController.handGameSelected(true);
      this.popUpController.root.setVisible(false);
      this.popUpController.root.setDisable(true);
    });

    this.popUpController.noButton.setText("NO");

    this.popUpController.noButton.setOnAction(e -> {
      isHandgame = false;
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

  public void showTrainingModeInfoText(String path, int width, int height) {
    this.trainingModeTextController.setPath(path);
    this.trainingModeTextController.setSize(width, height);
    this.trainingModeTextController.root.setVisible(true);
    this.trainingModeTextController.root.setDisable(false);

  }

  public boolean isHandgame() {
    return isHandgame;
  }

}
