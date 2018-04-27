package de.skat3.gui.matchfield;

import de.skat3.gamelogic.Player;
import de.skat3.main.SkatMain;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

/**
 * @author Aljoscha Domonell
 *
 */
public class InGameOverlayController {

  @FXML
  private TextArea chatArea;

  @FXML
  private Label nameLocalClient;

  @FXML
  private Label extraEnemyOne;

  @FXML
  private AnchorPane root;

  @FXML
  private Label nameEnemyOne;

  @FXML
  private TextField chatField;

  @FXML
  private ImageView imageEnemyTwo;

  @FXML
  private Label extra1LocalClient;

  @FXML
  private ImageView imageEnemyOne;

  @FXML
  private Label nameEnemyTwo;

  @FXML
  private Label extraEnemyTwo;

  @FXML
  private Label extra2LocalClient;

  public void handleSendMessage(KeyEvent e) {
    if (e.getCode().equals(KeyCode.ENTER)) {
      SkatMain.mainController.sendMessage(this.chatField.getText().trim());
      this.chatField.clear();
    }
  }

  void bindChat() {
    SkatMain.lgs.chatMessages.addListener(new ListChangeListener<String>() {

      @Override
      public void onChanged(Change<? extends String> c) {
        StringBuffer newText = new StringBuffer();
        for (String addedMessage : c.getAddedSubList()) {
          newText.append(addedMessage);
          newText.append("\n");
        }
        chatArea.setText(chatArea.getText() + newText.toString());
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
        System.err.println("No player name given");
        e.printStackTrace();
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
        System.err.println("No player name given.");
        e.printStackTrace();
      }
      try {
        this.imageEnemyOne.setImage(this.convertToTriangle(player.convertToImage()));
      } catch (Exception e) {
        System.err.println("Image Could not be added.");
        e.printStackTrace();
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
        System.err.println("No player name given.");
        e.printStackTrace();
      }
      try {
        this.imageEnemyTwo.setImage(this.convertToTriangle(player.convertToImage()));
      } catch (Exception e) {
        System.err.println("Image Could not be added.");
        e.printStackTrace();
      }
      this.extraEnemyTwo.setText(String.valueOf(player.getPoints()));
    }
  }

  void showBidRequest(int bid) {
    Pane p = new Pane();
    p.setPrefSize(400, 225);
    p.translateXProperty()
        .bind(this.root.widthProperty().divide(2).subtract(p.widthProperty().divide(2)));
    p.translateYProperty()
        .bind(this.root.heightProperty().divide(2).subtract(p.heightProperty().divide(2)));

    p.setStyle("-fx-border-color: #d60202");
    p.setStyle("-fx-background-color: #404040");

    Label v = new Label("Do you bid " + bid + "?");
    v.setPrefSize(300, 75);
    v.setFont(Font.font(30));
    v.setTextFill(Color.WHITE);
    v.setAlignment(Pos.CENTER);

    v.setTranslateY(+10);
    Button yes = new Button("Bid");
    yes.setFont(new Font(25));
    yes.setTextFill(Color.WHITE);
    yes.setStyle("-fx-background-radius: 20");
    yes.setStyle("-fx-background-color: #d60202");
    yes.setPrefSize(150, 80);
    yes.setTranslateX(25);
    yes.setTranslateY(210 - 75 - 20);
    Button no = new Button("Pass");
    no.setFont(new Font(25));
    no.setTextFill(Color.WHITE);
    no.setStyle("-fx-background-radius: 20");
    no.setStyle("-fx-background-color: #d60202");
    no.setPrefSize(150, 80);
    no.setTranslateX(225);
    no.setTranslateY(210 - 75 - 20);
    p.getChildren().addAll(v, yes, no);

    this.root.getChildren().add(p);

    yes.setOnAction(e -> {
      SkatMain.mainController.localBid(true);
      this.root.getChildren().remove(p);
    });
    no.setOnAction(e -> {
      SkatMain.mainController.localBid(false);
      this.root.getChildren().remove(p);
    });

  }

  void showStartButton() {
    Button button = new Button("Start Game");
    button.setFont(Font.font(40));
    button.setPrefSize(300, 100);

    button.translateXProperty()
        .bind(this.root.widthProperty().divide(2).subtract(button.widthProperty().divide(2)));
    button.translateYProperty()
        .bind(this.root.heightProperty().divide(2).subtract(button.heightProperty().divide(2)));

    // TODO
    // SkatMain.mainController.currentLobby.setMaxNumberOfPlayerProperty();
    // SkatMain.mainController.currentLobby.setNumberOfPlayerProperty();
    //
    // button.disableProperty().bind(SkatMain.mainController.currentLobby.numberOfPlayerProperty()
    // .lessThan(SkatMain.mainController.currentLobby.maxNumberOfPlayerProperty()));

    button.disableProperty().bind(SkatMain.mainController.numberOfPlayerProperty
        .lessThan(SkatMain.mainController.maxNumberOfPlayerProperty));

    button.setOnAction(e -> {
      this.root.getChildren().remove(button);
      SkatMain.mainController.startGame();
    });

    this.root.getChildren().add(button);
  }

  Image convertToTriangle(Image image) {
    Polygon dreieckE1 = new Polygon();
    dreieckE1.getPoints().addAll(0.0, 0.0, image.getWidth() / 2, image.getHeight(),
        image.getWidth(), 0.0);
    dreieckE1.setFill(new ImagePattern(image));
    dreieckE1.setStyle("-fx-border-color: #d60202");
    return dreieckE1.snapshot(null, null);
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
