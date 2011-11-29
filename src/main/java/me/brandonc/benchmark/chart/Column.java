package me.brandonc.benchmark.chart;

public enum Column {
	DATASTORE("DataStore"), ENGINE("Engine"), SETTING("Setting"), METHOD("Method"), THREAD("Threads"), DATA_SIZE("DataSize"), REQUEST_COUNT("RequsetCount"), TPS("TPS"), MB_SEC("MB/sec");
	public String name;

	Column(String name) {
		this.name = name;
	}

	public static int resolve(String name) {
		Column[] columns = Column.values();
		for (Column column : columns) {
			if (column.name.equals(name)) {
				return column.ordinal();
			}

		}
		return -1;
	}
}
