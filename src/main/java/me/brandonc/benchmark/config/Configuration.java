package me.brandonc.benchmark.config;

public interface Configuration {

	boolean contain(String key);

	String getString(String key);

	int getIntValue(String key);

	String[] getStringArray(String key);

	int[] getIntArray(String key);
}
