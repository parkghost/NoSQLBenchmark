package me.brandonc.datastore.memcached;

import java.util.concurrent.TimeoutException;

import me.brandonc.benchmark.AbstractKeyValueAction;
import me.brandonc.benchmark.Context;
import me.brandonc.benchmark.exception.OperationException;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

public class Set extends AbstractKeyValueAction<XMemcachedClient> {

	public Set() {
		super("Memcached-Set");
	}

	private int expired;
	private int opTimeout;

	@Override
	public boolean isAutoClean() {
		return true;
	}

	@Override
	public void setup(Context<XMemcachedClient> context) {
		super.setup(context);
		expired = context.getConfiguration().getIntValue("memcached.expired");
		opTimeout = context.getConfiguration().getIntValue("memcached.opTimeout");
	}

	@Override
	public boolean doAction(XMemcachedClient connection, String key, String value) {
		try {
			return connection.set(key, expired, value, opTimeout);

		} catch (TimeoutException e) {
			throw new OperationException(e);
		} catch (InterruptedException e) {
			throw new OperationException(e);
		} catch (MemcachedException e) {
			throw new OperationException(e);
		}
	}

}
