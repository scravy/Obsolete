package net.abusingjava.tests;

public class TestRunResult {
	final int $countTotal;
	final TestResult[] $results;
	int $countSuccess = 0;
	int $countFailed = 0;
	int $countErrorneous = 0;
	int $arrayPointer = 0;
	
	TestRunResult(final int $total) {
		$countTotal = $total;
		$results = new TestResult[$total];
	}

	public int getNumberOfSuccessfullTests() {
		return $countSuccess;
	}
	
	public int getNumberOfFailedTests() {
		return $countFailed;
	}
	
	public int getNumberOfErrorneousTests() {
		return $countErrorneous;
	}
	
	void addTestResult(final TestResult $result) {
		$results[$arrayPointer++] = $result;
		switch ($result.getResult()) {
		case SUCCESSFULL:
			$countSuccess++;
			break;
		case ASSERTION_FAILED:
			$countFailed++;
			break;
		default:
			$countErrorneous++;
		}
	}
	
}