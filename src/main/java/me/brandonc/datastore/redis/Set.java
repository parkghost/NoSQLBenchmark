package me.brandonc.datastore.redis;

import me.brandonc.benchmark.AbstractKeyValueAction;
import redis.clients.jedis.Jedis;

public class Set extends AbstractKeyValueAction<Jedis> {

	public Set() {
		super("Redis-Set");
	}

	@Override
	public boolean isAutoClean() {
		return true;
	}

	@Override
	public boolean doAction(Jedis connection, String key, String value) {
		String result = connection.set(key, value);
		return result.equals("OK");
	}

}
