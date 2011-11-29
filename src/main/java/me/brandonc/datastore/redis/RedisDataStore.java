package me.brandonc.datastore.redis;

import me.brandonc.benchmark.config.Configuration;
import me.brandonc.datastore.DataStore;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisDataStore implements DataStore<Jedis> {

	private JedisPool pool;

	private Configuration configuration;

	public RedisDataStore(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public void clean() {
		Jedis jedis = getConnection();
		jedis.flushDB();
		releaseConnection(jedis);
	}

	@Override
	public Jedis getConnection() {
		return pool.getResource();
	}

	@Override
	public void init() {
		JedisPoolConfig config = new JedisPoolConfig();

		config.setMaxActive(configuration.getIntValue("redis.maxActive"));
		config.setMaxIdle(configuration.getIntValue("redis.maxIdle"));

		pool = new JedisPool(config, configuration.getString("redis.host"), configuration.getIntValue("redis.port"));

	}

	@Override
	public void releaseConnection(Jedis connection) {
		pool.returnResource(connection);
	}

	@Override
	public void destory() {
		pool.destroy();
	}
}
