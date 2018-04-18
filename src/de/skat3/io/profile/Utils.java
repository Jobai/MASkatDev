package de.skat3.io.profile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Utils {
  public static final String IMAGE_1_PNG = "resources/profile pictures/IMAGE_1_PNG.png";
  public static final String IMAGE_1_PNG_NEW = "resources/profile pictures/image_png_new.png";
  public static final String IMAGE_1_JPG = "resources/profile pictures/IMAGE_1_JPG.jpg";
  static final String IMAGE_1_JPG_NEW = "resources/profile pictures/img_landscape_new.jpg";
  public static final String IMAGE_2_PNG = "resources/profile pictures/IMAGE_2_PNG.png";
  public static final String IMAGE_2_JPG = "resources/profile pictures/IMAGE_2_JPG.jpg";
  static final String JSON_PATH = "data/profiles.json";
  static final String JSON_ID_FIELD = "id";
  static final String JSON_NAME_FIELD = "name";
  static final String JSON_PASSWORD_FIELD = "password";
  static final String JSON_IMAGE_FIELD = "image";
  static final String JSON_LAST_USED_FIELD = "lastUsed";
  static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
}
