/* Part of the AbusingJava-Library.
 * 
 * Source:  http://github.com/scravy/AbusingJava
 * Home:    http://www.abusingjava.net/
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.abusingjava.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathFactory;

import net.abusingjava.AbusingReflection;
import net.abusingjava.AbusingStrings;
import net.abusingjava.Author;
import net.abusingjava.Version;

import org.w3c.dom.*;

@Author("Julian Fleischer")
@Version("2011-06-20")
/**
 * 
 */
public class AbusingXML {

	/**
	 * Loads an XML object from a file.
	 * <p>
	 * This loads an XML file into an object according to the structure defined
	 * in $class. Have a look at {@link XmlElement} and her sisters for further
	 * info.
	 * <p>
	 * It is worth noting that this function will not throw a
	 * {@link FileNotFoundException} but silently return null in case of an
	 * error.
	 * 
	 * @param <T>
	 *            The class which holds the definition for this XML structure.
	 * @param $file
	 *            The File to obtain the XML data from.
	 * @param $class
	 *            The actual class object which represents T.
	 * @return An object of the specified class T or null, if any kind of
	 *         exception occured.
	 */
	public static <T> T loadXML(final File $file, final Class<T> $class) {
		try {
			return loadXML(new FileInputStream($file), $class);
		} catch (FileNotFoundException $exc) {
			return null;
		}
	}

	/**
	 * Loads an XML object from an arbitrary InputStream.
	 * <p>
	 * This loads an XML file into an object according to the structure defined
	 * in $class. Have a look at {@link XmlElement} and her sisters for further
	 * info.
	 * <p>
	 * To load an XML object from a remote URL (such as an RSS feed), simply use
	 * the URL-class:
	 * <code>AbusingXML.loadXML(new URL("Your URL").openStream(), YourClass.class);</code>
	 * 
	 * @param <T>
	 *            The class which holds the definition for this XML structure.
	 * @param $stream
	 *            The InputStream to read the XML data from.
	 * @param $class
	 *            The actual class object which represents T.
	 * @return An object of the specified class T or null, if any kind of
	 *         exception occured.
	 */
	public static <T> T loadXML(final InputStream $stream, final Class<T> $class) {
		try {
			DocumentBuilderFactory $factory = DocumentBuilderFactory.newInstance();
			$factory.setValidating(false);
			$factory.setNamespaceAware(true);
			$factory.setCoalescing(true);
			DocumentBuilder $builder = $factory.newDocumentBuilder();
			Document $document = $builder.parse($stream);

			XPathFactory $xpathFactory = XPathFactory.newInstance();
			XPath $xpath = $xpathFactory.newXPath();

			Element $root = $document.getDocumentElement();

			return loadElement($root, $class, $xpath);
		} catch (Exception $exc) {
			throw new RuntimeException($exc);
		}
	}

	/**
	 * Loads an XML object from a DOM-Document.
	 * 
	 * @param <T>
	 *            The class which holds the definition for this XML structure.
	 * @param $document
	 *            The Document which contains the XML-Data.
	 * @param $class
	 *            The actual class object which represents T.
	 * @return An object of the specified class T or null, if any kind of
	 *         exception occured.
	 */
	public static <T> T loadXML(final Document $document, final Class<T> $class) {
		try {
			XPathFactory $xpathFactory = XPathFactory.newInstance();
			XPath $xpath = $xpathFactory.newXPath();

			Element $root = $document.getDocumentElement();

			return loadElement($root, $class, $xpath);
		} catch (Exception $exc) {
			throw new RuntimeException($exc);
		}
	}

