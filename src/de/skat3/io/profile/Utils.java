package de.skat3.io.profile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Utils {
  protected static final String EXISTING_FILE_PATH_PNG =
      "resources\\profile pictures\\image_png.png";
  protected static final String NEW_FILE_PATH_PNG =
      "resources\\profile pictures\\image_png_new.png";
  protected static final String EXISTING_FILE_PATH =
      "resources\\profile pictures\\img_landscape.jpg";
  protected static final String NEW_FILE_PATH =
      "resources\\profile pictures\\img_landscape_new.jpg";
  protected static final String JSON_PATH = "data\\profiles.json";
  protected static final String JSON_ID_FIELD = "id";
  protected static final String JSON_NAME_FIELD = "name";
  protected static final String JSON_PASSWORD_FIELD = "password";
  protected static final String JSON_IMAGE_FIELD = "image";
  protected static final String JSON_LAST_USED_FIELD = "lastUsed";
  protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
}
