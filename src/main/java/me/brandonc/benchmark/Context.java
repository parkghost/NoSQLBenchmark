package me.brandonc.benchmark;

import me.brandonc.benchmark.config.Configuration;
import me.brandonc.datastore.DataStore;

public interface Context<T> {

	Configuration getConfiguration();

	DataStore<T> getDataStore();

	Workload getWorkload();

}
