package me.brandonc.datastore;

public interface DataStore<T> {

	void init();

	void clean();

	T getConnection();

	void releaseConnection(T connection);

	void destory();

}
