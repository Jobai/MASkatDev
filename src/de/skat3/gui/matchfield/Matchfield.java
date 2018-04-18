package de.skat3.gui.matchfield;


import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Player;
import de.skat3.main.LocalGameState;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * View class of the Matchfield.
 * 
 * @author adomonel
 *
 */
public class Matchfield {

  private LocalGameState gameState;

  private InGameController controller;

  protected Scene scene;
  protected Pane table;
  protected GuiHand playerHand;
  protected GuiHand leftHand;
  protected GuiHand rightHand;
  protected GuiTrick trick;
  public static Duration animationTime = Duration.seconds(0.2);

  /**
   * Links a LocalGameState to this Matchfield.
   * 
   * @param gameState The gameState that will be linked.
   */
  public Matchfield(LocalGameState gameState) {

    // this.gameState = gameState;

    // this.controller = new InGameController(this.gameState, this);

    double sceneWidth = 1280;
    double sceneHeight = 720;
    this.table = new Pane();
    this.scene = new Scene(this.table, sceneWidth, sceneHeight, false, SceneAntialiasing.BALANCED);

    this.iniComponents();

    PerspectiveCamera cam = new PerspectiveCamera();
    cam.setTranslateY(-100);
    cam.getTransforms().add(new Rotate(-30, Rotate.X_AXIS));
    this.scene.setCamera(cam);
  }

  /**
   * 
   */
  private void iniComponents() {
    // Box x = new Box(100000, 10, 10);
    // x.setMaterial(new PhongMaterial(Color.GREEN));
    // Box y = new Box(10, 100000, 10);
    // y.setMaterial(new PhongMaterial(Color.BLUE));
    // Box z = new Box(10, 10, 100000);
    // z.setMaterial(new PhongMaterial(Color.RED));
    // this.table.getChildren().addAll(x, y, z);


    // y = 720 z = -200
    // y = 820
    this.playerHand = new GuiHand(this.table.getScene().widthProperty().divide(2.3),
        this.table.getScene().heightProperty().add(-200),
        this.table.getScene().heightProperty().multiply(0).add(-100), -20, 0, 0, null);



    this.leftHand = new GuiHand(this.table.getScene().widthProperty().multiply(0),
        this.table.getScene().heightProperty().multiply(0.7),
        this.table.getScene().heightProperty().multiply(0).add(1200), 0, -55, 0, null);

    this.rightHand = new GuiHand(this.table.getScene().widthProperty().multiply(1),
        this.table.getScene().heightProperty().multiply(0.7),
        this.table.getScene().heightProperty().multiply(0).add(1200), 0, 55, 0, null);

    this.trick = new GuiTrick(this.getScene().widthProperty().divide(2.5),
        this.getScene().heightProperty().divide(1.4),
        this.getScene().widthProperty().multiply(0).add(400), -80, 0, 0);

    this.table.getChildren().addAll(this.playerHand, this.leftHand, this.rightHand);

    this.table.setOnMouseClicked(event -> {
      // System.out.println(this.table.getScene().getWidth() + " : " + event.getPickResult());

      Node node = event.getPickResult().getIntersectedNode();
      try {
        if (node.getParent().getParent().equals(this.playerHand)) {
          GuiCard card = (GuiCard) node.getParent();
          this.playCard(this.playerHand, card);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    this.table.widthProperty().addListener(e -> {
      this.playerHand.resetPositions();
      this.leftHand.resetPositions();
      this.rightHand.resetPositions();
      this.trick.resetPostions();
    });

  }

  /**
   * @param playerHand2
   * @param card
   */
  protected synchronized void playCard(GuiHand hand, GuiCard card) {
    hand.moveCardAndRemove(card, this.trick.add(card), this.table);
  }

  public InGameController getController() {
    return this.controller;
  }

  public GuiHand getHand(Player owner) {
    try {
      if (this.playerHand.getOwner().equals(owner)) {
        return this.playerHand;
      }
      if (this.leftHand.getOwner().equals(owner)) {
        return this.leftHand;
      }
      if (this.rightHand.getOwner().equals(owner)) {
        return this.rightHand;
      }
      throw new Exception("Player does not own a GuiHand.");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public Scene getScene() {
    return this.scene;
  }

  private static ObservableList<GuiCard> convertCardList(ObservableList<Card> list) {
    ObservableList<GuiCard> guiList = FXCollections.observableArrayList();

    for (Card card : list) {
      guiList.add(new GuiCard(card));
    }
    return guiList;

  }
}
