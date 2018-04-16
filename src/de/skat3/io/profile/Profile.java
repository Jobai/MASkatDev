package de.skat3.io.profile;

import static de.skat3.io.profile.Utils.JSON_ID_FIELD;
import static de.skat3.io.profile.Utils.JSON_IMAGE_FIELD;
import static de.skat3.io.profile.Utils.JSON_LAST_USED_FIELD;
import static de.skat3.io.profile.Utils.JSON_NAME_FIELD;
import java.awt.Image;
import java.util.UUID;
import com.google.gson.annotations.SerializedName;


public class Profile {
  private transient Image image;

  @SerializedName(JSON_ID_FIELD)
  private String uuid = UUID.randomUUID().toString();
  @SerializedName(JSON_NAME_FIELD)
  private String name;
  @SerializedName(JSON_IMAGE_FIELD)
  private String encodedImage;
  @SerializedName(JSON_LAST_USED_FIELD)
  boolean lastUsed;


  public Profile(String name) {
    this.name = name;
  }

  public Profile(String name, Image image) {
    this.name = name;
    this.setImage(image);
  }

  public String getUuid() {
    return uuid.toString();
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

  public void setImage(Image image) {
    ImageConverter adapter = new ImageConverter();
    this.image = image;
    this.encodedImage = adapter.imageToEncodedString(image);
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
  }
}
