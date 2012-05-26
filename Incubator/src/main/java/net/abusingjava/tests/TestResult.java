package net.abusingjava.tests;

public class TestResult {
	private final String $name;
	private final Result $result;
	private final long $timeNeeded;
	private final Throwable $exception;
	private TestRunResult $testRunResult = null;

	TestResult(final String $name, final Result $result, final Throwable $exception, final long $timeNeeded) {
		this.$name = $name;
		this.$result = $result;
		this.$exception = $exception;
		this.$timeNeeded = $timeNeeded;
	}

	public String getName() {
		return $name;
	}
	
	public Result getResult() {
		return $result;
	}
	
	public long getTimeNeeded() {
		return $timeNeeded;
	}
	
	public Throwable getException() {
		return $exception;
	}

	void setTestRunResult(final TestRunResult $result) {
		$testRunResult = $result;
		$result.addTestResult(this);
	}
	
	public TestRunResult getTestRunResult() {
		return $testRunResult;
	}
}