package de.skat3.gui.menuframe;

import com.sun.swing.internal.plaf.synth.resources.synth;
import de.skat3.gui.Menu;
import de.skat3.gui.multiplayermenu.MultiplayerMenu;
import de.skat3.gui.optionsmenu.OptionsMenu;
import de.skat3.gui.singleplayermenu.SingleplayerMenu;
import de.skat3.gui.statsmenu.StatsMenu;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class to control the corresponding view file.
 * 
 * @author adomonel
 */
public class MenuFrameController {

  // FXML file imports

  @FXML
  public AnchorPane mainPane;
  @FXML
  public Button singleplayerMenuButton;
  @FXML
  public Button multiplayerMenuButton;
  @FXML
  public Button statsMenuButton;
  @FXML
  public Button optionsMenuButton;
  @FXML
  public Rectangle menuNameLine;
  @FXML
  public ImageView backgroundImage;

  // Menus

  private SingleplayerMenu singleplayerMenu;
  private MultiplayerMenu multiplayerMenu;
  private StatsMenu statsMenu;
  private OptionsMenu optionsMenu;

  private Menu activeMenu;

  // Various

  private Duration switchMenuDuration = Duration.millis(300);

  // Button handler

  public void showSingleplayerMenu() {
    this.switchMenus(this.singleplayerMenu);
  }

  public void showMultiplayerMenu() {
    this.switchMenus(this.multiplayerMenu);
  }

  public void showStatsMenu() {
    this.switchMenus(this.statsMenu);
  }

  public void showOptionsMenu() {
    this.switchMenus(this.optionsMenu);
  }

  public void startSingleplayerGame() {

  }

  public void hostMultiplayerGame() {

  }

  public void joinMultiplayerGame() {

  }

  public void refreshServerList() {

  }

  // Animations

  private ScaleTransition buttonSizeAnimation;
  private TranslateTransition menuLinePostionAnimation;

  private void startMenuUnderlineAnimation(Button targetButton) {
    // Changing the width of the line to the width of the button.
    this.menuNameLine
        .setWidth(targetButton.getText().length() * targetButton.getFont().getSize() / 1.5);

    // Animation to change the postion of the line.
    this.menuLinePostionAnimation = new TranslateTransition();
    this.menuLinePostionAnimation.setNode(this.menuNameLine);
    this.menuLinePostionAnimation.setDuration(this.switchMenuDuration);
    // Reset scaling
    targetButton.setScaleX(1);
    targetButton.setScaleX(1);
    this.menuLinePostionAnimation.setToX(targetButton.getBoundsInParent().getMinX()
        + (targetButton.getWidth() - this.menuNameLine.getWidth()) / 2);
    this.menuLinePostionAnimation.play();
  }

  /**
   * Is starting the animation when the cursor is over a button.
   * 
   * @param e Event to get the source from.
   */
  public void handleStartButtonAnimation(MouseEvent e) {
    Button source = (Button) e.getSource();
    buttonSizeAnimation = new ScaleTransition();
    buttonSizeAnimation.setDuration(Duration.millis(25));
    buttonSizeAnimation.setNode(source);
    buttonSizeAnimation.setToX(1.075);
    buttonSizeAnimation.setToY(1.1);
    buttonSizeAnimation.play();
  }

  /**
   * Is reversing the animation when the cursor is not over a button anymore.
   * 
   */
  public void handleEndButtonAnimation() {
    buttonSizeAnimation.setToX(1);
    buttonSizeAnimation.setToY(1);
    buttonSizeAnimation.play();
  }

  // General stuff

  /**
   * Slide in a new menu.
   * 
   * @param newMenu The Menu that is slid in.
   */
  private synchronized void switchMenus(Menu newMenu) {
    if (this.activeMenu == null) {
      this.mainPane.getChildren().add(newMenu.getPane());
      this.activeMenu = newMenu;
      return;
    }
    if (newMenu.compareTo(this.activeMenu) == 0) {
      return;
    } else {

      this.mainPane.setDisable(true);

      if (newMenu.equals(this.singleplayerMenu)) {
        this.startMenuUnderlineAnimation(this.singleplayerMenuButton);
      }
      if (newMenu.equals(this.multiplayerMenu)) {
        this.startMenuUnderlineAnimation(this.multiplayerMenuButton);
      }
      if (newMenu.equals(this.statsMenu)) {
        this.startMenuUnderlineAnimation(this.statsMenuButton);
      }
      if (newMenu.equals(this.optionsMenu)) {
        this.startMenuUnderlineAnimation(this.optionsMenuButton);
      }

      double slideInEndX, slideOutEndX;
      if (newMenu.compareTo(this.activeMenu) > 0) {
        newMenu.getPane().setTranslateX(-this.mainPane.getWidth() - 1);
        slideOutEndX = this.mainPane.getWidth() + 1;
      } else {
        newMenu.getPane().setTranslateX(this.mainPane.getWidth() + 1);
        slideOutEndX = -this.mainPane.getWidth() - 1;
      }

      this.mainPane.getChildren().add(newMenu.getPane());

      TranslateTransition slideIn = new TranslateTransition();
      TranslateTransition slideOut = new TranslateTransition();
      slideInEndX = 0;
      slideIn.setToX(slideInEndX);
      slideIn.setNode(newMenu.getPane());
      slideIn.setDuration(this.switchMenuDuration);

      slideOut.setToX(slideOutEndX);
      slideOut.setNode(this.activeMenu.getPane());
      slideOut.setDuration(this.switchMenuDuration);

      slideIn.play();
      slideOut.play();

      slideOut.setOnFinished(e -> {
        this.mainPane.getChildren().remove(this.activeMenu.getPane());
        this.activeMenu = newMenu;
        AnchorPane.setBottomAnchor(newMenu.getPane(), 0.0);
        AnchorPane.setLeftAnchor(newMenu.getPane(), 0.0);
        AnchorPane.setRightAnchor(newMenu.getPane(), 0.0);
        AnchorPane.setTopAnchor(newMenu.getPane(), 141.0);
        this.mainPane.setDisable(false);
      });
    }
  }

  protected void initialize() {
    this.singleplayerMenu = new SingleplayerMenu();
    this.multiplayerMenu = new MultiplayerMenu();
    this.statsMenu = new StatsMenu();
    this.optionsMenu = new OptionsMenu();

    // Stage s = (Stage) mainPane.getScene().getWindow();
    // System.out.println(s.widthProperty());
    // this.backgroundImage.fitWidthProperty().bind( s.widthProperty() );
    // this.backgroundImage.fitHeightProperty().bind( s.heightProperty());
    // code first initilizing of the singleplayer without an animation when the
    // programm starts.
  }

  public void delayedInitialize() {
    this.showSingleplayerMenu();
  }

}