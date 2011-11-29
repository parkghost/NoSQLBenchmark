package me.brandonc.datastore.redis;

import me.brandonc.benchmark.AbstractKeyValueAction;
import redis.clients.jedis.Jedis;

public class Get extends AbstractKeyValueAction<Jedis> {

	public Get() {
		super("Redis-Get");
	}

	public boolean doAction(Jedis connection, String key, String value) {
		String result = connection.get(key);
		return result != null;
	}
}
