package de.skat3.io.profile;

import static de.skat3.io.profile.Utils.IMAGE_1_PNG;
import static de.skat3.io.profile.Utils.PNG;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

public class ProfileTest {

  public static void main(String[] args) {
    IoController controller = new IoController();
    JFXPanel panel = new JFXPanel();
    Image TEST_IMAGE_1_PNG = new Image("file:" + IMAGE_1_PNG);

    Profile profile = new Profile("user", TEST_IMAGE_1_PNG, PNG);
    controller.addProfile(profile);
  }

}
