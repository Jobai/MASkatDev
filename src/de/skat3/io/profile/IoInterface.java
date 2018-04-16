package de.skat3.io.profile;

import java.util.ArrayList;
import javafx.scene.image.Image;

public interface IoInterface {

  public Profile readProfile(String id);

  public ArrayList<Profile> getProfileList();

  public void addProfile(Profile profile);

  public void editProfile(Profile profile, String name, Image image);

  public boolean deleteProfile(Profile profile);

}
