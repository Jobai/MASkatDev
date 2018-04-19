package de.skat3.gui.menuframe;

import java.io.File;
import de.skat3.io.profile.Profile;
import de.skat3.main.SkatMain;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller class to handle the events of the profile popup.
 * 
 * @author tistraub
 */
public class ProfileController {
  private Stage profileStage;
  private Profile profile;
  private String fileFormat;

  @FXML
  private ImageView profileImage;
  @FXML
  private TextField profileName;
  @FXML
  private Label popupHeaderText;

  /**
   * .
   */
  public void setProfileToScreen() {
    if (profile != null) {
      profileName.setText(profile.getName());
      profileImage.setImage(profile.getImage());
    }
  }

  public void setHeaderText(String text) {
    popupHeaderText.setText(text);
  }

  public void setStage(Stage s) {
    this.profileStage = s;
  }

  public void setProfile(Profile p) {
    this.profile = p;

    if (profile != null) {
      profileName.setText(profile.getName());
      profileImage.setImage(profile.getImage());
    }
  }

  /**
   * Dummy.
   **/
  public void handleSaveProfile() {

    // New profile
    Image image = profileImage.getImage();

    if (profile == null) {
      // New profile
      Profile newProfile = new Profile(profileName.getText(), image, fileFormat);
      SkatMain.ioController.addProfile(newProfile);
    } else {
      // Update profile
      SkatMain.ioController.editProfile(profile, profileName.getText(), image, fileFormat);
    }
    profileStage.close();
  }

  public void handleDelProfile() {
    SkatMain.ioController.deleteProfile(this.profile);
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
      Image i = new Image(file.toURI().toString());
      fileFormat = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1,
          file.getAbsolutePath().length());
      profileImage.setImage(i);
    }
  }

}
