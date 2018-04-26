package de.skat3.gui.matchfield;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Player;
import de.skat3.main.SkatMain;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * View class of the Matchfield.
 * 
 * @author adomonel
 *
 */
public class Matchfield {
  public static Duration animationTime = Duration.seconds(0.2);
  protected GuiHand leftHand;
  protected GuiHand playerHand;
  protected GuiHand rightHand;

  protected AnchorPane root;
  protected Scene scene;
  protected Pane table;
  protected SubScene tableScene;
  protected GuiTrick trick;

  private InGameController controller;

  private GuiCard selectedCard;

  private Parent[] skatPositions;

  /**
   * Returns a Matchfield.
   */
  public Matchfield() {
    this.controller = new InGameController(this);
    double sceneWidth = 1280;
    double sceneHeight = 720;
    this.table = new Pane();
    this.tableScene =
        new SubScene(this.table, sceneWidth, sceneHeight, false, SceneAntialiasing.BALANCED);

    AnchorPane.setTopAnchor(this.tableScene, 0.0);
    AnchorPane.setBottomAnchor(this.tableScene, 0.0);
    AnchorPane.setRightAnchor(this.tableScene, 0.0);
    AnchorPane.setLeftAnchor(this.tableScene, 0.0);

    PerspectiveCamera cam = new PerspectiveCamera();
    cam.setTranslateY(-100);
    cam.getTransforms().add(new Rotate(-30, Rotate.X_AXIS));
    this.tableScene.setCamera(cam);

    this.root = new AnchorPane();
    this.root.getChildren().add(this.tableScene);

    this.scene = new Scene(this.root, sceneWidth, sceneHeight);

    this.iniTableComponents();
  }

  public InGameController getController() {
    return this.controller;
  }

