package de.skat3.io.profile;

import static de.skat3.io.profile.Utils.JSON_ID_FIELD;
import static de.skat3.io.profile.Utils.JSON_IMAGE_FIELD;
import static de.skat3.io.profile.Utils.JSON_LAST_USED_FIELD;
import static de.skat3.io.profile.Utils.JSON_NAME_FIELD;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import com.google.gson.annotations.SerializedName;
import javafx.scene.image.Image;


public class Profile {
  private transient IoController controller = new IoController();
  private transient Image image;

  @SerializedName(JSON_ID_FIELD)
  private UUID uuid = UUID.randomUUID();
  @SerializedName(JSON_NAME_FIELD)
  private String name;
  @SerializedName(JSON_IMAGE_FIELD)
  private String encodedImage;
  @SerializedName(JSON_LAST_USED_FIELD)
  boolean lastUsed;


  public Profile(String name) {
    this.name = name;
    controller.getReader().setLastUsedProfile(this);
  }

  public Profile(String name, Image image, String imageFormat) {
    this.name = name;
    this.setImage(image, imageFormat);
    controller.getReader().setLastUsedProfile(this);
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image, String imageFormat) {
    ImageConverter adapter = new ImageConverter();
    this.image = image;
    this.encodedImage = adapter.imageToEncodedString(image, imageFormat);
  }

  public void setImageFromEncodedString() {
    ImageConverter adapter = new ImageConverter();
    this.image = adapter.encodedStringToImage(this.encodedImage);
  }
  
  public boolean getLastUsed() {
    return lastUsed;
  }

  public void setLastUsed(boolean lastUsed) {
    this.lastUsed = lastUsed;
    if (lastUsed) {
      // FIXME last used doesnot work properly
      // updateLastUsed();
    }
  }

  public void updateLastUsed() {
    ArrayList<Profile> profilesList = controller.getProfileList();
    Iterator<Profile> iter = profilesList.iterator();
    while (iter.hasNext()) {
      Profile current = iter.next();
      if (!current.equals(this)) {
        current.setLastUsed(false);
      }
    }
    controller.updateProfiles();
  }
}
