package net.abusingjava.io;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.abusingjava.collections.AbusingCollections;
import net.abusingjava.collections.SetMap;

public class SetMapParser {

	public static SetMap<String,String> parse(final String $regEx, final InputStream $stream, final Charset $charset) throws IOException {
		SetMap<String,String> $setMap = AbusingCollections.createSetMap();
		BufferedReader $reader = new BufferedReader(new InputStreamReader($stream, $charset));
		
		Pattern $pattern = Pattern.compile($regEx);
		
		String $currentString = "";
		String $line = "";
		while (($line = $reader.readLine()) != null) {
			Matcher $m = $pattern.matcher($line);
			if ($m.matches()) {
				if ($m.groupCount() > 0) {
					$currentString = $m.groupCount() > 0 ? $m.group(1) : $line;
				}
			} else {
				$setMap.add($currentString, $line.trim());
			}
		}
		
		return $setMap;
	}
	
	public static void main(final String... $args) throws Exception {
		
		SetMap<String,String> $map = parse("^# (.+)", new ByteArrayInputStream("# A\na\na\nc\n# B\na\nb\na\nc".getBytes()), AbusingIO.UTF8);
		
		System.out.println($map.keySet());
		System.out.println($map.valuesForKey("B"));
		
		for (Entry<String,Charset> $e : Charset.availableCharsets().entrySet()) {
			System.out.println($e.getKey());
		}
	}
}
