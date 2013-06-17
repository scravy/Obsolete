package net.scravy.technetium.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import net.scravy.technetium.domain.ifaces.HasId;

@Entity
@Table(name = "t_course_page_permission",
		uniqueConstraints = @UniqueConstraint(columnNames = {
				"f_page", "f_given_to", "f_privilege" }))
@XmlRootElement
public class CoursePagePermission implements HasId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@JoinColumn(name = "f_page")
	private CoursePage page;

	@ManyToOne
	@JoinColumn(name = "f_given_to")
	private Person givenTo;

	@ManyToOne
	@JoinColumn(name = "f_privilege")
	private Privilege privilege;

	@ManyToOne
	@JoinColumn(name = "f_given_by")
	private Person givenBy;

	@Column(name = "c_given_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date givenAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Person getGivenTo() {
		return givenTo;
	}

	public void setGivenTo(Person givenTo) {
		this.givenTo = givenTo;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public Person getGivenBy() {
		return givenBy;
	}

	public void setGivenBy(Person givenBy) {
		this.givenBy = givenBy;
	}

	public Date getGivenAt() {
		return givenAt;
	}

	public void setGivenAt(Date givenAt) {
		this.givenAt = givenAt;
	}

	public CoursePage getPage() {
		return page;
	}

	public void setPage(CoursePage page) {
		this.page = page;
	}

}
