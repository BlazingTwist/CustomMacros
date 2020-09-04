package config.instructions.callbacks;

public class ChangeToActionCallback extends InstructionCallback {
	private String actionName;

	public ChangeToActionCallback(String actionName) {
		super(InstructionCallbackType.CHANGE_TO_ACTION);
		this.actionName = actionName;
	}

	public String getActionName() {
		return actionName;
	}
}
