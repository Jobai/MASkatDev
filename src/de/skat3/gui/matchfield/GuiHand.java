package de.skat3.gui.matchfield;


import de.skat3.gamelogic.Card;
import java.util.List;
import java.util.UUID;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.util.Duration;

/**
 * @author Aljoscha Domonell
 *
 */
public class GuiHand extends Parent {

  private ObservableList<GuiCard> cards;

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
  public GuiHand(double x, double y, double z, double xr, double yr, double zr,
      List<GuiCard> cards) {
    this.cards = FXCollections.observableArrayList();
    this.setTranslateX(x);
    this.setTranslateY(y);
    this.setTranslateZ(z);
    this.getTransforms().add(new Rotate(xr, Rotate.X_AXIS));
    this.getTransforms().add(new Rotate(yr, Rotate.Y_AXIS));
    this.getTransforms().add(new Rotate(zr, Rotate.Z_AXIS));

    this.cards.addAll(cards);

    this.getChildren().addAll(cards);
    this.refreshPositions();
  }

  /**
   * ASD.
   * 
   * @param card ASD.
   * @return
   */
  public GuiCard getGuiCard(Card card) {

    for (GuiCard c : this.cards) {
      if (c.card.equals(card)) {
        return c;
      }
    }

    return null;
  }

  /**
   * Add a card to this hand.
   * 
   * @param newCard GuiCard to add.
   */
  public void add(GuiCard newCard) {

    // Order of cards are important
    this.cards.add(newCard);
    this.raiseCard(newCard, true, true, true, false);
    this.getChildren().add(newCard);

    this.refreshPositions();
  }

  /**
   * Remove a card from this hand.
   * 
   * @param oldCard Card to remove.
   */
  public void remove(GuiCard oldCard) {
    this.cards.remove(oldCard);
    this.getChildren().remove(oldCard);
    this.refreshPositions();
  }

  /**
   * Get all the cards in this hand. This list can not be modified.
   * 
   * @return A unmodifiableObservableList.
   */
  public ObservableList<GuiCard> getCards() {
    return FXCollections.unmodifiableObservableList(this.cards);
  }

  private void refreshPositions() {
    if (this.getChildren().isEmpty()) {
      return;
    }
    Parent[] newPositions = caculateCardPostions(this.getChildren().size(),
        this.cards.get(0).card.view.getFitWidth(), 0, 0, 0);

    Duration time = Matchfield.animationTime;

    int i = 0;
    for (Node child : this.getChildren()) {

      GuiCard card = (GuiCard) child;

      TranslateTransition cordsAni = new TranslateTransition();
      cordsAni.setNode(card);
      cordsAni.setDuration(time);
      cordsAni.setFromX(card.getTranslateX());
      cordsAni.setFromY(card.getTranslateY());
      cordsAni.setToX(newPositions[i].getTranslateX());
      cordsAni.setToY(newPositions[i].getTranslateY());
      cordsAni.setToZ(newPositions[i].getTranslateZ());
      cordsAni.play();

      Timeline timeline = new Timeline();
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

      timeline.play();

      i++;
    }
  }

