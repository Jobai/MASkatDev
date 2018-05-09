package de.skat3.gui;

import java.io.IOException;
import de.skat3.gui.matchfield.InGameController;
import de.skat3.gui.matchfield.Matchfield;
import de.skat3.gui.menuframe.MenuFrame;
import de.skat3.main.SkatMain;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
 * Class to manage the GUI.
 */
public class Gui extends Application {
  private Stage mainStage;
  private MenuFrame menuFrame;
  private Matchfield matchfield;

  /**
   * Shows the GUI and waits until it is closed.
   * 
   * @author Aljoscha Domonell
   */
  public static void showAndWait() {
    Application.launch(Gui.class);
  }

  /**
   * ASD.
   */
  public void showMenu() {
    this.matchfield = null;
    this.mainStage.setScene(this.menuFrame.getScene());
  }

  /**
   * ASD.
   * 
   * @return
   */
  public InGameController showMatchfield() {
    this.matchfield = new Matchfield();
    this.mainStage.setScene(this.matchfield.getScene());
    return this.matchfield.getController();
  }

  /**
   * (non-Javadoc)
   * 
   * @see javafx.application.Application#start(javafx.stage.Stage)
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    SkatMain.guiController.setGui(this);
    this.mainStage = primaryStage;

    this.mainStage.setOnCloseRequest(e -> System.exit(0)); // TODO

    this.initializeComponents();

    this.mainStage.setTitle("MA Skat");
    this.mainStage.getIcons()
        .add(new Image(getClass().getResourceAsStream("/guifiles/AppIcon.png")));

    this.mainStage.setScene(this.menuFrame.getScene());
    this.mainStage.show();
    this.delayedInitialize();

  }

  private void initializeComponents() {
    // size stuff. These are not the max values...s
    this.mainStage.setMinWidth(1280);
    this.mainStage.setMinHeight(720);
    this.mainStage.setMaximized(true);
    this.menuFrame = new MenuFrame();
  }

  private void delayedInitialize() {
    this.menuFrame.getController().backgroundImage.fitWidthProperty()
        .bind(this.mainStage.widthProperty());
    this.menuFrame.getController().backgroundImage.fitHeightProperty()
        .bind(this.mainStage.heightProperty());
    this.menuFrame.getController().delayedInitialize();

  }

  public Stage getMainStage() {
    return this.mainStage;
  }

  public MenuFrame getMenuFrame() {
    return this.menuFrame;
  }
}
