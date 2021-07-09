package edu.pse.beast.api.codegen.helperfunctions.init_vote;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.specificcbmcrun.VotingParameters;

public class SpecificValueInitVoteHelper extends InitVoteHelper {

	private VotingParameters votingParameters;

	public SpecificValueInitVoteHelper(VotingParameters votingParameters) {
		this.votingParameters = votingParameters;
	}

	@Override
	public String generateCode(int voteNumber,
			ElectionTypeCStruct voteArrStruct, VotingInputTypes votingInputType,
			CodeGenOptions options, CodeGenLoopBoundHandler loopBoundHandler,
			CBMCGeneratedCodeInfo codeInfo) {
		String voteVarName = getVoteVarName(voteNumber);
		return votingParameters.generateVoteStructInitCode(voteArrStruct,
				options, codeInfo, voteVarName);
	}

	@Override
	public int getHighestVote() {
		return votingParameters.getHighestVote();
	}

}
