package io; 
 
import static io.TestUtils.JPG;
import static io.TestUtils.PNG;
import static io.TestUtils.TEST_IMAGE_1_JPG;
import static io.TestUtils.TEST_IMAGE_1_PNG;
import static io.TestUtils.TEST_IMAGE_2_PNG;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.Test;
import de.skat3.io.profile.IoController;
import de.skat3.io.profile.Profile;
import javafx.embed.swing.JFXPanel; 
 
public class TestProfileManagement { 
  IoController controller = new IoController(); 
  private JFXPanel panel = new JFXPanel(); 
 
  @Test 
  public void addAndDeleteProfileWithoutImage() { 
    Profile profile = new Profile("TestUser"); 
    controller.addProfile(profile); 
    ArrayList<Profile> profileList = controller.getProfileList(); 
    assertEquals(profile, profileList.get(0)); 
    controller.deleteProfile(profile); 
  } 
 
  @Test 
  public void addAndDeleteProfileWith_PNG_Image() { 
    Profile profile = new Profile("TestUser", TEST_IMAGE_1_PNG, PNG); 
    controller.addProfile(profile); 
    ArrayList<Profile> profileList = controller.getProfileList(); 
    assertEquals(profile, profileList.get(0)); 
    controller.deleteProfile(profile); 
  } 
 
  @Test 
  public void addAndDeleteProfileWith_JPG_Image() { 
    Profile profile = new Profile("TestUser", TEST_IMAGE_1_JPG, JPG); 
    controller.addProfile(profile); 
    ArrayList<Profile> profileList = controller.getProfileList(); 
    assertEquals(profile, profileList.get(0)); 
    controller.deleteProfile(profile); 
  } 
 
 
  @Test 
  public void checkEditNameAndImage() { 
    Profile profile = new Profile("TestUser", TEST_IMAGE_1_JPG, JPG); 
    controller.addProfile(profile); 
 
    controller.editProfile(profile, "TestUserNew", TEST_IMAGE_1_PNG, PNG); 
    assertEquals(profile.getImage(), TEST_IMAGE_1_PNG); 
    controller.deleteProfile(profile); 
  } 
 
  @Test 
  public void checkEditProfile() { 
    Profile profile = new Profile("TestUser", TEST_IMAGE_1_JPG, JPG); 
    controller.addProfile(profile); 
 
    controller.editProfile(profile, "TestUserNew", TEST_IMAGE_2_PNG, PNG); 
    assertEquals(profile.getName(), ("TestUserNew")); 
    controller.deleteProfile(profile); 
  } 
 
  @Test 
  public void checkEditTwoProfiles() { 
    Profile first = new Profile("TestUser", TEST_IMAGE_1_JPG, JPG); 
    Profile second = new Profile("TestUser", TEST_IMAGE_1_JPG, JPG); 
    controller.addProfile(first); 
    controller.addProfile(second); 
    controller.editProfile(first, "TestUserNew", TEST_IMAGE_2_PNG, PNG); 
    controller.editProfile(second, "TestUserNew", TEST_IMAGE_2_PNG, PNG); 
    controller.deleteProfile(first); 
    controller.deleteProfile(second); 
    assert (controller.getProfileList().size() == 0); 
  } 
 
  @Test 
  public void findProfile() { 
    Profile profile = new Profile("TestUser", TEST_IMAGE_1_PNG, PNG); 
    controller.addProfile(profile); 
    Profile newProfile = controller.readProfile(profile.getUuid()); 
    assertEquals(profile, newProfile); 
    controller.deleteProfile(profile); 
    controller.deleteProfile(newProfile); 
  } 
 
  @Test 
  public void deleteAllProfiles() { 
    ArrayList<Profile> list = controller.getProfileList(); 
 
    Iterator<Profile> iter = list.iterator(); 
    while (iter.hasNext()) { 
      Profile currentProfile = iter.next(); 
      controller.deleteProfile(currentProfile); 
    } 
 
  } 
} 

