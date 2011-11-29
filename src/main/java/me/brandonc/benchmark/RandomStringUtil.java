package me.brandonc.benchmark;

public class RandomStringUtil {

	public static String getString(int dataSize) {

		int seed = (int) (Math.random() * 26);

		char[] asciiArray = new char[dataSize];
		for (int i = 0; i < dataSize; i++) {
			asciiArray[i] = (char) (65 + (seed++) % 26);
		}
		return new String(asciiArray);
	}

}
