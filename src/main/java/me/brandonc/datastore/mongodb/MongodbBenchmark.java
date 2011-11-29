package me.brandonc.datastore.mongodb;

import me.brandonc.benchmark.BenchmarkHelper;
import me.brandonc.benchmark.CompositeAction;
import me.brandonc.benchmark.config.Configuration;
import me.brandonc.benchmark.config.PropertiesBasedConfiguration;
import me.brandonc.datastore.DataStore;

import com.mongodb.DB;

public class MongodbBenchmark {
	public static void main(String[] args) {

		Configuration configuration = new PropertiesBasedConfiguration("configuration/configuration.properties");
		DataStore<DB> dataStore = new MongodbDataStore(configuration);
		BenchmarkHelper.run(configuration, dataStore, new InsertDoc(), new FindDoc(), new CompositeAction<DB>("Mongodb-InsertAndFind", new InsertDoc(), new FindDoc()));

		// Mongodb-java-client use asynchronous writing to make high throughput
		// and therefore could not read result by the same key immediately

		// BenchmarkHelper.run(configuration, dataStore, new
		// CompositeAction<DB>("Mongodb-InsertAndFind", new InsertDoc(), new
		// FindDoc()));

	}
}
