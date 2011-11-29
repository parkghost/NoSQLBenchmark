package me.brandonc.benchmark;

public class Workload {
	public int concurrencyLevel;
	public int requestCount;
	public int dataSize;

	public Workload(int concurrencyLevel, int requestCount, int dataSize) {
		this.concurrencyLevel = concurrencyLevel;
		this.requestCount = requestCount;
		this.dataSize = dataSize;
	}

	public int getConcurrencyLevel() {
		return concurrencyLevel;
	}

	public void setConcurrencyLevel(int concurrencyLevel) {
		this.concurrencyLevel = concurrencyLevel;
	}

	public int getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

}
