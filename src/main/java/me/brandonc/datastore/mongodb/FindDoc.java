package me.brandonc.datastore.mongodb;

import me.brandonc.benchmark.AbstractKeyValueAction;
import me.brandonc.benchmark.Context;
import me.brandonc.benchmark.exception.OperationException;

import com.mongodb.DB;
import com.mongodb.DBObject;

public class FindDoc extends AbstractKeyValueAction<DB> {

	public FindDoc() {
		super("Mongodb-FindDoc");
	}

	private String collectionName;

	@Override
	public void setup(Context<DB> context) {
		super.setup(context);

		collectionName = context.getConfiguration().getString("mongodb.collection");

	}

	@Override
	public boolean doAction(DB connection, String key, String value) throws OperationException {

		DBObject result = connection.getCollection(collectionName).findOne(key);
		if (result == null) {
			return false;
		} else {
			return result.containsField("value");
		}
	}

}
