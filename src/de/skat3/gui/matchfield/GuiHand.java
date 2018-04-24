package de.skat3.gui.matchfield;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Player;
import de.skat3.main.SkatMain;
import java.util.Collection;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.util.Duration;

/**
 * View class of a hand.
 * 
 * @author adomonel
 *
 */
public class GuiHand extends Parent {

  private ObservableList<GuiCard> cards = FXCollections.observableArrayList();
  Player owner;

  /**
   * Gui represetation of a hand of cards.
   * 
   * @param x Translate x.
   * @param y Translate y.
   * @param z Translate z.
   * @param xr Rotate x.
   * @param yr Rotate y.
   * @param zr Rotate z.
   */
  public GuiHand(DoubleBinding x, DoubleBinding y, DoubleBinding z, double xr, double yr, double zr,
      List<GuiCard> cards) {
    this.cards = FXCollections.observableArrayList();
    // this.setTranslateX(x);
    this.translateXProperty().bind(x);
    this.translateYProperty().bind(y);
    this.translateZProperty().bind(z);
    this.getTransforms().add(new Rotate(xr, Rotate.X_AXIS));
    this.getTransforms().add(new Rotate(yr, Rotate.Y_AXIS));
    this.getTransforms().add(new Rotate(zr, Rotate.Z_AXIS));

    if (cards != null) {
      this.cards.addAll(cards);
      this.getChildren().addAll(cards);
      this.resetPositions();
    }
  }

  protected synchronized void resetPositions() {
    if (this.cards.isEmpty()) {
      return;
    }
    // double width = this.getScene().getWidth() / 7;
    // double height = width * 1.52821997106;

    Parent[] newPositions = caculateCardPostions(this.getChildren().size(), GuiCard.width, 0, 0, 0);

    Duration time = Matchfield.animationTime;

    if (this.cards.get(0).card.getValue() != null) {
      Hand h = new Hand();
      h.cards = new Card[this.cards.size()];
      int j = 0;
      for (GuiCard c : this.cards) {
        h.cards[j] = c.card;
        j++;
      }

      h.sort(SkatMain.lgs.contract);

      for (int z = 0; z < h.cards.length; z++) {
        GuiCard card = this.getGuiCard(h.cards[z]);
        GuiCard temp;
        int index = this.cards.indexOf(card);
        temp = this.cards.get(z);
        this.cards.set(z, card);
        this.cards.set(index, temp);
      }
    }

    int i = 0;
    for (GuiCard card : this.cards) {
      this.getChildren().remove(card);
      this.getChildren().add(card);

      card.card.getImage().setFitWidth(GuiCard.width);
      card.card.getImage().setFitHeight(GuiCard.heigth);

      TranslateTransition cordsAni = new TranslateTransition();
      cordsAni.setNode(card);
      cordsAni.setDuration(time);
      cordsAni.setToX(newPositions[i].getTranslateX());
      cordsAni.setToY(newPositions[i].getTranslateY());
      cordsAni.setToZ(newPositions[i].getTranslateZ());
      cordsAni.play();

      Timeline timeline = new Timeline();
      if (card.getTransforms().size() == 0) {
        for (Transform tr : newPositions[i].getTransforms()) {
          if (tr.getClass().equals(Rotate.class)) {
            Rotate rotation = new Rotate();
            rotation.setAxis(((Rotate) tr).getAxis());
            card.getTransforms().add(rotation);
            double angle = ((Rotate) tr).getAngle();

            timeline.getKeyFrames()
                .add(new KeyFrame(time, new KeyValue(rotation.angleProperty(), angle)));
          }
        }
      } else {
        for (Transform tr : newPositions[i].getTransforms()) {
          for (Transform t : card.getTransforms()) {
            if (tr.getClass().equals(Rotate.class) && t.getClass().equals(Rotate.class)) {
              if (((Rotate) tr).getAxis().equals(((Rotate) t).getAxis())) {
                double angle = ((Rotate) tr).getAngle();
                timeline.getKeyFrames()
                    .add(new KeyFrame(time, new KeyValue(((Rotate) t).angleProperty(), angle)));
              }
            }
          }
        }
      }
      timeline.play();
      i++;
    }

  }