	private static <T> T loadElement(final Element $element, final Class<T> $class, final XPath $xpath)
			throws IllegalAccessException, InstantiationException, XPathException {

		XmlElement $xmlElement = $class.getAnnotation(XmlElement.class);
		XmlNamespace $xmlNamespace = $class.getAnnotation(XmlNamespace.class);
		String $elementName = $xmlElement.value();
		if ($elementName.isEmpty()) {
			$elementName = $class.getSimpleName();
		}
		boolean $isTheElement = false;
		if ($xmlNamespace == null) {
			$isTheElement = $elementName.equals($element.getTagName());
		} else {
			$isTheElement = $elementName.equals($element.getLocalName())
					&& $xmlNamespace.value().equals($element.getNamespaceURI());
		}

		if ($isTheElement) {
			T $target = $class.newInstance();

			Field[] $fields = AbusingReflection.fields($class);

			AccessibleObject.setAccessible($fields, true);
			for (Field $f : $fields) {
				String $name = $f.getName();
				if ($name.startsWith("$")) {
					$name = $name.substring(1);
				}
				$name = $name.replace("_", "-");

				XmlAttribute $xmlAttr = $f.getAnnotation(XmlAttribute.class);
				if ($xmlAttr != null) {
					if ($xmlAttr.value().length() > 0) {
						$name = $xmlAttr.value();
					}
					XmlNamespace $xmlNs = $f.getAnnotation(XmlNamespace.class);
					if ((($xmlNs != null) && $element.hasAttributeNS($xmlNs.value(), $name))
							|| (($xmlNs == null) && $element.hasAttribute($name))) {
						String $value = $xmlNs == null
								? $element.getAttribute($name)
								: $element.getAttributeNS($xmlNs.value(), $name);
						Object $realValue = AbusingStrings.convertString($value, $f.getType(), $f.get($target));
						$f.set($target, $realValue);
					}
				} else {
					XmlTextContent $xmlText = $f.getAnnotation(XmlTextContent.class);
					if ($xmlText != null) {
						Object $value = AbusingStrings.convertString($element.getTextContent(), $f.getType(), null);
						if ($value != null) {
							$f.set($target, $value);
						}
					} else {
						XmlChildElement $xmlElem = $f.getAnnotation(XmlChildElement.class);
						if ($xmlElem != null) {
							Class<?> $c = $xmlElem.value().equals(void.class)
									? $f.getType() : $xmlElem.value();
							String $tagName = $c.getAnnotation(XmlElement.class).value();
							XmlNamespace $ns = $c.getAnnotation(XmlNamespace.class);
							if ($tagName.isEmpty()) {
								$tagName = $c.getSimpleName();
							}
							NodeList $nodes = ($ns != null)
									? $element.getElementsByTagNameNS($ns.value(), $tagName)
									: $element.getElementsByTagName($tagName);
							if ($nodes.getLength() > 0) {
								$f.set($target, loadElement((Element) $nodes.item(0), $c, $xpath));
							}
						} else {
							XmlChildElements $xmlNodes = $f.getAnnotation(XmlChildElements.class);
							if ($xmlNodes != null) {
								Map<String, Class<?>> $childrenClasses = new HashMap<String, Class<?>>();
								if ($xmlNodes.value().length > 0) {
									for (Class<?> $c : $xmlNodes.value()) {
										String $tagName = $c.getAnnotation(XmlElement.class).value();
										if ($tagName.isEmpty()) {
											$tagName = $c.getSimpleName();
										}
										$childrenClasses.put($tagName, $c);
									}
								} else if ($f.getType().isArray()) {
									Class<?> $c = $f.getType().getComponentType();
									String $tagName = $c.getAnnotation(XmlElement.class).value();
									if ($tagName.isEmpty()) {
										$tagName = $c.getSimpleName();
									}
									$childrenClasses.put($tagName, $c);
								} else if (Collection.class.isAssignableFrom($f.getType())) {
									Class<?> $c = AbusingReflection.genericBaseType($f.getGenericType());
									String $tagName = $c.getAnnotation(XmlElement.class).value();
									if ($tagName.isEmpty()) {
										$tagName = $c.getSimpleName();
									}
									$childrenClasses.put($tagName, $c);
								}
								NodeList $children = $element.getChildNodes();
								List<Object> $objects = new LinkedList<Object>();
								for (int $i = 0; $i < $children.getLength(); $i++) {
									Node $child = $children.item($i);
									if ($child instanceof Element) {
										Element $e = (Element) $child;
										if ($childrenClasses.keySet().contains($e.getTagName())) {
											$objects.add(loadElement($e, $childrenClasses.get($e.getTagName()), $xpath));
										}
									}
								}
								if ($f.getType().isArray()) {
									Object[] $array = (Object[]) Array.newInstance($f.getType().getComponentType(),
											$objects.size());
									$f.set($target, $objects.toArray($array));
								} else if ($f.getType().isAssignableFrom(ArrayList.class)) {
									ArrayList<Object> $list = new ArrayList<Object>($objects.size());
									$list.addAll($objects);
									$f.set($target, $list);
								} else if ($f.getType().isAssignableFrom(LinkedList.class)) {
									$f.set($target, $objects);
								} else if ($f.getType().isAssignableFrom(HashSet.class)) {
									HashSet<Object> $set = new HashSet<Object>($objects);
									$f.set($target, $set);
								} else if ($f.getType().isAssignableFrom(TreeSet.class)) {
									TreeSet<Object> $set = new TreeSet<Object>($objects);
									$f.set($target, $set);
								}
							} else {
								final XmlSelect $xmlSelect = $f.getAnnotation(XmlSelect.class);
								if ($xmlSelect != null) {
									$xpath.reset();
									if ($xmlSelect.namespaceContext().length > 0) {
										$xpath.setNamespaceContext(new NamespaceContext() {
											Map<String, String> $namespaces = new HashMap<String, String>();

											{
												for (XmlNamespaceDefinition $d : $xmlSelect.namespaceContext()) {
													$namespaces.put($d.prefix(), $d.uri());
												}
											}

											@Override
											public String getNamespaceURI(final String $prefix) {
												return $namespaces.get($prefix);
											}

											@Override
											public String getPrefix(final String $namespaceURI) {
												return null;
											}

											@Override
											public Iterator<String> getPrefixes(final String $namespaceURI) {
												return null;
											}

										});
									}
									QName $resultType = null;
									Class<?> $type = $f.getType();
									if (($type == int.class) || ($type == long.class) || ($type == double.class)
											|| ($type == float.class)
											|| Number.class.isAssignableFrom($type)) {
										$resultType = XPathConstants.NUMBER;
									} else if (($type == boolean.class) || ($type == Boolean.class)) {
										$resultType = XPathConstants.BOOLEAN;
									} else if ($type.isArray()) {
										$resultType = XPathConstants.NODESET;
									} else if ($type.isAnnotationPresent(XmlElement.class)) {
										$resultType = XPathConstants.NODE;
									} else {
										$resultType = XPathConstants.STRING;
									}
									if ($resultType != null) {
										Object $result = $xpath.evaluate($xmlSelect.value(), $element, $resultType);
										if (($type == int.class) || ($type == Integer.class)) {
											$result = ((Double) $result).intValue();
										} else if (($type == long.class) || ($type == Long.class)) {
											$result = ((Double) $result).longValue();
										} else if (($type == float.class) || ($type == Float.class)) {
											$result = ((Double) $result).floatValue();
										} else if (($type == double.class) || ($type == Double.class)) {
											$result = ((Double) $result).doubleValue();
										} else if (($type == short.class) || ($type == Short.class)) {
											$result = ((Double) $result).shortValue();
										} else if (($type == byte.class) || ($type == Byte.class)) {
											$result = ((Double) $result).byteValue();
										} else if ($type == BigInteger.class) {
											$result = new BigInteger(Long.toString(((Double) $result).longValue()));
										} else if ($type == BigDecimal.class) {
											$result = new BigDecimal(((Double) $result).toString());
										} else if (($type == boolean.class) || ($type == Boolean.class)) {
											//
										} else if ($resultType == XPathConstants.STRING) {
											$result = AbusingStrings.convertString($result.toString(), $type, null);
										} else if ($resultType == XPathConstants.NODE) {
											$result = loadElement((Element) $result, $type, $xpath);
										} else if ($resultType == XPathConstants.NODESET) {
											NodeList $nodes = (NodeList) $result;
											List<Object> $objects = new LinkedList<Object>();
											for (int $i = 0; $i < $nodes.getLength(); $i++) {
												Node $n = $nodes.item($i);
												if ($n instanceof Element) {
													Element $e = (Element) $n;
													$objects.add(loadElement($e, $type.getComponentType(), $xpath));
												} else {
													$objects.add($n.getNodeValue());
												}
											}
											Object[] $results = (Object[]) Array.newInstance($f.getType()
													.getComponentType(), $objects.size());
											for (int $i = 0; $i < $results.length; $i++) {
												$results[$i] = $objects.get($i);
											}
											$result = $results;
										} else {
											$result = null;
										}
										if ($result != null) {
											$f.set($target, $result);
										}
									}
								}
							}
						}
					}
				}
				$f.setAccessible(false);
			}
			AccessibleObject.setAccessible($fields, false);

			return $target;
		}
		return null;
	}

