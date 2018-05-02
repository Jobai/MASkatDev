package de.skat3.io.profile;

import java.util.ArrayList;
import java.util.UUID;
import javafx.scene.image.Image;

public interface IoInterface {

  public Profile readProfile(UUID id);

  public ArrayList<Profile> getProfileList();

  public void addProfile(Profile profile);

  public void editProfile(Profile profile, String name, Image image, String imageFormat);

  public boolean deleteProfile(Profile profile);

  public Profile getLastUsedProfile();

  public void updateProfileStatistics(Profile profile) throws NullPointerException;
}