  /**
   * Moves the card to the postions of the targetPos Parent.
   * 
   * @param card Card to move.
   * @param targetPos Parent of which the transitions and rotations are used.
   * 
   */
  public synchronized void moveCardAndRemove(GuiCard card, Parent targetPos, Pane root) {
    System.out.println(card);
    Transform t = card.getLocalToSceneTransform();
    Affine sourceTr = new Affine(t);
    sourceTr.getClass();

    this.remove(card);
    card.getTransforms().clear();
    card.setTranslateX(0);
    card.setTranslateY(0);
    card.setTranslateZ(0);

    card.getTransforms().add(sourceTr);

    root.getChildren().add(card);

    Duration time = Matchfield.animationTime;
    Timeline timeline = new Timeline();
    Transform tarTr = targetPos.getLocalToSceneTransform();

    timeline.getKeyFrames()
        .add(new KeyFrame(time, new KeyValue(sourceTr.txProperty(), tarTr.getTx())));
    timeline.getKeyFrames()
        .add(new KeyFrame(time, new KeyValue(sourceTr.tyProperty(), tarTr.getTy())));
    timeline.getKeyFrames()
        .add(new KeyFrame(time, new KeyValue(sourceTr.tzProperty(), tarTr.getTz())));
    timeline.getKeyFrames()
        .add(new KeyFrame(time, new KeyValue(sourceTr.mxxProperty(), tarTr.getMxx())));
    timeline.getKeyFrames()
        .add(new KeyFrame(time, new KeyValue(sourceTr.mxyProperty(), tarTr.getMxy())));
    timeline.getKeyFrames()
        .add(new KeyFrame(time, new KeyValue(sourceTr.mxzProperty(), tarTr.getMxz())));
    timeline.getKeyFrames()
        .add(new KeyFrame(time, new KeyValue(sourceTr.myxProperty(), tarTr.getMyx())));
    timeline.getKeyFrames()
        .add(new KeyFrame(time, new KeyValue(sourceTr.myyProperty(), tarTr.getMyy())));
    timeline.getKeyFrames()
        .add(new KeyFrame(time, new KeyValue(sourceTr.myzProperty(), tarTr.getMyz())));
    timeline.getKeyFrames()
        .add(new KeyFrame(time, new KeyValue(sourceTr.mzxProperty(), tarTr.getMzx())));
    timeline.getKeyFrames()
        .add(new KeyFrame(time, new KeyValue(sourceTr.mzyProperty(), tarTr.getMzy())));
    timeline.getKeyFrames()
        .add(new KeyFrame(time, new KeyValue(sourceTr.mzzProperty(), tarTr.getMzz())));

    timeline.play();
  }

  /**
   * Changes the translation values of the card to a new postion based on the settings of this
   * method.
   * 
   * @param card The Card to be moved.
   * @param raise When true the card will be raised/lowered. When false the card will be set to its
   *        normal postion.
   * @param underneathPos When true the card will be lowerd. When false the card will be raised.
   * @param hoverPositon When true the card will placed further away from the hand.
   * @param showAnimation When true animations are played while changing postions.
   * @param duration Duration of the animation played. If null duration will be set to the standard
   *        value stored in Matchfield.
   */
  public void raiseCard(GuiCard card, boolean raise, boolean underneathPos, boolean hoverPositon,
      boolean showAnimation, Duration duration) {
    if (duration == null) {
      duration = Matchfield.animationTime;
    }
    Parent[] positions = caculateCardPostions(this.cards.size(), GuiCard.width, 0, 0, 0);
    int cardIndex = this.cards.indexOf(card);

    double y = -50;
    if (hoverPositon) {
      y = -card.card.getImage().getFitHeight();
    }
    if (underneathPos) {
      y = -y;
    }
    double x = Math.tan(Math.toRadians(card.getRotate())) * Math.abs(y);

    if (showAnimation) {
      TranslateTransition raiseCard = new TranslateTransition();
      raiseCard.setNode(card);
      raiseCard.setDuration(duration);
      if (raise) {
        raiseCard.setToX(positions[cardIndex].getTranslateX() + x);
        raiseCard.setToY(positions[cardIndex].getTranslateY() + y);
        raiseCard.play();
      } else {
        raiseCard.setToX(positions[cardIndex].getTranslateX());
        raiseCard.setToY(positions[cardIndex].getTranslateY());
        raiseCard.play();
      }
    } else {
      if (raise) {
        card.setTranslateX(positions[cardIndex].getTranslateX() + x);
        card.setTranslateY(positions[cardIndex].getTranslateY() + y);
      } else {
        card.setTranslateX(positions[cardIndex].getTranslateX());
        card.setTranslateY(positions[cardIndex].getTranslateY());
      }
    }

  }

