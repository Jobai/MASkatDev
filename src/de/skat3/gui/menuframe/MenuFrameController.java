package de.skat3.gui.menuframe;

import de.skat3.gui.Menu;
import de.skat3.gui.multiplayermenu.MultiplayerMenu;
import de.skat3.gui.optionsmenu.OptionsMenu;
import de.skat3.gui.singleplayermenu.SingleplayerMenu;
import de.skat3.gui.statsmenu.StatsMenu;
import de.skat3.io.profile.Profile;
import de.skat3.main.PlayTimeTimer;
import de.skat3.main.SkatMain;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class to control the corresponding view file.
 * 
 * @author Aljoscha Domonell, Timo Straub
 */
public class MenuFrameController {

  // FXML file imports
  @FXML
  public AnchorPane mainPane;
  @FXML
  private HBox hboxProfile;
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
  private ArrayList<Profile> allProfile;
  private Profile currentProfile;
  private PlayTimeTimer timer;


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
    this.optionsMenu.getController().checkArchivment();
    this.switchMenus(this.optionsMenu);
  }

  // Animations

  private ScaleTransition buttonSizeAnimation;
  private TranslateTransition menuLinePostionAnimation;

  /**
   * Starts the underline animation.
   * 
   * @param targetButton Button which should be underlined
   */
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
        this.statsMenu.getController().refresh();
      }
      if (newMenu.equals(this.optionsMenu)) {
        this.startMenuUnderlineAnimation(this.optionsMenuButton);
      }

      newMenu.getPane().setPrefSize(this.mainPane.getScene().getWidth(),
          this.mainPane.getScene().getHeight() - 141);

      double slideOutEndX;

      if (newMenu.compareTo(this.activeMenu) > 0) {
        newMenu.getPane().setTranslateX(-this.mainPane.getWidth() - 1);
        slideOutEndX = this.mainPane.getWidth() + 1;
      } else {
        newMenu.getPane().setTranslateX(this.mainPane.getWidth() + 1);
        slideOutEndX = -this.mainPane.getWidth() - 1;
      }

      this.mainPane.getChildren().add(newMenu.getPane());

      double slideInEndX;

      TranslateTransition slideIn = new TranslateTransition();
      slideInEndX = 0;
      slideIn.setToX(slideInEndX);
      slideIn.setNode(newMenu.getPane());
      slideIn.setDuration(this.switchMenuDuration);

      TranslateTransition slideOut = new TranslateTransition();
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

  /**
   * Initialize the menu frame with all the sub menus and set the current profile.
   */
  protected void initialize() {
    this.singleplayerMenu = new SingleplayerMenu();
    this.multiplayerMenu = new MultiplayerMenu();
    this.statsMenu = new StatsMenu();
    this.optionsMenu = new OptionsMenu();

    fillProfileMenu();
    setCurrentProfile(SkatMain.ioController.getLastUsedProfile());
  }

  /**
   * Fills the profile dropdown with all the available profiles.
   */
  private void fillProfileMenu() {
    // clear old data
    profileMenuButton.getItems().clear();

    allProfile = SkatMain.ioController.getProfileList();
    // prompt create profile
    if (allProfile.isEmpty()) {
      openProfile(null);
      fillProfileMenu();
      return;
    }

    allProfile = SkatMain.ioController.getProfileList();

    for (Profile profile : allProfile) {
      Label l1 = new Label(profile.getName());
      l1.setFont(new Font(16));
      CustomMenuItem item = new CustomMenuItem(l1);
      item.setId(profile.getUuid().toString());
      item.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          CustomMenuItem source = (CustomMenuItem) event.getSource();
          UUID uuid = UUID.fromString(source.getId());
          Profile p = SkatMain.ioController.readProfile(uuid);
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
        openProfile(null);
        fillProfileMenu();
        setCurrentProfile(SkatMain.ioController.getLastUsedProfile());
      }
    });
    profileMenuButton.getItems().add(new CustomMenuItem(add));
  }

  /**
   * Initialization after screen is created.
   */
  public void delayedInitialize() {
    this.showSingleplayerMenu();
  }

  /**
   * Handels user mouse click on profile.
   */
  public void handleMouseClickProfileMenu() {
    openProfile(currentProfile);
    fillProfileMenu();
    setCurrentProfile(SkatMain.ioController.getLastUsedProfile());
  }

  /**
   * Opens a popup in which the current profile is shown.
   * 
   * @param p Profile which sould be opened
   */
  private void openProfile(Profile p) {
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

    stage.getIcons().add(new Image(getClass().getResourceAsStream("/guifiles/AppIcon.png")));

    ProfileController profileController = fxmlLoader.getController();
    profileController.setProfile(p);
    profileController.setStage(stage);

    if (p == null) {
      profileController.setHeaderText("Create a profile");
    }

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.showAndWait();
  }

  private void setCurrentProfile(Profile p) {
    this.currentProfile = p;
    try {
      currentProfileImage.setImage(p.getImage());
    } catch (Exception e) {
      // Do nothing
    }
    currentProfileName.setText(p.getName());

    // Refresh Stats
    this.statsMenu.getController().refresh();

    if (timer != null) {
      timer.interrupt();
    }
    timer = new PlayTimeTimer(p.getPlayerGameTime());

  }

  /**
   * Returns Statistik Menu.
   * 
   * @return Statistik Menu
   */
  public StatsMenu getStatsMenu() {
    return this.statsMenu;
  }

}
