package de.skat3.io.profile;

import static de.skat3.io.profile.Utils.GSON;
import static de.skat3.io.profile.Utils.JSON_PATH_PRODUCTION;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONProfileReader {

  private ArrayList<Profile> profileList;
  private Profile lastUsed;

  public JSONProfileReader() {
    this.profileList = getAllProfileAsList();
    determineLastUsedProfile();
  }

  public ArrayList<Profile> getProfileList() {
    return profileList;
  }

  public Profile getLastUsedProfile() {
    return lastUsed;
  }

  public void setLastUsedProfile(Profile lastUsed) {
    this.lastUsed = lastUsed;
  }

  public void setProfilesList(ArrayList<Profile> profileList) {
    this.profileList = profileList;
  }

  public Profile readProfile(UUID id) {
    Iterator<Profile> iter = profileList.iterator();
    while (iter.hasNext()) {
      Profile current = iter.next();
      if (current.getUuid().equals(id)) {
        return current;
      }
    }
    return null;
  }


  public void determineLastUsedProfile() {
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
        //
        // System.out.println("user = null" + profileList);

        lastUsed = profileList.get(0);
      }
    }
  }

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

  private JsonArray convertFileToJsonArray() {
    JsonArray jsonArray = new JsonArray();
    try {
      JsonParser parser = new JsonParser();
      JsonElement jsonElement = parser.parse(new FileReader(JSON_PATH_PRODUCTION));
      jsonArray = jsonElement.getAsJsonArray();
    } catch (FileNotFoundException e) {
      System.out.println("The json file was not found");
      e.printStackTrace();
    } catch (NullPointerException ex) {

      return jsonArray;
    }
    return jsonArray;
  }

}
