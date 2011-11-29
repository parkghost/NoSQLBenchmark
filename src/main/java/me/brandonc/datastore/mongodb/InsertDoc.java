package me.brandonc.datastore.mongodb;

import me.brandonc.benchmark.AbstractKeyValueAction;
import me.brandonc.benchmark.Context;
import me.brandonc.benchmark.exception.OperationException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.WriteResult;

public class InsertDoc extends AbstractKeyValueAction<DB> {

	public InsertDoc() {
		super("Mongodb-InsertDoc");
	}

	private String collectionName;

	@Override
	public boolean isAutoClean() {
		return true;
	}

	@Override
	public void setup(Context<DB> context) {
		super.setup(context);

		collectionName = context.getConfiguration().getString("mongodb.collection");

	}

	@Override
	public boolean doAction(DB connection, String key, String value) throws OperationException {

		BasicDBObject doc = new BasicDBObject();

		doc.put("_id", key);
		doc.put("value", value);

		WriteResult result = connection.getCollection(collectionName).insert(doc);

		return result.getError() == null ? true : false;
	}

}
