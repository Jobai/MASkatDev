package de.skat3.io.profile;

import static de.skat3.io.profile.Utils.GSON;
import static de.skat3.io.profile.Utils.JSON_PATH;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONProfileReader {

  private ArrayList<Profile> profileList = getAllProfileAsList();
  private Profile lastUsed = determineLastUsedProfile();

  public Profile readProfile(String id) {
    Iterator<Profile> iter = profileList.iterator();
    while (iter.hasNext()) {
      Profile current = iter.next();
      if (current.getUuid() == id) {
        return current;
      }
    }
    return null;
  }

  public ArrayList<Profile> getProfileList() {
    return profileList;
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

  private Profile determineLastUsedProfile() {
    Iterator<Profile> itr = profileList.iterator();
    while (itr.hasNext()) {
      if (itr.next().getLastUsed()) {
        lastUsed = itr.next();
      }
    }
    return lastUsed;
  }

  private ArrayList<Profile> getAllProfileAsList() {
    ArrayList<Profile> list = new ArrayList<Profile>();
    JsonArray jsonArray = convertFileToJsonArray();
    for (int i = 0; i < jsonArray.size(); i++) {
      JsonObject profileElement = jsonArray.get(i).getAsJsonObject();
      Profile profileObject = GSON.fromJson(profileElement, Profile.class);
      list.add(profileObject);
    }
    return list;
  }

  private JsonArray convertFileToJsonArray() {
    JsonArray jsonArray = new JsonArray();
    try {
      JsonParser parser = new JsonParser();
      JsonElement jsonElement = parser.parse(new FileReader(JSON_PATH));
      jsonArray = jsonElement.getAsJsonArray();
    } catch (FileNotFoundException e) {
      System.out.println("The json file was not found");
      e.printStackTrace();
    }
    return jsonArray;
  }

  // checks if there is a JSON_LAST_USED_FIELD, which value is true and returns its id
  // otherwise returns null
  // private Profile determineLastUsedProfile1() {
  // Profile lastUsed = null;
  // JsonArray jsonArray = convertFileToJsonArray();
  // for (int i = 0; i < jsonArray.size(); i++) {
  // JsonElement element = jsonArray.get(i);
  // JsonElement potentialyLastUsed = element.getAsJsonObject().get(JSON_LAST_USED_FIELD);
  // String temp = potentialyLastUsed.toString();
  // if (temp.equals("true")) {
  // lastUsed = GSON.fromJson(potentialyLastUsed, Profile.class);
  // }
  // }
  // return lastUsed;
  // }

}
