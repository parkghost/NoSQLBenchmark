package me.brandonc.datastore.mysql;

import java.sql.Connection;

import me.brandonc.benchmark.BenchmarkHelper;
import me.brandonc.benchmark.CompositeAction;
import me.brandonc.benchmark.config.Configuration;
import me.brandonc.benchmark.config.PropertiesBasedConfiguration;
import me.brandonc.datastore.DataStore;

public class MySQLBenchmark {
	public static void main(String[] args) {

		Configuration configuration = new PropertiesBasedConfiguration("configuration/configuration.properties");
		DataStore<Connection> dataStore = new MySQLDataStore(configuration);
		BenchmarkHelper.run(configuration, dataStore, new InsertKeyValue(), new SelectValueByKey(), new CompositeAction<Connection>("MySQL-InsertAndSelect", new InsertKeyValue(),
				new SelectValueByKey()));

	}

}
