package de.skat3.io.profile;

import java.util.ArrayList;
import java.util.UUID;
import javafx.scene.image.Image;

public interface IoInterface {

  /**
   * Finds Profile in the list of saved Profiles.
   * 
   * @param id the id of the profile that is to be found.
   * @return if there is a profile in the list of saved profiles whose id that matches the parameter
   *         id than it will be returned. Otherwise null is returned.
   */
  public Profile readProfile(UUID id);

  /**
   * Returns the list of saved profiles.
   * 
   * @return the list of saved profiles.
   */
  public ArrayList<Profile> getProfileList();

  /**
   * Adds profile to the list of saved profiles.
   * 
   * @param profile profile that is to be added
   */
  public void addProfile(Profile profile);

  /**
   * Edits existing profile from the list of saved profiles.
   * 
   * @param profile the profile that is to be edited.
   * @param name new name.
   * @param image new image.
   * @param imageFormat new image format.
   */
  public void editProfile(Profile profile, String name, Image image, String imageFormat);

  /**
   * Deletes profile from the list of saved profiles.
   * 
   * @param profile profile that is to be deleted.
   * @return if profile was deleted successfully - returns true, otherwise returns false.
   */
  public boolean deleteProfile(Profile profile);

  /**
   * returns last used profile
   * 
   * @return last used profile.
   */
  public Profile getLastUsedProfile();

  /**
   * Updates profile statistics.
   * 
   * @param profile profile whose statistics are to be saved.
   * @throws NullPointerException if profile is not in the list of saved profiles - throws NullPointerException
   */
  public void updateProfileStatistics(Profile profile) throws NullPointerException;
}
