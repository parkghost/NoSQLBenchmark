package me.brandonc.benchmark;

public class CompositeAction<T> implements Action<T> {

	private String name;
	private Action<T>[] actions;
	private int totalActions;

	public CompositeAction(String name, Action<T>... actions) {
		this.name = name;
		this.actions = actions;

		for (Action<T> action : actions) {
			this.totalActions += action.getExecutions();
		}
	}

	@Override
	public boolean isAutoClean() {
		for (Action<T> action : actions) {
			if (action.isAutoClean()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setup(Context<T> context) {
		for (Action<T> action : actions) {
			action.setup(context);
		}
	}

	@Override
	public void execute(Context<T> context, int id) {
		int sharedId = id / actions.length;
		Action<T> action = actions[id % actions.length];
		action.execute(context, sharedId);

	}

	@Override
	public void teardown(Context<T> context) {

		for (Action<T> action : actions) {
			action.teardown(context);
		}

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getExecutions() {
		return totalActions;
	}

}
