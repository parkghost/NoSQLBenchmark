package me.brandonc.benchmark.chart;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.brandonc.benchmark.config.Configuration;
import me.brandonc.benchmark.config.PropertiesBasedConfiguration;

public class ChartGenerator {

	public static void main(String[] args) {

		Configuration configuration = new PropertiesBasedConfiguration("configuration/configuration.properties");

		File resultDir = new File(configuration.getString("result.dir"));
		File chartDir = new File(configuration.getString("chart.dir"));
		int width = configuration.getIntValue("chart.width");
		int height = configuration.getIntValue("chart.height");

		if (!resultDir.exists() || !resultDir.isDirectory()) {
			System.err.println("Benchmark result dir is not exist");
			System.err.println(1);
		}

		if (!chartDir.exists()) {
			chartDir.mkdir();
		}

		ResultSet resultSet = new ResultSet();
		resultSet.loadDataFromFolder(resultDir);

		String[] users = configuration.getStringArray("workload.users");
		String[] dataSizeSet = configuration.getStringArray("workload.dataSize");
		String[] dataStoreSet = new String[] { "Memcached", "Redis", "Mongodb", "MySQL" };

		for (String dataSize : dataSizeSet) {
			for (String dataStore : dataStoreSet) {
				Map<Column, String> conditions = new HashMap<Column, String>();
				conditions.put(Column.DATA_SIZE, dataSize);
				conditions.put(Column.DATASTORE, dataStore);
				ChartHelper.drawChart(chartDir, width, height, resultSet, Column.METHOD, Column.THREAD, conditions);
			}
		}

		for (String user : users) {
			for (String dataStore : dataStoreSet) {
				Map<Column, String> conditions = new HashMap<Column, String>();
				conditions.put(Column.THREAD, user);
				conditions.put(Column.DATASTORE, dataStore);
				ChartHelper.drawChart(chartDir, width, height, resultSet, Column.METHOD, Column.DATA_SIZE, conditions);
			}
		}

		for (String dataSize : dataSizeSet) {
			for (String dataStore : dataStoreSet) {
				Map<Column, String> conditions = new HashMap<Column, String>();
				conditions.put(Column.DATA_SIZE, dataSize);
				ChartHelper.drawChart(chartDir, width, height, resultSet, Column.METHOD, Column.THREAD, conditions);
			}
		}

		for (String user : users) {
			for (String dataStore : dataStoreSet) {
				Map<Column, String> conditions = new HashMap<Column, String>();
				conditions.put(Column.THREAD, user);
				ChartHelper.drawChart(chartDir, width, height, resultSet, Column.METHOD, Column.DATA_SIZE, conditions);
			}
		}

	}

}
