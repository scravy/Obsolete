package net.scravy.technetium.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.scravy.weblet.User;

@XmlRootElement
@Entity
@Table(name = "t_person")
@NamedQueries({
		@NamedQuery(
				name = "login#getUser",
				query = "SELECT p FROM Person p WHERE p.loginName = :login OR p.email = :login"),
		@NamedQuery(
				name = "Auth#getUserByLoginName",
				query = "SELECT p FROM Person p WHERE p.loginName = :loginName"),
		@NamedQuery(name = "users#findAll", query = "SELECT p FROM Person p"),
		@NamedQuery(
				name = "users#search",
				query = "SELECT p FROM Person p WHERE "
						+ "p.firstName LIKE :query ESCAPE '\\' OR "
						+ "p.lastName LIKE :query ESCAPE '\\' OR "
						+ "p.loginName LIKE :query ESCAPE '\\' OR "
						+ "p.displayName LIKE :query ESCAPE '\\' "),
		@NamedQuery(
				name = "Auth#checkLoginName",
				query = "SELECT count(p) FROM Person p WHERE p.loginName = :loginName"),
		@NamedQuery(
				name = "users#checkEmail",
				query = "SELECT count(p) FROM Person p WHERE p.email = :email"),
		@NamedQuery(name = "users#count",
				query = "SELECT count(p) FROM Person p") })
public class Person implements User, Comparable<Person> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "c_password", length = 200)
	private String password;

	@Size(max = 80)
	@Column(name = "c_first_name", length = 80)
	private String firstName;

	@Size(max = 80)
	@Column(name = "c_last_name", length = 80)
	private String lastName;

	@Size(max = 40)
	@Column(name = "u_login_name", length = 40, unique = true)
	private String loginName;

	@Size(max = 40)
	@Column(name = "c_display_name", length = 40)
	private String displayName;

	@Size(max = 200)
	@Column(name = "u_email", length = 200, unique = true)
	private String email;

	@NotNull
	@Column(name = "c_date_created", updatable = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated = new Date();

	@Column(name = "c_last_time_logged_in")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastTimeLoggedIn;

	@ManyToOne
	@JoinColumn(name = "c_preferred_language", referencedColumnName = "id")
	private Language preferredLanguage = null;

	@OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
	private List<PersonPage> pages = new ArrayList<PersonPage>();

	@ManyToMany
	private List<TimeSlot> notAvailable = new ArrayList<TimeSlot>();

	@Column(name = "c_time_zone")
	private String timeZone;

	public Person() {

	}

	public Person(String loginName) {
		setLoginName(loginName);
	}

	public Person(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
		setLoginName(firstName.toLowerCase() + '.' + lastName.toLowerCase());
		setDisplayName(firstName.toUpperCase().charAt(0) + ". " + lastName);
	}

	public Person(String firstName, String lastName, String loginName) {
		setFirstName(firstName);
		setLastName(lastName);
		setLoginName(loginName);
		setDisplayName(firstName.toUpperCase().charAt(0) + ". " + lastName);
	}

	public Person(String firstName, String lastName, String loginName,
			String displayName) {
		setFirstName(firstName);
		setLastName(lastName);
		setLoginName(loginName);
		setDisplayName(displayName);
	}

	@XmlAttribute
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlAttribute
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlAttribute
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@XmlAttribute
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlAttribute
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@XmlTransient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlAttribute
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@XmlTransient
	@Override
	public String getUserPassword() {
		return getPassword();
	}

	@Override
	public void setUserPassword(String password) {
		setPassword(password);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> clazz) {
		if (clazz.equals(Person.class)) {
			return (T) this;
		}
		return null;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public Date getLastTimeLoggedIn() {
		return lastTimeLoggedIn;
	}

	@Override
	public void setLastTimeLoggedIn(Date lastTimeLoggedIn) {
		this.lastTimeLoggedIn = lastTimeLoggedIn;
	}

	public Language getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(Language preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	public List<PersonPage> getPages() {
		return pages;
	}

	public void setPages(List<PersonPage> pages) {
		this.pages = pages;
	}

	@Override
	public int hashCode() {
		return (int) (id % Integer.MAX_VALUE);
	}

	@Override
	public boolean equals(Object object) {
		return object instanceof Person && ((Person) object).id == id;
	}

	@Override
	public int compareTo(Person person) {
		long diff = id - person.id;
		if (diff < 0) {
			return -1;
		} else if (diff > 0) {
			return 1;
		}
		return 0;
	}

	public List<TimeSlot> getNotAvailable() {
		return notAvailable;
	}

	public void setNotAvailable(List<TimeSlot> notAvailable) {
		this.notAvailable = notAvailable;
	}

	@Override
	public TimeZone getUserTimeZone() {
		String timeZone = getTimeZone();
		return timeZone == null ? null : TimeZone.getTimeZone(timeZone);
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

}
