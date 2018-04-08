package de.skat3.gui.matchfield;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * @author Aljoscha Domonell
 *
 */
public class Trick extends Parent {

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
  public Trick(double x, double y, double z, double xr, double yr, double zr) {
    this.cards = new GuiCard[3];
    this.postions = new Parent[3];
    this.postions[0] = new Parent() {};
    this.postions[1] = new Parent() {};
    this.postions[2] = new Parent() {};

    this.postions[0].setTranslateX(x);
    this.postions[0].setTranslateY(y);
    this.postions[0].setTranslateZ(z);
    this.postions[0].getTransforms().add(new Rotate(xr, Rotate.X_AXIS));
    this.postions[0].getTransforms().add(new Rotate(yr, Rotate.Y_AXIS));
    this.postions[0].getTransforms().add(new Rotate(zr, Rotate.Z_AXIS));

    this.postions[1].setTranslateX(900);
    this.postions[1].setTranslateY(899);
    this.postions[1].setTranslateZ(500);
    this.postions[1].getTransforms().add(new Rotate(-180 + 90, Rotate.X_AXIS));
    this.postions[1].getTransforms().add(new Rotate(0, Rotate.Y_AXIS));
    this.postions[1].getTransforms().add(new Rotate(30, Rotate.Z_AXIS));

    this.postions[2].setTranslateX(900);
    this.postions[2].setTranslateY(898);
    this.postions[2].setTranslateZ(500);
    this.postions[2].getTransforms().add(new Rotate(-180 + 90, Rotate.X_AXIS));
    this.postions[2].getTransforms().add(new Rotate(0, Rotate.Y_AXIS));
    this.postions[2].getTransforms().add(new Rotate(60, Rotate.Z_AXIS));

    this.setTranslateX(x);
    this.setTranslateY(y);
    this.setTranslateZ(z);

    this.getTransforms().add(new Rotate(xr, Rotate.X_AXIS));
    this.getTransforms().add(new Rotate(yr, Rotate.Y_AXIS));
    this.getTransforms().add(new Rotate(zr, Rotate.Z_AXIS));

  }

  /**
   * Add a card to this trick.
   * 
   * @param card Card to be added to this trick.
   * @return A parent which functions as a container for all relevant positioning informations.
   */
  public Parent add(GuiCard card) {
    this.cards[index] = card;
    Timeline timeline = new Timeline();
    timeline.getKeyFrames()
        .add(new KeyFrame(Matchfield.animationTime, e -> this.getChildren().add(card)));
    timeline.play();

    // if (index > 1) {
    // this.clear();
    // }

    return this.postions[index++];
  }

  public void clear() {
    this.cards = new GuiCard[3];
  }

}
