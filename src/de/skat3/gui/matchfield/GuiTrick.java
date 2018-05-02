package de.skat3.gui.matchfield;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.transform.Affine;
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
  private int index;
  private Parent[] postions;
  private DoubleProperty translateXOne;

  /**
   * Creates a trick on the specified postion.
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

  /**
   * Add a card to this trick.
   * 
   * @param card Card to be added to this trick.
   * @return A parent which functions as a container for all relevant positioning informations.
   */
  public synchronized Parent add(GuiCard card) {
    if (index > 2) {
      this.clear();
    }

    this.cards[index] = card;

//    int i = index;
//    Timeline tl = new Timeline();
//    tl.getKeyFrames().add(new KeyFrame(Matchfield.animationTime.add(Duration.millis(20)), e -> {
//
//      Affine newTr = new Affine(card.getTransforms().get(0));
//      card.setTranslateX(newTr.getTx());
//      card.setTranslateY(newTr.getTy());
//      card.setTranslateZ(newTr.getTz());
//      newTr.setTx(0);
//      newTr.setTy(0);
//      newTr.setTz(0);
//
//      card.getTransforms().clear();
//      card.getTransforms().add(newTr);
//
//      card.translateXProperty().bind(this.postions[i].translateXProperty());
//      card.translateYProperty().bind(this.postions[i].translateYProperty());
//      card.translateZProperty().bind(this.postions[i].translateZProperty());
//    }));
//    tl.play();

    if (index == 2) {
      GuiCard old = this.cards[index];
      Timeline clearDelay = new Timeline();
      clearDelay.getKeyFrames().add(new KeyFrame(Duration.seconds(2.5), e -> {
        try {
          if (this.index == 3 && old.getCard().equals(this.cards[2].getCard())) {
            this.clear();
          }
        } catch (Exception e1) {
          e1.getMessage();
        }
      }));
      clearDelay.play();
    }

    return this.postions[index++];
  }


  /**
   * Clears this trick form all cards.
   * 
   */
  public synchronized void clear() {
    for (GuiCard c : this.cards) {
      c.setVisible(false);
    }

    this.cards = new GuiCard[3];
    this.index = 0;
  }

  /**
   * Recaluculates postion and rotations of all cards in this hand. Plays Animations if anything
   * need to be changed.
   */
  public synchronized void resetPostions() {
    double maxMainAxisLength;
    double mainAxisLength;

    if (this.cards[0] != null) {
      maxMainAxisLength = this.cards[0].getCard().getImage().getFitWidth() * 2;
      mainAxisLength = this.cards[0].getScene().getWidth() / 2;
      if (mainAxisLength > maxMainAxisLength) {
        mainAxisLength = maxMainAxisLength;
      }
      this.translateXOne.set(mainAxisLength / 2);
    }
  }

}
