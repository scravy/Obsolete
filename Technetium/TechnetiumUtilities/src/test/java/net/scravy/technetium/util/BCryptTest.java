package net.scravy.technetium.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author scravy
 * 
 */
public class BCryptTest {

	/**
	 * 
	 */
	@Test
	public void testBCrypt() {
		String pw = "password";
		String hash = BCrypt.hashpw(pw, BCrypt.gensalt(4));
		Assert.assertTrue(BCrypt.checkpw(pw, hash));
	}

}
