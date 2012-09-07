package net.abusingjava;


@Author("Julian Fleischer")
@Version("2011-09-05")
@Since(value = "2011-09-05", version = "1.0")
@SuppressWarnings("unused")
final public class AbusingExceptions {

	AbusingExceptions() {}

	public static <T extends Throwable> boolean isCauseFor(final Class<T> $class, final Throwable $throwable) {
		Throwable $t = $throwable;
		while ($t.getCause() != null) {
			$t = $t.getCause();
			if ($class.isAssignableFrom($t.getClass())) {
				return true;
			}
		}
		return false;
	}

	public static <T extends Throwable> boolean isExactCauseFor(final Class<T> $class, final Throwable $throwable) {
		Throwable $t = $throwable;
		while ($t.getCause() != null) {
			$t = $t.getCause();
			if ($class.equals($t.getClass())) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Throwable> T getCauseFor(final Class<T> $causeClass, final Throwable $throwable) {
		Throwable $t = $throwable;
		while ($t.getCause() != null) {
			$t = $t.getCause();
			if ($causeClass.isAssignableFrom($t.getClass())) {
				return (T) $t;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Throwable> T getExactCauseFor(final Class<T> $causeClass, final Throwable $throwable) {
		Throwable $t = $throwable;
		while ($t.getCause() != null) {
			$t = $t.getCause();
			if ($causeClass.equals($t.getClass())) {
				return (T) $t;
			}
		}
		return null;
	}
	
	@Experimental
	public static String packageName(final String $className) {
		try {
			return Class.forName($className).getPackage().getName();
		} catch (Exception $exc) {
			return "";
		}
	}
}
