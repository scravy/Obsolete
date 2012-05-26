package net.abusingjava.mockups;

import net.abusingjava.Author;
import net.abusingjava.Version;


@Author("Julian Fleischer")
@Version("2011-07-24")
public interface SpecificValueProvider<T> {
	T provide();
}
