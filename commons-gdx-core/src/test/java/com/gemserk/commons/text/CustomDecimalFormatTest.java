package com.gemserk.commons.text;

import static org.junit.Assert.*;

import org.junit.Test;

public class CustomDecimalFormatTest {

	@Test
	public void shouldFillTheMissingNumbersWithDefaultCharacter() {
		CustomDecimalFormat customDecimalFormat = new CustomDecimalFormat(5);
		customDecimalFormat.setFillCharacter('0');
		assertEquals("00300", customDecimalFormat.format(300L).toString());
	}
	
	@Test
	public void shouldFillTheMissingNumbersWithDefaultCharacter2() {
		CustomDecimalFormat customDecimalFormat = new CustomDecimalFormat(5);
		customDecimalFormat.setFillCharacter('0');
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
	
	@Test
	public void shouldFillWithSpecifiedFillCharacter() {
		CustomDecimalFormat customDecimalFormat = new CustomDecimalFormat(5);
		customDecimalFormat.setFillCharacter('A');
		assertEquals("AA123", customDecimalFormat.format(123L).toString());
	}

	@Test
	public void shouldFillWithSpecifiedFillCharacter2() {
		CustomDecimalFormat customDecimalFormat = new CustomDecimalFormat(5);
		customDecimalFormat.setFillCharacter(' ');
		assertEquals("  123", customDecimalFormat.format(123L).toString());
	}
	
	@Test
	public void shouldFillWithSpecifiedFillCharacter3() {
		CustomDecimalFormat customDecimalFormat = new CustomDecimalFormat(5);
		customDecimalFormat.setFillCharacter(null);
		assertEquals("123", customDecimalFormat.format(123L).toString());
	}
}
