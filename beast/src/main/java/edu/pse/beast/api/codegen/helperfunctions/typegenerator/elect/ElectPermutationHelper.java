package edu.pse.beast.api.codegen.helperfunctions.typegenerator.elect;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.helperfunctions.code_template.templates.elect.CodeTemplateElectPermutation;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class ElectPermutationHelper {
	public static String generateCode(
			String generatedVarName,
			String varName,
			ElectionTypeCStruct electStruct,
			VotingOutputTypes votingOutputType,
			CodeGenOptions options,
			LoopBoundHandler loopBoundHandler) {
		
		Map<String, String> replacementMap = Map.of(
					"ELECT_TYPE", electStruct.getStruct().getName(),
					"AMT_MEMBER", electStruct.getAmtName(),
					"LIST_MEMBER", electStruct.getListName(),
					"GENERATED_VAR_NAME", generatedVarName,
					"ASSUME", options.getCbmcAssumeName(),
					"NONDET_UINT", options.getCbmcNondetUintName(),
					"RHS", varName,
					"PERM", "permutationIndices",
					"AMT_CANDIDATES", options.getCbmcAmountCandidatesVarName()
				);
		
		String code = null;		
		List<LoopBoundType> loopbounds = List.of();
	
		switch(votingOutputType) {
			case CANDIDATE_LIST : {
				code = CodeTemplateElectPermutation.templateCandidateList;
				loopbounds = CodeTemplateElectPermutation.loopBoundsCandidateList;
				break;
			}
			case PARLIAMENT : {
				break;
			}
			case PARLIAMENT_STACK : {
				break;
			}
			case SINGLE_CANDIDATE : {
				break;
			}
		}

		loopBoundHandler.pushMainLoopBounds(loopbounds);
		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
		return code;
	}
}
