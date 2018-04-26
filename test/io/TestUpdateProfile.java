package io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.Test;
import de.skat3.io.profile.IoController;
import de.skat3.io.profile.Profile;

public class TestUpdateProfile {
  IoController controller = new IoController();

  @Test
  public void testUpdateProfile() {
    Profile profile1 = new Profile("1");
    Profile profile2 = new Profile("2");
    Profile profile3 = new Profile("3");
    Profile profile4 = new Profile("4");
    Profile profile5 = new Profile("5");

    controller.addProfile(profile1);
    controller.addProfile(profile2);
    controller.addProfile(profile3);
    controller.addProfile(profile4);
    controller.addProfile(profile5);

    profile3.incrementMultiPlayerRoundsLostGrand();
    profile3.incrementMultiPlayerRoundsLostGrand();
    profile3.incrementMultiPlayerRoundsLostGrand();
    profile3.setMultiPlayerHighestScore(10);
    profile3.incrementSinglePlayerRoundsWonNull();
    profile3.incrementSinglePlayerRoundsWonNull();

    ArrayList<Profile> list = controller.getProfileList();

    controller.updateProfileStatistics(profile3);

    boolean contains = false;
    Iterator<Profile> iter = list.iterator();
    while (iter.hasNext()) {
      Profile current = iter.next();
      if (current.getUuid().equals(profile3.getUuid())) {
        contains = true;
        assertEquals(current.getMultiPlayerHighestScore(), profile3.getMultiPlayerHighestScore());
        assertEquals(current.getMultiPlayerRoundsLostGrand(),
            profile3.getMultiPlayerRoundsLostGrand());
        assertEquals(current.getSinglePlayerRoundsWonNull(),
            profile3.getSinglePlayerRoundsWonNull());
      }
    }
    assertTrue(contains);
  }

  @Test(expected = NullPointerException.class)
  public void testUpdateProfileNullPointerException() {
    Profile notAddedProfile = new Profile("1");
    notAddedProfile.incrementMultiPlayerRoundsLostGrand();

    controller.updateProfileStatistics(notAddedProfile);
  }
}
