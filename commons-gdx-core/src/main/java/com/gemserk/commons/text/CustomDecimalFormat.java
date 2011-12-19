package com.gemserk.commons.text;

public class CustomDecimalFormat {
	
	private StringBuffer stringBuffer;

	public CustomDecimalFormat(int capacity) {
		stringBuffer = new StringBuffer(capacity);
	}
	
	public CharSequence format(long number) {
		return format(number, stringBuffer);
	}

	public CharSequence format(long number, StringBuffer stringBuffer) {

		int index = stringBuffer.capacity();
		long digitBase = 10;

		stringBuffer.setLength(0);

		while (number > 0) {
			long digit = number % digitBase;

			number -= digit;

			long currentDigit = digit * 10 / digitBase;

			stringBuffer.append(currentDigit);

			digitBase *= 10;
			index--;
		}

		while (index > 0) {
			stringBuffer.append(0);
			index--;
		}

		stringBuffer.reverse();

		return stringBuffer;
	}

}