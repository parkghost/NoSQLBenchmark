package me.brandonc.benchmark;

import me.brandonc.benchmark.exception.OperationException;
import me.brandonc.datastore.DataStore;

public abstract class AbstractKeyValueAction<T> implements Action<T> {

	private String name;

	private boolean autoClean = false;

	public AbstractKeyValueAction(String name) {
		this.name = name;
	}

	@Override
	public void execute(Context<T> context, int id) {

		SampleResult result = new SampleResult();
		result.sampleStart();
		DataStore<T> dataStore = context.getDataStore();
		T connection = dataStore.getConnection();

		try {

			Workload workload = context.getWorkload();
			String key = Integer.toString(id);
			String value = RandomStringUtil.getString(workload.getDataSize());
			result.setResult(doAction(connection, key, value));

		} catch (OperationException e) {
			// TODO flow-control
			e.printStackTrace();
		} finally {
			dataStore.releaseConnection(connection);
		}

		if (!result.isResult()) {
			System.out.println("[" + this.getName() + "] key missing or blank result at key:" + id);
		}

		result.sampleEnd();

		// TODO statistic
	}

	@Override
	public void setup(Context<T> context) {

	}

	@Override
	public void teardown(Context<T> context) {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean isAutoClean() {
		return autoClean;
	}

	@Override
	public int getExecutions() {
		return 1;
	}

	public abstract boolean doAction(T connection, String key, String value);

}
