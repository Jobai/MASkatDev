package de.skat3.gui.matchfield;

import de.skat3.gamelogic.Card;
import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.util.Duration;

/**
 * View class of a hand.
 * 
 * @author adomonel
 *
 */
public class GuiCard extends Parent {
  public static double heigth = 300;
  public static double width = 200;
  private Card card;

  /**
   * Creates a GuiCard object out of a Card.
   * 
   * @param card The corresponding Card for this view.
   * 
   */
  public GuiCard(Card card) {
    this.setCard(card);
    this.getCard().getImage().setFitHeight(heigth);
    this.getCard().getImage().setFitWidth(width);
    this.getChildren().add(this.getCard().getImage());
  }

  public Card getCard() {
    return card;
  }

  public void setCard(Card card) {
    this.card = card;
  }

  public boolean isPlayable(Card[] playableRef, GuiCard card) {
    for (Card c : playableRef) {
      try {
        if (card.getCard().equals(c)) {
          if (c.isPlayable()) {
            return true;
          }
        }
      } catch (Exception e) {
        // not important
      }
    }
    return false;
  }

  private FadeTransition bling;

  void setBlingBling(boolean value) {
    if (this.bling == null) {
      this.bling = new FadeTransition();
      bling.setNode(this);
      bling.setDuration(Duration.seconds(0.5));
      bling.setFromValue(1);
      bling.setToValue(0.6);
      bling.setAutoReverse(true);
      bling.setCycleCount(MediaPlayer.INDEFINITE);
    }

    if (value) {
      this.bling.playFromStart();
    } else {
      this.bling.stop();
      this.setOpacity(1);
    }
  }

  /**
   * To String.
   * 
   * @return String.
   */
  @Override
  public String toString() {
    StringBuffer s = new StringBuffer();
    s.append(this.getCard().toString());
    s.append(" at relative postion: ");
    s.append("x=" + this.getTranslateX());
    s.append(", ");
    s.append("y=" + this.getTranslateY());
    s.append(", ");
    s.append("z=" + this.getTranslateZ());
    for (Transform t : this.getTransforms()) {
      s.append(", ");
      if (t.getClass().equals(Rotate.class)) {
        s.append(((Rotate) t).getAngle());
      }
    }

    s.append(" Number of Transforms: " + this.getTransforms().size());
    return s.toString();
  }
}
