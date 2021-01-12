package edu.pse.beast.api.electiondescription;

import java.util.ArrayList;
import java.util.List;

public class VotingSigFunction {
	private String name;
	private List<String> code = new ArrayList<>();
	VotingInputTypes inputType;
	VotingOutputTypes outputType;

	public VotingSigFunction(String name, VotingInputTypes inputType, VotingOutputTypes outputType) {
		this.name = name;
		this.inputType = inputType;
		this.outputType = outputType;
	}

	public String getName() {
		return name;
	}

	public VotingInputTypes getInputType() {
		return inputType;
	}

	public VotingOutputTypes getOutputType() {
		return outputType;
	}

	public String getVotingVarName() {
		return "votes";
	}

	public List<String> getCode() {
		return code;
	}

}
