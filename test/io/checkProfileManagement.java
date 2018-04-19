package io;

import static io.TestUtils.JPG;
import static io.TestUtils.PNG;
import static io.TestUtils.TEST_IMAGE_1_JPG;
import static io.TestUtils.TEST_IMAGE_1_PNG;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import de.skat3.io.profile.IoController;
import de.skat3.io.profile.Profile;
import javafx.embed.swing.JFXPanel;

class checkProfileManagement {
  IoController controller = new IoController();
  private JFXPanel panel = new JFXPanel();
  ArrayList<Profile> list;

  @BeforeClass
  void setUp() {
    // ArrayList<Profile> clone = (ArrayList<Profile>) controller.getProfileList().clone();
    // list = clone;
  }

  @AfterClass
  void tearDown() {
    // controller.setProfileList(list);
  }

  @Test
  void addAndDeleteProfileWithoutImage() {
    Profile profile = new Profile("TestUser");
    controller.addProfile(profile);
    ArrayList<Profile> profileList = controller.getProfileList();
    assertEquals(profile, profileList.get(0));
    controller.deleteProfile(profile);
  }

  @Test
  void addAndDeleteProfileWith_PNG_Image() {
    Profile profile = new Profile("TestUser", TEST_IMAGE_1_PNG, PNG);
    controller.addProfile(profile);
    ArrayList<Profile> profileList = controller.getProfileList();
    assertEquals(profile, profileList.get(0));
    controller.deleteProfile(profile);
  }

  @Test
  void addAndDeleteProfileWith_JPG_Image() {
    Profile profile = new Profile("TestUser", TEST_IMAGE_1_JPG, JPG);
    controller.addProfile(profile);
    ArrayList<Profile> profileList = controller.getProfileList();
    assertEquals(profile, profileList.get(0));
    controller.deleteProfile(profile);
  }


  @Test
  void checkEditNameAndImage() {
    Profile profile = new Profile("TestUser", TEST_IMAGE_1_JPG, JPG);
    controller.addProfile(profile);

    controller.editProfile(profile, "TestUserNew", TEST_IMAGE_1_PNG, PNG);
    assertEquals(profile.getImage(), TEST_IMAGE_1_PNG);
    controller.deleteProfile(profile);
  }

  @Test
  void checkEditProfile() {
    Profile profile = new Profile("TestUser", TEST_IMAGE_1_JPG, JPG);
    controller.addProfile(profile);

    controller.editProfile(profile, "TestUserNew", null, null);
    assertEquals(profile.getName(), ("TestUserNew"));
    controller.deleteProfile(profile);
  }

  @Test
  void findProfile() {
    Profile profile = new Profile("TestUser", TEST_IMAGE_1_PNG, PNG);
    controller.addProfile(profile);
    Profile newProfile = controller.readProfile(profile.getUuid());
    assertEquals(profile, newProfile);
  }



  // @Test
  // void deleteAllProfiles() {
  // ArrayList<Profile> list = controller.getProfileList();
  //
  // Iterator<Profile> iter = list.iterator();
  //
  // while (iter.hasNext()) {
  // Profile currentProfile = iter.next();
  // controller.deleteProfile(currentProfile);
  // }
  //
  // }



}
