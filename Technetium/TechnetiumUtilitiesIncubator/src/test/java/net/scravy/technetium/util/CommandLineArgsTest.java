package net.scravy.technetium.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * A Unit test for {@link CommandLineArgs}.
 */
public class CommandLineArgsTest {

	@Test
	public void testOneOptionOnly() {
		CommandLineArgs cliArgs = new CommandLineArgs();
		cliArgs.configureArg("--help", "-h");
		cliArgs.load(new String[] { "--help" });
		Assert.assertTrue("Number of arguments", cliArgs.numArgs() == 0);
		Assert.assertTrue("Opts contain --help", cliArgs.hasOption("--help"));
		Assert.assertTrue("Opts contain -h", cliArgs.hasOption("-h"));
	}

	@Test
	public void testArgs() {
		CommandLineArgs cliArgs = new CommandLineArgs();
		cliArgs.load(new String[] { "1337", "42", "hello world" });
		Assert.assertEquals("Number of arguments equals 3", 3,
				cliArgs.numArgs());
		Assert.assertTrue("Zeroth argument is 1337",
				cliArgs.getArg(0, 0) == 1337);
		Assert.assertTrue("First argument is 42", cliArgs.getArg(1, 0) == 42);
		Assert.assertTrue("Second argument is \"hello world",
				cliArgs.getArg(2, "").equals("hello world"));
	}

	@Test
	public void testAnonymousArg() {
		CommandLineArgs cliArgs = new CommandLineArgs();
		cliArgs.load(new String[] { "--anonymous", "test.txt" });

		Assert.assertTrue("Has option anonymous",
				cliArgs.hasOption("--anonymous"));
		Assert.assertEquals("Number of arguments is 1", 1, cliArgs.numArgs());
	}

	@Test
	public void testIgnoresOptions() {
		CommandLineArgs cliArgs = new CommandLineArgs();
		cliArgs.load(new String[] { "--option", "arg", "--", "--ignored", "arg" });

		Assert.assertEquals(3, cliArgs.numArgs());
		Assert.assertTrue(cliArgs.hasOption("--option"));
		Assert.assertFalse(cliArgs.hasOption("--ignored"));
		Assert.assertEquals("arg", cliArgs.getArg(0));
		Assert.assertEquals("--ignored", cliArgs.getArg(1));
		Assert.assertEquals("arg", cliArgs.getArg(2));
	}
}
