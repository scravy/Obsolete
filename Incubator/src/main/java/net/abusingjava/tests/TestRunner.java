package net.abusingjava.tests;

import static net.abusingjava.AbusingArrays.*;
import static net.abusingjava.AbusingReflection.*;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.abusingjava.Author;
import net.abusingjava.Since;
import net.abusingjava.Version;

@Author("Julian Fleischer")
@Version("2011-07-26")
@Since("2011-07-26")
public class TestRunner implements Iterable<TestRunner.TestRun> {

	private final Map<String,TestRun> $tests = new TreeMap<String,TestRun>();
	private final List<Throwable> $problems = new LinkedList<Throwable>();
	private Method $before;
	private Method $after;
	private Method $beforeClass;
	private Method $afterClass;
	private Method $afterException;
	
	public TestRunner(final Class<?> $class) {
		Set<Method> $methods = new HashSet<Method>();
		for (Class<?> $c : reverse(shift(parents($class), $class))) {
			for (Method $m : $c.getDeclaredMethods()) {
				$methods.add($m);
			}
		}
		for (Method $m : $methods) {
			if ($m.isAnnotationPresent(Before.class)) {
				if (($m.getModifiers() & Modifier.STATIC) == 0 && $m.getParameterTypes().length == 0) {
					$before = $m;
				}
			} else if ($m.isAnnotationPresent(After.class)) {
				if (($m.getModifiers() & Modifier.STATIC) == 0 && $m.getParameterTypes().length == 0) {
					$after = $m;
				}
			} else if ($m.isAnnotationPresent(AfterException.class)) {
				if ($m.getParameterTypes().length == 1 && $m.getParameterTypes()[0].isAssignableFrom(Throwable.class)) {
					$afterException = $m;
				}
			} else if ($m.isAnnotationPresent(BeforeClass.class)) {
				if (($m.getModifiers() & Modifier.STATIC) != 0 && $m.getParameterTypes().length == 0) {
					$beforeClass = $m;
				}
			} else if ($m.isAnnotationPresent(AfterClass.class)) {
				if (($m.getModifiers() & Modifier.STATIC) != 0 && $m.getParameterTypes().length == 0) {
					$afterClass = $m;
				}
			} else if ($m.isAnnotationPresent(Test.class)) {
				newTestRun($class, $m);
			}
		}
	}
	
	private static String getName(Class<?> $class, Method $m) {
		Test $annotation = $m.getAnnotation(Test.class);
		String $prefix = $class.getCanonicalName() + '#';
		if ($annotation.name().isEmpty()) {
			return $prefix + $m.getName();
		}
		return $prefix + $annotation.name();
	}
	
	public int getNumberOfTests() {
		return $tests.size();
	}
	
	
	private TestRun newTestRun(Class<?> $c, Method $m) {
		TestRun $testRun = new TestRun($c, $m);
		synchronized ($tests) {
			$tests.put(getName($c, $m), $testRun);
		}
		return $testRun;
	}
	
	
	public int size() {
		return $tests.size();
	}
	
	
	public class TestRun {
		private final String $name;
		private final Method $method;
		private final Test $test;
		private final Class<?> $class;
		
		TestRun(Class<?> $c, Method $m) {
			$method = $m;
			$class = $c;
			$test = $method.getAnnotation(Test.class);
			$name = TestRunner.getName($c, $m);
		}
		
		public String getName() {
			return $name;
		}
		
		class Capsule {
			volatile Object $obj = null;
		}
		
