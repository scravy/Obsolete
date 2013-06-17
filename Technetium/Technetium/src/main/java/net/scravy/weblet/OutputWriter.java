package net.scravy.weblet;

import java.io.IOException;
import java.io.OutputStream;

public interface OutputWriter {
	void write(OutputStream outputStream) throws IOException;
}