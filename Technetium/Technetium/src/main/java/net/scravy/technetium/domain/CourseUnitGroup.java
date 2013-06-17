package net.scravy.technetium.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import net.scravy.technetium.domain.ifaces.HasId;

@Entity
@Table(name = "t_course_unit_group")
public class CourseUnitGroup implements HasId {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@NotNull
	@JoinColumn(
			name = "f_course_unit_type",
			nullable = false,
			referencedColumnName = "id")
	private CourseUnitType type;

	@ManyToOne
	@NotNull
	@JoinColumn(
			name = "f_course",
			nullable = false,
			referencedColumnName = "id")
	private Course course;

	@OneToMany
	private List<CourseUnit> courses = new ArrayList<CourseUnit>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CourseUnitType getType() {
		return type;
	}

	public void setType(CourseUnitType type) {
		this.type = type;
	}

	public List<CourseUnit> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseUnit> courses) {
		this.courses = courses;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}