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
@Table(name = "t_person_attribute", uniqueConstraints = { @UniqueConstraint(
		columnNames = { "f_person", "f_template" }) })
@XmlRootElement
public class PersonAttribute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@NotNull
	@JoinColumn(
			name = "f_person",
			referencedColumnName = "id",
			nullable = false)
	private Person person;

	@ManyToOne
	@NotNull
	@JoinColumn(
			name = "f_template",
			referencedColumnName = "id",
			nullable = false)
	private PersonAttributeTemplate template;

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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public PersonAttributeTemplate getTemplate() {
		return template;
	}

	public void setTemplate(PersonAttributeTemplate template) {
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
