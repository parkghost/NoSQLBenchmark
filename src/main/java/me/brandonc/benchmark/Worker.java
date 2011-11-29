package me.brandonc.benchmark;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import me.brandonc.benchmark.exception.OperationException;

public class Worker<T> implements Runnable {
	private final CyclicBarrier barrier;
	private final int begin;
	private final int end;
	private final Action<T> action;
	private final Context<T> context;

	public Worker(int begin, int end, Context<T> context, Action<T> action, CyclicBarrier barrier) {
		this.begin = begin;
		this.end = end;
		this.context = context;
		this.action = action;
		this.barrier = barrier;

	}

	public void run() {

		callAwait();

		try {
			action.setup(context);

			for (int id = begin; id < end; id++) {
				action.execute(context, id);
			}
			action.teardown(context);
		} finally {
			callAwait();
		}

	}

	public void callAwait() {
		try {
			barrier.await();
		} catch (InterruptedException e) {
			throw new OperationException(e);
		} catch (BrokenBarrierException e) {
			throw new OperationException(e);
		}

	}

}