	/**
	 * Serializes a DOM-Document as plain text XML.
	 * 
	 * @param $document
	 *            A DOM-Document which is to be serialized.
	 * @return The String representation of the XML Document.
	 */
	public static String saveXML(final Document $document) {
		StringBuilder $builder = new StringBuilder();

		$builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		Set<String> $namespaces = new HashSet<String>();
		Map<String, String> $namespaceMapping = new HashMap<String, String>();
		Element $top = $document.getDocumentElement();
		extractNamespaces($top, $namespaces);
		int $n = 0;
		for (String $namespace : $namespaces) {
			String $prefix = "n" + $n++;
			$namespaceMapping.put($namespace, $prefix);
		}

		String $tagName = makeNodeName($top, $namespaceMapping);
		$builder.append('<');
		$builder.append($tagName);
		for (Entry<String, String> $namespace : $namespaceMapping.entrySet()) {
			$builder.append(" xmlns:");
			$builder.append($namespace.getValue());
			$builder.append("=\"");
			$builder.append(escapeAttributeValue($namespace.getKey()));
			$builder.append("\"");
		}
		saveXML($top.getAttributes(), $namespaceMapping, $builder);
		$builder.append('>');

		NodeList $children = $top.getChildNodes();
		for (int $i = 0; $i < $children.getLength(); $i++) {
			saveXML($children.item($i), $namespaceMapping, $builder);
		}

		$builder.append("</");
		$builder.append($tagName);
		$builder.append(">\n");

		return $builder.toString();
	}

