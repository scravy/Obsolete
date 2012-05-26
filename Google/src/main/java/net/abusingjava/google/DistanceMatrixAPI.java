package net.abusingjava.google;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A Java-Interface for accessing the Google Maps Distance Matrix API
 * 
 * @see <a href="http://code.google.com/apis/maps/documentation/distancematrix/">Distance Matrix API</a>
 */
public class DistanceMatrixAPI {

	final public String URL_ENDPOINT = "http://maps.googleapis.com/maps/api/distancematrix/xml?";
	Distance[][] $matrix = null;
	Exception $exception = null;

	public static class Distance {
		private final int $duration;
		private final int $distance;
		
		Distance(final Element $e) {
			NodeList $n = $e.getElementsByTagName("value");
			$duration = Integer.parseInt($n.item(0).getTextContent());
			$distance = Integer.parseInt($n.item(1).getTextContent());
		}
		
		public int getDuration() {
			return $duration;
		}
		
		public int getDistance() {
			return $distance;
		}
		
		@Override
		public String toString() {
			return String.format("%.3fkm, %dh %dm %ss", $distance / 1000.0, $duration / 3600, ($duration / 60) % 60, $duration % 60);
		}
	}
	
	public static enum Mode {
		DRIVING("driving"),
		WALKING("walking"),
		BICYCLING("bicycling"),

		;

		private final String $modeString;

		Mode(final String $modeString) {
			this.$modeString = $modeString;
		}

		@Override
		public String toString() {
			return $modeString;
		}
	}

	public class Request {
		List<String> $origins = new ArrayList<String>(10);
		List<String> $destinations = new ArrayList<String>(10);
		Mode $mode = Mode.DRIVING;

		Request() {
		}
		
		public void setMode(final Mode $mode) {
			if ($mode != null)
				this.$mode = $mode;
		}

		public void addOrigin(final String $origin) {
			if ($origin != null)
				$origins.add($origin);
		}

		public void addOrigin(final double $latitude, final double $longitude) {
			$origins.add($latitude + "," + $longitude);
		}

		public void addDestination(final String $destination) {
			if ($destination != null)
				$destinations.add($destination);
		}

		public void addDestination(final double $latitude, final double $longitude) {
			$destinations.add($latitude + "," + $longitude);
		}

		public boolean fire() {
			
			if (($origins.size() < 1) || ($destinations.size() < 1)) {
				return false;
			}
			
			try {
				StringBuilder $builder = new StringBuilder(URL_ENDPOINT);

				$builder.append("mode=");
				$builder.append($mode);

				$builder.append("&origins=");
				$builder.append(URLEncoder.encode($origins.get(0), "UTF-8"));
				for (int $i = 1; $i < $origins.size(); $i++) {
					$builder.append('|');
					$builder.append(URLEncoder.encode($origins.get($i), "UTF-8"));
				}
				$builder.append("&destinations=");
				$builder.append(URLEncoder.encode($destinations.get(0), "UTF-8"));
				for (int $i = 1; $i < $destinations.size(); $i++) {
					$builder.append('|');
					$builder.append(URLEncoder.encode($destinations.get($i), "UTF-8"));
				}

				$builder.append("&units=metric");
				$builder.append("&sensor=false");

				InputStream $res = new URL($builder.toString()).openStream();
				DocumentBuilder $b = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document $responseMatrix = $b.parse($res);
				NodeList $rows = $responseMatrix.getElementsByTagName("row");
				
				$matrix = new Distance[$origins.size()][$destinations.size()];
				
				for (int $i = 0; $i < $rows.getLength(); $i++) {
					NodeList $elements = ((Element)$rows.item($i)).getElementsByTagName("element");
					for (int $j = 0; $j < $elements.getLength(); $j++) {
						Element $e = (Element) $elements.item($j);
						if ($e.getElementsByTagName("status").item(0).getTextContent().equals("OK")) {
							$matrix[$i][$j] = new Distance($e);
						} else {
							$matrix[$i][$j] = null;
						}
					}
				}
				
			} catch (Exception $exc) {
				$exception = $exc;
				return false;
			}
			return true;
		}

		public Distance getDistance(final int $i, final int $j) {
			try {
				return $matrix[$i][$j];
			} catch (Exception $exc) {
				return null;
			}
		}
		
		public int getNumberOfOrigins() {
			return $origins.size();
		}
		
		public int getNumberOfDestinations() {
			return $destinations.size();
		}
		
		public Exception getException() {
			return $exception;
		}
	}
	
	DistanceMatrixAPI() {
		
	}
	
	public DistanceMatrixAPI create() {
		return new DistanceMatrixAPI();
	}

	public Request newRequest() {
		return new Request();
	}
}
