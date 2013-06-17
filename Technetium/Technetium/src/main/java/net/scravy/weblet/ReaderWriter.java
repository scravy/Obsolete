package net.scravy.weblet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;

import net.scravy.technetium.util.iterator.IteratorUtil;


public class ReaderWriter implements OutputWriter {

	final InputStream inputStream;
	
	public ReaderWriter(InputStream... inputStreams) {
		if (inputStreams == null || inputStreams.length == 0) {
			throw new IllegalArgumentException("`inputStream` may not be null and must contain at least a single inputStream.");
		}
		if (inputStreams.length == 1) {
			this.inputStream = inputStreams[0];
		} else if (inputStreams.length == 2) {
			this.inputStream = new SequenceInputStream(inputStreams[0], inputStreams[1]);
		} else {
			this.inputStream = new SequenceInputStream(IteratorUtil.enumerate(inputStreams));
		}
	}
	
	@Override
	public void write(OutputStream outputStream) throws IOException {
		int read = 0;
		byte[] buffer = new byte[1024];
		while ((read = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, read);
		}
	}

}
