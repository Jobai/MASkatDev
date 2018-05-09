package de.skat3.gui.matchfield;

import java.util.logging.Logger;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Player;
import de.skat3.main.SkatMain;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * @author Aljoscha Domonell
 *
 */
public class InGameTableController {

  InGameTableView tableView;

  boolean isPlaying;

  private GuiCard selectedCard;

  public InGameTableController(InGameTableView view) {
    this.tableView = view;
  }

  /**
   * Plays a card from a hand on the trick.
   * 
   * @param hand Hand from which the card is played.
   * @param card Card to be played.
   */
  synchronized void playCard(GuiHand hand, GuiCard card) {
    hand.moveCardAndRemove(card, this.tableView.trick.add(card), this.tableView.table);
  }

  /**
   * Is refreshing the color of all cards in your hand. Only playable cards are in a normal color.
   * 
   * @param value True to show to color effect. False to disable the color effect.
   * @param playableRef TODO
   */
  void showPlayableColor(boolean value, Card[] playableRef) {
    if (value) {
      ColorAdjust grey = new ColorAdjust();
      grey.setBrightness(-0.4);

      for (Card c : playableRef) {
        if (!c.isPlayable()) {
          GuiCard card = this.tableView.playerHand.getGuiCard(c);
          if (card != null) {
            card.getCard().getImage().setEffect(grey);
          }
        } else {
          GuiCard card = this.tableView.playerHand.getGuiCard(c);
          if (card != null) {
            try {
              ((ColorAdjust) card.getCard().getImage().getEffect()).setBrightness(0);
            } catch (NullPointerException e) {
              e.toString();
            }
          }
        }
      }
    } else {
      for (Card c : playableRef) {
        GuiCard card = this.tableView.playerHand.getGuiCard(c);
        if (card != null) {
          try {
            ((ColorAdjust) card.getCard().getImage().getEffect()).setBrightness(0);
            card.setBlingBling(false);
          } catch (NullPointerException e) {
            e.toString();
          }
        }
      }
    }

  }

  void playCard(GuiCard card) {
    if (this.selectedCard != null && this.selectedCard.equals(card)) {
      this.selectedCard = null;
    }

    // Play card
    SkatMain.mainController.localCardPlayed(card.getCard());
    // Disable local cards
    SkatMain.guiController.getInGameController().showMakeAMoveRequest(false);
    // hide kontra
    SkatMain.guiController.getInGameController().matchfield.overlayController.annouceContraButton
        .setVisible(false);
  }

  /**
   * Enables/Disables the option to play a card via the GUI from the local hand.
   * 
   * @param value Value.
   */
  void setCardsPlayable(boolean value, Card[] playableRef) {

    this.isPlaying = value;

    this.showPlayableColor(value, playableRef);

    this.showCardAnimationInLCHand(value);

    if (value) {

      // click --> check --> play
      this.tableView.table.setOnMouseClicked(event -> {
        Node node = event.getPickResult().getIntersectedNode();
        try {
          if (node.getParent().getParent().equals(this.tableView.playerHand)) {
            GuiCard card = (GuiCard) node.getParent();
            if (card.isPlayable(playableRef, card)) {
              // PLAY CARD
              this.playCard(card);
            }

          }
        } catch (Exception e) {
          // No parent so an error is thrown every time when the cursor is not over a card.
        }
      });
    } else {

      // Disable all interactions
      this.tableView.table.setOnMouseClicked(event -> {
      });
    }
  }

  /**
   * Hover a card in your localHand when the cursor is over it.
   * 
   * @param value
   */
  private void showCardAnimationInLCHand(boolean value) {
    if (value) {
      SkatMain.guiController.getInGameController().matchfield.root.setOnMouseMoved(event -> {
        Node node = event.getPickResult().getIntersectedNode();
        try {
          if (node.getParent().getParent().equals(this.tableView.playerHand)) {
            GuiCard card = (GuiCard) node.getParent();
            if (this.selectedCard != null && !this.selectedCard.equals(card)) {
              // lower old card and raise new one.
              Duration d = Duration.millis(50);
              this.tableView.playerHand.raiseCard(this.selectedCard, false, false, false, true, d);
              this.selectedCard = card;
              this.tableView.playerHand.raiseCard(card, true, false, false, true, d);
            } else {
              if (this.selectedCard == null) {
                // raise new card.
                Duration d = Duration.millis(50);
                this.selectedCard = card;
                this.tableView.playerHand.raiseCard(card, true, false, false, true, d);
              }
              // do noting because the card is already hovering.
            }
          }
        } catch (Exception e) {
          // No parent so an error is thrown every time when the cursor is not over a card.
          if (this.selectedCard != null) {
            // lower old card.
            Duration d = Duration.millis(50);
            this.tableView.playerHand.raiseCard(this.selectedCard, false, false, false, true, d);
            this.selectedCard = null;
          }
        }
      });
    } else {
      SkatMain.guiController.getInGameController().matchfield.root.setOnMouseMoved(event -> {
      });
    }
  }

  private boolean animationRunning;
  Timeline animationTimer;

