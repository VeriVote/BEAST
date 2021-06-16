package edu.pse.beast.api.codegen.helperfunctions.code_template.templates;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateInitVote {
	public final static String templateSingleChoice =
			  "    //initializing Vote VOTE_NUMBER\n" 
			+ "    unsigned int CURRENT_AMT_VOTER = NONDET_UINT();\n"
			+ "    ASSUME(CURRENT_AMT_VOTER >= 0);\n"
			+ "    ASSUME(CURRENT_AMT_VOTER < MAX_AMT_VOTER);\n"
			+ "    unsigned int CURRENT_AMT_SEAT = NONDET_UINT();\n"
			+ "    ASSUME(CURRENT_AMT_SEAT >= 0);\n"
			+ "    ASSUME(CURRENT_AMT_SEAT < MAX_AMT_SEAT);\n"
			+ "    unsigned int CURRENT_AMT_CAND = NONDET_UINT();\n"
			+ "    ASSUME(CURRENT_AMT_CAND >= 0);\n"
			+ "    ASSUME(CURRENT_AMT_CAND < MAX_AMT_CAND);\n"
			+ "    VOTE_TYPE VAR_NAME;\n"
			+ "    VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "    ASSUME(VAR_NAME.amtVotes == CURRENT_AMT_VOTER);\n"
			+ "    for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {\n"
			+ "            VAR_NAME.LIST_MEMBER[i] = NONDET_UINT();\n"
			+ "            ASSUME(VAR_NAME.votes[i] >= LOWER_VOTE_BOUND);\n"
			+ "            ASSUME(VAR_NAME.votes[i] < UPPER_VOTE_BOUND);\n"
			+ "    }\n"
			+ "    for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {\n"
			+ "            VAR_NAME.LIST_MEMBER[i] = NONDET_UINT();\n"
			+ "            ASSUME(VAR_NAME.votes[i] == INVALID_VOTE);\n"
			+ "    }\n";

	public final static List<LoopBound> loopBoundsSingleChoice =
			LoopBound.codeGenLoopboundList(
					Arrays.asList(
							LoopBoundType.LOOP_BOUND_AMT_VOTERS, 
							LoopBoundType.LOOP_BOUND_AMT_VOTERS)
					);

	public final static String templatePreference = 
			  "    //initializing Vote VOTE_NUMBER\n" 
			+ "    unsigned int CURRENT_AMT_VOTER = NONDET_UINT();\n"
			+ "    ASSUME(CURRENT_AMT_VOTER >= 0);\n"
			+ "    ASSUME(CURRENT_AMT_VOTER < MAX_AMT_VOTER);\n"
			+ "    unsigned int CURRENT_AMT_SEAT = NONDET_UINT();\n"
			+ "    ASSUME(CURRENT_AMT_SEAT >= 0);\n"
			+ "    ASSUME(CURRENT_AMT_SEAT < MAX_AMT_SEAT);\n"
			+ "    unsigned int CURRENT_AMT_CAND = NONDET_UINT();\n"
			+ "    ASSUME(CURRENT_AMT_CAND >= 0);\n"
			+ "    ASSUME(CURRENT_AMT_CAND < MAX_AMT_CAND);\n"
			+ "    VOTE_TYPE VAR_NAME;\n"		
			+ "    VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "    ASSUME(VAR_NAME.amtVotes == CURRENT_AMT_VOTER);\n"
			+ "    for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {\n"
			+ "        for (int j = 0; j < CURRENT_AMT_CAND; ++j) {\n"
			+ "            VAR_NAME.LIST_MEMBER[i][j] = NONDET_UINT();\n"
			+ "            ASSUME(VAR_NAME.LIST_MEMBER[i][j] >= LOWER_VOTE_BOUND);\n"
			+ "            ASSUME(VAR_NAME.LIST_MEMBER[i][j] < UPPER_VOTE_BOUND);\n"
			+ "        }\n" 
			+ "    }\n"
			+ "    for (int i = CURRENT_AMT_VOTER; i < AMT_VOTERS; ++i) {\n"
			+ "        for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
			+ "            VAR_NAME.LIST_MEMBER[i][j] = NONDET_UINT();\n"
			+ "            ASSUME(VAR_NAME.LIST_MEMBER[i][j] == INVALID_VOTE);\n"
			+ "        }\n" 
			+ "    }\n"
			+ "    for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {\n"
			+ "        unsigned int tmp[AMT_CANDIDATES];\n"
			+ "        for (int k = 0; k < CURRENT_AMT_CAND; ++k) {\n"
			+ "            tmp[k] = 0;\n" + "        }\n"
			+ "        for (int j = 0; j < CURRENT_AMT_CAND; ++j) {\n"
			+ "            for (int k = 0; k < CURRENT_AMT_CAND; ++k) {\n"
			+ "                if (VAR_NAME.LIST_MEMBER[i][j] == k) {\n"
			+ "                    ASSUME(tmp[k] == 0);\n"
			+ "                    tmp[k] = 1;\n"
			+ "                }\n"
			+ "            }\n" 
			+ "        }\n" 
			+ "    }\n";

	public final static List<LoopBound> loopBoundsPreference =
			LoopBound.codeGenLoopboundList(
				Arrays
				.asList(LoopBoundType.NECESSARY_LOOP_BOUND_AMT_VOTERS,
						LoopBoundType.LOOP_BOUND_AMT_CANDS,
						LoopBoundType.LOOP_BOUND_AMT_VOTERS,
						LoopBoundType.LOOP_BOUND_AMT_CANDS,
						LoopBoundType.NECESSARY_LOOP_BOUND_AMT_CANDS,
						LoopBoundType.LOOP_BOUND_AMT_CANDS)
			);

	public final static String templateApproval = 
			  "    //initializing Vote VOTE_NUMBER\n" 
			+ "    unsigned int CURRENT_AMT_VOTER = NONDET_UINT();\n"
			+ "    ASSUME(CURRENT_AMT_VOTER >= 0);\n"
			+ "    ASSUME(CURRENT_AMT_VOTER < MAX_AMT_VOTER);\n"
			+ "    unsigned int CURRENT_AMT_SEAT = NONDET_UINT();\n"
			+ "    ASSUME(CURRENT_AMT_SEAT >= 0);\n"
			+ "    ASSUME(CURRENT_AMT_SEAT < MAX_AMT_SEAT);\n"
			+ "    unsigned int CURRENT_AMT_CAND = NONDET_UINT();\n"
			+ "    ASSUME(CURRENT_AMT_CAND >= 0);\n"
			+ "    ASSUME(CURRENT_AMT_CAND < MAX_AMT_CAND);\n"
			+ "    VOTE_TYPE VAR_NAME;\n"		
			+ "    VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "    ASSUME(VAR_NAME.amtVotes == CURRENT_AMT_VOTER);\n"
			+ "    for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {\n"
			+ "        for (int j = 0; j < CURRENT_AMT_CAND; ++j) {\n"
			+ "            VAR_NAME.LIST_MEMBER[i][j] = NONDET_UINT();\n"
			+ "            ASSUME(VAR_NAME.LIST_MEMBER[i][j] >= LOWER_VOTE_BOUND);\n"
			+ "            ASSUME(VAR_NAME.LIST_MEMBER[i][j] < UPPER_VOTE_BOUND);\n"
			+ "        }\n"
			+ "    }\n"
			+ "    for (int i = CURRENT_AMT_VOTER; i < AMT_VOTERS; ++i) {\n"
			+ "        for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
			+ "            VAR_NAME.LIST_MEMBER[i][j] = NONDET_UINT();\n"
			+ "            ASSUME(VAR_NAME.LIST_MEMBER[i][j] == INVALID_VOTE);\n"
			+ "        }\n" 
			+ "    }\n";

	public final static List<LoopBound> loopBoundsApproval =
			LoopBound.codeGenLoopboundList(
				Arrays.asList(
				LoopBoundType.NECESSARY_LOOP_BOUND_AMT_VOTERS,
				LoopBoundType.LOOP_BOUND_AMT_CANDS,
				LoopBoundType.LOOP_BOUND_AMT_VOTERS,
				LoopBoundType.LOOP_BOUND_AMT_CANDS)
			);
}
