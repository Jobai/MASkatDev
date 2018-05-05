package de.skat3.io.profile;

import static de.skat3.io.profile.Utils.GSON;
import static de.skat3.io.profile.Utils.JSON_PATH_PRODUCTION;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.image.Image;


public class IoController implements IoInterface {

  private ArrayList<Profile> profileList;
  private Profile lastUsed;

  public IoController() {
    this.profileList = getAllProfileAsList();
    determineLastUsedProfile();
  }

  @Override
  public Profile getLastUsedProfile() {
    determineLastUsedProfile();
    return lastUsed;
  }

  public void setLastUsedProfile(Profile lastUsed) {
    this.lastUsed = lastUsed;
  }

  @Override
  public ArrayList<Profile> getProfileList() {
    return this.profileList;
  }

  public void setProfileList(ArrayList<Profile> profileList) {
    this.profileList = profileList;
  }

  @Override
  public void addProfile(Profile profile) {
    profileList.add(profile);
    updateLastUsed(profile);
  }

  @Override
  public boolean deleteProfile(Profile profile) {
    boolean deleted = profileList.remove(profile);
    updateProfiles();

    return deleted;
  }

  /**
   * 
   * Throws NullPointerException if profileToSave is not in the profileList
   */
  @Override
  public void updateProfileStatistics(Profile profileToSave) throws NullPointerException {
    boolean profileFoundInProfileList = false;

    Iterator<Profile> iter = profileList.iterator();
    while (iter.hasNext()) {
      Profile current = iter.next();
      if (current.getUuid().equals(profileToSave.getUuid())) {
        profileFoundInProfileList = true;
        current = profileToSave;
      }
    }
    updateProfiles();

    if (!profileFoundInProfileList) {
      throw new NullPointerException(
          "The profile passed to updateProfileStatistics was not found in the profileList");
    }
  }



  /**
   * toEdit is needed to produce a NullPointerException if profile is not in the profileList That
   * should not happen, as the profile should not be showed in GUI thats why exception is not being
   * catched
   *
   *
   */
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
    if (!imageFormat.isEmpty()) {
      toEdit.setImage(image, imageFormat);
    }
    updateLastUsed(profile);
  }

  @Override
  public Profile readProfile(UUID id) {
    Iterator<Profile> iter = profileList.iterator();
    while (iter.hasNext()) {
      Profile current = iter.next();
      if (current.getUuid().equals(id)) {
        updateLastUsed(current);
        return current;
      }
    }
    return null;
  }

  /**
   * @param profile is a profile to be set as current
   * 
   *        Calls at @param profile setLastUsed(true), and setLastUsed(false) for all other profiles
   *        then calls updateProfiles()
   */
  public void updateLastUsed(Profile profile) {
    profile.setLastUsed(true);
    Iterator<Profile> iter = profileList.iterator();
    while (iter.hasNext()) {
      Profile current = iter.next();
      if (!current.equals(profile)) {
        current.setLastUsed(false);
      }
    }
    updateProfiles();
  }

  /**
   * calls determine determineLastUsedProfile() and writes profile list to json file
   */
  private void updateProfiles() {
    determineLastUsedProfile();
    String profileJsonString = GSON.toJson(profileList);
    try {
      FileWriter writer = new FileWriter(JSON_PATH_PRODUCTION);
      writer.write(profileJsonString);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Determines the last used profile Used to initialize and update lastUsed Is called by
   * updateProfiles()
   */
  private void determineLastUsedProfile() {
    lastUsed = null;
    if (profileList.size() <= 0) {
      return;
    } else {
      Iterator<Profile> itr = profileList.iterator();
      while (itr.hasNext()) {
        Profile potentiallyCurrent = itr.next();
        if (potentiallyCurrent.getLastUsed()) {
          lastUsed = potentiallyCurrent;
        }
      }
      if (lastUsed == null) {
        lastUsed = profileList.get(0);
      }
    }
  }

  /**
   * Reads all profiles from json file Used only to initialize profileList
   * 
   * @return list of all profiles
   */
  private ArrayList<Profile> getAllProfileAsList() {
    ArrayList<Profile> list = new ArrayList<Profile>();
    JsonArray jsonArray = convertFileToJsonArray();
    for (int i = 0; i < jsonArray.size(); i++) {
      JsonObject profileElement = jsonArray.get(i).getAsJsonObject();
      Profile profileObject = GSON.fromJson(profileElement, Profile.class);
      profileObject.setImageFromEncodedString();
      list.add(profileObject);
    }
    return list;
  }

  /**
   * Converts file to JsonArray Used only in getAllProfilesAsList()
   * 
   * 
   * @return JsonArray consisting of all profiles
   */
  private JsonArray convertFileToJsonArray() {
    JsonArray jsonArray = new JsonArray();
    try {
      JsonParser parser = new JsonParser();
      JsonElement jsonElement = parser.parse(new FileReader(JSON_PATH_PRODUCTION));
      jsonArray = jsonElement.getAsJsonArray();
    } catch (FileNotFoundException e) {
      System.out.println("The json file was not found");
      //e.printStackTrace();
    } catch (NullPointerException ex) {
      return null;
    }
    return jsonArray;
  }
}

