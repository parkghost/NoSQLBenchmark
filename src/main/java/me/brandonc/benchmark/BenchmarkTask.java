package me.brandonc.benchmark;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

import me.brandonc.benchmark.exception.OperationException;

public class BenchmarkTask<T> {

	private ExecutorService service;
	private Context<T> context;
	private Action<T> action;

	public BenchmarkTask(ExecutorService service, Context<T> context, Action<T> action) {
		this.service = service;
		this.context = context;
		this.action = action;
	}

	public void start() {
		try {

			Workload workload = context.getWorkload();

			CyclicBarrier barrier = new CyclicBarrier(workload.getConcurrencyLevel() + 1);

			int requestCount = workload.getRequestCount();
			int concurrencyLevel = workload.getConcurrencyLevel();

			// CompositionAction may contain many executions per action
			int executionsPerAction = action.getExecutions();

			if (requestCount % concurrencyLevel == 0 && (requestCount / concurrencyLevel) % executionsPerAction == 0) {
				// fairness execute action on each thread
				int executionsPerWorker = requestCount / concurrencyLevel;
				for (int i = 0; i < concurrencyLevel; i++) {
					service.execute(new Worker<T>(i * executionsPerWorker, i * executionsPerWorker + executionsPerWorker, context, action, barrier));
				}
			} else {

				if (requestCount % executionsPerAction == 0) {
					// some worker run more action
					int additionActions = (requestCount / executionsPerAction) % concurrencyLevel;
					int averageExecutionPerWorker = (requestCount - additionActions * executionsPerAction) / concurrencyLevel;

					for (int i = 0; i < requestCount;) {
						int executions = averageExecutionPerWorker;
						if (additionActions > 0) {
							executions += executionsPerAction;
							additionActions--;
						}
						service.execute(new Worker<T>(i, i + executions, context, action, barrier));
						i = i + executions;
					}
				} else {
					throw new IllegalArgumentException("requestCount:" + requestCount + " can't completely share at action:" + action.getName());
				}
			}

			barrier.await(); // start benchmark
			long start = System.currentTimeMillis();
			barrier.await(); // end benchmark
			long elapsed = System.currentTimeMillis() - start;

			float tps = workload.getRequestCount() / (elapsed / 1000f);
			float dps = workload.getRequestCount() * workload.getDataSize() / 1024 / 1024 / (elapsed / 1000f);

			System.out.printf("Method: %16s \tThreads: %d \tDataSize: %d \tRequsetCount: %d  \tTPS: %.1f \tMB/sec: %.3f", action.getName(), workload.getConcurrencyLevel(), workload.getDataSize(),
					workload.getRequestCount(), tps, dps);
			System.out.println();
		} catch (InterruptedException e) {
			throw new OperationException(e);
		} catch (BrokenBarrierException e) {
			throw new OperationException(e);
		}
	}
}