		void executeWithTimeout(final Object $object, final Method $method, long $timeout)
				throws InterruptedException, InvocationTargetException, IllegalAccessException {
			final Capsule $capsule = new Capsule();
			Thread $t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						$method.invoke($object);
					} catch (IllegalAccessException $exc) {
						throw new RuntimeException($exc);
					} catch (InvocationTargetException $exc) {
						throw new RuntimeException($exc);
					}
				}
			});
			$t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread $t, Throwable $exc) {
					if ($exc.getCause() instanceof InvocationTargetException) {
						synchronized ($capsule) {
							$capsule.$obj = $exc.getCause();
						}
					}
				}
			});
			$t.setDaemon(true);
			$t.start();
			$t.join($timeout);
			if ($t.isAlive()) {
				$t.interrupt();
				throw new TimeoutException();
			} else if ($capsule.$obj instanceof Exception) {
				synchronized ($capsule) {
					throw (InvocationTargetException) $capsule.$obj;
				}
			} else if ($capsule.$obj instanceof IllegalAccessException) {
				synchronized ($capsule) {
					throw (IllegalAccessException) $capsule.$obj;
				}
			}
		}
		
		public TestResult execute() {
			long $start = System.currentTimeMillis();
			Throwable $exception = new AbusingTests.None();
			Result $result;
			try {
				if ($test.ignore()) {
					$result = Result.IGNORED;
				} else {
					final Object $object = $class.newInstance();
					if ($before != null) {
						$before.invoke($object);
					}
					$method.setAccessible(true);
					if ($test.timeout() > 0) {
						executeWithTimeout($object, $method, $test.timeout());
					} else {
						$method.invoke($object);
					}
					if ($after != null) {
						$after.invoke($object);
					}
					if (!$test.expected().equals(AbusingTests.None.class)) {
						$result = Result.EXPECTED_EXCEPTION;
					} else {
						$result = Result.SUCCESSFULL;
					}
				}
			} catch (TimeoutException $exc) {
				$exception = $exc;
				$result = Result.TIMEOUT;
			} catch (InvocationTargetException $exc) {
				$exception = $exc.getCause();
				if (AssertionFailedException.class.isAssignableFrom($exception.getClass())) {
					$result = Result.ASSERTION_FAILED;
				} else if (!$test.expectedExactly() && $test.expected().isAssignableFrom($exception.getClass())) {
					$result = Result.SUCCESSFULL;
				} else if ($test.expectedExactly() && $test.expected().equals($exception.getClass())) {
					$result = Result.SUCCESSFULL;
 				} else {
					$result = Result.UNEXPECTED_EXCEPTION;
				}
			} catch (IllegalArgumentException $exc) {
				$exception = $exc;
				$result = Result.ILLEGAL_TEST;
			} catch (IllegalAccessException $exc) {
				$exception = $exc;
				$result = Result.ILLEGAL_TEST;
			} catch (InstantiationException $exc) {
				$exception = $exc;
				$result = Result.ILLEGAL_TEST;
			} catch (InterruptedException $exc) {
				$exception = $exc;
				$result = Result.INTERRUPTED;
			}
			if (!$exception.getClass().equals(AbusingTests.None.class)) {
				try {
					$afterException.invoke(null, $exception);
				} catch (Exception $exc) {
					$problems.add($exc);
				}
			}
			return new TestResult($name, $result, $exception, System.currentTimeMillis() - $start);
		}
	}
	
	class TestRunIterator implements Iterator<TestResult> {

		final Iterator<TestRun> $iterator;
		final TestRunResult $result;
		
		TestRunIterator() {
			Collection<TestRunner.TestRun> $myTests = Collections.unmodifiableCollection($tests.values());
			$iterator = $myTests.iterator();
			$result = new TestRunResult($myTests.size());
		}
		
		@Override
		public boolean hasNext() {
			return $iterator.hasNext();
		}

		@Override
		public TestResult next() {
			TestResult $testResult = $iterator.next().execute();
			$testResult.setTestRunResult($result);
			return $testResult;
		}

		/**
		 * @throws UnsupportedOperationException <code>remove</code> is not supported.
		 */
		@Override
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
		
	}

	@Override
	public Iterator<TestRun> iterator() {
		return $tests.values().iterator();
	}

	public Iterator<TestResult> runIterator() {
		synchronized ($tests) {
			return new TestRunIterator();
		}
	}
	
	public void run() {
		System.out.printf("Running %d tests:\n", size());
		int $success = 0;
		int $failure = 0;
		int $error = 0;
		int $ignored = 0;
		List<TestResult> $results = new LinkedList<TestResult>();
		if ($beforeClass != null) {
			try {
				$beforeClass.invoke(null);
			} catch (Exception $exc) {
				$problems.add($exc);
			}
		}
		for (TestRun $test : this) {
			System.out.printf("\n* Running `%s`\n", $test.getName());
			TestResult $result = $test.execute();
			System.out.printf("  -> %s (%fs)\n",
					$result.getResult(),
					$result.getTimeNeeded() / 1000f);
			if (!AbusingTests.None.class.equals($result.getException().getClass())) {
				System.out.printf("     %s", $result.getException().getClass().getSimpleName());
				if ($result.getException().getMessage() != null) {
					System.out.printf(": \"%s\"", $result.getException().getMessage());
				}
				System.out.println();
			}
			$results.add($result);
		}
		if ($afterClass != null) {
			try {
				$afterClass.invoke(null);
			} catch (Exception $exc) {
				$problems.add($exc);
			}
		}
		System.out.println();
		for (TestResult $result : $results) {
			switch ($result.getResult()) {
			case SUCCESSFULL:
				$success++;
				System.out.print(".");
				break;
			case ASSERTION_FAILED:
				$failure++;
				System.out.print("A");
				break;
			case IGNORED:
				$ignored++;
				System.out.print("I");
				break;
			default:
				$error++;
				System.out.print("E");
			}
		}
		System.out.printf("\nSuccessful: %d, Assertion failures: %d, Exceptions: %d, Ignored: %d\n",
				$success, $failure, $error, $ignored);
	}

}