  /**
   * Is searching a hand which is owned by the spezified Player.
   * 
   * @param owner Player to search for.
   * @return Hand of this Player.
   */
  public GuiHand getHand(Player owner) {
    try {
      try {
        if (this.playerHand.getOwner().equals(owner)) {
          return this.playerHand;
        }
      } catch (NullPointerException e) {
      }
      try {
        if (this.leftHand.getOwner().equals(owner)) {
          return this.leftHand;
        }
      } catch (NullPointerException e) {
      }
      try {
        if (this.rightHand.getOwner().equals(owner)) {
          return this.rightHand;
        }
      } catch (NullPointerException e) {
      }

      throw new Exception("Player does not own a GuiHand.");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public Scene getScene() {
    return this.scene;
  }

  void showBidRequest(int bid) {
    Pane p = new Pane();
    p.setPrefSize(400, 225);
    p.translateXProperty()
        .bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(this.playerHand.translateXProperty())
            .subtract(p.widthProperty().divide(2)));
    p.translateYProperty()
        .bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(this.playerHand.translateYProperty())
            .subtract(p.heightProperty().divide(2)));

    p.setStyle("-fx-border-color: d60202");
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

  /**
   * Plays a card from a hand on the trick.
   * 
   * @param hand From which the card is played.
   * @param card Card to be played.
   */
  synchronized void playCard(GuiHand hand, GuiCard card) {
    hand.moveCardAndRemove(card, this.trick.add(card), this.table);
  }

  /**
   * Enables/Disables the option to play a card via the GUI from the local hand.
   * 
   * @param value Value.
   */
  void setCardsPlayable(boolean value) {
    if (value) {
      ColorAdjust grey = new ColorAdjust();
      grey.setBrightness(-0.4);
      for (Card c : SkatMain.lgs.localClient.getHand().cards) {
        if (!c.isPlayable()) {
          GuiCard card = this.playerHand.getGuiCard(c);
          if (card != null) {
            card.getCard().getImage().setEffect(grey);
          }
        }
      }

      this.table.setOnMouseMoved(event -> {
        Node node = event.getPickResult().getIntersectedNode();
        try {
          if (node.getParent().getParent().equals(this.playerHand)) {
            GuiCard card = (GuiCard) node.getParent();
            if (this.selectedCard != null && !this.selectedCard.equals(card)) {
              Duration d = Duration.millis(50);
              this.playerHand.raiseCard(this.selectedCard, false, false, false, true, d);
              this.selectedCard = card;
              this.playerHand.raiseCard(card, true, false, false, true, d);
            } else {
              if (this.selectedCard == null) {
                Duration d = Duration.millis(50);
                this.selectedCard = card;
                this.playerHand.raiseCard(card, true, false, false, true, d);
              }
            }
          }
        } catch (Exception e) {
          // No parent so an error is thrown every time when the cursor is not over a card.
          if (this.selectedCard != null) {
            Duration d = Duration.millis(50);
            this.playerHand.raiseCard(this.selectedCard, false, false, false, true, d);
            this.selectedCard = null;
          }
        }
      });

      this.table.setOnMouseClicked(event -> {
        Node node = event.getPickResult().getIntersectedNode();
        try {
          if (node.getParent().getParent().equals(this.playerHand)) {
            GuiCard card = (GuiCard) node.getParent();
            for (Card c : SkatMain.lgs.localClient.getHand().cards) {
              if (card.getCard().equals(c)) {
                if (c.isPlayable()) {
                  // Play card
                  if (this.selectedCard.equals(card)) {
                    this.selectedCard = null;
                  }
                  SkatMain.mainController.localCardPlayed(card.getCard());
                  this.setCardsPlayable(false);
                  break;
                }
              }
            }
          }
        } catch (Exception e) {
          // No parent so an error is thrown every time when the cursor is not over a card.
        }
      });
    } else {

      // Colorize all cards
      for (Card c : SkatMain.lgs.localClient.getHand().cards) {
        GuiCard card = this.playerHand.getGuiCard(c);
        if (card != null) {
          ((ColorAdjust) card.getCard().getImage().getEffect()).setBrightness(0);
        }
      }

      // Disable all interactions
      this.table.setOnMouseMoved(event -> {
      });
      this.table.setOnMouseClicked(event -> {
      });
    }
  }

  /**
   * Shows the skat selection on the screen.
   */
  void showSkatSelection() {
    GuiCard[] skat = new GuiCard[2];

    skat[0] = new GuiCard(SkatMain.lgs.skat[0]);
    skat[0].translateXProperty().bind(this.skatPositions[0].translateXProperty());
    skat[0].translateYProperty().bind(this.skatPositions[0].translateYProperty());
    skat[0].translateZProperty().bind(this.skatPositions[0].translateZProperty());

    skat[1] = new GuiCard(SkatMain.lgs.skat[1]);
    skat[1].translateXProperty().bind(this.skatPositions[1].translateXProperty());
    skat[1].translateYProperty().bind(this.skatPositions[1].translateYProperty());
    skat[1].translateZProperty().bind(this.skatPositions[1].translateZProperty());

    this.table.getChildren().addAll(skat);

    this.table.setOnMouseMoved(event -> {
      Node node = event.getPickResult().getIntersectedNode();
      try {
        if (node.getParent().getParent().equals(this.playerHand)) {
          GuiCard card = (GuiCard) node.getParent();
          if (this.selectedCard != null && !this.selectedCard.equals(card)) {
            Duration d = Duration.millis(50);
            this.playerHand.raiseCard(this.selectedCard, false, false, false, true, d);
            this.selectedCard = card;
            this.playerHand.raiseCard(card, true, false, false, true, d);
          } else {
            if (this.selectedCard == null) {
              Duration d = Duration.millis(50);
              this.selectedCard = card;
              this.playerHand.raiseCard(card, true, false, false, true, d);
            }
          }
        }
      } catch (Exception e) {
        // No parent so an error is thrown every time when the cursor is not over a card.
        if (this.selectedCard != null) {
          Duration d = Duration.millis(50);
          this.playerHand.raiseCard(this.selectedCard, false, false, false, true, d);
          this.selectedCard = null;
        }
      }
    });

    this.table.setOnMouseClicked(event -> {
      Node node = event.getPickResult().getIntersectedNode();
      try {
        if (node.getParent().equals(skat[0])) {
          GuiCard card = (GuiCard) node.getParent();
          skat[0] = null;
          this.table.getChildren().remove(card);
          this.playerHand.add(card);
          if (this.selectedCard.equals(card)) {
            this.selectedCard = null;
          }
          return;
        }
        if (node.getParent().equals(skat[1])) {
          GuiCard card = (GuiCard) node.getParent();
          skat[1] = null;
          this.table.getChildren().remove(card);
          this.playerHand.add(card);
          if (this.selectedCard.equals(card)) {
            this.selectedCard = null;
          }
          return;
        }
        if (node.getParent().getParent().equals(this.playerHand)) {
          GuiCard card = (GuiCard) node.getParent();
          if (this.selectedCard.equals(card)) {
            this.selectedCard = null;
          }

          if (skat[0] == null) {
            skat[0] = card;
            this.playerHand.moveCardAndRemove(card, this.skatPositions[0], this.table);

            Timeline tl = new Timeline();
            tl.getKeyFrames().add(new KeyFrame(Matchfield.animationTime, e -> {
              try {
                skat[0].translateXProperty().unbind();
                skat[0].translateYProperty().unbind();
                skat[0].translateZProperty().unbind();
                skat[0].getTransforms().clear();
                skat[0].translateXProperty().bind(this.skatPositions[0].translateXProperty());
                skat[0].translateYProperty().bind(this.skatPositions[0].translateYProperty());
                skat[0].translateZProperty().bind(this.skatPositions[0].translateZProperty());
              } catch (NullPointerException nullE) {
                // The Card is clicked on before it is placen on the right spot.
              }
            }));
            tl.play();
            return;
          }

          if (skat[1] == null) {
            skat[1] = card;
            this.playerHand.moveCardAndRemove(card, this.skatPositions[1], this.table);
            Timeline tl = new Timeline();
            tl.getKeyFrames().add(new KeyFrame(Matchfield.animationTime, e -> {
              try {
                skat[1].translateXProperty().unbind();
                skat[1].translateYProperty().unbind();
                skat[1].translateZProperty().unbind();
                skat[1].getTransforms().clear();
                skat[1].translateXProperty().bind(this.skatPositions[1].translateXProperty());
                skat[1].translateYProperty().bind(this.skatPositions[1].translateYProperty());
                skat[1].translateZProperty().bind(this.skatPositions[1].translateZProperty());
              } catch (NullPointerException nullE) {
                // The Card is clicked on before it is placen on the right spot.
              }
            }));
            tl.play();
            return;
          }
        }
      } catch (Exception e) {
        // No parent so an error is thrown every time when the cursor is not over a card.
      }
    });

    Button button = new Button("Save");
    button.setFont(Font.font(40));
    button.setPrefSize(150, 100);
    button.translateXProperty().bind(this.skatPositions[0].translateXProperty().add(225));
    button.translateYProperty().bind(this.skatPositions[0].translateYProperty().add(100));
    button.translateZProperty().bind(this.skatPositions[0].translateZProperty());
    button.setOnAction(e -> {
      if (skat[0] != null && skat[1] != null) {
        Card[] skat2 = new Card[2];
        skat2[0] = skat[0].getCard();
        skat2[1] = skat[1].getCard();

        Card[] cards = new Card[this.playerHand.getCards().size()];
        int j = 0;
        for (GuiCard c : this.playerHand.getCards()) {
          cards[j] = c.getCard();
        }
        Hand hand = new Hand(cards);
        SkatMain.mainController.skatSelected(hand, skat2);
        this.table.getChildren().removeAll(button, skat[0], skat[1]);
        this.setCardsPlayable(false);
      }
    });
    this.table.getChildren().add(button);
  }

  void showStartButton() {
    Button button = new Button("Start Game");
    button.setFont(Font.font(40));
    button.setPrefSize(300, 100);

    button.translateXProperty()
        .bind(this.scene.widthProperty().divide(2).subtract(button.widthProperty().divide(2)));
    button.translateYProperty()
        .bind(this.scene.heightProperty().divide(2).subtract(button.heightProperty().divide(2)));;

    SkatMain.mainController.currentLobby.setMaxNumberOfPlayerProperty();
    SkatMain.mainController.currentLobby.setNumberOfPlayerProperty();

    button.disableProperty().bind(SkatMain.mainController.currentLobby.numberOfPlayerProperty()
        .lessThan(SkatMain.mainController.currentLobby.maxNumberOfPlayerProperty()));

    button.setOnAction(e -> {
      this.root.getChildren().remove(button);
      SkatMain.mainController.startGame();
    });

    this.root.getChildren().add(button);
  }

  /**
   * Initializes all preset components of this Matchfield.
   */
  private void iniTableComponents() {
    // Box x = new Box(100000, 10, 10);
    // x.setMaterial(new PhongMaterial(Color.GREEN));
    // Box y = new Box(10, 100000, 10);
    // y.setMaterial(new PhongMaterial(Color.BLUE));
    // Box z = new Box(10, 10, 100000);
    // z.setMaterial(new PhongMaterial(Color.RED));
    // this.table.getChildren().addAll(x, y, z);

    this.tableScene.widthProperty().bind(this.scene.widthProperty());
    this.tableScene.heightProperty().bind(this.scene.heightProperty());

    this.playerHand = new GuiHand(this.tableScene.widthProperty().divide(2.3),
        this.tableScene.heightProperty().add(-200),
        this.tableScene.heightProperty().multiply(0).add(-100), -20, 0, 0, null);

    this.leftHand = new GuiHand(this.tableScene.widthProperty().multiply(0),
        this.tableScene.heightProperty().multiply(0.7),
        this.tableScene.heightProperty().multiply(0).add(1200), 0, -55, 0, null);

    this.rightHand = new GuiHand(this.tableScene.widthProperty().multiply(1),
        this.tableScene.heightProperty().multiply(0.7),
        this.tableScene.heightProperty().multiply(0).add(1200), 0, 55, 0, null);

    this.trick = new GuiTrick(this.tableScene.widthProperty().divide(2.5),
        this.tableScene.heightProperty().divide(1.4),
        this.tableScene.widthProperty().multiply(0).add(400), -80, 0, 0);

    this.table.getChildren().addAll(this.playerHand, this.leftHand, this.rightHand);


    this.skatPositions = new Parent[2];

    this.skatPositions[0] = new Parent() {};
    this.skatPositions[0].translateXProperty().bind(ReadOnlyDoubleProperty
        .readOnlyDoubleProperty(this.playerHand.translateXProperty()).subtract(200));
    this.skatPositions[0].translateYProperty().bind(ReadOnlyDoubleProperty
        .readOnlyDoubleProperty(this.playerHand.translateYProperty()).multiply(0.6));
    this.skatPositions[0].translateZProperty().bind(ReadOnlyDoubleProperty
        .readOnlyDoubleProperty(this.playerHand.translateZProperty()).add(300));


    this.skatPositions[1] = new Parent() {};
    this.skatPositions[1].translateXProperty().bind(ReadOnlyDoubleProperty
        .readOnlyDoubleProperty(this.playerHand.translateXProperty()).add(200));
    this.skatPositions[1].translateYProperty().bind(ReadOnlyDoubleProperty
        .readOnlyDoubleProperty(this.playerHand.translateYProperty()).multiply(0.6));
    this.skatPositions[1].translateZProperty().bind(ReadOnlyDoubleProperty
        .readOnlyDoubleProperty(this.playerHand.translateZProperty()).add(300));

    this.tableScene.widthProperty().addListener(e -> {

      this.playerHand.resetPositions();
      this.leftHand.resetPositions();
      this.rightHand.resetPositions();
      this.trick.resetPostions();
    });

    if (SkatMain.mainController.isHost) {
      this.showStartButton();
    }

    // FIXME
  }

}
