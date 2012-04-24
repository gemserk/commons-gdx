package com.gemserk.commons.text;

public class CustomDecimalFormat {

	private StringBuilder stringBuilder;
	private Character fillCharacter = null;
	private char fillChar = ' ';

	public void setFillCharacter(Character fillCharacter) {
		this.fillCharacter = fillCharacter;
		if (fillCharacter != null)
			fillChar = fillCharacter.charValue();
	}

	public CustomDecimalFormat(int capacity) {
		stringBuilder = new StringBuilder(capacity);
		setFillCharacter(null);
	}

	public CustomDecimalFormat(int capacity, char fillCharacter) {
		stringBuilder = new StringBuilder(capacity);
		setFillCharacter(Character.valueOf(fillCharacter));
	}

	public CharSequence format(long number) {
		return format(number, stringBuilder);
	}

	public CharSequence format(long number, StringBuilder stringBuilder) {

		int index = stringBuilder.capacity();
		long digitBase = 10;

		stringBuilder.setLength(0);

		boolean zero = number == 0;

		while (number > 0) {
			long digit = number % digitBase;

			number -= digit;

			long currentDigit = digit * 10 / digitBase;

			stringBuilder.append(currentDigit);

			digitBase *= 10;
			index--;
		}

		if (zero && index > 0) {
			stringBuilder.append(0L);
			index--;
		}

		while (index > 0 && fillCharacter != null) {
			stringBuilder.append(fillChar);
			index--;
		}

		stringBuilder.reverse();

		return stringBuilder;
	}

}