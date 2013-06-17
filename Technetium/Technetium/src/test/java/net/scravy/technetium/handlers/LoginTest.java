package net.scravy.technetium.handlers;

import junit.framework.Assert;
import net.scravy.technetium.DatabaseTest;
import net.scravy.technetium.modules.auth.Login;
import net.scravy.weblet.Request;
import net.scravy.weblet.RequestMethod;
import net.scravy.weblet.RequestMockup;
import net.scravy.weblet.Response;
import net.scravy.weblet.ResponseMockup;
import net.scravy.weblet.ServletMockup;
import net.scravy.weblet.Weblet;

import org.junit.BeforeClass;
import org.junit.Test;

public class LoginTest {

	static Login loginHandler = new Login();
	static Weblet servlet;

	@BeforeClass
	public static void before() {
		DatabaseTest.init();

		servlet = ServletMockup.genServlet(DatabaseTest.getEntityManager()
				.getEntityManagerFactory());
	}

	@Test
	public void testLogin() {
		Request request = RequestMockup.genRequest(RequestMethod.POST,
				"login?login.username=john.doe&login.password=password");
		Response response = ResponseMockup.genResponse();

		Assert.assertFalse(request.isLoggedIn());
		
		loginHandler.handle(servlet, request, response);
		
		Assert.assertTrue(request.isLoggedIn());
		Assert.assertTrue(response.hasRedirect());
	}

	@Test
	public void testLoginWithEmail() {
		Request request = RequestMockup.genRequest(RequestMethod.POST,
				"login?login.username=john.doe@example.com&login.password=password");
		Response response = ResponseMockup.genResponse();

		Assert.assertFalse(request.isLoggedIn());
		
		loginHandler.handle(servlet, request, response);
		
		Assert.assertTrue(request.isLoggedIn());
		Assert.assertTrue(response.hasRedirect());
	}

	@Test
	public void testLoginFail() {
		Request request = RequestMockup.genRequest(RequestMethod.POST,
				"login?login.username=john.doe&login.password=wrongPassword");
		Response response = ResponseMockup.genResponse();

		Assert.assertFalse(request.isLoggedIn());
		
		loginHandler.handle(servlet, request, response);
		
		Assert.assertFalse(request.isLoggedIn());
		Assert.assertTrue(response.hasRedirect());

		Request redirect = RequestMockup.genRequest(response.getRedirect());
		Assert.assertEquals("login", redirect.getPath()[0]);
		Assert.assertEquals("denied", redirect.getString("login.status"));
	}

	@Test
	public void testLoginUnknownUser() {
		Request request = RequestMockup.genRequest(RequestMethod.POST,
				"login?login.username=aUserThatDoesNotExist@localhost&login.password=wrongPassword");
		Response response = ResponseMockup.genResponse();

		Assert.assertFalse(request.isLoggedIn());
		
		loginHandler.handle(servlet, request, response);
		
		Assert.assertFalse(request.isLoggedIn());
		Assert.assertTrue(response.hasRedirect());

		Request redirect = RequestMockup.genRequest(response.getRedirect());
		Assert.assertEquals("login", redirect.getPath()[0]);
		Assert.assertEquals("unknownUser", redirect.getString("login.status"));
	}

	@Test
	public void testLoginGet() {
		Request request = RequestMockup.genRequest(RequestMethod.GET,
				"login?login.username=john.doe&login.password=password");
		Response response = ResponseMockup.genResponse();

		Assert.assertFalse(request.isLoggedIn());
		
		loginHandler.handle(servlet, request, response);
		
		Assert.assertFalse(request.isLoggedIn());
		Assert.assertFalse(response.hasRedirect());
		Assert.assertEquals("auth-login", response.getTemplate());
	}

	@Test
	public void testLoginLostPassword() {
		Request request = RequestMockup.genRequest(RequestMethod.POST,
				"login?login.username=john.doe&login.password=wrongPassword&login.passwordLost=");
		Response response = ResponseMockup.genResponse();

		Assert.assertFalse(request.isLoggedIn());
		
		loginHandler.handle(servlet, request, response);
		
		Assert.assertFalse(request.isLoggedIn());
		Assert.assertTrue(response.hasRedirect());

		Request redirect = RequestMockup.genRequest(response.getRedirect());
		Assert.assertEquals("forgot", redirect.getPath()[0]);
		Assert.assertEquals("john.doe", redirect.getString("forgot.username"));
	}

}
