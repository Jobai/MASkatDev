package de.skat3.io.profile;

import static de.skat3.io.profile.Utils.GSON;
import static de.skat3.io.profile.Utils.JSON_PATH_PRODUCTION;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import javafx.scene.image.Image;


public class IoController implements IoInterface {
  // this reader must be used by all components
  public JSONProfileReader reader = new JSONProfileReader();
  private ArrayList<Profile> profilesList = getProfileList();

  @Override
  public void addProfile(Profile profile) {
    profilesList.add(profile);
    reader.setProfilesList(profilesList);
    profile.setLastUsed(true);
    updateProfiles();
  }

  @Override
  public boolean deleteProfile(Profile profile) {
    boolean deleted = profilesList.remove(profile);
    reader.setProfilesList(profilesList);
    updateProfiles();

    return deleted;
  }

  @Override
  public void editProfile(Profile profile, String name, Image image, String imageFormat) {
    Profile toEdit = null;

    Iterator<Profile> iter = profilesList.iterator();
    while (iter.hasNext()) {
      Profile next = iter.next();
      if (next.equals(profile)) {
        toEdit = next;
        break;
      }
    }

    toEdit.setName(name);
    toEdit.setImage(image, imageFormat);
    toEdit.setLastUsed(true);

    reader.setProfilesList(profilesList);
    updateProfiles();
  }

  @Override
  public Profile readProfile(UUID id) {
    Profile profile = reader.readProfile(id);
    profile.setLastUsed(true);

    return profile;
  }

  @Override
  public ArrayList<Profile> getProfileList() {
    return reader.getProfileList();
  }

  @Override
  public Profile getLastUsedProfile() {
    return reader.getLastUsedProfile();
  }

  public JSONProfileReader getReader() {
    return reader;
  }

  public void updateProfiles() {
    String profileJsonString = GSON.toJson(profilesList);
    try {
      FileWriter writer = new FileWriter(JSON_PATH_PRODUCTION);
      writer.write(profileJsonString);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

