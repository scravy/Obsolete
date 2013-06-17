package net.scravy.technetium.modules.auth;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import net.scravy.persistence.EntityManager;
import net.scravy.technetium.util.BCrypt;
import net.scravy.weblet.Handler;
import net.scravy.weblet.Request;
import net.scravy.weblet.RequestMethod;
import net.scravy.weblet.Response;
import net.scravy.weblet.User;
import net.scravy.weblet.Weblet;

public class Login implements Handler {

	@Override
	public void handle(Weblet servlet, Request request, Response response) {
		String userName = request.get("login.username", "");
		String password = request.get("login.password", "");

		TypedQuery<User> query = servlet.db().createNamedQuery(
				"login#getUser",
				User.class);
		query.setParameter("login", userName);
		List<User> persons = query.getResultList();

		if (request.getMethod() != RequestMethod.POST) {
			response.setTemplate("auth-login");
			response.set("loginStatus", request.getString("login.status"));
			response.set("loginUser", userName);
		} else if (request.has("login.passwordLost")) {
			response.setRedirect("forgot?forgot.username=" + userName);
		} else if (persons.size() == 0) {
			response.setRedirect("login?login.status=unknownUser&login.username="
					+ userName);
		} else {
			User p = persons.get(0);
			if (BCrypt.checkpw(password, p.getUserPassword())) {
				p.setLastTimeLoggedIn(new Date());
				EntityManager db = servlet.db();
				db.persist(p);
				db.detach(p);
				request.getSession().setUser(p);
				response.setRedirect("start");
			} else {
				response.setRedirect("login?login.status=denied&login.username="
						+ userName);
			}
		}
	}
}