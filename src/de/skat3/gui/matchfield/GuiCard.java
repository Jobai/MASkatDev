package de.skat3.gui.matchfield;


import de.skat3.gamelogic.Card;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;

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
    this.card.view.setFitHeight(300);
    this.card.view.setFitWidth(150);
    this.getChildren().add(this.card.view);
  }

}
