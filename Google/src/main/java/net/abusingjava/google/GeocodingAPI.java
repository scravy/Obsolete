package net.abusingjava.google;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A Java-Interface for accessing the Google Maps Geocoding API.
 * 
 * @see <a href="http://code.google.com/apis/maps/documentation/geocoding/">Geocoding API</a>
 */
public class GeocodingAPI {

	final public String URL_ENDPOINT = "http://maps.googleapis.com/maps/api/geocode/xml?";
	final DocumentBuilder $builder;

	public static enum StatusCode {
		OK,
		ZERO_RESULTS,
		OVER_QUERY_LIMIT,
		REQUEST_DENIED,
		INVALID_REQUEST,
	}

	public class Geocode {
		Exception $exception;
		
		String $locality = null;
		String $country = null;
		String $sublocality = null;
		String $postalCode = null;
		String $streetNumber = null;
		
		String $route = null;
		
		double $latitude, $longitude;
		Bounds $bounds = null;

		Geocode(final Element $e) {
			NodeList $n = $e.getElementsByTagName("address_component");
			for (int $i = 0; $i < $n.getLength(); $i++) {
				Element $c = (Element) $n.item($i);
				NodeList $t = $c.getElementsByTagName("type");
				for (int $j = 0; $j < $t.getLength(); $j++) {
					String $type = $t.item($j).getTextContent();
					String $longName = $c.getElementsByTagName("long_name").item(0).getTextContent();
					if ("locality".equals($type)) {
						$locality = $longName;
					} else if ("country".equals($type)) {
						$country = $longName;
					} else if ("sublocality".equals($type)) {
						$sublocality = $longName;
					} else if ("street_number".equals($type)) {
						$streetNumber = $longName;
					} else if ("postal_code".equals($type)) {
						$postalCode = $longName;
					} else if ("route".equals($type)) {
						$route = $longName;
					}
				}
			}
			$n = $e.getElementsByTagName("geometry");
			if ($n.getLength() > 0) {
				Element $c = (Element) $n.item(0);
				
				$n = $c.getElementsByTagName("location");
				Double.parseDouble(((Element)$n.item(0)).getElementsByTagName("lat").item(0).getTextContent());
				Double.parseDouble(((Element)$n.item(0)).getElementsByTagName("lng").item(0).getTextContent());
				
				try {
					double $north, $east, $west, $south;
					$n = $c.getElementsByTagName("southwest");
					$south = Double.parseDouble(((Element)$n.item(0)).getElementsByTagName("lat").item(0).getTextContent());
					$west = Double.parseDouble(((Element)$n.item(0)).getElementsByTagName("lng").item(0).getTextContent());
					$n = $c.getElementsByTagName("northeast");
					$north = Double.parseDouble(((Element)$n.item(0)).getElementsByTagName("lat").item(0).getTextContent());
					$east = Double.parseDouble(((Element)$n.item(0)).getElementsByTagName("lng").item(0).getTextContent());
					$bounds = new Bounds($north, $east, $west, $south);
				} catch (Exception $exc) {
					$exception = $exc;
				}
			}
		}

		public String getLocality() {
			return $locality;
		}

		public String getCountry() {
			return $country;
		}

		public String getSublocality() {
			return $sublocality;
		}

		public String getPostalCode() {
			return $postalCode;
		}

		public String getStreetNumber() {
			return $streetNumber;
		}

		public String getRoute() {
			return $route;
		}

		public double getLatitude() {
			return $latitude;
		}

		public double getLongitude() {
			return $longitude;
		}

		public Bounds getBounds() {
			return $bounds;
		}

		public Exception getException() {
			return $exception;
		}
	}

	static class Bounds {
		final double $north;
		final double $east;
		final double $south;
		final double $west;
		
		Bounds(final double $north, final double $east, final double $south, final double $west) {
			this.$north = $north;
			this.$east = $east;
			this.$south = $south;
			this.$west = $west;
		}
	}

	public class Request implements Iterable<Geocode> {
		
		private String $address = "";
		private final List<Geocode> $geocodes = new ArrayList<Geocode>(2);
		private Bounds $bounds = null;
		private Exception $exception;
		private String $language = null;
		private final StatusCode $status = null;
		
		Request() {
			
		}
		
		Request(final String $address) {
			setAddress($address);
		}
		
		public void setAddress(final String $address) {
			this.$address = $address;
		}
		
		public void setBounds(final double $north, final double $east, final double $south, final double $west) {
			$bounds = new Bounds($north, $east, $south, $west);
		}
		
		public void setLanguage(final String $language) {
			this.$language = $language;
		}

		public boolean fire() {
			try {
				StringBuilder $args = new StringBuilder();
				if ($language != null) {
					$args.append("language=");
					$args.append($language);
					$args.append('&');
				}
				if ($bounds != null) {
					$args.append("bounds=");
					$args.append($bounds.$south);
					$args.append(',');
					$args.append($bounds.$west);
					$args.append('|');
					$args.append($bounds.$north);
					$args.append(',');
					$args.append($bounds.$east);
				}
				$args.append("sensor=false&address=");
				InputStream $res = new URL(URL_ENDPOINT + $args + URLEncoder.encode($address, "UTF-8")).openStream();
				Element $doc = $builder.parse($res).getDocumentElement();
				if ($doc.getElementsByTagName("status").item(0).getTextContent().equals("OK")) {
					NodeList $l = $doc.getElementsByTagName("result");
					for (int $i = 0; $i < $l.getLength(); $i++) {
						$geocodes.add(new Geocode((Element) $l.item($i)));
					}
					return true;
				}
			} catch (Exception $exc) {
				$exception = $exc;
			}
			return false;
		}
		
		public StatusCode getStatus() {
			return $status;
		}
		
		public Exception getException() {
			return $exception;
		}
		
		@Override
		public Iterator<Geocode> iterator() {
			return $geocodes.iterator();
		}
	}

	GeocodingAPI() {
		try {
			$builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (Exception $exc) {
			throw new RuntimeException($exc);
		}
	}
	
	public GeocodingAPI create() {
		return new GeocodingAPI();
	}

	public Request newRequest() {
		return new Request();
	}
	
	public Request newRequest(final String $address) {
		return new Request($address);
	}
}
