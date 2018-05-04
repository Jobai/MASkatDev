package de.skat3.gui.matchfield;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Player;
import de.skat3.main.SkatMain;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
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
          } catch (NullPointerException e) {
            e.toString();
          }
        }
      }
    }

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
              if (this.selectedCard.equals(card)) {
                this.selectedCard = null;
              }
              // Play card
              SkatMain.mainController.localCardPlayed(card.getCard());
              // Disable cards
              SkatMain.guiController.getInGameController().makeAMoveRequest(false);
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
      this.tableView.table.setOnMouseMoved(event -> {
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
      this.tableView.table.setOnMouseMoved(event -> {
      });
    }
  }

  /**
   * Shows the skat selection.
   */
  void showSkatSelection() {
    GuiCard[] skat = new GuiCard[2];

    skat[0] = new GuiCard(SkatMain.lgs.skat[0]);
    skat[0].translateXProperty().bind(this.tableView.skatPositions[0].translateXProperty());
    skat[0].translateYProperty().bind(this.tableView.skatPositions[0].translateYProperty());
    skat[0].translateZProperty().bind(this.tableView.skatPositions[0].translateZProperty());

    skat[1] = new GuiCard(SkatMain.lgs.skat[1]);
    skat[1].translateXProperty().bind(this.tableView.skatPositions[1].translateXProperty());
    skat[1].translateYProperty().bind(this.tableView.skatPositions[1].translateYProperty());
    skat[1].translateZProperty().bind(this.tableView.skatPositions[1].translateZProperty());

    this.tableView.table.getChildren().addAll(skat);

    // Raise and lower cards when the cursor is hovering over them.
    this.showCardAnimationInLCHand(true);

    this.tableView.table.setOnMouseClicked(event -> {
      Node node = event.getPickResult().getIntersectedNode();
      try {
        // add skat card 1 to the local hand
        if (node.getParent().equals(skat[0])) {
          GuiCard card = (GuiCard) node.getParent();
          skat[0] = null;
          this.tableView.table.getChildren().remove(card);
          this.tableView.playerHand.add(card, true);
          if (this.selectedCard.equals(card)) {
            this.selectedCard = null;
          }
          return;
        }
        // add skat card 2 to the local hand
        if (node.getParent().equals(skat[1])) {
          GuiCard card = (GuiCard) node.getParent();
          skat[1] = null;
          this.tableView.table.getChildren().remove(card);
          this.tableView.playerHand.add(card, true);
          if (this.selectedCard.equals(card)) {
            this.selectedCard = null;
          }
          return;
        }
        if (node.getParent().getParent().equals(this.tableView.playerHand)) {
          GuiCard card = (GuiCard) node.getParent();
          if (this.selectedCard.equals(card)) {
            this.selectedCard = null;
          }
          // is adding the selected card from your hand to the skat in postion 1.
          if (skat[0] == null) {
            skat[0] = card;
            this.tableView.playerHand.moveCardAndRemove(card, this.tableView.skatPositions[0],
                this.tableView.table);

            Timeline tl = new Timeline();
            tl.getKeyFrames().add(new KeyFrame(Matchfield.animationTime, e -> {
              try {
                skat[0].translateXProperty().unbind();
                skat[0].translateYProperty().unbind();
                skat[0].translateZProperty().unbind();
                skat[0].getTransforms().clear();
                skat[0].translateXProperty()
                    .bind(this.tableView.skatPositions[0].translateXProperty());
                skat[0].translateYProperty()
                    .bind(this.tableView.skatPositions[0].translateYProperty());
                skat[0].translateZProperty()
                    .bind(this.tableView.skatPositions[0].translateZProperty());
              } catch (NullPointerException nullE) {
                // The Card is clicked on before it is placed on the right spot.
              }
            }));
            tl.play();
            return;
          }
          // is adding the selected card from your hand to the skat in postion 2.
          if (skat[1] == null) {
            skat[1] = card;
            this.tableView.playerHand.moveCardAndRemove(card, this.tableView.skatPositions[1],
                this.tableView.table);
            Timeline tl = new Timeline();
            tl.getKeyFrames().add(new KeyFrame(Matchfield.animationTime, e -> {
              try {
                skat[1].translateXProperty().unbind();
                skat[1].translateYProperty().unbind();
                skat[1].translateZProperty().unbind();
                skat[1].getTransforms().clear();
                skat[1].translateXProperty()
                    .bind(this.tableView.skatPositions[1].translateXProperty());
                skat[1].translateYProperty()
                    .bind(this.tableView.skatPositions[1].translateYProperty());
                skat[1].translateZProperty()
                    .bind(this.tableView.skatPositions[1].translateZProperty());
              } catch (NullPointerException nullE) {
                // The Card is clicked on before it is placed on the right spot.
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

    // Button the Save your selection
    Button button = new Button("Save");
    button.setFont(Font.font(40));
    button.setPrefSize(150, 100);
    button.translateXProperty().bind(this.tableView.skatPositions[0].translateXProperty().add(225));
    button.translateYProperty().bind(this.tableView.skatPositions[0].translateYProperty().add(100));
    button.translateZProperty().bind(this.tableView.skatPositions[0].translateZProperty());
    button.setOnAction(e -> {
      if (skat[0] != null && skat[1] != null) {
        Card[] skat2 = new Card[2];
        skat2[0] = skat[0].getCard();
        skat2[1] = skat[1].getCard();

        Card[] cards = new Card[this.tableView.playerHand.getCards().size()];
        int j = 0;
        for (GuiCard c : this.tableView.playerHand.getCards()) {
          cards[j++] = c.getCard();
        }
        Hand hand = new Hand(cards);
        SkatMain.mainController.skatSelected(hand, skat2);
        this.tableView.table.getChildren().removeAll(button, skat[0], skat[1]);
        SkatMain.guiController.getInGameController().makeAMoveRequest(false);
      }
    });
    this.tableView.table.getChildren().add(button);
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
    this.tableView.playerHand.setPlayer(SkatMain.lgs.getLocalClient());
    this.tableView.leftHand.setPlayer(SkatMain.lgs.getEnemyOne());
    this.tableView.rightHand.setPlayer(SkatMain.lgs.getEnemyTwo());

    this.tableView.playerHand.clear();
    this.tableView.leftHand.clear();
    this.tableView.leftHand.clear();

    this.tableView.playerHand.addAll(SkatMain.lgs.getLocalClient().getHand().getCards(), false);
    this.tableView.leftHand.addAll(SkatMain.lgs.getEnemyOne().getHand().getCards(), false);
    this.tableView.rightHand.addAll(SkatMain.lgs.getEnemyTwo().getHand().getCards(), false);

  }

}
