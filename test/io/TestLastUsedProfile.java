package io;

import static io.TestUtils.JPG;
import static io.TestUtils.PNG;
import static io.TestUtils.TEST_IMAGE_1_JPG;
import static io.TestUtils.TEST_IMAGE_1_PNG;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Random;
import org.junit.Test;
import de.skat3.io.profile.IoController;
import de.skat3.io.profile.Profile;
import javafx.embed.swing.JFXPanel;

public class TestLastUsedProfile {

  IoController controller = new IoController();
  private JFXPanel panel = new JFXPanel();

  @Test
  public void addOneProfileAndCheck() {
    Profile profile = new Profile("TestUser", TEST_IMAGE_1_JPG, JPG);
    controller.addProfile(profile);
    assertEquals(profile, controller.getLastUsedProfile());
    controller.deleteProfile(profile);
  }

  @Test
  public void addTwoProfileAndCheck() {
    Profile first = new Profile("TestUser", TEST_IMAGE_1_JPG, JPG);
    Profile second = new Profile("TestUser", TEST_IMAGE_1_PNG, PNG);
    controller.addProfile(first);
    controller.addProfile(second);
    assertEquals(second, controller.getLastUsedProfile());
    controller.deleteProfile(first);
    controller.deleteProfile(second);
  }

  @Test
  public void addTwoProfilesThanEditAndCheck() {
    Profile first = new Profile("TestUser1", TEST_IMAGE_1_JPG, JPG);
    Profile second = new Profile("TestUser2", TEST_IMAGE_1_PNG, PNG);
    controller.addProfile(first);
    controller.addProfile(second);
    controller.editProfile(second, "TestUserEdited", TEST_IMAGE_1_JPG, JPG);
    controller.editProfile(first, "TestUserEdited", TEST_IMAGE_1_PNG, PNG);
    assertEquals(first, controller.getLastUsedProfile());
    controller.deleteProfile(first);
    controller.deleteProfile(second);
  }



  @Test
  public void addTwoProfileAndDeleteOneCheck() {
    Profile first = new Profile("TestUser1", TEST_IMAGE_1_JPG, JPG);
    Profile second = new Profile("TestUser2", TEST_IMAGE_1_PNG, PNG);
    controller.addProfile(first);
    controller.addProfile(second);
    controller.deleteProfile(second);
    assertEquals(first, controller.getLastUsedProfile());
    controller.deleteProfile(first);
  }

  @Test
  public void addThreeProfilesAndDeleteAndEditCheck() {
    Profile first = new Profile("TestUser1", TEST_IMAGE_1_JPG, JPG);
    Profile second = new Profile("TestUser2", TEST_IMAGE_1_PNG, PNG);
    Profile third = new Profile("TestUser2", TEST_IMAGE_1_PNG, PNG);
    controller.addProfile(first);
    controller.addProfile(second);
    assertEquals(second, controller.getLastUsedProfile());
    controller.deleteProfile(second);
    assertEquals(first, controller.getLastUsedProfile());
    controller.addProfile(third);
    assertEquals(third, controller.getLastUsedProfile());
    controller.readProfile(first.getUuid());
    assertEquals(first, controller.getLastUsedProfile());

    controller.deleteProfile(first);
    controller.deleteProfile(third);
  }

  @Test
  public void fullStackLastUsedCheck() {
    for (int i = 0; i < 10; i++) {
      // at least 1
      int profilesAdded = new Random().nextInt(10) + 1;

      for (int j = 0; j < profilesAdded; j++) {
        Profile newProfile = new Profile("testUser");
        controller.addProfile(newProfile);
        assertEquals(newProfile, controller.getLastUsedProfile());
      }
      for (int j = 0; j < profilesAdded; j++) {
        ArrayList<Profile> profileList = controller.getProfileList();
        int profileIndex = new Random().nextInt(profileList.size());
        Profile toEdit = profileList.get(profileIndex);
        controller.editProfile(toEdit, "newTestUser", TEST_IMAGE_1_PNG, PNG);
        assertEquals(toEdit, controller.getLastUsedProfile());
      }

      for (int j = 0; j < profilesAdded; j++) {
        ArrayList<Profile> profileList = controller.getProfileList();
        int profileIndex = new Random().nextInt(profileList.size());
        assert (!controller.getLastUsedProfile().equals(null));
        controller.deleteProfile(profileList.get(profileIndex));
      }
    }
  }

}
