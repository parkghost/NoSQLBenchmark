package me.brandonc.benchmark;

public interface Action<T> {

	String getName();

	boolean isAutoClean();

	int getExecutions();

	void setup(Context<T> context);

	void execute(Context<T> context, int id);

	void teardown(Context<T> context);

}
