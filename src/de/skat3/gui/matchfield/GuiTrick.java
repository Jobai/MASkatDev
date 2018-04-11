package de.skat3.gui.matchfield;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * View class of a trick.
 * 
 * @author adomonel
 *
 */
public class GuiTrick {

  private GuiCard[] cards;
  private Parent[] postions;
  private int index;

  /**
   * ASD.
   * 
   * @param x Translate x.
   * @param y Translate y.
   * @param z Translate z.
   * @param xr Rotate x.
   * @param yr Rotate y.
   * @param zr Rotate z.
   */
  public GuiTrick(DoubleBinding x, DoubleBinding y, DoubleBinding z, double xr, double yr,
      double zr) {
    this.cards = new GuiCard[3];
    this.postions = new Parent[3];
    this.postions[0] = new Parent() {};
    this.postions[1] = new Parent() {};
    this.postions[2] = new Parent() {};

    this.postions[0].translateXProperty().bind(x.add(-150));
    this.postions[0].translateYProperty().bind(y);
    this.postions[0].translateZProperty().bind(z.add(-100));
    this.postions[0].getTransforms().add(new Rotate(xr, Rotate.X_AXIS));
    this.postions[0].getTransforms().add(new Rotate(yr, Rotate.Y_AXIS));
    this.postions[0].getTransforms().add(new Rotate(zr - 30, Rotate.Z_AXIS));

    this.postions[1].translateXProperty().bind(x);
    this.postions[1].translateYProperty().bind(y.add(-1));
    this.postions[1].translateZProperty().bind(z);
    this.postions[1].getTransforms().add(new Rotate(xr, Rotate.X_AXIS));
    this.postions[1].getTransforms().add(new Rotate(yr, Rotate.Y_AXIS));
    this.postions[1].getTransforms().add(new Rotate(zr, Rotate.Z_AXIS));

    this.postions[2].translateXProperty().bind(x.add(200));
    this.postions[2].translateYProperty().bind(y.add(-2));
    this.postions[2].translateZProperty().bind(z);
    this.postions[2].getTransforms().add(new Rotate(xr, Rotate.X_AXIS));
    this.postions[2].getTransforms().add(new Rotate(yr, Rotate.Y_AXIS));
    this.postions[2].getTransforms().add(new Rotate(zr + 30, Rotate.Z_AXIS));

  }

  /**
   * Add a card to this trick.
   * 
   * @param card Card to be added to this trick.
   * @return A parent which functions as a container for all relevant positioning informations.
   */
  public Parent add(GuiCard card) {
    this.cards[index] = card;

    if (index > 1) {
      this.clear();
    }

    return this.postions[index++];
  }

  public void clear() {

    for (GuiCard c : this.cards) {
      c.setVisible(false);
    }

    this.cards = new GuiCard[3];

  }

}
