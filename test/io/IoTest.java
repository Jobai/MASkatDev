package io;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

// FIXME Tests should be ran with empty profiles.json and will leave the file empty as well
@RunWith(Suite.class)
@SuiteClasses({TestImageConversion.class, TestLastUsedProfile.class, TestProfileManagement.class,
    TestProfileStatistics.class})
public class IoTest {

}
