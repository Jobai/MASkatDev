package de.skat3.gui.matchfield;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.CardDeck;
import de.skat3.gamelogic.Suit;
import de.skat3.gamelogic.Value;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author adomonel
 *
 */
public class TestGui extends Application {
  Stage mainStage;
  Pane p;

  /*
   * (non-Javadoc)
   * 
   * @see javafx.application.Application#start(javafx.stage.Stage)
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    this.mainStage = primaryStage;
    this.p = new Pane();
    // Slider xs = new Slider();
    // xs.setMax(10000);
    // Slider ys = new Slider();
    // ys.setMax(10000);
    // Slider zs = new Slider();
    // zs.setMax(10000);
    //
    // FlowPane fp = new FlowPane();
    // fp.getChildren().addAll(xs, ys, zs);
    // SubScene sub = new SubScene(fp, 300, 500);
    // sub.setTranslateX(1000);
    // sub.setTranslateY(200);
    // p.getChildren().add(sub);
    Scene scene = new Scene(p, 1800, 1000, true, SceneAntialiasing.BALANCED);
    primaryStage.setScene(scene);

    p.setPrefSize(1800, 1000);

    // Box x = new Box(100000, 10, 10);
    // x.setMaterial(new PhongMaterial(Color.GREEN));
    // Box y = new Box(10, 100000, 10);
    // y.setMaterial(new PhongMaterial(Color.BLUE));
    // Box z = new Box(10, 10, 100000);
    // z.setMaterial(new PhongMaterial(Color.RED));
    // // p.getChildren().addAll(x, y, z);



    Parent trick1 = new Parent() {};
    trick1.getTransforms().add(new Rotate(-180 + 90, Rotate.X_AXIS));
    trick1.getTransforms().add(new Rotate(0, Rotate.Y_AXIS));
    trick1.getTransforms().add(new Rotate(-30, Rotate.Z_AXIS));
    trick1.setTranslateX(800 - 150);
    trick1.setTranslateY(900);
    trick1.setTranslateZ(500 - 100);
    Parent trick2 = new Parent() {};
    trick2.getTransforms().add(new Rotate(-180 + 90, Rotate.X_AXIS));
    trick2.getTransforms().add(new Rotate(0, Rotate.Y_AXIS));
    trick2.getTransforms().add(new Rotate(0, Rotate.Z_AXIS));
    trick2.setTranslateX(800);
    trick2.setTranslateY(899);
    trick2.setTranslateZ(500);
    Parent trick3 = new Parent() {};
    trick3.setTranslateX(800 + 200);
    trick3.setTranslateY(898);
    trick3.setTranslateZ(500);
    trick3.getTransforms().add(new Rotate(-180 + 90, Rotate.X_AXIS));
    trick3.getTransforms().add(new Rotate(0, Rotate.Y_AXIS));
    trick3.getTransforms().add(new Rotate(30, Rotate.Z_AXIS));

    // Trick trick = new Trick(900.0, 900.0, 500.0, -90, 0.0, 0.0);

    GuiHand h1 = new GuiHand(primaryStage.widthProperty().divide(2).multiply(0.9), 720, -200, -20,
        0, 0, null);
    GuiHand h2 = new GuiHand(primaryStage.widthProperty().multiply(0), 820, 1200, 0, -55, 0, null);
    GuiHand h3 = new GuiHand(primaryStage.widthProperty().multiply(1), 820, 1200, 0, 55, 0, null);

    p.getChildren().addAll(h1, h2, h3);
    // p.getChildren().addAll(h1, h2, h3, trick1, trick2);

    PerspectiveCamera cam = new PerspectiveCamera();
    cam.setTranslateY(-100);
    cam.getTransforms().add(new Rotate(-30, Rotate.X_AXIS));

    scene.setCamera(cam);
    primaryStage.show();

    this.video(h1, h2, h3, trick1, trick2, trick3);

  }


  private void video(GuiHand h1, GuiHand h2, GuiHand h3, Parent p1, Parent p2, Parent p3) {
    Timeline timer = new Timeline();
    int time = 2;
    CardDeck d = new CardDeck();
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {

      List<GuiCard> l1 = new ArrayList<GuiCard>();
      List<GuiCard> l2 = new ArrayList<GuiCard>();
      List<GuiCard> l3 = new ArrayList<GuiCard>();

      int n = 6;
      int j = 0;
      for (int i = 0; i < n; i++) {
        l1.add(new GuiCard(d.getCards()[j++]));
        l2.add(new GuiCard(d.getCards()[j++]));
        l3.add(new GuiCard(d.getCards()[j++]));
      }

      h1.addAll(l1);
      h2.addAll(l2);
      h3.addAll(l3);


    }));

    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      h1.remove(h1.getCards().get(1));
    }));
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      h2.remove(h2.getCards().get(4));
    }));
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      h3.remove(h3.getCards().get(2));
    }));

    Card herzass = new Card(Suit.CLUBS, Value.ACE);
    Card herzdame = new Card(Suit.CLUBS, Value.QUEEN);
    Card herz10 = new Card(Suit.CLUBS, Value.TEN);

    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      h1.add(new GuiCard(herzass));
    }));
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      h2.add(new GuiCard(herzdame));
    }));
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      h3.add(new GuiCard(herz10));
    }));



    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      h1.raiseCard(h1.getCards().get(1), true, false, false, true);
    }));
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      h1.raiseCard(h1.getCards().get(1), true, false, false, true);
    }));
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      h1.raiseCard(h1.getCards().get(1), false, false, false, true);
    }));
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      h1.raiseCard(h1.getCards().get(3), true, false, false, true);
    }));
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      h1.raiseCard(h1.getCards().get(3), true, false, true, true);
    }));
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      h1.raiseCard(h1.getCards().get(3), true, false, false, true);
    }));
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      h1.raiseCard(h1.getCards().get(3), false, false, false, true);
    }));

    List<GuiCard> l1 = new ArrayList<GuiCard>();

    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      GuiCard c1 = h1.getCards().get(3);
      l1.add(c1);
      h1.moveCardAndRemove(c1, p1, this.p);
    }));


    time++;
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      GuiCard c2 = h2.getCards().get(3);
      l1.add(c2);
      h2.moveCardAndRemove(c2, p2, this.p);
    }));

    time++;
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      GuiCard c3 = h3.getCards().get(3);
      l1.add(c3);
      h3.moveCardAndRemove(c3, p3, this.p);
    }));


    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(time++), e -> {
      Parent trick1 = new Parent() {};
      trick1.setTranslateX(900 + 350);
      trick1.setTranslateY(900);
      trick1.setTranslateZ(2000);
      trick1.getTransforms().add(new Rotate(-180 + 90, Rotate.X_AXIS));


      GuiHand.moveCard(l1.get(0), trick1);
      GuiHand.moveCard(l1.get(1), trick1);
      GuiHand.moveCard(l1.get(2), trick1);

    }));

    timer.play();

  }

  private void sym(GuiHand h1, Parent p1, Parent p2, Parent p3) {

    Timeline timer = new Timeline();
    int i = 1;
    // Timeline timera = new Timeline();
    // timera.getKeyFrames().add(new KeyFrame(Duration.seconds(i++), e -> h1.add(new GuiCard())));
    // timera.play();
    //
    // Timeline timerab = new Timeline();
    // timerab.getKeyFrames().add(new KeyFrame(Duration.seconds(i++), e -> h1.add(new GuiCard())));
    // timerab.play();

    // int index = 0;

    // Timeline timer = new Timeline();
    // timer.getKeyFrames().add(new KeyFrame(Duration.seconds(i++),
    // e -> h1.raiseCard(h1.getCards().get(index), true, false, false, true)));
    // timer.play();
    //
    // Timeline timer2 = new Timeline();
    // timer2.getKeyFrames().add(new KeyFrame(Duration.seconds(i++),
    // e -> h1.raiseCard(h1.getCards().get(index), false, false, false, true)));
    // timer2.play();
    //
    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(i++),
        e -> h1.raiseCard(h1.getCards().get(2), true, false, true, true)));

    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(i++),
        e -> h1.moveCardAndRemove(h1.getCards().get(2), p1, this.p)));


    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(i++ + 1), e -> {
      GuiCard card = h1.getCards().get(2);
      double xxx = card.localToScene(card.getBoundsInLocal()).getMinX();
      double yyy = card.localToScene(card.getBoundsInLocal()).getMinY();
      System.out.println(xxx);
      System.out.println(yyy);

      double xxx2 = p1.localToScene(p1.getBoundsInLocal()).getMinX();
      double yyy2 = p1.localToScene(p1.getBoundsInLocal()).getMinY();
      System.out.println(xxx2);
      System.out.println(yyy2);

      System.out.println(yyy2 - yyy);

      System.out.println("card: " + card.getTranslateY());
      System.out.println("p card: " + card.getParent().getTranslateY());
      System.out.println("trick: " + p1.getTranslateY());
      System.out.println("p trick: " + p1.getParent().getTranslateY());

      System.out.println("card l y: " + card.getLayoutY());
      System.out.println("p card l y: " + card.getParent().getLayoutY());
      System.out.println("trick l y: " + p1.getLayoutY());
      System.out.println("p trick l y: " + p1.getParent().getLayoutY());

      Box b = new Box();

    }));

    // timer.getKeyFrames()
    // .add(new KeyFrame(Duration.seconds(i++ + 1), e -> h1.remove(h1.getCards().get(2))));

    timer.play();


  }

  public static void main(String[] args) {
    launch();
  }

}
