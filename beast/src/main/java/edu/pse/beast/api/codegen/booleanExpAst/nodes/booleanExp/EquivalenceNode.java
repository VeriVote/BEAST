package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BinaryCombinationSymbols;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

/**
 * The Class EquivalenceNode.
 *
 * @author Lukas Stapelbroek
 */
public final class EquivalenceNode extends BinaryRelationshipNode {
	/**
	 * Instantiates a new equivalence node.
	 *
	 * @param lhsExpNode the lhs node
	 * @param rhsExpNode the rhs node
	 */
	public EquivalenceNode(final BooleanExpressionNode lhsExpNode,
			final BooleanExpressionNode rhsExpNode) {
		super(lhsExpNode, rhsExpNode);
	}


	@Override
	public String getTreeString(final int depth) {
		return null;
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitBinaryRelationNode(this, BinaryCombinationSymbols.EQUIV);
	}
}