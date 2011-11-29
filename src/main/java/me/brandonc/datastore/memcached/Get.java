package me.brandonc.datastore.memcached;

import java.util.concurrent.TimeoutException;

import me.brandonc.benchmark.AbstractKeyValueAction;
import me.brandonc.benchmark.exception.OperationException;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

public class Get extends AbstractKeyValueAction<XMemcachedClient> {

	public Get() {
		super("Memcached-Get");
	}

	@Override
	public boolean doAction(XMemcachedClient connection, String key, String value) {
		try {
			Object obj = connection.get(key);
			return obj != null;
		} catch (TimeoutException e) {
			throw new OperationException(e);
		} catch (InterruptedException e) {
			throw new OperationException(e);
		} catch (MemcachedException e) {
			throw new OperationException(e);
		}
	}

}
