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
package net.abusingjava.mockups;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.Random;

import net.abusingjava.*;

/**
 * Provides randomly generated values for many kinds of data.
 */
@Author("Julian Fleischer")
@Version("2011-07-10")
public class DefaultValueProvider implements ValueProvider {

	private final Random $random;
	
	public DefaultValueProvider() {
		$random = new Random();
	}

	public DefaultValueProvider(final Random $random) {
		if ($random == null)
			throw new IllegalArgumentException(new NullPointerException());
		this.$random = $random;
	}

	public DefaultValueProvider(final long $seed) {
		this.$random = new Random($seed);
	}
	
	@Override
	public Object provide(final Class<?> $type, final String $name, final Annotation[] $annotations) {
		if (($type == int.class) || ($type == Integer.class)) {
			if (AbusingArrays.containsType($annotations, Min.class)
					&& AbusingArrays.containsType($annotations, Max.class)) {
				Min $min = AbusingArrays.firstOfType($annotations, Min.class);
				Max $max = AbusingArrays.firstOfType($annotations, Max.class);
				return provideInt((int) $min.value(), (int) $max.value());
			}
			return provideInt();
		} else if (($type == long.class) || ($type == Long.class)) {
			if (AbusingArrays.containsType($annotations, Min.class)
					&& AbusingArrays.containsType($annotations, Max.class)) {
				Min $min = AbusingArrays.firstOfType($annotations, Min.class);
				Max $max = AbusingArrays.firstOfType($annotations, Max.class);
				return provideLong($min.value(), $max.value());
			}
			return provideLong();
		} else if (($type == byte.class) || ($type == Byte.class)) {
			return provideByte();
		} else if (($type == char.class) || ($type == Character.class)) {
			return provideCharacter();
		} else if (($type == short.class) || ($type == Short.class)) {
			return provideShort();
		} else if (($type == boolean.class) || ($type == Boolean.class)) {
			return provideBoolean();
		} else if (($type == float.class) || ($type == Float.class)) {
			if (AbusingArrays.containsType($annotations, MinFloat.class)
					&& AbusingArrays.containsType($annotations, MaxFloat.class)) {
				MinFloat $min = AbusingArrays.firstOfType($annotations, MinFloat.class);
				MaxFloat $max = AbusingArrays.firstOfType($annotations, MaxFloat.class);
				return provideFloat((float) $min.value(), (float) $max.value());
			}
			return provideFloat();
		} else if (($type == double.class) || ($type == Double.class)) {
			if (AbusingArrays.containsType($annotations, MinFloat.class)
					&& AbusingArrays.containsType($annotations, MaxFloat.class)) {
				MinFloat $min = AbusingArrays.firstOfType($annotations, MinFloat.class);
				MaxFloat $max = AbusingArrays.firstOfType($annotations, MaxFloat.class);
				return provideDouble($min.value(), $max.value());
			}
			return provideDouble();
		} else if ($type == String.class) {
			if (AbusingArrays.containsType($annotations, StringPattern.class)) {
				switch (AbusingArrays.firstOfType($annotations, StringPattern.class).value()) {
				case ASCII:
					if (AbusingArrays.containsType($annotations, MinLength.class)
							&& AbusingArrays.containsType($annotations, MaxLength.class)) {
						MinLength $min = AbusingArrays.firstOfType($annotations, MinLength.class);
						MaxLength $max = AbusingArrays.firstOfType($annotations, MaxLength.class);
						return provideString($min.value(), $max.value(),
							"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!^$%&/()=?[]{}><|~;,:._-#'+*@\\\"");
					}
					return provideString(provideInt(10, 15), provideInt(25, 30),
							"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!^$%&/()=?[]{}><|~;,:._-#'+*@\\\"");
				case EMAIL:
					return provideString(5, 10, "abcdefghijklmnopqrstuvwxyz") + '.'
							+ provideString(5, 10, "abcdefghijklmnopqrstuvwxyz") + '@'
							+ provideString(5, 10, "abcdefghijklmnopqrstuvwxyz") + '.'
							+ provideOneOf(new String[] {"de", "at", "co.uk", "ch", "pl", "dk", "com", "net", "org", "info", "biz"});
				case URI:
				case URL:
					return provideOneOf("http://", "https://") + provideOneOf("www.", "www2.", "", "web.")
							+ provideString(3, 17, "abcdefghijklmnopqrstuvwxyz") + '.'
							+ provideString(3, 17, "abcdefghijklmnopqrstuvwxyz") + '.'
							+ provideOneOf(new String[] {"de", "at", "co.uk", "ch", "pl", "dk", "com", "net", "org", "info", "biz"})
							+ '/' + provideString(0, 13, "abcdefghijklmnopqrstuvwxyz");
				}
			}
			if (AbusingArrays.containsType($annotations, MinLength.class)
					&& AbusingArrays.containsType($annotations, MaxLength.class)) {
				MinLength $min = AbusingArrays.firstOfType($annotations, MinLength.class);
				MaxLength $max = AbusingArrays.firstOfType($annotations, MaxLength.class);
				return provideString($min.value(), $max.value());
			}
			return provideString(20);
		} else if ($type == Date.class) {
			return new Date(System.currentTimeMillis() + provideLong(-360000000000L, 360000000000L));
		}
		
		return null;
	}

