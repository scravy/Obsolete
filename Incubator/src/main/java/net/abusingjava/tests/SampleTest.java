package net.abusingjava.tests;

public class SampleTest {

	@Before
	public void before() {
		System.out.println("BEFORE");
	}
	
	@After
	public void after() {
		System.out.println("AFTER");
	}
	
	@BeforeClass
	public static void beforeClass() {
		System.out.println("Before CLASS");
	}
	
	@AfterClass
	public static void afterClass() {
		System.out.println("After CLASS");
	}
	
	@AfterException
	public static void afterException(final Throwable $t) {
		System.out.println("AFTER:" + $t.getClass());
	}
	
	@Test(expected = RuntimeException.class)
	public void test1() {
		throw new RuntimeException();
	}
	
	@Test(name = "Der sinnlose Test")
	void test2() {
		
	}
	
	@Test
	public void test3() {
		AbusingTests.assertTrue(false);
	}
	
	@Test(ignore = true)
	public void test4() {
		
	}
	
	@Test(expected = NumberFormatException.class)
	public void test5() throws Exception {
		Thread.sleep(105);
	}
	
	@Test(expected = RuntimeException.class, expectedExactly = true)
	public void test6() {
		throw new NumberFormatException();
	}

	@Test(expected = RuntimeException.class)
	public void test7() {
		throw new NumberFormatException("Dummy exception");
	}
	
	volatile int $i = 0;
	
	@Test(timeout = 200)
	public void test9() {
		while (true) {
			$i++;
		}
	}
	
	@Test(timeout = 3500)
	public void testA() throws Exception {
		
	}

	@Test(timeout = 1000)
	public void testB() {
		throw new IllegalArgumentException();
	}

	@Test(timeout = 1000, expected = RuntimeException.class)
	public void testB2() {
		throw new IllegalArgumentException();
	}

	@Test(timeout = 200)
	public void testC() throws Exception {
		Thread.sleep(5000);
		throw new IllegalArgumentException();
	}
	
	@Test
	public void test8() {
		throw new NullPointerException();
	}
	
	public static void main(final String... $args) {
		AbusingTests.run(SampleTest.class);
	}
}
