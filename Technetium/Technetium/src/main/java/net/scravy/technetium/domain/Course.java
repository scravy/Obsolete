package net.scravy.technetium.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.scravy.technetium.domain.ifaces.HasId;
import net.scravy.technetium.domain.ifaces.HasName;
import net.scravy.technetium.util.value.Tuple;

@Entity
@Table(name = "t_course", uniqueConstraints = { @UniqueConstraint(
		columnNames = { "f_program", "u_name" }) })
@XmlRootElement
public class Course implements HasId, HasName {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Size(max = 80)
	@NotNull
	@Column(name = "u_name", length = 80, nullable = false)
	private String name;

	@Min(1)
	@Column(name = "c_duration", nullable = false)
	private int duration;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(
			name = "j_course_title",
			joinColumns = @JoinColumn(name = "f_course",
					referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "f_localized_string",
					referencedColumnName = "id"),
			uniqueConstraints = @UniqueConstraint(columnNames = {
					"f_course", "f_language" }))
	@MapKeyJoinColumn(name = "f_language", referencedColumnName = "id")
	private Map<Language, LocalizedString> title = new HashMap<Language, LocalizedString>();

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(
			name = "j_course_description",
			joinColumns = @JoinColumn(name = "f_course",
					referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "f_localized_text",
					referencedColumnName = "id"),
			uniqueConstraints = @UniqueConstraint(columnNames = {
					"f_course", "f_language" }))
	@MapKeyJoinColumn(name = "f_language", referencedColumnName = "id")
	private Map<Language, LocalizedText> description = new HashMap<Language, LocalizedText>();

	@NotNull
	@JoinColumn(name = "f_program", nullable = false,
			referencedColumnName = "id")
	@ManyToOne
	private Program program;

	@ManyToOne
	private Person lecturer;

	@ManyToMany
	private List<Person> possibleLecturers = new ArrayList<Person>();

	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	private List<CoursePage> pages = new ArrayList<CoursePage>();

	@OneToMany(mappedBy = "course")
	@MapKeyColumn(name = "f_template")
	private Map<CourseAttributeTemplate, CourseAttribute> courseAttributes =
			new HashMap<CourseAttributeTemplate, CourseAttribute>();

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(
			name = "j_course_unit_groups",
			joinColumns = @JoinColumn(name = "f_course",
					referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "f_course_unit_group",
					referencedColumnName = "id"),
			uniqueConstraints = @UniqueConstraint(columnNames = {
					"f_course", "f_course_unit_group" }))
	@MapKeyJoinColumn(name = "f_language", referencedColumnName = "id")
	private Map<CourseUnitType, CourseUnitGroup> courseUnitGroups =
			new HashMap<CourseUnitType, CourseUnitGroup>();

	public Course() {

	}

	public Course(String name, Tuple<Language, LocalizedString>... titles) {
		this.setName(name);
	}

	@Override
	@XmlAttribute
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	@XmlAttribute
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public List<CoursePage> getPages() {
		return pages;
	}

	public void setPages(List<CoursePage> pages) {
		this.pages = pages;
	}

	public Map<Language, LocalizedString> getTitle() {
		return title;
	}

	public void setTitle(Map<Language, LocalizedString> title) {
		this.title = title;
	}

	public Map<Language, LocalizedText> getDescription() {
		return description;
	}

	public void setDescription(Map<Language, LocalizedText> description) {
		this.description = description;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public Map<CourseAttributeTemplate, CourseAttribute> getCourseAttributes() {
		return courseAttributes;
	}

	public void setCourseAttributes(
			Map<CourseAttributeTemplate, CourseAttribute> courseAttributes) {
		this.courseAttributes = courseAttributes;
	}

	public List<Person> getPossibleLecturers() {
		return possibleLecturers;
	}

	public void setPossibleLecturers(List<Person> possibleLecturers) {
		this.possibleLecturers = possibleLecturers;
	}

	public Person getLecturer() {
		return lecturer;
	}

	public void setLecturer(Person lecturer) {
		this.lecturer = lecturer;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Map<CourseUnitType, CourseUnitGroup> getCourseUnitGroups() {
		return courseUnitGroups;
	}

	public void setCourseUnitGroups(
			Map<CourseUnitType, CourseUnitGroup> courseUnitGroups) {
		this.courseUnitGroups = courseUnitGroups;
	}

}
