package de.skat3.gui.menuframe;

import java.io.File;
import de.skat3.io.profile.Profile;
import de.skat3.main.SkatMain;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller class to handle the events of the profile popup. Test2
 * 
 * @author tistraub Test
 */
public class ProfileController {
  private Stage profileStage;
  private Profile profile;

  @FXML
  private ImageView profileImage;
  @FXML
  private TextField profileName;

  /**
   * .
   */
  public void setProfileToScreen() {
    if (profile != null) {
      profileName.setText(profile.getName());
      profileImage.setImage(profile.getImage());
    }
  }

  public void setStage(Stage s) {
    this.profileStage = s;
  }

  public void setProfile(Profile p) {
    this.profile = p;
  }

  /**
   * Dummy.
   **/
  public void handleSaveProfile() {

    // New profile
    Image image = profileImage.getImage();

    if (profile == null) {
      // New profile
      Profile newProfile = new Profile(profileName.getText(), image);
      SkatMain.ioController.addProfile(newProfile);
    } else {
      // Update profile
      SkatMain.ioController.editProfile(profile, profileName.getText(), image);
    }
    profileStage.close();
  }
  // Swing to FX
  // Image image = SwingFXUtils.toFXImage(capture, null);

  // https://docs.oracle.com/javafx/2/api/javafx/embed/swing/SwingFXUtils.html#fromFXImage(javafx.scene.image.Image,%20java.awt.image.BufferedImage)
  // FX to Swing
  // BufferedImage SwingFXUtils.fromFXImage(Image img, BufferedImage bimg)

  public void handleDelProfile() {
    // TODO Call interface method from IO
    profileStage.close();
  }

  /**
   * Dummy.
   */
  public void handleChangeProfilePic() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose profile picture");
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
        new FileChooser.ExtensionFilter("PNG", "*.png"));

    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      System.out.println(file.getPath());
      Image i = new Image(file.toURI().toString());
      profileImage.setImage(i);
    }
  }

}
