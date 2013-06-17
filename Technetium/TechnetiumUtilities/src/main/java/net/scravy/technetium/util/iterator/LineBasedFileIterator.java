package net.scravy.technetium.util.iterator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import net.scravy.technetium.util.IOUtil;
import net.scravy.technetium.util.RuntimeIOException;

class LineBasedFileIterator extends GenericIterableIterator<String> {

	final BufferedReader reader;

	LineBasedFileIterator(File file) throws FileNotFoundException {
		reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), IOUtil.UTF8));
	}

	LineBasedFileIterator(File file, Charset charset)
			throws FileNotFoundException {
		reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), charset));
	}

	@Override
	protected String nextElement() {
		try {
			return reader.readLine();
		} catch (IOException exc) {
			throw new RuntimeIOException(exc);
		}
	}
}