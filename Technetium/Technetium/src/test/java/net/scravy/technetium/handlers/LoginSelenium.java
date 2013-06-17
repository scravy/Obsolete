package net.scravy.technetium.handlers;

import junit.framework.Assert;
import net.scravy.technetium.SeleniumTest;

import org.junit.Test;

public class LoginSelenium extends SeleniumTest {

	@Test
	public void testLogin() throws Exception {
		selenium().open("http://localhost:4447/login");
		selenium().type("login.username", "john.doe");
		selenium().type("login.password", "password");
		selenium().submit("//form");
		selenium().waitForPageToLoad("500");
		Assert.assertEquals("Welcome to Technetium!", selenium().getText("//h1"));
	}
}
