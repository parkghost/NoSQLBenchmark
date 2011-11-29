package me.brandonc.datastore.mongodb;

import java.net.UnknownHostException;

import me.brandonc.benchmark.config.Configuration;
import me.brandonc.benchmark.exception.OperationException;
import me.brandonc.datastore.DataStore;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class MongodbDataStore implements DataStore<DB> {

	private Mongo client = null;

	private Configuration configuration;

	private String dbName;

	public MongodbDataStore(Configuration configuration) {
		this.configuration = configuration;
		this.dbName = configuration.getString("mongodb.db");
	}

	@Override
	public DB getConnection() {
		return client.getDB(dbName);
	}

	@Override
	public void clean() {
		getConnection().getCollection(configuration.getString("mongodb.collection")).drop();
	}

	@Override
	public void init() {
		try {
			MongoOptions options = new MongoOptions();
			options.connectionsPerHost = configuration.getIntValue("mongodb.connectionsPerHost");
			options.threadsAllowedToBlockForConnectionMultiplier = configuration.getIntValue("mongodb.threadsAllowedToBlockForConnectionMultiplier");

			ServerAddress serverAddress = new ServerAddress(configuration.getString("mongodb.host"), configuration.getIntValue("mongodb.port"));
			client = new Mongo(serverAddress, options);
			client.setWriteConcern(WriteConcern.valueOf(configuration.getString("mongodb.WriteConcern")));
		} catch (UnknownHostException e) {
			throw new OperationException(e);
		} catch (MongoException e) {
			throw new OperationException(e);
		}
	}

	@Override
	public void releaseConnection(DB connection) {
		// ignored
	}

	@Override
	public void destory() {
		if (client != null) {
			client.close();
		}
	}
}
