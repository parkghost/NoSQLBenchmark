package me.brandonc.datastore.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.brandonc.benchmark.AbstractKeyValueAction;
import me.brandonc.benchmark.exception.OperationException;

public class SelectValueByKey extends AbstractKeyValueAction<Connection> {

	public SelectValueByKey() {
		super("MySQL-SelectValueByKey");
	}

	@Override
	public boolean doAction(Connection connection, String key, String value) throws OperationException {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT value FROM keyvalues WHERE `key`=?");
			preparedStatement.setString(1, key);
			ResultSet resultSet = preparedStatement.executeQuery();

			boolean result = resultSet.next();
			preparedStatement.close();
			return result;
		} catch (SQLException e) {
			throw new OperationException(e);
		}
	}
}