  /**
   * Calculates the right postions and angles of all the cards in this hand.
   * 
   * @param size How many postions to return.
   * @return Array of postions and angles. int[n][0] = x cordinate. int[n][1] y cordiante. int[n][3]
   *         angle.
   */
  private Parent[] caculateCardPostions(int size, double objectWidth, double xoffset,
      double yoffset, double zoffset) {
    // double maxHandWidth = 1000;
    double maxHandWidth = this.getScene().getWidth() / 2;
    if (maxHandWidth > objectWidth * (size - 1) / 1.3) {
      maxHandWidth = objectWidth * (size - 1) / 1.3;
    }
    double objectGap = 0;
    if (size > 1) {
      objectGap = maxHandWidth / (size - 1);
    } else {
      objectGap = maxHandWidth / 2;
    }
    double halfMainAxisLength = 2000;
    double halfMinorAxisLegth = 1500;
    double firstObjectPos = (halfMainAxisLength * 2 - maxHandWidth) / 2;
    Parent[] postions = new Parent[size];
    for (int i = 0; i < size; i++) {
      double x;
      double y;
      x = firstObjectPos + objectGap * i - halfMainAxisLength;
      y = (halfMinorAxisLegth / halfMainAxisLength)
          * Math.sqrt(Math.pow(halfMainAxisLength, 2) - Math.pow(x, 2)) + halfMinorAxisLegth;
      Parent pos = new Parent() {};
      // Old angle that points to the middel of the elipse
      double r = 90 - (360 / (Math.PI * 2)) * Math.atan(y / x);
      // double r = ((30 / size) * i);

      if (r > 90) {
        r = r - 180;
      }
      pos.getTransforms().add(new Rotate(r, Rotate.Z_AXIS));
      y = y + Math.abs(y - halfMinorAxisLegth * 2) * 2;
      pos.setTranslateX(x + xoffset);
      pos.setTranslateY(y + yoffset - halfMinorAxisLegth * 2);
      pos.setTranslateZ(-1 * i + zoffset);
      postions[i] = pos;
    }

    return postions;
  }

  /**
   * Add a card to this hand.
   * 
   * @param newCard GuiCard to add.
   */
  public synchronized void add(GuiCard newCard) {

    // Order of cards are important
    newCard.translateXProperty().unbind();
    newCard.translateYProperty().unbind();
    newCard.translateZProperty().unbind();
    newCard.getTransforms().clear();
    this.cards.add(newCard);
    this.raiseCard(newCard, true, true, true, false, null);
    this.getChildren().add(newCard);
    this.resetPositions();
  }

  public synchronized void add(int index, GuiCard newCard, boolean animation) {

    // Order of cards are important
    newCard.translateXProperty().unbind();
    newCard.translateYProperty().unbind();
    newCard.translateZProperty().unbind();
    newCard.getTransforms().clear();
    this.cards.add(index, newCard);
    if (animation) {
      this.raiseCard(newCard, true, true, true, false, null);
    }
    this.getChildren().add(index, newCard);
    this.resetPositions();
  }

  /**
   * Add all cards to this hand.
   * 
   * @param cards Cards to be added.
   */
  public void addAll(GuiCard... cards) {
    for (GuiCard card : cards) {
      this.add(card);
    }
  }

  /**
   * Add all cards to this hand.
   * 
   * @param cards Cards to be added.
   */
  public void addAll(Collection<GuiCard> cards) {
    for (GuiCard card : cards) {
      this.add(card);
    }
  }

  /**
   * Add all cards to this hand.
   * 
   * @param cards Cards to be added.
   */
  public void addAll(Card[] cards) {
    for (Card card : cards) {
      this.add(new GuiCard(card));
    }
  }

  /**
   * Remove a card from this hand.
   * 
   * @param oldCard Card to remove.
   */
  public synchronized void remove(GuiCard oldCard) {
    this.cards.remove(oldCard);
    this.getChildren().remove(oldCard);
    this.resetPositions();
  }

  public synchronized void remove(int index) {
    this.cards.remove(index);
    this.getChildren().remove(index);
    this.resetPositions();
  }

  /**
   * Clears this hand from all cards.
   * 
   */
  public void clear() {
    for (GuiCard c : this.cards) {
      this.remove(c);
    }
  }

  public void setPlayer(Player owner) {
    this.owner = owner;
  }

  public Player getOwner() {
    return this.owner;
  }

  /**
   * Get all the cards in this hand. This list can not be modified.
   * 
   * @return A unmodifiableObservableList.
   */
  public ObservableList<GuiCard> getCards() {
    return FXCollections.unmodifiableObservableList(this.cards);
  }

  /**
   * ASD.
   * 
   * @param card ASD.
   * @return
   */
  public GuiCard getGuiCard(Card card) {
    for (GuiCard c : this.cards) {
      try {
        if (c.card.equals(card)) {
          return c;
        }
      } catch (Exception e) {
        System.err.println("Equals error in getGuiCard");
      }
    }

    return null;
  }

  /**
   * ASD.
   */
  public String toString() {
    StringBuffer s = new StringBuffer();
    for (Node child : this.getChildren()) {
      GuiCard c = (GuiCard) child;
      s.append(c.toString());

      s.append("\n");
    }
    return s.toString();
  }

}
