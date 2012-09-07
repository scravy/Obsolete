package net.abusingjava;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class AbusingArraysTest {

	@Test
	public void sliceTestEven() throws Exception {
		int[] $a = {1, 2, 3, 4, 5, 6};

		int[] $expectedSlice = new int[] {4, 5, 6};
		int[] $actualSlice = AbusingArrays.split(2, $a, 1);
		assertEquals($expectedSlice.length, $actualSlice.length);
	}

	@Test
	public void sliceTestOdd() throws Exception {
		int[] $a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

		int[] $expectedSlice0 = new int[] {1, 2, 3};
		int[] $actualSlice0 = AbusingArrays.split(3, $a, 0);
		int[] $expectedSlice1 = new int[] {4, 5, 6};
		int[] $actualSlice1 = AbusingArrays.split(3, $a, 1);
		int[] $expectedSlice2 = new int[] {7, 8, 9, 10, 11};
		int[] $actualSlice2 = AbusingArrays.split(3, $a, 2);
		assertEquals($expectedSlice0.length, $actualSlice0.length);
		assertEquals($expectedSlice1.length, $actualSlice1.length);
		assertEquals($expectedSlice2.length, $actualSlice2.length);
		
	}
}
