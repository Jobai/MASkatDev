package de.skat3.gui.menuframe;

import de.skat3.io.profile.Profile;
import de.skat3.main.SkatMain;
import java.io.File;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller class to handle the events of the profile popup.
 * 
 * @author Timo Straub
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
  @FXML
  private Button delButton;
  @FXML
  private Button btnSave;

  /**
   * Initialize the screen.
   */
  @FXML
  public void initialize() {
    btnSave.disableProperty().bind(Bindings.isEmpty(profileName.textProperty()));
  }

  public void setHeaderText(String text) {
    popupHeaderText.setText(text);
  }

  public void setStage(Stage s) {
    this.profileStage = s;
  }

  /**
   * Fills screen fields with profile data.
   * 
   * @param p Profile which should be displayed
   */
  public void setProfile(Profile p) {
    this.profile = p;
    fileFormat = "";
    delButton.setDisable(true);

    if (profile != null) {
      delButton.setDisable(false);
      profileName.setText(profile.getName());
      profileImage.setImage(profile.getImage());
    } else {
      fileFormat = "JPG";
    }
  }

  /**
   * Saves current profile data.
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

  /**
   * Handle action, when user wants to delete his profile.
   */
  public void handleDelProfile() {
    SkatMain.ioController.deleteProfile(this.profile);
    profileStage.close();
  }

  /**
   * Opens a FileChooser and set the selected image from the user to screen.
   */
  public void handleChangeProfilePic() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose profile picture");
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
        new FileChooser.ExtensionFilter("PNG", "*.png"));

    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      Image image = new Image(file.toURI().toString());
      if (file.length() > 1000000) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("Image is too big! \nMax. 1 MB allowed!");
        a.setHeaderText(null);
        a.showAndWait();
        return;
      }
      fileFormat = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1,
          file.getAbsolutePath().length());
      profileImage.setImage(image);
    }
  }

}
