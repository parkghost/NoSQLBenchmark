package me.brandonc.benchmark.config;

import java.util.Map;

public class SimpleConfiguration implements Configuration {

	private static final String DELIMITER = ",";

	protected Map<String, String> store;

	public SimpleConfiguration(Map<String, String> store) {
		this.store = store;
	}

	@Override
	public boolean contain(String key) {
		return store.containsKey(key);
	}

	@Override
	public String getString(String key) {
		return store.get(key);
	}

	@Override
	public int getIntValue(String key) {
		return Integer.parseInt(getString(key));
	}

	@Override
	public String[] getStringArray(String key) {
		return getString(key).split(DELIMITER);
	}

	@Override
	public int[] getIntArray(String key) {
		String[] stringArray = getStringArray(key);
		int[] intArray = new int[stringArray.length];

		for (int i = 0; i < stringArray.length; i++) {
			intArray[i] = Integer.parseInt(stringArray[i]);
		}

		return intArray;
	}

}
