package de.skat3.gui.matchfield;


import de.skat3.gamelogic.Card;
import de.skat3.main.LocalGameState;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.Pane;
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

    this.table = new Pane();
    double sceneWidth = 1800;
    double sceneHeight = 1000;
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
    this.playerHand = new GuiHand(this.table.getScene().widthProperty().divide(2).multiply(0.9),
        720, -200, -20, 0, 0, null);
    this.leftHand =
        new GuiHand(this.table.getScene().widthProperty().multiply(0), 820, 1200, 0, -55, 0, null);
    this.rightHand =
        new GuiHand(this.table.getScene().widthProperty().multiply(1), 820, 1200, 0, 55, 0, null);

    this.trick = new GuiTrick(this.getScene().widthProperty().divide(2.5),
        this.getScene().heightProperty().divide(1.4), 500, -90, 0, 0);

    this.table.getChildren().addAll(this.playerHand, this.leftHand, this.rightHand);

    this.table.setOnMouseClicked(event -> {
      System.out.println(" AS: " + event.getPickResult());
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

  }

  /**
   * @param playerHand2
   * @param card
   */
  private void playCard(GuiHand hand, GuiCard card) {
    hand.moveCardAndRemove(card, this.trick.add(card), this.table);
  }

  public InGameController getController() {
    return this.controller;
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
