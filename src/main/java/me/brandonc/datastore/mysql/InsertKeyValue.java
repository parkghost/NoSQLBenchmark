package me.brandonc.datastore.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import me.brandonc.benchmark.AbstractKeyValueAction;
import me.brandonc.benchmark.exception.OperationException;

public class InsertKeyValue extends AbstractKeyValueAction<Connection> {

	public InsertKeyValue() {
		super("MySQL-InsertKeyValue");
	}

	@Override
	public boolean isAutoClean() {
		return true;
	}

	@Override
	public boolean doAction(Connection conection, String key, String value) throws OperationException {
		try {
			PreparedStatement preparedStatement = conection.prepareStatement("INSERT INTO keyvalues VALUES(?, ?)");
			preparedStatement.setString(1, key);
			preparedStatement.setString(2, value);
			int result = preparedStatement.executeUpdate();
			preparedStatement.close();

			return result > 0;

		} catch (SQLException e) {
			throw new OperationException(e);
		}
	}
}
