package de.skat3.gui.matchfield;

import de.skat3.main.SkatMain;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

/**
 * @author Aljoscha Domonell
 *
 */
public class InGameTableView {

  Matchfield matchfield;

  SubScene tableScene;

  Group table;

  GuiTrick trick;

  GuiHand leftHand;
  GuiHand playerHand;
  GuiHand rightHand;

  Parent[] skatPositions;

  public InGameTableView(Matchfield m) {
    this.matchfield = m;
    this.table = new Group();
    this.tableScene = new SubScene(this.table, this.matchfield.sceneWidth,
        this.matchfield.sceneHeight, false, SceneAntialiasing.BALANCED);

    AnchorPane.setTopAnchor(this.tableScene, 0.0);
    AnchorPane.setBottomAnchor(this.tableScene, 0.0);
    AnchorPane.setRightAnchor(this.tableScene, 0.0);
    AnchorPane.setLeftAnchor(this.tableScene, 0.0);

    PerspectiveCamera cam = new PerspectiveCamera();
    cam.setTranslateY(-100);
    cam.getTransforms().add(new Rotate(-30, Rotate.X_AXIS));
    this.tableScene.setCamera(cam);
  }

  void iniComponents() {
    // Box x = new Box(100000, 10, 10);
    // x.setMaterial(new PhongMaterial(Color.GREEN));
    // Box y = new Box(10, 100000, 10);
    // y.setMaterial(new PhongMaterial(Color.BLUE));
    // Box z = new Box(10, 10, 100000);
    // z.setMaterial(new PhongMaterial(Color.RED));
    // this.table.getChildren().addAll(x, y, z);

    this.tableScene.widthProperty().bind(this.matchfield.scene.widthProperty());
    this.tableScene.heightProperty().bind(this.matchfield.scene.heightProperty());

    this.playerHand = new GuiHand(this.tableScene.widthProperty().divide(2),
        this.tableScene.heightProperty().add(-200),
        this.tableScene.heightProperty().multiply(0).add(-100), -20, 0, 0, null);

    this.leftHand = new GuiHand(this.tableScene.widthProperty().multiply(0),
        this.tableScene.heightProperty().multiply(0.7),
        this.tableScene.heightProperty().multiply(0).add(1200), 0, -55, 0, null);

    this.rightHand = new GuiHand(this.tableScene.widthProperty().multiply(1),
        this.tableScene.heightProperty().multiply(0.7),
        this.tableScene.heightProperty().multiply(0).add(1200), 0, 55, 0, null);

    this.trick = new GuiTrick(this.tableScene.widthProperty().divide(2).subtract(GuiCard.width / 2),
        this.tableScene.heightProperty().divide(1.4),
        this.tableScene.widthProperty().multiply(0).add(400), -80, 0, 0, this.table);

    this.table.getChildren().addAll(this.playerHand, this.leftHand, this.rightHand);

    this.skatPositions = new Parent[2];

    this.skatPositions[0] = new Parent() {};
    this.skatPositions[0].translateXProperty()
        .bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(this.playerHand.translateXProperty())
            .subtract(200).subtract(GuiCard.width / 2));
    this.skatPositions[0].translateYProperty().bind(ReadOnlyDoubleProperty
        .readOnlyDoubleProperty(this.playerHand.translateYProperty()).multiply(0.6));
    this.skatPositions[0].translateZProperty().bind(ReadOnlyDoubleProperty
        .readOnlyDoubleProperty(this.playerHand.translateZProperty()).add(300));

    this.skatPositions[1] = new Parent() {};
    this.skatPositions[1].translateXProperty()
        .bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(this.playerHand.translateXProperty())
            .add(200).subtract(GuiCard.width / 2));
    this.skatPositions[1].translateYProperty().bind(ReadOnlyDoubleProperty
        .readOnlyDoubleProperty(this.playerHand.translateYProperty()).multiply(0.6));
    this.skatPositions[1].translateZProperty().bind(ReadOnlyDoubleProperty
        .readOnlyDoubleProperty(this.playerHand.translateZProperty()).add(300));

    this.tableScene.widthProperty().addListener(e -> {
      this.playerHand.resetPositions();
      this.leftHand.resetPositions();
      this.rightHand.resetPositions();
      this.trick.resetPostions();
    });
  }



}
