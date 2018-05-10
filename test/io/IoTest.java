package io;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestImageConversion.class, TestLastUsedProfile.class, TestProfileManagement.class,
    TestProfileStatistics.class})
public class IoTest {

}