  private void setAnimationRunning(Duration d) {
    SkatMain.guiController.logger.entering(this.getClass().getName(), "setAnimationRunning");

    if (this.animationTimer == null) {
      this.animationTimer = new Timeline();
    }
    if (this.animationTimer.getStatus().equals(Status.RUNNING)) {
      this.animationTimer.setOnFinished(e -> {
      });
      this.animationTimer.stop();
      this.animationTimer.getKeyFrames().clear();
      this.setAnimationRunning(d);
      return;
    } else {
      this.animationRunning = true;
      this.animationTimer.getKeyFrames().add(new KeyFrame(d, e -> {
      }));
      this.animationTimer.setOnFinished(e -> this.animationRunning = false);
      this.animationTimer.playFromStart();
    }

  }

  /**
   * Shows the skat selection.
   */
  void showSkatSelection(boolean show) {
    if (this.tableView.skat != null && this.tableView.saveSkatButton != null) {
      if (show) {
        return;
      } else {
        this.tableView.table.getChildren().removeAll(this.tableView.saveSkatButton,
            this.tableView.skat[0], this.tableView.skat[1]);
        this.tableView.skat = null;
        this.tableView.saveSkatButton = null;
        this.tableView.playerHand.clear();
        this.tableView.playerHand.addAll(SkatMain.lgs.getLocalClient().getHand().getCards(), false);
      }


    } else {
      if (!show) {
        return;
      }
    }

    this.tableView.skat = new GuiCard[2];

    this.tableView.skat[0] = new GuiCard(SkatMain.lgs.getSkat()[0]);
    this.tableView.skat[0].translateXProperty()
        .bind(this.tableView.skatPositions[0].translateXProperty());
    this.tableView.skat[0].translateYProperty()
        .bind(this.tableView.skatPositions[0].translateYProperty());
    this.tableView.skat[0].translateZProperty()
        .bind(this.tableView.skatPositions[0].translateZProperty());

    this.tableView.skat[1] = new GuiCard(SkatMain.lgs.getSkat()[1]);
    this.tableView.skat[1].translateXProperty()
        .bind(this.tableView.skatPositions[1].translateXProperty());
    this.tableView.skat[1].translateYProperty()
        .bind(this.tableView.skatPositions[1].translateYProperty());
    this.tableView.skat[1].translateZProperty()
        .bind(this.tableView.skatPositions[1].translateZProperty());

    this.tableView.table.getChildren().addAll(this.tableView.skat);

    // Raise and lower cards when the cursor is hovering over them.
    this.showCardAnimationInLCHand(true);

    this.tableView.table.setOnMouseClicked(event -> {

      if (this.animationRunning) {
        return;
      }

      Node node = event.getPickResult().getIntersectedNode();
      try {
        // add skat card 1 to the local hand
        if (node.getParent().equals(this.tableView.skat[0])) {
          GuiCard card = (GuiCard) node.getParent();
          this.tableView.skat[0] = null;
          this.tableView.table.getChildren().remove(card);
          this.tableView.playerHand.add(card, true);
          if (this.selectedCard.equals(card)) {
            this.selectedCard = null;
          }
          this.setAnimationRunning(Matchfield.animationTime);
          return;
        }
        // add skat card 2 to the local hand
        if (node.getParent().equals(this.tableView.skat[1])) {
          GuiCard card = (GuiCard) node.getParent();
          this.tableView.skat[1] = null;
          this.tableView.table.getChildren().remove(card);
          this.tableView.playerHand.add(card, true);
          if (this.selectedCard.equals(card)) {
            this.selectedCard = null;
          }
          this.setAnimationRunning(Matchfield.animationTime);
          return;
        }
        if (node.getParent().getParent().equals(this.tableView.playerHand)) {
          GuiCard card = (GuiCard) node.getParent();
          if (this.selectedCard.equals(card)) {
            this.selectedCard = null;
          }
          // is adding the selected card from your hand to the skat in postion 1.
          if (this.tableView.skat[0] == null) {
            this.tableView.skat[0] = card;
            this.tableView.playerHand.moveCardAndRemove(card, this.tableView.skatPositions[0],
                this.tableView.table);

            Timeline tl = new Timeline();
            tl.getKeyFrames().add(new KeyFrame(Matchfield.animationTime, e -> {
              try {
                this.tableView.skat[0].translateXProperty().unbind();
                this.tableView.skat[0].translateYProperty().unbind();
                this.tableView.skat[0].translateZProperty().unbind();
                this.tableView.skat[0].getTransforms().clear();
                this.tableView.skat[0].translateXProperty()
                    .bind(this.tableView.skatPositions[0].translateXProperty());
                this.tableView.skat[0].translateYProperty()
                    .bind(this.tableView.skatPositions[0].translateYProperty());
                this.tableView.skat[0].translateZProperty()
                    .bind(this.tableView.skatPositions[0].translateZProperty());
              } catch (NullPointerException nullE) {
                // The Card is clicked on before it is placed on the right spot.
              }
            }));
            tl.play();
            this.setAnimationRunning(Matchfield.animationTime);
            return;
          }
          // is adding the selected card from your hand to the skat in postion 2.
          if (this.tableView.skat[1] == null) {
            this.tableView.skat[1] = card;
            this.tableView.playerHand.moveCardAndRemove(card, this.tableView.skatPositions[1],
                this.tableView.table);
            Timeline tl = new Timeline();
            tl.getKeyFrames().add(new KeyFrame(Matchfield.animationTime, e -> {
              try {
                this.tableView.skat[1].translateXProperty().unbind();
                this.tableView.skat[1].translateYProperty().unbind();
                this.tableView.skat[1].translateZProperty().unbind();
                this.tableView.skat[1].getTransforms().clear();
                this.tableView.skat[1].translateXProperty()
                    .bind(this.tableView.skatPositions[1].translateXProperty());
                this.tableView.skat[1].translateYProperty()
                    .bind(this.tableView.skatPositions[1].translateYProperty());
                this.tableView.skat[1].translateZProperty()
                    .bind(this.tableView.skatPositions[1].translateZProperty());
              } catch (NullPointerException nullE) {
                // The Card is clicked on before it is placed on the right spot.
              }
            }));
            tl.play();
            this.setAnimationRunning(Matchfield.animationTime);
            return;
          }
        }
      } catch (Exception e) {
        // No parent so an error is thrown every time when the cursor is not over a card.
      }


    });

    // Button the Save your selection
    this.tableView.saveSkatButton = new Button("Save");
    this.tableView.saveSkatButton.setFont(Font.font(40));
    this.tableView.saveSkatButton.setPrefSize(170, 100);
    this.tableView.saveSkatButton.setTextFill(Color.WHITE);
    this.tableView.saveSkatButton
        .setStyle("-fx-background-color: #d60202; -fx-background-radius: 30; "
            + "-fx-border-color: #404040; -fx-border-radius: 30;");
    this.tableView.saveSkatButton.translateXProperty()
        .bind(this.tableView.skatPositions[0].translateXProperty().add(215));
    this.tableView.saveSkatButton.translateYProperty()
        .bind(this.tableView.skatPositions[0].translateYProperty().add(100));
    this.tableView.saveSkatButton.translateZProperty()
        .bind(this.tableView.skatPositions[0].translateZProperty());
    this.tableView.saveSkatButton.setOnAction(e -> {
      if (this.tableView.skat[0] != null && this.tableView.skat[1] != null) {
        SkatMain.guiController.getInGameController().matchfield.overlayController.showTimer(false);
        Card[] skat2 = new Card[2];
        skat2[0] = this.tableView.skat[0].getCard();
        skat2[1] = this.tableView.skat[1].getCard();

        Card[] cards = new Card[this.tableView.playerHand.getCards().size()];
        int j = 0;
        for (GuiCard c : this.tableView.playerHand.getCards()) {
          cards[j++] = c.getCard();
        }
        Hand hand = new Hand(cards);
        SkatMain.mainController.skatSelected(hand, skat2);
        this.tableView.table.getChildren().removeAll(this.tableView.saveSkatButton,
            this.tableView.skat[0], this.tableView.skat[1]);
        SkatMain.guiController.getInGameController().showMakeAMoveRequest(false);
      }
    });
    this.tableView.table.getChildren().add(this.tableView.saveSkatButton);

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
        if (this.tableView.playerHand.getOwner().equals(owner)) {
          return this.tableView.playerHand;
        }
      } catch (NullPointerException e) {
        // Not this player.
      }
      try {
        if (this.tableView.leftHand.getOwner().equals(owner)) {
          return this.tableView.leftHand;
        }
      } catch (NullPointerException e) {
        // Not this player.
      }
      try {
        if (this.tableView.rightHand.getOwner().equals(owner)) {
          return this.tableView.rightHand;
        }
      } catch (NullPointerException e) {
        // Not this player.
      }

      throw new Exception("Player does not own a GuiHand.");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  void refreshHands() {
    this.tableView.playerHand.resetPositions();
    this.tableView.leftHand.resetPositions();
    this.tableView.rightHand.resetPositions();
  }

  /**
   * Is initializing all hands by first clearing them and than adding new cards form the
   * LocalGameState to them and setting the new owners.
   * 
   */
  public void iniHands() {

    this.tableView.playerHand.clear();
    this.tableView.leftHand.clear();
    this.tableView.rightHand.clear();

    this.tableView.playerHand.setPlayer(SkatMain.lgs.getLocalClient());
    this.tableView.leftHand.setPlayer(SkatMain.lgs.getEnemyOne());
    this.tableView.rightHand.setPlayer(SkatMain.lgs.getEnemyTwo());

    this.tableView.playerHand.addAll(SkatMain.lgs.getLocalClient().getHand().getCards(), false);
    this.tableView.leftHand.addAll(SkatMain.lgs.getEnemyOne().getHand().getCards(), false);
    this.tableView.rightHand.addAll(SkatMain.lgs.getEnemyTwo().getHand().getCards(), false);

  }


}
