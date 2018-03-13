package de.skat3.gui.menuframe;

import de.skat3.gui.Menu;
import de.skat3.gui.multiplayermenu.MultiplayerMenu;
import de.skat3.gui.optionsmenu.OptionsMenu;
import de.skat3.gui.singleplayermenu.SingleplayerMenu;
import de.skat3.gui.statsmenu.StatsMenu;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
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

  // Menus

  private SingleplayerMenu singleplayerMenu;
  private MultiplayerMenu multiplayerMenu;
  private StatsMenu statsMenu;
  private OptionsMenu optionsMenu;

  private Menu activeMenu;

  // Button handler

  public void showSingleplayerMenu() {
    // this.changeMenus(this.singleplayerMenu);
    this.startMenuUnderlineAnimation(singleplayerMenuButton);
  }

  public void showMultiplayerMenu() {
    // this.changeMenus(this.multiplayerMenu);
    this.startMenuUnderlineAnimation(multiplayerMenuButton);
  }

  public void showStatsMenu() {
    // this.changeMenus(this.statsMenu);
    this.startMenuUnderlineAnimation(statsMenuButton);
  }

  public void showOptionsMenu() {
    // this.changeMenus(this.optionsMenu);
    this.startMenuUnderlineAnimation(optionsMenuButton);
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
    this.menuLinePostionAnimation.setDuration(Duration.millis(150));
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
  private void changeMenus(Menu newMenu) {
    if (newMenu.compareTo(this.activeMenu) == 0) {
      return;
    } else {
      if (newMenu.compareTo(this.activeMenu) < 0) {
        // code slide in from the left side
      } else {
        // code slide in from the right side
      }
    }
    this.activeMenu = newMenu;
    // delete not visible pane
  }

  protected void initialize() {
    this.singleplayerMenu = new SingleplayerMenu();
    this.multiplayerMenu = new MultiplayerMenu();
    this.statsMenu = new StatsMenu();
    this.optionsMenu = new OptionsMenu();
    // code first initilizing of the singleplayer without an animation when the
    // programm starts.
  }

}
