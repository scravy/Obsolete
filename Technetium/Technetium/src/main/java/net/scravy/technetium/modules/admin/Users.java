package net.scravy.technetium.modules.admin;

import javax.persistence.TypedQuery;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import net.scravy.technetium.domain.Person;
import net.scravy.technetium.util.BCrypt;
import net.scravy.weblet.Handler;
import net.scravy.weblet.Request;
import net.scravy.weblet.RequestMethod;
import net.scravy.weblet.Response;
import net.scravy.weblet.User;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.transform.TransformerWeblet;

import org.w3c.dom.Element;

public class Users implements Handler {

	@Override
	public void handle(Weblet servlet, Request request, Response response)
			throws JAXBException {
		if (request.isLoggedIn()) {
			if (request.getMethod() == RequestMethod.POST) {
				if (request.has("action.createNewUser")) {
					Person p = new Person();

					p.setFirstName(request.getString("firstName"));
					p.setLastName(request.getString("lastName"));
					p.setEmail(request.getString("email"));
					p.setDisplayName(request.getString("displayName"));
					p.setLoginName(request.getString("loginName"));
					p.setPassword(BCrypt.hashpw(request.getString("password"),
							BCrypt.gensalt()));
					
					servlet.db().persist(p);
					
					response.setRedirect("/admin/users");
					return;
				}
			}

			response.setTemplate("admin-users");

			Element users = response.createElement("users");

			TypedQuery<User> query;
			if (request.has("search.query")
					&& !request.getString("search.query").isEmpty()) {
				String needle = request.getString("search.query");
				query = servlet.db().createNamedQuery("users#search",
						User.class);
				query.setParameter("query",
						'%' + servlet.db().escapeLike(needle) + '%');

				response.set("query", needle);
			} else {
				query = servlet.db().createNamedQuery("users#findAll",
						User.class);
			}

			users.setAttribute("count",
					servlet.db().createNamedQuery("users#count", Long.class)
							.getSingleResult().toString());

			Marshaller m = servlet.unwrap(TransformerWeblet.class)
					.createMarshaller();

			for (User p : query.getResultList()) {
				m.marshal(p, users);
			}

			response.append(users);
		}
	}
}
