package me.brandonc.datastore.memcached;

import me.brandonc.benchmark.BenchmarkHelper;
import me.brandonc.benchmark.CompositeAction;
import me.brandonc.benchmark.config.Configuration;
import me.brandonc.benchmark.config.PropertiesBasedConfiguration;
import me.brandonc.datastore.DataStore;
import net.rubyeye.xmemcached.XMemcachedClient;

public class MemcachedBenchmark {
	public static void main(String[] args) {
		Configuration configuration = new PropertiesBasedConfiguration("configuration/configuration.properties");
		DataStore<XMemcachedClient> dataStore = new MemcachedDataStore(configuration);
		BenchmarkHelper.run(configuration, dataStore, new Set(), new Get(), new CompositeAction("Memcached-SetAndGet", new Set(), new Get()));

	}
}
