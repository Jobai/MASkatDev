package de.skat3.gui.matchfield;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import de.skat3.main.SkatMain;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * @author Aljoscha Domonell
 *
 */
public class InGameOverlayController {

  public static final String yourMove = "Your Move!";

  private PopUpController popUpController;

  private ScoreboardController scoreboardController;

  private ChooseContractController contractController;

  @FXML
  private TextArea chatArea;

  @FXML
  private Label multiInfo1;

  @FXML
  private Label multiInfo2;

  @FXML
  private Label nameLocalClient;

  @FXML
  public Label extraEnemyOne;

  @FXML
  public Label extra2EnemyOne;

  @FXML
  private Label playInfo;

  @FXML
  private AnchorPane root;

  @FXML
  private Label nameEnemyOne;

  @FXML
  private TextField chatField;

  @FXML
  private ImageView imageEnemyTwo;

  @FXML
  public Label extra1LocalClient;

  @FXML
  private ImageView imageEnemyOne;

  @FXML
  private Label nameEnemyTwo;

  @FXML
  public Label extraEnemyTwo;

  @FXML
  public Label extra2EnemyTwo;

  @FXML
  public Label extra2LocalClient;

  @FXML
  public Label trumpInfo;

  // Initializing

  void setTrump(Contract con, AdditionalMultipliers addMulti) {
    this.trumpInfo
        .setText(con.toString().substring(con.toString().indexOf(" "), con.toString().length()));
    this.trumpInfo.setVisible(true);

    if (addMulti.isSchneiderAnnounced()) {
      this.multiInfo1.setText("Schneider");
      this.multiInfo1.setVisible(true);
      // TODO
    }

    if (addMulti.isSchwarzAnnounced()) {
      this.multiInfo1.setText("Schwarz");
      this.multiInfo1.setVisible(true);
      // TODO
    }

    if (addMulti.isOpenHand()) {
      this.multiInfo1.setText("Open");
      this.multiInfo1.setVisible(true);
      // TODO
    }

    if (addMulti.isHandGame()) {
      this.multiInfo1.setText("Hand");
      this.multiInfo1.setVisible(true);
      // TODO
    }


  }

  public void handleSendMessage(KeyEvent e) {
    if (e.getCode().equals(KeyCode.ENTER)) {
      SkatMain.mainController.sendMessage(this.chatField.getText().trim());
      this.chatField.clear();
    }
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
    this.playInfo.setTextAlignment(TextAlignment.CENTER);
    this.playInfo.setLayoutX(0);
    this.playInfo.translateXProperty()
        .bind(root.widthProperty().divide(2).subtract(this.playInfo.widthProperty().divide(2)));
    this.playInfo.setVisible(show);
    this.playInfo.setText(text);
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
    SkatMain.mainController.chatMessages.addListener(new ListChangeListener<String>() {

      @Override
      public void onChanged(Change<? extends String> c) {
        StringBuffer newText = new StringBuffer();
        c.next();
        for (String addedMessage : c.getAddedSubList()) {
          newText.append(addedMessage);
          newText.append("\n");
        }
        chatArea.setText(chatArea.getText() + newText.toString());
        chatArea.setScrollTop(Double.MAX_VALUE);
      }

    });
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
    if (player == null) {
      this.nameEnemyOne.setText("");
      this.extraEnemyOne.setText("");
      this.imageEnemyOne.setImage(null);
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
    if (player == null) {
      this.nameEnemyTwo.setText("");
      this.extraEnemyTwo.setText("");
      this.imageEnemyTwo.setImage(null);
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
