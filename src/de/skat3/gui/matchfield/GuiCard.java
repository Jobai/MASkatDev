package de.skat3.gui.matchfield;


import de.skat3.gamelogic.Card;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;

/**
 * Hand.
 * 
 * @author Aljoscha Domonell
 *
 */
public class GuiCard extends Parent {
  Card card;

  /**
   * .
   * 
   */
  public GuiCard(Card card) {
    this.card = card;
    this.card.view.setFitHeight(300);
    this.card.view.setFitWidth(150);
    this.getChildren().add(this.card.view);
  }

}
