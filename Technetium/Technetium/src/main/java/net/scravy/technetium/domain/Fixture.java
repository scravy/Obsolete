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
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "t_fixture",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = { "f_timeslot",
						"f_room" }),
				@UniqueConstraint(columnNames = { "f_timeslot",
						"f_lecturer1" }),
				@UniqueConstraint(columnNames = { "f_timeslot",
						"f_lecturer2" }),
				@UniqueConstraint(columnNames = { "f_timeslot",
						"f_lecturer3" }) })
public class Fixture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@JoinColumn(name = "f_timeslot", referencedColumnName = "id")
	private TimeSlot timeslot;

	@ManyToOne
	@JoinColumn(name = "f_room", referencedColumnName = "id")
	private Room room;

	@ManyToOne
	@JoinColumn(name = "f_lecturer1", referencedColumnName = "id")
	private Person mainLecturer;

	@ManyToOne
	@JoinColumn(name = "f_lecturer2", referencedColumnName = "id")
	private Person secondLecturer;

	@ManyToOne
	@JoinColumn(name = "f_lecturer3", referencedColumnName = "id")
	Person thirdLecturer;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TimeSlot getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(TimeSlot timeslot) {
		this.timeslot = timeslot;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Person getMainLecturer() {
		return mainLecturer;
	}

	public void setMainLecturer(Person mainLecturer) {
		this.mainLecturer = mainLecturer;
	}

	public Person getSecondLecturer() {
		return secondLecturer;
	}

	public void setSecondLecturer(Person secondLecturer) {
		this.secondLecturer = secondLecturer;
	}
}
