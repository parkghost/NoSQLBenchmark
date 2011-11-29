package me.brandonc.benchmark;

import me.brandonc.benchmark.config.Configuration;
import me.brandonc.datastore.DataStore;

public class ExecutionContext<T> implements Context<T> {

	private DataStore<T> dataStore;
	private Workload workload;
	private Configuration configuration;

	public ExecutionContext(Configuration configuration, DataStore<T> dataStore, Workload workload) {
		this.configuration = configuration;
		this.dataStore = dataStore;
		this.workload = workload;
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public Workload getWorkload() {
		return workload;
	}

	@Override
	public DataStore<T> getDataStore() {
		return dataStore;
	}

}
