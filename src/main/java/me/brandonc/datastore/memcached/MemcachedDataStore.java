package me.brandonc.datastore.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeoutException;

import me.brandonc.benchmark.config.Configuration;
import me.brandonc.benchmark.exception.OperationException;
import me.brandonc.datastore.DataStore;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

public class MemcachedDataStore implements DataStore<XMemcachedClient> {

	private XMemcachedClient client = null;

	private Configuration configuration;

	public MemcachedDataStore(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public void init() {
		try {
			client = new XMemcachedClient(new InetSocketAddress(configuration.getString("memcached.host"), configuration.getIntValue("memcached.port")));
			client.setOpTimeout(configuration.getIntValue("memcached.opTimeout"));
		} catch (IOException e) {
			throw new OperationException(e);
		}

	}

	@Override
	public void releaseConnection(XMemcachedClient connection) {
		// ignored
	}

	@Override
	public void destory() {
		try {
			if (client != null) {
				client.shutdown();
			}
		} catch (IOException e) {
			throw new OperationException(e);
		}

	}

	public XMemcachedClient getConnection() {
		return client;
	}

	@Override
	public void clean() {

		try {
			getConnection().flushAll();
		} catch (TimeoutException e) {
			throw new OperationException(e);
		} catch (InterruptedException e) {
			throw new OperationException(e);
		} catch (MemcachedException e) {
			throw new OperationException(e);
		}

	}

}