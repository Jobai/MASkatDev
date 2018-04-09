package de.skat3.io.profile;

import static de.skat3.io.profile.Utils.EXISTING_FILE_PATH_PNG;
import static de.skat3.io.profile.Utils.NEW_FILE_PATH_PNG;
import java.awt.Image;
import java.util.ArrayList;

public class ProfileController {

  // copies existing image from EXISTING_FILE_PATH_PNG
  // encodes it, decodes it and pastes int NEW_FILE_PATH_PNG
  public static void main(String[] args) {
    JSONProfileWriter writer = new JSONProfileWriter();

    ArrayList<Profile> profilesList = writer.getProfilesList();
    Profile toEdit = profilesList.get(0);

    ImageConverter adapter = new ImageConverter();

    writer.editProfile(toEdit, "Craig", "", EXISTING_FILE_PATH_PNG);

    Profile toEditNew = profilesList.get(0);
    String encodedImage = toEditNew.getImage();
    Image img = adapter.encodedStringToImage(encodedImage, NEW_FILE_PATH_PNG);
  }
}


// TODO implement log in
// TODO implement change profile
// TODO im
