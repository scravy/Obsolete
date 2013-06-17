package net.scravy.technetium;


import net.scravy.technetium.handlers.LoginSelenium;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({LoginSelenium.class})
public class WithSeleniumTest extends SeleniumTest {

}
