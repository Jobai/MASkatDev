package de.skat3.gui.matchfield;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableNumberValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import sun.applet.Main;

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
  DoubleProperty translateXOne;

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
    this.translateXOne = new SimpleDoubleProperty();
    this.translateXOne.set(200);
    this.postions[0] = new Parent() {};
    this.postions[1] = new Parent() {};
    this.postions[2] = new Parent() {};

    this.postions[0].translateXProperty().bind(x.subtract(this.translateXOne));
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

    this.postions[2].translateXProperty().bind(x.add(this.translateXOne).add(50));
    this.postions[2].translateYProperty().bind(y.add(-2));
    this.postions[2].translateZProperty().bind(z);
    this.postions[2].getTransforms().add(new Rotate(xr, Rotate.X_AXIS));
    this.postions[2].getTransforms().add(new Rotate(yr, Rotate.Y_AXIS));
    this.postions[2].getTransforms().add(new Rotate(zr + 30, Rotate.Z_AXIS));

  }

  public synchronized void resetPostions() {

    double maxMainAxisLength;
    double mainAxisLength;

    if (this.cards.length > 0) {
      maxMainAxisLength = this.cards[0].card.getImage().getFitWidth() * 2;
      mainAxisLength = this.cards[0].getScene().getWidth() / 2;
      if (mainAxisLength > maxMainAxisLength) {
        mainAxisLength = maxMainAxisLength;
      }
      this.translateXOne.set(mainAxisLength / 2);
    }

  }


  /**
   * Add a card to this trick.
   * 
   * @param card Card to be added to this trick.
   * @return A parent which functions as a container for all relevant positioning informations.
   */
  public synchronized Parent add(GuiCard card) {
    if (index > 2) {
      for (GuiCard c : this.cards) {
        c.setVisible(false);
      }

      this.cards = new GuiCard[3];
      this.index = 0;
    }

    this.cards[index] = card;

    int i = index;
    Timeline tl = new Timeline();
    tl.getKeyFrames().add(new KeyFrame(Matchfield.animationTime.add(Duration.millis(20)), e -> {

      Affine newTr = new Affine(card.getTransforms().get(0));
      card.setTranslateX(newTr.getTx());
      card.setTranslateY(newTr.getTy());
      card.setTranslateZ(newTr.getTz());
      newTr.setTx(0);
      newTr.setTy(0);
      newTr.setTz(0);

      card.getTransforms().clear();
      card.getTransforms().add(newTr);

      card.translateXProperty().bind(this.postions[i].translateXProperty());
      card.translateYProperty().bind(this.postions[i].translateYProperty());
      card.translateZProperty().bind(this.postions[i].translateZProperty());
    }));
    tl.play();

    return this.postions[index++];
  }

  public synchronized void clear() {

    for (GuiCard c : this.cards) {
      c.setVisible(false);
    }

    this.cards = new GuiCard[3];
    this.index = 0;

  }

}