	public <T> T provideOneOf(final T... $things) {
		return $things[provideInt(0, $things.length - 1)];
	}

	public void setProviderSeed(final long $seed) {
		$random.setSeed($seed);
	}

	public String provideString(final int $length) {
		return provideString($length, $length);
	}
	
	public String provideString(final int $minLength, final int $maxLength) {
		return provideString($minLength, $maxLength, "abcdefghijklmnopqrstuvwxyz");
	}
	
	public String provideString(final int $minLength, final int $maxLength, final String $chars) {
		if ($chars == null)
			throw new IllegalArgumentException(new NullPointerException());
		int $length = provideInt($minLength, $maxLength);
		char[] $string = new char[$length];
		for (int $i = 0; $i < $length; $i++) {
			$string[$i] = $chars.charAt(provideInt(0, $chars.length() - 1));
		}
		return new String($string);
	}
	
	public String provideText(final int $maxLength) {
		StringBuilder $builder = new StringBuilder($maxLength);

		int $length = 0;
		String $word = "";
		do {
			if ($builder.length() > 0) {
				$builder.append(" ");
			}
			$builder.append($word);
			$word = provideString(provideInt(2, 12));
			if (provideBoolean()) {
				$word = AbusingStrings.capitalize($word);
			}
		} while(($builder.length() + $length) < $maxLength);
		
		return $builder.toString();
	}

	public Character provideCharacter() {
		return Character.valueOf((char) provideInt(32, Character.MAX_CODE_POINT));
	}
	
	public byte[] provideBytes(final int $length) {
		return provideBytes($length, $length);
	}
	
	public byte[] provideBytes(final int $minLength, final int $maxLength) {
		byte[] $bytes = new byte[provideInt($minLength, $maxLength)];
		$random.nextBytes($bytes);
		return $bytes;
	}
	
	public byte provideByte() {
		return (byte) provideInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
	}
	
	public byte provideByte(final byte $min, final byte $max) {
		return (byte) provideInt($min, $max);
	}
	
	public short provideShort() {
		return (short) provideInt(Short.MIN_VALUE, Short.MAX_VALUE);
	}

	public short provideShort(final short $min, final short $max) {
		return (short) provideInt($min, $max);
	}
	
	public int provideInt() {
		return $random.nextInt();
	}
	
	public int provideInt(final int $min, final int $max) {
		if ($min == $max) return $max;
		return $min + $random.nextInt(($max - $min) + 1);
	}

	public long provideLong() {
		return $random.nextLong();
	}
	
	public long provideLong(final long $min, final long $max) {
		if ($min == $max) return $max;
		return ($random.nextLong() % ($max - $min)) + $min;
	}
	
	public float provideFloat() {
		return $random.nextFloat();
	}

	@SuppressWarnings("unused")
	public float provideFloat(final float $min, final float $max) {
		// TODO: Respect min/max
		return $random.nextFloat();
	}
	
	public double provideDouble() {
		return $random.nextDouble();
	}

	@SuppressWarnings("unused")
	public double provideDouble(final double $min, final double $max) {
		// TODO: Respect min/max
		return $random.nextDouble();
	}
	
	public boolean provideBoolean() {
		return $random.nextBoolean();
	}
}
