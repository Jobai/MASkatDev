package de.skat3.io.profile;

import static de.skat3.io.profile.Utils.GSON;
import static de.skat3.io.profile.Utils.JSON_PATH;
import java.awt.Image;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class IoController implements IoInterface {
  private JSONProfileReader reader = new JSONProfileReader();
  private ArrayList<Profile> profilesList = reader.getProfileList();

  @Override
  public void addProfile(Profile profile) {
    profilesList.add(profile);
    reader.setProfilesList(profilesList);
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
  public void editProfile(Profile profile, String name, Image image) {
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
    toEdit.setImage(image);

    reader.setProfilesList(profilesList);
    updateProfiles();
  }

  @Override
  public Profile readProfile(String id) {
    return reader.readProfile(id);
  }

  @Override
  public ArrayList<Profile> getProfileList() {
    return reader.getProfileList();

  }

  public JSONProfileReader getReader() {
    return reader;
  }

  private void updateProfiles() {
    String profileJsonString = GSON.toJson(profilesList);
    try {
      FileWriter writer = new FileWriter(JSON_PATH);
      writer.write(profileJsonString);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
