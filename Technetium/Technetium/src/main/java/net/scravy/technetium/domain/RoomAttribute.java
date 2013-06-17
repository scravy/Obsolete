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

@Entity
@Table(name = "t_room_attribute", uniqueConstraints = { @UniqueConstraint(
		columnNames = { "f_room", "f_template" }) })
@XmlRootElement
public class RoomAttribute {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "f_room", referencedColumnName = "id", nullable = false)
	private Room room;

	@ManyToOne
	@NotNull
	@JoinColumn(
			name = "f_template",
			referencedColumnName = "id",
			nullable = false)
	private RoomAttributeTemplate template;

	@Column(name = "c_string_value", nullable = true)
	private String stringValue;

	@Column(name = "c_int_value", nullable = true)
	private Integer intValue;

	@XmlAttribute
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public RoomAttributeTemplate getTemplate() {
		return template;
	}

	public void setTemplate(RoomAttributeTemplate template) {
		this.template = template;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
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
