package com.gemserk.commons.text;

import static org.junit.Assert.*;

import org.junit.Test;

public class CustomDecimalFormatTest {

	@Test
	public void shouldFillTheMissingNumbersWithDefaultCharacter() {
		CustomDecimalFormat customDecimalFormat = new CustomDecimalFormat(5);
		assertEquals("00300", customDecimalFormat.format(300L).toString());
	}
	
	@Test
	public void shouldFillTheMissingNumbersWithDefaultCharacter2() {
		CustomDecimalFormat customDecimalFormat = new CustomDecimalFormat(5);
		assertEquals("00000", customDecimalFormat.format(0L).toString());
	}
	
	@Test
	public void shouldFillTheMissingNumbersWithDefaultCharacter3() {
		CustomDecimalFormat customDecimalFormat = new CustomDecimalFormat(5);
		assertEquals("99999", customDecimalFormat.format(99999L).toString());
	}

	@Test
	public void shouldGrowWhenNumberGreaterThanExpectedDigits() {
		CustomDecimalFormat customDecimalFormat = new CustomDecimalFormat(5);
		assertEquals("123456", customDecimalFormat.format(123456L).toString());
	}

}
