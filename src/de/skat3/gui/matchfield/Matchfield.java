package de.skat3.gui.matchfield;


import de.skat3.gamelogic.Card;
import de.skat3.main.LocalGameState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * @author Aljoscha Domonell
 *
 */
public class Matchfield {

  private LocalGameState gameState;

  private InGameController controller;

  private Scene scene;
  private Pane table;
  protected GuiHand playerHand;
  protected GuiHand leftHand;
  protected GuiHand rightHand;
  protected Trick trick;
  public static Duration animationTime = Duration.millis(500);

  /**
   * View class of the Matchfield.
   */
  public Matchfield(LocalGameState gameState) {

    this.gameState = gameState;

    this.controller = new InGameController(this.gameState, this);

    this.table = new Pane();
    // add overlay as subscene
    this.scene = new Scene(this.table);
    double sceneWidth = 1800;
    double sceneHeight = 1800;

    this.table.setPrefSize(sceneWidth, sceneHeight);

    this.playerHand = new GuiHand(sceneWidth / 2, sceneHeight * 0.82, -200, -20, 0, 0,
        convertCardList(this.gameState.hand));
    this.leftHand =
        new GuiHand(0, sceneHeight * 0.82, 1000, 0, -55, 0, convertCardList(this.gameState.hand2));
    this.rightHand = new GuiHand(sceneWidth, sceneHeight * 0.82, 1000, 0, 55, 0,
        convertCardList(this.gameState.hand3));

    this.trick = new Trick(sceneWidth / 2, sceneHeight * 0.9, 500.0, -90, 0.0, 0.0);

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
