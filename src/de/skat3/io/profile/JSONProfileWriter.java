package de.skat3.io.profile;

import static de.skat3.io.profile.Utils.GSON;
import static de.skat3.io.profile.Utils.JSON_PATH;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import com.google.gson.reflect.TypeToken;


public class JSONProfileWriter implements ProfileWriter {
  JSONProfileReader reader = new JSONProfileReader();
  ArrayList<Profile> profilesList = reader.getprofileList();
  Type type = new TypeToken<ArrayList<Profile>>() {}.getType();

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
  public void editProfile(Profile profile, String name, String password, String imagePath) {
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
    toEdit.setPassword(password);
    toEdit.setImage(imagePath);

    reader.setProfilesList(profilesList);
    updateProfiles();
  }

  // @Override
  public boolean checkProfileExistance(Profile profile) {
    return profilesList.contains(profile);
  }

  public ArrayList<Profile> getProfilesList() {
    return profilesList;
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
