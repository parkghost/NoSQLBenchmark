package me.brandonc.benchmark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.brandonc.benchmark.config.Configuration;
import me.brandonc.datastore.DataStore;

public class BenchmarkHelper {
	public static <T> void run(Configuration configuration, DataStore<T> dataStore, Action<T>... actions) {

		int requestCount = configuration.getIntValue("workload.requestCount");
		int[] usersSet = configuration.getIntArray("workload.users");
		int[] dataSizeSet = configuration.getIntArray("workload.dataSize");

		ExecutorService pool = null;
		try {

			pool = Executors.newFixedThreadPool(getMaxThreads(usersSet));

			dataStore.init();

			for (int users : usersSet) {
				for (int dataSize : dataSizeSet) {

					for (Action<T> action : actions) {

						if (action.isAutoClean()) {
							dataStore.clean();
						}

						Workload workload = new Workload(users, requestCount, dataSize);
						Context<T> context = new ExecutionContext<T>(configuration, dataStore, workload);

						if (workload.requestCount < workload.getConcurrencyLevel()) {
							throw new IllegalArgumentException("request count must larger than users");
						}

						BenchmarkTask<T> task = new BenchmarkTask<T>(pool, context, action);
						task.start();
					}

				}
			}
		} finally {
			if (pool != null) {
				pool.shutdown();
			}

			dataStore.destory();
		}

	}

	public static <T> void validateReqestCountAndExecutionsOfAction(int requestCount, Action<T>... actions) {
		for (Action<T> action : actions) {
			if (requestCount % action.getExecutions() != 0) {
				throw new IllegalArgumentException("requestCount:" + requestCount + " can't completely share at action:" + action.getName());
			}

		}
	}

	public static int getMaxThreads(int[] usersSet) {
		int maxThreadCount = 0;
		for (int users : usersSet) {
			if (maxThreadCount < users) {
				maxThreadCount = users;
			}
		}
		return maxThreadCount;
	}
}
