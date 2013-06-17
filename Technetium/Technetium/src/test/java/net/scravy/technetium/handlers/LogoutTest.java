package net.scravy.technetium.handlers;

import junit.framework.Assert;
import net.scravy.technetium.DatabaseTest;
import net.scravy.technetium.domain.Person;
import net.scravy.technetium.modules.auth.Logout;
import net.scravy.weblet.Request;
import net.scravy.weblet.RequestMethod;
import net.scravy.weblet.RequestMockup;
import net.scravy.weblet.Response;
import net.scravy.weblet.ResponseMockup;
import net.scravy.weblet.ServletMockup;
import net.scravy.weblet.SessionMockup;
import net.scravy.weblet.Weblet;

import org.junit.BeforeClass;
import org.junit.Test;

public class LogoutTest {

	static Logout logoutHandler = new Logout();
	static Weblet servlet;

	@BeforeClass
	public static void before() {
		DatabaseTest.init();

		servlet = ServletMockup.genServlet(DatabaseTest.getEntityManager()
				.getEntityManagerFactory());
	}

	@Test
	public void testLogout() {
		Request request = RequestMockup.genRequest(RequestMethod.GET, "logout?"
				+ Math.random(), SessionMockup.genSession(new Person()));
		Response response = ResponseMockup.genResponse();

		Assert.assertTrue(request.isLoggedIn());
		logoutHandler.handle(servlet, request, response);
		Assert.assertFalse(request.isLoggedIn());
	}

}
