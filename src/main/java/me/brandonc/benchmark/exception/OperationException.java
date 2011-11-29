package me.brandonc.benchmark.exception;

public class OperationException extends RuntimeException {

	private static final long serialVersionUID = 9106610125452857386L;

	public OperationException(Exception exception) {
		super(exception);
	}
}
