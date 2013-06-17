package net.scravy.technetium.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import net.scravy.technetium.domain.ifaces.HasId;
import net.scravy.technetium.domain.ifaces.LocalizedTitle;
import net.scravy.technetium.domain.ifaces.MultiLanguagePage;

@Entity
@Table(name = "t_course_page", uniqueConstraints = @UniqueConstraint(
		columnNames = { "f_course", "u_name" }))
@XmlRootElement
public class CoursePage implements MultiLanguagePage, HasId, LocalizedTitle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "f_course", nullable = false)
	private Course course;

	@NotNull
	@Size(max = 80)
	@Column(name = "u_name", nullable = false, length = 80)
	private String name;

	@NotNull
	@Column(name = "c_date_created", updatable = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated = new Date();

	@NotNull
	@Column(name = "c_date_last_modified", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateLastModified = new Date();

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(
			name = "j_course_page_title",
			joinColumns = @JoinColumn(
					name = "f_course_page",
					referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "f_localized_string",
					referencedColumnName = "id"),
			uniqueConstraints = @UniqueConstraint(
					columnNames = { "f_course_page", "f_language" }))
	@MapKeyJoinColumn(name = "f_language", referencedColumnName = "id")
	private Map<Language, LocalizedString> title = new HashMap<Language, LocalizedString>();

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(
			name = "j_course_page_content",
			joinColumns = @JoinColumn(
					name = "f_course_page",
					referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "f_localized_text",
					referencedColumnName = "id"),
			uniqueConstraints = @UniqueConstraint(
					columnNames = { "f_course_page", "f_language" }))
	@MapKeyJoinColumn(name = "f_language", referencedColumnName = "id")
	private Map<Language, LocalizedText> content = new HashMap<Language, LocalizedText>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Language, LocalizedString> getTitle() {
		return title;
	}

	public void setTitle(Map<Language, LocalizedString> title) {
		this.title = title;
	}

	public Map<Language, LocalizedText> getContent() {
		return content;
	}

	public void setContent(Map<Language, LocalizedText> content) {
		this.content = content;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateLastModified() {
		return dateLastModified;
	}

	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
