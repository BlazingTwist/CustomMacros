package config.instructions.callbacks;

public abstract class InstructionCallback {
	private InstructionCallbackType callbackType;

	protected InstructionCallback(InstructionCallbackType callbackType){
		this.callbackType = callbackType;
	}

	public InstructionCallbackType getCallbackType() {
		return callbackType;
	}
}
