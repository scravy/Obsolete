package net.scravy.technetium.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.scravy.technetium.domain.ifaces.HasId;

@Entity
@Table(name = "t_course_unit")
@XmlRootElement
public class CourseUnit implements HasId {
	
	@Id
	@Column(name = "id")
	private long id;

	@Min(1)
	@Column(name = "c_duration")
	private int duration;
	
	@XmlAttribute
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}
	
}