	private static void saveXML(final Node $n, final Map<String, String> $namespaceMapping, final StringBuilder $builder) {

		if ($n instanceof Element) {
			$builder.append("<");
			$builder.append(makeNodeName($n, $namespaceMapping));
			saveXML($n.getAttributes(), $namespaceMapping, $builder);
			$builder.append(">");

			NodeList $children = $n.getChildNodes();
			for (int $i = 0; $i < $children.getLength(); $i++) {
				saveXML($children.item($i), $namespaceMapping, $builder);
			}

			$builder.append("</");
			$builder.append(makeNodeName($n, $namespaceMapping));
			$builder.append(">");
		} else if ($n instanceof Text) {
			$builder.append(escapeString($n.getTextContent()));
		}

	}

	private static void saveXML(final NamedNodeMap $attributes, final Map<String, String> $namespaceMapping,
			final StringBuilder $builder) {
		for (int $i = 0; $i < $attributes.getLength(); $i++) {
			$builder.append(' ');
			$builder.append(makeNodeName($attributes.item($i), $namespaceMapping));
			$builder.append("=\"");
			$builder.append(escapeAttributeValue($attributes.item($i).getTextContent()));
			$builder.append("\"");
		}
	}

	private static void extractNamespaces(final Node $node, final Set<String> $namespaces) {
		if ($node instanceof Element) {
			Element $e = (Element) $node;
			String $namespace = $e.getNamespaceURI();
			if (($namespace != null) && !$namespace.isEmpty()) {
				$namespaces.add($namespace);
			}
			NamedNodeMap $a = $e.getAttributes();
			for (int $i = 0; $i < $a.getLength(); $i++) {
				$namespace = $a.item($i).getNamespaceURI();
				if (($namespace != null) && !$namespace.isEmpty()) {
					$namespaces.add($namespace);
				}
			}
			NodeList $c = $e.getChildNodes();
			for (int $i = 0; $i < $c.getLength(); $i++) {
				extractNamespaces($c.item($i), $namespaces);
			}
		}
	}

	private static String makeNodeName(final Node $n, final Map<String, String> $namespaceMapping) {
		String $namespace = $n.getNamespaceURI();
		if ($namespace == null) {
			if ($n instanceof Element) {
				return ((Element) $n).getTagName();
			}
			return ((Attr) $n).getName();
		}
		return $namespaceMapping.get($namespace) + ':' + $n.getLocalName();
	}

	/**
	 * Escapes a string to be used in an elements content.
	 * <p>
	 * Escapes "&amp;" and "&lt;".
	 * 
	 * @param $text
	 *            The string to escape.
	 * @return The escaped string.
	 */
	public static String escapeString(final String $text) {
		return $text
				.replace("&", "&amp;")
				.replace("<", "&lt;");
	}

	/**
	 * Escapes a string to be used as an attributes value.
	 * <p>
	 * Escapes "&amp;", "&lt;", and "&quot;".
	 * 
	 * @param $text
	 *            The string to escape.
	 * @return The escaped string.
	 */
	public static String escapeAttributeValue(final String $text) {
		return $text
				.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace("\"", "&quot;");
	}

}
