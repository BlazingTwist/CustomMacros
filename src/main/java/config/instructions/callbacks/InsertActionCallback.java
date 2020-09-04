package config.instructions.callbacks;

import config.instructions.Instruction;
import java.util.List;

public class InsertActionCallback extends InstructionCallback {
	private List<Instruction> action;

	public InsertActionCallback(List<Instruction> action) {
		super(InstructionCallbackType.INSERT_ACTION);
		this.action = action;
	}

	public List<Instruction> getAction() {
		return action;
	}
}
