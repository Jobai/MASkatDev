package de.skat3.gui.matchfield;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Suit;
import de.skat3.gamelogic.Value;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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

  private InGameController controller;

  protected Scene scene;
  protected Pane table;
  protected GuiHand playerHand;
  protected GuiHand leftHand;
  protected GuiHand rightHand;
  protected GuiTrick trick;
  public static Duration animationTime = Duration.seconds(0.2);
  private Parent[] skatPositions;

  /**
   * Returns a Matchfield.
   */
  public Matchfield() {
    this.controller = new InGameController(this);
    double sceneWidth = 1280;
    double sceneHeight = 720;
    this.table = new Pane();
    this.scene = new Scene(this.table, sceneWidth, sceneHeight, false, SceneAntialiasing.BALANCED);

    this.iniComponents();

    PerspectiveCamera cam = new PerspectiveCamera();
    cam.setTranslateY(-100);
    cam.getTransforms().add(new Rotate(-30, Rotate.X_AXIS));
    this.scene.setCamera(cam);
  }

  /**
   * Initializes all preset components of this Matchfield.
   */
  private void iniComponents() {
    // Box x = new Box(100000, 10, 10);
    // x.setMaterial(new PhongMaterial(Color.GREEN));
    // Box y = new Box(10, 100000, 10);
    // y.setMaterial(new PhongMaterial(Color.BLUE));
    // Box z = new Box(10, 10, 100000);
    // z.setMaterial(new PhongMaterial(Color.RED));
    // this.table.getChildren().addAll(x, y, z);

    this.playerHand = new GuiHand(this.table.getScene().widthProperty().divide(2.3),
        this.table.getScene().heightProperty().add(-200),
        this.table.getScene().heightProperty().multiply(0).add(-100), -20, 0, 0, null);



    this.leftHand = new GuiHand(this.table.getScene().widthProperty().multiply(0),
        this.table.getScene().heightProperty().multiply(0.7),
        this.table.getScene().heightProperty().multiply(0).add(1200), 0, -55, 0, null);

    this.rightHand = new GuiHand(this.table.getScene().widthProperty().multiply(1),
        this.table.getScene().heightProperty().multiply(0.7),
        this.table.getScene().heightProperty().multiply(0).add(1200), 0, 55, 0, null);

    this.trick = new GuiTrick(this.getScene().widthProperty().divide(2.5),
        this.getScene().heightProperty().divide(1.4),
        this.getScene().widthProperty().multiply(0).add(400), -80, 0, 0);

    this.table.getChildren().addAll(this.playerHand, this.leftHand, this.rightHand);


    this.skatPositions = new Parent[2];

    this.skatPositions[0] = new Parent() {};
    this.skatPositions[0].translateXProperty().bind(
        DoubleProperty.readOnlyDoubleProperty(this.playerHand.translateXProperty()).subtract(200));
    this.skatPositions[0].translateYProperty().bind(
        DoubleProperty.readOnlyDoubleProperty(this.playerHand.translateYProperty()).multiply(0.6));
    this.skatPositions[0].translateZProperty()
        .bind(DoubleProperty.readOnlyDoubleProperty(this.playerHand.translateZProperty()).add(300));


    this.skatPositions[1] = new Parent() {};
    this.skatPositions[1].translateXProperty()
        .bind(DoubleProperty.readOnlyDoubleProperty(this.playerHand.translateXProperty()).add(200));
    this.skatPositions[1].translateYProperty().bind(
        DoubleProperty.readOnlyDoubleProperty(this.playerHand.translateYProperty()).multiply(0.6));
    this.skatPositions[1].translateZProperty()
        .bind(DoubleProperty.readOnlyDoubleProperty(this.playerHand.translateZProperty()).add(300));

    this.table.widthProperty().addListener(e -> {
      this.playerHand.resetPositions();
      this.leftHand.resetPositions();
      this.rightHand.resetPositions();
      this.trick.resetPostions();
    });

  }

  /**
   * Plays a card from a hand on the trick.
   * 
   * @param hand From which the card is played.
   * @param card Card to be played.
   */
  protected synchronized void playCard(GuiHand hand, GuiCard card) {
    hand.moveCardAndRemove(card, this.trick.add(card), this.table);
  }

  public InGameController getController() {
    return this.controller;
  }

  /**
   * Shows the skat selection on the screen.
   */
  public void showSkatSelection() {
    GuiCard[] skat = new GuiCard[2];

    // skat[0] = new GuiCard(this.gameState.skat[0]); // TODO
    // skat[1] = new GuiCard(this.gameState.skat[1]);

    skat[0] = new GuiCard(new Card(Suit.CLUBS, Value.ACE));
    skat[0].translateXProperty().bind(this.skatPositions[0].translateXProperty());
    skat[0].translateYProperty().bind(this.skatPositions[0].translateYProperty());
    skat[0].translateZProperty().bind(this.skatPositions[0].translateZProperty());
    skat[1] = new GuiCard(new Card(Suit.CLUBS, Value.TEN));
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
              skat[0].translateXProperty().unbind();
              skat[0].translateYProperty().unbind();
              skat[0].translateZProperty().unbind();
              skat[0].getTransforms().clear();
              skat[0].translateXProperty().bind(this.skatPositions[0].translateXProperty());
              skat[0].translateYProperty().bind(this.skatPositions[0].translateYProperty());
              skat[0].translateZProperty().bind(this.skatPositions[0].translateZProperty());
            }));
            tl.play();
            return;
          }

          if (skat[1] == null) {
            skat[1] = card;
            this.playerHand.moveCardAndRemove(card, this.skatPositions[1], this.table);
            Timeline tl = new Timeline();
            tl.getKeyFrames().add(new KeyFrame(Matchfield.animationTime, e -> {
              skat[1].translateXProperty().unbind();
              skat[1].translateYProperty().unbind();
              skat[1].translateZProperty().unbind();
              skat[1].getTransforms().clear();
              skat[1].translateXProperty().bind(this.skatPositions[1].translateXProperty());
              skat[1].translateYProperty().bind(this.skatPositions[1].translateYProperty());
              skat[1].translateZProperty().bind(this.skatPositions[1].translateZProperty());
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
        skat2[0] = skat[0].card;
        skat2[1] = skat[1].card;

        // SkatMain.mainController.skatSelected(null, skat2); // TODO
        this.table.getChildren().removeAll(button, skat[0], skat[1]);
        this.setCardsPlayable(false);
      }
    });
    this.table.getChildren().add(button);
  }

  /**
   * Is searching a hand which is owned by the spezified Player.
   * 
   * @param owner Player to search for.
   * @return Hand of this Player.
   */
  public GuiHand getHand(Player owner) {
    try {
      if (this.playerHand.getOwner().equals(owner)) {
        return this.playerHand;
      }
      if (this.leftHand.getOwner().equals(owner)) {
        return this.leftHand;
      }
      if (this.rightHand.getOwner().equals(owner)) {
        return this.rightHand;
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

  private GuiCard selectedCard;

  /**
   * Enables/Disables the option to play a card via the GUI from the local hand.
   * 
   * @param value Value.
   */
  public void setCardsPlayable(boolean value) {
    if (value) {
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
            if (this.selectedCard.equals(card)) {
              this.selectedCard = null;
            }
            // for (Card c : this.gameState.localClient.getHand().cards) {
            // if (card.card.equals(c)) {
            // if (c.isPlayable()) {
            this.playCard(this.playerHand, card); // TODO
            // break;
            // }
            // }
            // }
          }
        } catch (Exception e) {
          // No parent so an error is thrown every time when the cursor is not over a card.
        }
      });
    } else {
      this.table.setOnMouseMoved(event -> {
      });
      this.table.setOnMouseClicked(event -> {
      });
    }
  }
}
