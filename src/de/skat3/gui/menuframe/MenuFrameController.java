package de.skat3.gui.menuframe;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.plaf.metal.MetalPopupMenuSeparatorUI;
import com.sun.swing.internal.plaf.synth.resources.synth;
import de.skat3.gui.Menu;
import de.skat3.gui.multiplayermenu.MultiplayerMenu;
import de.skat3.gui.optionsmenu.OptionsMenu;
import de.skat3.gui.singleplayermenu.SingleplayerMenu;
import de.skat3.gui.statsmenu.StatsMenu;
import de.skat3.io.profile.Profile;
import de.skat3.main.SkatMain;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 * Class to control the corresponding view file.
 * 
 * @author adomonel, tistraub
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
  @FXML
  public MenuButton profileMenuButton;
  @FXML
  private ImageView currentProfileImage;
  @FXML
  private Label currentProfileName;

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


    fillProfileMenu();

  }

  private void fillProfileMenu() {
    // TODO get profiles from IO
    ArrayList<Profile> allProfile = SkatMain.ioController.getProfileList();

    for (Profile profile : allProfile) {
      Label l1 = new Label(profile.getName());
      l1.setFont(new Font(16));
      CustomMenuItem item = new CustomMenuItem(l1);
      item.setId(profile.getUuid());
      item.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          CustomMenuItem source = (CustomMenuItem) event.getSource();
          Profile p = SkatMain.mainController.readProfile(source.getId());
          setCurrentProfile(p);
        }

      });
      profileMenuButton.getItems().add(item);
    }

    profileMenuButton.getItems().add(new SeparatorMenuItem());

    Button add = new Button("Add");
    add.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        openProfile("");
      }
    });
    profileMenuButton.getItems().add(new CustomMenuItem(add));
  }

  public void delayedInitialize() {
    this.showSingleplayerMenu();
  }

  public void handleMouseClickProfileMenu() {
    openProfile("");
  }

  private void openProfile(String id) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfilePopupView.fxml"));
    Parent root = null;
    try {
      root = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Stage stage = new Stage();
    stage.setTitle("Profile");
    stage.setScene(new Scene(root));

    ProfileController profileController = fxmlLoader.getController();
    profileController.setStage(stage);

    stage.show();
  }

  public void handleMouseClickAddProfile() {

  }

  private void setCurrentProfile(Profile p) {
    BufferedImage bImage = (BufferedImage) p.getImage();
    WritableImage fxImage = SwingFXUtils.toFXImage(bImage, null);

    currentProfileImage.setImage(fxImage);
    currentProfileName.setText(p.getName());
  }

  public void handleProfileChanged() {
    System.out.println("test");
  }

}
