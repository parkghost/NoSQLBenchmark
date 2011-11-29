package me.brandonc.benchmark.chart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ResultSet {

	private List<String[]> data = new ArrayList<String[]>();

	public void loadDataFromFolder(File results) {
		FileFilter fileFilter = new FileFilter() {
			public boolean accept(File file) {
				return file.getName().endsWith(".log");
			}
		};

		File[] files = results.listFiles(fileFilter);

		for (File file : files) {
			if (file.isFile()) {
				loadDataFromFile(file);
			}
		}

	}

	public void loadDataFromFile(File file) {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			// fetch dataStoreInfo from filename
			String filename = file.getName();

			String[] dataStoreInfo = filename.split("\\.");

			String dataStore = dataStoreInfo[0];
			String engine = "";
			String setting = "";

			String suffix = "";

			if (dataStoreInfo.length > 2) {
				engine = dataStoreInfo[1];
				suffix += engine;
			}

			if (dataStoreInfo.length > 3) {
				setting = dataStoreInfo[2];
				suffix += "." + setting;
			}

			// fetch results from log
			String line;
			while ((line = br.readLine()) != null) {
				StringTokenizer tokens = new StringTokenizer(line, "\t");
				String values[] = new String[Column.values().length];

				values[Column.DATASTORE.ordinal()] = dataStore;
				values[Column.ENGINE.ordinal()] = engine;
				values[Column.SETTING.ordinal()] = setting;

				while (tokens.hasMoreTokens()) {
					String token = tokens.nextToken();
					String[] namePair = token.split(":");
					int pos = Column.resolve(namePair[0].trim());
					values[pos] = namePair[1].trim();
				}

				if (!suffix.equals("")) {
					values[Column.METHOD.ordinal()] = values[Column.METHOD.ordinal()] + "(" + suffix + ")";
				}

				data.add(values);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(br);
			IOUtils.closeQuietly(fr);
		}
	}

	public List<String[]> getData() {
		return data;
	}

}
