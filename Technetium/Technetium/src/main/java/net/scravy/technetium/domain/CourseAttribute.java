package net.scravy.technetium.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.scravy.technetium.domain.ifaces.HasId;

@Entity
@Table(name = "t_course_attribute", uniqueConstraints = { @UniqueConstraint(
		columnNames = { "f_course", "f_template" }) })
@XmlRootElement
public class CourseAttribute implements HasId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@NotNull
	@JoinColumn(
			name = "f_course",
			nullable = false,
			referencedColumnName = "id")
	private Course course;

	@ManyToOne
	@NotNull
	@JoinColumn(
			name = "f_template",
			nullable = false,
			referencedColumnName = "id")
	private CourseAttributeTemplate template;

	@Column(name = "c_string_value", nullable = true)
	private String stringValue;

	@Column(name = "c_int_value", nullable = true)
	private Integer intValue;

	@Override
	@XmlAttribute
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public CourseAttributeTemplate getTemplate() {
		return template;
	}

	public void setTemplate(CourseAttributeTemplate template) {
		this.template = template;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Integer getIntValue() {
		return intValue;
	}

	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}

}
