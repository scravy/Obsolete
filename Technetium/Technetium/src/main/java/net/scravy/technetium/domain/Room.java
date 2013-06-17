package net.scravy.technetium.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.scravy.technetium.domain.ifaces.HasId;

@Entity
@Table(name = "t_room", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"f_building", "u_name" }) })
@XmlRootElement
public class Room implements HasId {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "c_capacity", nullable = false)
	@Min(1)
	private int capacity;

	@Size(max = 80)
	@NotNull
	@Column(name = "u_name", length = 80, nullable = false)
	private String name;

	@NotNull
	@JoinColumn(name = "f_building", referencedColumnName = "id",
			nullable = false)
	@ManyToOne
	private Building building;

	@ManyToMany
	private List<TimeSlot> notAvailable = new ArrayList<TimeSlot>();

	public Room() {

	}

	public Room(int capacity) {
		this.capacity = capacity;
	}

	public Room(Building building) {
		this.building = building;
	}

	public Room(String name) {
		this.name = name;
	}

	public Room(Building building, int capacity) {
		this.building = building;
		this.capacity = capacity;
	}

	public Room(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
	}

	public Room(Building building, String name) {
		this.building = building;
		this.name = name;
	}

	public Room(Building building, String name, int capacity) {
		this.building = building;
		this.name = name;
		this.capacity = capacity;
	}

	@XmlAttribute
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public List<TimeSlot> getNotAvailable() {
		return notAvailable;
	}

	public void setNotAvailable(List<TimeSlot> notAvailable) {
		this.notAvailable = notAvailable;
	}

	@XmlAttribute
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
