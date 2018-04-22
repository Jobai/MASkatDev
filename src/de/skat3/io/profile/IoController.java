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
  public JSONProfileReader reader;
  private ArrayList<Profile> profileList;

  public IoController() {
    this.reader = new JSONProfileReader();
    this.profileList = getProfileList();
  }

  @Override
  public void addProfile(Profile profile) {
    profileList.add(profile);
    updateProfiles();
    profile.setLastUsedTrue(this);
  }

  @Override
  public boolean deleteProfile(Profile profile) {
    boolean deleted = profileList.remove(profile);
    updateProfiles();

    return deleted;
  }

  @Override
  public void editProfile(Profile profile, String name, Image image, String imageFormat) {
    Profile toEdit = null;

    Iterator<Profile> iter = profileList.iterator();
    while (iter.hasNext()) {
      Profile next = iter.next();
      if (next.equals(profile)) {
        toEdit = next;
        break;
      }
    }

    toEdit.setName(name);
    toEdit.setImage(image, imageFormat);
    updateProfiles();
    toEdit.setLastUsedTrue(this);
  }

  @Override
  public Profile readProfile(UUID id) {
    Profile profile = reader.readProfile(id);
    profile.setLastUsedTrue(this);

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
    reader.determineLastUsedProfile();
    String profileJsonString = GSON.toJson(profileList);
    try {
      FileWriter writer = new FileWriter(JSON_PATH_PRODUCTION);
      writer.write(profileJsonString);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

