package config.instructions.callbacks;

public class EncounteredExceptionCallback extends InstructionCallback {
	private Exception exception;

	public EncounteredExceptionCallback(Exception exception){
		super(InstructionCallbackType.ENCOUNTERED_EXCEPTION);
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}
}
