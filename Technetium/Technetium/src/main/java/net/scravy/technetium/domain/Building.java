package net.scravy.technetium.domain;

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
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.scravy.technetium.domain.ifaces.HasId;
import net.scravy.technetium.domain.ifaces.HasName;

@Entity
@Table(name = "t_building")
@XmlRootElement
public class Building implements HasId, HasName {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Size(max = 80)
	@NotNull
	@Column(name = "u_name", length = 80, nullable = false, unique = true)
	private String name;

	@OneToMany(mappedBy = "building")
	private List<Room> rooms;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(
			name = "j_building_title",
			joinColumns = @JoinColumn(name = "f_building",
					referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "f_localized_string",
					referencedColumnName = "id"),
			uniqueConstraints = @UniqueConstraint(columnNames = {
					"f_building", "f_language" }))
	@MapKeyJoinColumn(name = "f_language", referencedColumnName = "id")
	private Map<Language, LocalizedString> title = new HashMap<Language, LocalizedString>();

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(
			name = "j_building_description",
			joinColumns = @JoinColumn(name = "f_building",
					referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "f_localized_text",
					referencedColumnName = "id"),
			uniqueConstraints = @UniqueConstraint(columnNames = {
					"f_building", "f_language" }))
	@MapKeyJoinColumn(name = "f_language", referencedColumnName = "id")
	private Map<Language, LocalizedText> description = new HashMap<Language, LocalizedText>();

	public Building() {

	}

	public Building(String name) {
		this.name = name;
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

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
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
}
