package de.skat3.io.profile;

import static de.skat3.io.profile.Utils.JSON_ID_FIELD;
import static de.skat3.io.profile.Utils.JSON_IMAGE_FIELD;
import static de.skat3.io.profile.Utils.JSON_LAST_USED_FIELD;
import static de.skat3.io.profile.Utils.JSON_NAME_FIELD;
import static de.skat3.io.profile.Utils.JSON_PASSWORD_FIELD;
import java.util.UUID;
import com.google.gson.annotations.SerializedName;


public class Profile {
  // Probably not needed
  @SerializedName(JSON_ID_FIELD)
  private String uuid = UUID.randomUUID().toString();
  @SerializedName(JSON_NAME_FIELD)
  private String name;
  @SerializedName(JSON_PASSWORD_FIELD)
  private String password;
  @SerializedName(JSON_IMAGE_FIELD)
  private String encodedImage;
  @SerializedName(JSON_LAST_USED_FIELD)
  boolean lastUsed;

  public Profile(String name) {
    this.name = name;
  }

  public Profile(String name, String password) {
    this.name = name;
    this.password = password;
  }

  public Profile(String name, String password, String imagePath) {
    this.name = name;
    this.password = password;
    this.setImage(imagePath);
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getImage() {
    return encodedImage;
  }

  public void setImage(String filePath) {
    ImageConverter adapter = new ImageConverter();
    String encoded = adapter.imageToString(filePath);
    this.encodedImage = encoded;
  }

  public boolean getLastUsed() {
    return lastUsed;
  }

  public void setLastUsed(boolean lastUsed) {
    this.lastUsed = lastUsed;
  }
}
