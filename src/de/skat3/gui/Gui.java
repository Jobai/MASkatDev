package de.skat3.gui;

import de.skat3.gui.matchfield.Matchfield;
import de.skat3.gui.menuframe.MenuFrame;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
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
   * (non-Javadoc)
   * 
   * @see javafx.application.Application#start(javafx.stage.Stage)
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    // SkatMain.getGuiController().setGui(this);
    StartTestClass.guiController.setGui(this);
    this.mainStage = primaryStage;
    this.initializeComponents();
    this.mainStage.setScene(this.menuFrame.getScene());
    this.mainStage.show();
  }

  private void initializeComponents() {
    // size stuff. These are not the max values...s

    this.menuFrame = new MenuFrame();
  }
}
