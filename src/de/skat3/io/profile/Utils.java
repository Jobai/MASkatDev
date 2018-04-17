package de.skat3.io.profile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Utils {
  public static final String EXISTING_FILE_PATH_PNG = "resources/profile pictures/image_png.png";
  public static final String NEW_FILE_PATH_PNG = "resources/profile pictures/image_png_new.png";
  public static final String EXISTING_FILE_PATH_JPG =
      "resources/profile pictures/img_landscape.jpg";
  static final String NEW_FILE_PATH_JPG = "resources/profile pictures/img_landscape_new.jpg";
  static final String JSON_PATH = "data/profiles.json";
  static final String JSON_ID_FIELD = "id";
  static final String JSON_NAME_FIELD = "name";
  static final String JSON_PASSWORD_FIELD = "password";
  static final String JSON_IMAGE_FIELD = "image";
  static final String JSON_LAST_USED_FIELD = "lastUsed";
  static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  //
}
