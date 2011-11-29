package me.brandonc.benchmark;

public class SampleResult {

	private boolean result = false;

	private long startTime = 0;

	private long endTime = 0;

	public void sampleStart() {

		if (startTime > 0) {
			throw new IllegalStateException("already called");
		}

		startTime = System.nanoTime();
	}

	public void sampleEnd() {

		if (startTime == 0) {
			throw new IllegalStateException("must call sampleStart() first");
		}

		endTime = System.nanoTime();
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public long getExecuteTime() {

		if (startTime == 0 || endTime == 0) {
			throw new IllegalStateException("must call sampleStart()/sampleEnd() before");
		}

		return endTime - startTime;
	}

	public boolean isResult() {
		return result;
	}

}