  /**
   * Moves the card to the postions of the targetPos Parent.
   * 
   * @param card Card to move.
   * @param targetPos Parent from which the transitions and rotations are getted.
   * 
   */
  public void moveCardAndRemove(GuiCard card, Parent targetPos) {
    Duration time = Matchfield.animationTime;

    TranslateTransition cordsAni = new TranslateTransition();
    cordsAni.setNode(card);
    cordsAni.setDuration(time);
    cordsAni.setToX(targetPos.getTranslateX() - this.getTranslateX());
    cordsAni.setToY(targetPos.getTranslateY() - this.getTranslateY());
    cordsAni.setToZ(targetPos.getTranslateZ() - this.getTranslateZ());
    cordsAni.play();

    Timeline timeline = new Timeline();
    for (Transform tr : targetPos.getTransforms()) {
      if (tr.getClass().equals(Rotate.class)) {
        Rotate rotation = new Rotate();
        rotation.setAxis(((Rotate) tr).getAxis());
        card.getTransforms().add(rotation);
        double angle = ((Rotate) tr).getAngle();

        for (Transform tt : this.getTransforms()) {
          if (tt.getClass().equals(Rotate.class)) {
            if (((Rotate) tt).getAxis().equals(((Rotate) tr).getAxis())) {
              angle = angle - ((Rotate) tt).getAngle();
            }
          }
        }
        timeline.getKeyFrames()
            .add(new KeyFrame(time, new KeyValue(rotation.angleProperty(), angle)));
      }
    }
    timeline.getKeyFrames().add(new KeyFrame(time, e -> this.remove(card)));

    timeline.play();



    // Transform targetTrans = targetPos.getLocalToSceneTransform();
    //
    // RotateTransition rotateAniX = new RotateTransition();
    // rotateAniX.setNode(card);
    // rotateAniX.setDuration(Duration.millis(500));
    // rotateAniX.setAxis(Rotate.X_AXIS);
    // double xr = Math.toDegrees(Math.atan2(targetTrans.getMzy(), targetTrans.getMzz()));
    // rotateAniX.setToAngle(xr -
    // Math.toDegrees(Math.atan2(this.getLocalToSceneTransform().getMzy(),
    // this.getLocalToSceneTransform().getMzz())));
    // rotateAniX.play();
    //
    // RotateTransition rotateAniY = new RotateTransition();
    // rotateAniY.setNode(card);
    // rotateAniY.setDuration(Duration.millis(500));
    // rotateAniY.setAxis(Rotate.Y_AXIS);
    // double yr = 90 - Math.toDegrees(Math.atan2(targetTrans.getMxx(), targetTrans.getMxz()));
    // rotateAniY
    // .setToAngle(yr - 90 - Math.toDegrees(Math.atan2(this.getLocalToSceneTransform().getMxx(),
    // this.getLocalToSceneTransform().getMxz())));
    // // rotateAniY.play();
    //
    // RotateTransition rotateAniZ = new RotateTransition();
    // rotateAniZ.setNode(card);
    // rotateAniZ.setDuration(Duration.millis(500));
    // rotateAniZ.setAxis(Rotate.Z_AXIS);
    // double zr = Math.toDegrees(Math.atan2(-targetTrans.getMxy(), targetTrans.getMxx()));
    // rotateAniZ.setToAngle(zr -
    // Math.toDegrees(Math.atan2(-this.getLocalToSceneTransform().getMxy(),
    // this.getLocalToSceneTransform().getMxx())));
    // // rotateAniZ.play();

  }

  /**
   * Raises or lowers a card when it is selected.
   * 
   */
  public void raiseCard(GuiCard card, boolean value, boolean fromUnderneath, boolean hoverPositon,
      boolean showAnimation) {
    Parent[] positions =
        caculateCardPostions(this.cards.size(), this.cards.get(0).card.view.getFitWidth(), 0, 0, 0);
    int cardIndex = this.cards.indexOf(card);

    TranslateTransition raiseCard = new TranslateTransition();
    raiseCard.setNode(card);
    raiseCard.setDuration(Matchfield.animationTime);

    double y = -100;
    if (hoverPositon) {
      y = -card.card.view.getFitHeight();
    }
    if (fromUnderneath) {
      y = -y;
    }
    double x = Math.tan(Math.toRadians(card.getRotate())) * Math.abs(y);

    if (showAnimation) {
      if (value) {
        raiseCard.setToX(positions[cardIndex].getTranslateX() + x);
        raiseCard.setToY(positions[cardIndex].getTranslateY() + y);
        raiseCard.play();
      } else {
        raiseCard.setToX(positions[cardIndex].getTranslateX());
        raiseCard.setToY(positions[cardIndex].getTranslateY());
        raiseCard.play();
      }
    } else {
      if (value) {
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
   * 
   * @return Array of postions and angles. int[n][0] = x cordinate. int[n][1] y cordiante. int[n][3]
   *         angle.
   */
  private static Parent[] caculateCardPostions(int size, double objectWidth, double xoffset,
      double yoffset, double zoffset) {
    double maxHandWidth = 1000;
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
      double r = 90 - (360 / (Math.PI * 2)) * Math.atan(y / x);
      if (r > 90) {
        r = r - 180;
      }
      pos.getTransforms().add(new Rotate(r, Rotate.Z_AXIS));
      y = y + Math.abs(y - halfMinorAxisLegth * 2) * 2;
      pos.setTranslateX(x + xoffset);
      pos.setTranslateY(y + yoffset - halfMinorAxisLegth * 2);
      pos.setTranslateZ(1 * i + zoffset);
      postions[i] = pos;
    }
    return postions;
  }

}
