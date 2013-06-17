package net.scravy.technetium.domain.ifaces;

import java.util.Map;

import net.scravy.technetium.domain.Language;
import net.scravy.technetium.domain.LocalizedString;

public interface LocalizedTitle {

	Map<Language, LocalizedString> getTitle();

	void setTitle(Map<Language, LocalizedString> title);
}
