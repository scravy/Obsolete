package net.scravy.technetium.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "t_nation")
public class Nation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Size(max = 3)
	@Column(name = "u_iso_code", length = 3, unique = true)
	private String isoCode;
	
	@Size(max = 80)
	@Column(name = "c_short_de", length = 80)
	private String shortDe;
	
	@Size(max = 80)
	@Column(name = "c_long_de", length = 80)
	private String longDe;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getShortDe() {
		return shortDe;
	}

	public void setShortDe(String shortDe) {
		this.shortDe = shortDe;
	}

	public String getLongDe() {
		return longDe;
	}

	public void setLongDe(String longDe) {
		this.longDe = longDe;
	}

}
