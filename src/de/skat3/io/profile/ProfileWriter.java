package de.skat3.io.profile;

public interface ProfileWriter {

  public void addProfile(Profile profile);

  public void editProfile(Profile profile, String name, String password, String encodedImage);

  public boolean deleteProfile(Profile profile);
}
