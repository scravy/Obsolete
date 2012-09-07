package net.abusingjava.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Iterator;

import net.abusingjava.Author;
import net.abusingjava.Since;
import net.abusingjava.Version;

@Author("Julian Fleischer")
@Version("2011-11-14")
@Since("2011-11-14")
public class LineReader implements Iterable<String> {

	final BufferedReader $reader;
	
	public LineReader(final InputStream $in) {
		$reader = new BufferedReader(new InputStreamReader($in));
	}
	
	public LineReader(final InputStream $in, final Charset $charset) {
		$reader = new BufferedReader(new InputStreamReader($in, $charset));
	}
	
	public LineReader(final InputStream $in, final String $charset) {
		$reader = new BufferedReader(new InputStreamReader($in, Charset.forName($charset)));
	}
	
	public LineReader(final Reader $in) {
		$reader = new BufferedReader($in);
	}
	
	class It implements Iterator<String> {

		String $line;
		
		It() {
			try {
				$line = $reader.readLine();
			} catch (IOException $exc) {
				$line = null;
			}
		}
		
		@Override
		public boolean hasNext() {
			return $line != null;
		}

		@Override
		public String next() {
			String $currentLine = $line;
			try {
				$line = $reader.readLine();
			} catch (IOException $exc) {
				$line = null;
			}
			return $currentLine;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public Iterator<String> iterator() {
		return new It();
	}
	
}
