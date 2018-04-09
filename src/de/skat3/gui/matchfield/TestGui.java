package de.skat3.gui.matchfield;


import java.util.ArrayList;
import java.util.List;
import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author adomonel
 *
 */
public class TestGui extends Application {

  /*
   * (non-Javadoc)
   * 
   * @see javafx.application.Application#start(javafx.stage.Stage)
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

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

    Pane p = new Pane();
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


    GuiCard trick1 = new GuiCard(null);
    trick1.setTranslateX(900);
    trick1.setTranslateY(900);
    trick1.setTranslateZ(500);
    trick1.getTransforms().add(new Rotate(-180 + 90, Rotate.X_AXIS));
    trick1.getTransforms().add(new Rotate(0, Rotate.Y_AXIS));
    trick1.getTransforms().add(new Rotate(0, Rotate.Z_AXIS));
    GuiCard trick2 = new GuiCard(null);
    trick2.setTranslateX(900);
    trick2.setTranslateY(899);
    trick2.setTranslateZ(500);
    trick2.getTransforms().add(new Rotate(-180 + 90, Rotate.X_AXIS));
    trick2.getTransforms().add(new Rotate(0, Rotate.Y_AXIS));
    trick2.getTransforms().add(new Rotate(20, Rotate.Z_AXIS));

    // Trick trick = new Trick(900.0, 900.0, 500.0, -90, 0.0, 0.0);

    List<GuiCard> l1 = new ArrayList<GuiCard>();
    List<GuiCard> l2 = new ArrayList<GuiCard>();
    List<GuiCard> l3 = new ArrayList<GuiCard>();

    int n = 6;
    for (int i = 0; i < n; i++) {
      l1.add(new GuiCard(null));
      l2.add(new GuiCard(null));
      l3.add(new GuiCard(null));
    }

    GuiHand h1 = new GuiHand(900, 820, -200, -20, 0, 0, l1);
    GuiHand h2 = new GuiHand(0, 820, 1000, 0, -55, 0, l2);
    GuiHand h3 = new GuiHand(1800, 820, 1000, 0, 55, 0, l3);

    p.getChildren().addAll(h1, h2, h3, trick1, trick2);

    PerspectiveCamera cam = new PerspectiveCamera();
    cam.setTranslateY(-100);
    cam.getTransforms().add(new Rotate(-30, Rotate.X_AXIS));

    scene.setCamera(cam);
    primaryStage.show();

    this.sym(h1, trick2);

  }

  private void sym(GuiHand h1, GuiCard trick2) {

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
        e -> h1.moveCardAndRemove(h1.getCards().get(2), trick2)));


    timer.getKeyFrames().add(new KeyFrame(Duration.seconds(i++ + 1), e -> {
      GuiCard card = h1.getCards().get(2);
      double xxx = card.localToScene(card.getBoundsInLocal()).getMinX();
      double yyy = card.localToScene(card.getBoundsInLocal()).getMinY();
      System.out.println(xxx);
      System.out.println(yyy);

      double xxx2 = trick2.localToScene(trick2.getBoundsInLocal()).getMinX();
      double yyy2 = trick2.localToScene(trick2.getBoundsInLocal()).getMinY();
      System.out.println(xxx2);
      System.out.println(yyy2);

      System.out.println(yyy2 - yyy);

      System.out.println("card: " + card.getTranslateY());
      System.out.println("p card: " + card.getParent().getTranslateY());
      System.out.println("trick: " + trick2.getTranslateY());
      System.out.println("p trick: " + trick2.getParent().getTranslateY());

      System.out.println("card l y: " + card.getLayoutY());
      System.out.println("p card l y: " + card.getParent().getLayoutY());
      System.out.println("trick l y: " + trick2.getLayoutY());
      System.out.println("p trick l y: " + trick2.getParent().getLayoutY());

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
