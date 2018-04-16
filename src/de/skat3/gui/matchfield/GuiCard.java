package de.skat3.gui.matchfield;


import de.skat3.gamelogic.Card;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

/**
 * View class of a hand.
 * 
 * @author adomonel
 *
 */
public class GuiCard extends Parent {
  Card card;

  /**
   * Creates a GuiCard object out of a Card.
   * 
   * @param card The corresponding Card for this view.
   * 
   */
  public GuiCard(Card card) {
    this.card = card;
    this.card.getImage().setFitHeight(400);
    this.card.getImage().setFitWidth(200);
    this.getChildren().add(this.card.getImage());
  }

  public String toString() {
    StringBuffer s = new StringBuffer();
    s.append(this.card.toString());
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
