package me.brandonc.benchmark.chart;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;

public class IOUtils {

	public static void closeQuietly(OutputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				// ignored
			}
		}
	}

	public static void closeQuietly(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				// ignored
			}
		}
	}
}
