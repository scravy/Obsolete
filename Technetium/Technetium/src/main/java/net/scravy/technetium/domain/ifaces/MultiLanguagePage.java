package net.scravy.technetium.domain.ifaces;

import java.util.Map;

import net.scravy.technetium.domain.Language;
import net.scravy.technetium.domain.LocalizedString;
import net.scravy.technetium.domain.LocalizedText;

public interface MultiLanguagePage {
	
	Map<Language, LocalizedString> getTitle();

	void setTitle(Map<Language, LocalizedString> text);

	Map<Language, LocalizedText> getContent();

	void setContent(Map<Language, LocalizedText> text);
}
