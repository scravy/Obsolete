package net.scravy.technetium.domain.ifaces;

import java.util.Date;

public interface TimeRecording {

	Date getDateCreated();

	void setDateCreated(Date dateCreated);

	Date getDateLastModified();

	void setDateLastModified(Date dateLastModified);

}
