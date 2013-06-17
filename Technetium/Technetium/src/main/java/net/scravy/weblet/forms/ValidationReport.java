package net.scravy.weblet.forms;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "validation")
public class ValidationReport {

	private List<ValidationViolation> validationViolations =
			new ArrayList<ValidationViolation>();

	ValidationReport() {
		// JAXB needs a no-arg default constructor.
	}

	ValidationReport(List<ValidationViolation> violations) {
		this.setValidationViolations(violations);
	}

	@XmlElements(value = @XmlElement(name = "violation"))
	public List<ValidationViolation> getValidationViolations() {
		return validationViolations;
	}

	public void setValidationViolations(List<ValidationViolation> validationViolations) {
		this.validationViolations = validationViolations;
	}

}