package net.scravy.technetium.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "t_program")
@XmlRootElement
public class Program {

	public static enum Status {
		PLANNING, FROZEN, SCHEDULING, PUBLISHED, OVER;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Size(max = 80)
	@NotNull
	@Column(name = "u_name", length = 80, nullable = false)
	private String name;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(
			name = "j_program_title",
			joinColumns = @JoinColumn(name = "f_program",
					referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "f_localized_string",
					referencedColumnName = "id"),
			uniqueConstraints = @UniqueConstraint(columnNames = {
					"f_program", "f_language" }))
	@MapKeyJoinColumn(name = "f_language", referencedColumnName = "id")
	private Map<Language, LocalizedString> title = new HashMap<Language, LocalizedString>();

	@OneToMany(mappedBy = "program")
	private List<Course> courses;

	@Enumerated
	@Column(name = "c_status", nullable = false)
	private Status status = Status.PLANNING;

	@ManyToOne
	@JoinColumn(name = "f_department", referencedColumnName = "id")
	private Department department;

	@XmlTransient
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@XmlAttribute
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@XmlTransient
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@XmlAttribute
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
