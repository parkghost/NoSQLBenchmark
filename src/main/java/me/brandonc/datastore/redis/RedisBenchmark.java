package me.brandonc.datastore.redis;

import me.brandonc.benchmark.BenchmarkHelper;
import me.brandonc.benchmark.CompositeAction;
import me.brandonc.benchmark.config.Configuration;
import me.brandonc.benchmark.config.PropertiesBasedConfiguration;
import me.brandonc.datastore.DataStore;
import redis.clients.jedis.Jedis;

public class RedisBenchmark {
	public static void main(String[] args) {

		Configuration configuration = new PropertiesBasedConfiguration("configuration/configuration.properties");
		DataStore<Jedis> dataStore = new RedisDataStore(configuration);
		BenchmarkHelper.run(configuration, dataStore, new Set(), new Get(), new CompositeAction<Jedis>("Redis-SetAndGet", new Set(), new Get()));

	}
}
