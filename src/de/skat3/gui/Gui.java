package de.skat3.gui;

import de.skat3.gui.matchfield.InGameController;
import de.skat3.gui.matchfield.Matchfield;
import de.skat3.gui.menuframe.MenuFrame;
import de.skat3.main.SkatMain;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
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
   * @author adomonel
   */
  public static void showAndWait() {
    Application.launch(Gui.class);
  }

  /**
   * ASD.
   */
  public void showMenu() {
    this.mainStage.setScene(this.menuFrame.getScene());
  }

  /**
   * ASD.
   * 
   * @return
   */
  public InGameController showMatchfield() {
    this.matchfield = new Matchfield(SkatMain.lgs);
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
    // SkatMain.getGuiController().setGui(this);
    SkatMain.guiController.setGui(this);
    this.mainStage = primaryStage;
    this.initializeComponents();

    this.mainStage.setTitle("MA Skat");
    this.mainStage.getIcons()
        .add(new Image(getClass().getResourceAsStream("../../../guifiles/AppIcon.png")));
    
System.out.println(com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());

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
}
