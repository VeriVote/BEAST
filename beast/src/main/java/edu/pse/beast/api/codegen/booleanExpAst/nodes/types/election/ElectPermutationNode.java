package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.types.InOutType;

public class ElectPermutationNode extends ElectionTypeNode {
	
	private int electNumber;

	public ElectPermutationNode(InOutType inOutType) {
		super(inOutType);
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
		
	}

	@Override
	public String getTreeString(int depth) {
		return "Permutation Elect " + electNumber;
	}
	
	public void setElectNumber(int electionNumber) {
		this.electNumber = electionNumber;
	}
	
	public int getElectNumber() {
		return electNumber;
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitElectPermutation(this);
	}

}