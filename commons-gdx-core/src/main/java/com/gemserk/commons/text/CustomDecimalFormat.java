package com.gemserk.commons.text;

public class CustomDecimalFormat {
	
	private StringBuilder stringBuilder;

	public CustomDecimalFormat(int capacity) {
		stringBuilder = new StringBuilder(capacity);
	}
	
	public CharSequence format(long number) {
		return format(number, stringBuilder);
	}

	public CharSequence format(long number, StringBuilder stringBuilder) {

		int index = stringBuilder.capacity();
		long digitBase = 10;

		stringBuilder.setLength(0);

		while (number > 0) {
			long digit = number % digitBase;

			number -= digit;

			long currentDigit = digit * 10 / digitBase;

			stringBuilder.append(currentDigit);

			digitBase *= 10;
			index--;
		}

		while (index > 0) {
			stringBuilder.append(0);
			index--;
		}

		stringBuilder.reverse();

		return stringBuilder;
	}

}