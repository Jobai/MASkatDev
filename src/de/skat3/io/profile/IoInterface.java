package de.skat3.io.profile;

import java.awt.Image;
import java.util.ArrayList;

public interface IoInterface {

  public Profile readProfile(String id);

  public ArrayList<Profile> getProfileList();

  public void addProfile(Profile profile);

  public void editProfile(Profile profile, String name, Image image);

  public boolean deleteProfile(Profile profile);

}
