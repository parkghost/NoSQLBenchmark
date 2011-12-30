package me.brandonc.datastore.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import me.brandonc.benchmark.config.Configuration;
import me.brandonc.benchmark.exception.OperationException;
import me.brandonc.datastore.DataStore;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class MySQLDataStore implements DataStore<Connection> {

	private Configuration configuration;

	private DataSource pool;

	public MySQLDataStore(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public Connection getConnection() {
		try {
			return pool.getConnection();
		} catch (SQLException e) {
			throw new OperationException(e);
		}
	}

	@Override
	public void clean() {
		Connection connection = null;
		try {
			connection = getConnection();

			Statement statement = connection.createStatement();
			statement.executeUpdate("truncate keyvalues");
			statement.close();

		} catch (SQLException e) {
			throw new OperationException(e);
		} finally {
			if (connection != null) {
				releaseConnection(connection);
			}

		}

	}

	@Override
	public void init() {
		PoolProperties p = new PoolProperties();
		p.setUrl(configuration.getString("jdbc.url"));
		p.setDriverClassName(configuration.getString("jdbc.className"));
		p.setUsername(configuration.getString("jdbc.username"));
		p.setPassword(configuration.getString("jdbc.password"));
		p.setJmxEnabled(true);
		p.setMaxActive(configuration.getIntValue("jdbc.maxActive"));
		p.setMaxIdle(configuration.getIntValue("jdbc.maxIdle"));
		p.setJdbcInterceptors(configuration.getString("jdbc.jdbcInterceptors"));
		pool = new DataSource();
		pool.setPoolProperties(p);

	}

	@Override
	public void releaseConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new OperationException(e);
		}
	}

	@Override
	public void destory() {
		if (pool != null) {
			pool.close();
		}
	}
}