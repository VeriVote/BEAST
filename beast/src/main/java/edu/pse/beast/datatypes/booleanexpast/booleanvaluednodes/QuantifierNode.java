package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

/**
 *
 * @author Holger
 *
 */
public abstract class QuantifierNode extends BooleanExpressionNode {
    private final SymbolicVariable declSymbVar;
    private final BooleanExpressionNode followingNode;

    /**
     *
     * @param declSymbVariable  the symbolic variable of this quantifier
     * @param followingExprNode the following node of this quantifier
     */
    public QuantifierNode(final SymbolicVariable declSymbVariable,
                          final BooleanExpressionNode followingExprNode) {
        this.declSymbVar = declSymbVariable;
        this.followingNode = followingExprNode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((declSymbVar == null)
                        ? 0 : declSymbVar.hashCode());
        result = prime * result
                + ((followingNode == null)
                        ? 0 : followingNode.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuantifierNode that = (QuantifierNode) o;
        if (declSymbVar != null
                ? !declSymbVar.equals(that.declSymbVar)
                        : that.declSymbVar != null) {
            return false;
        }
        return followingNode != null
                ? followingNode.equals(that.followingNode)
                        : that.followingNode == null;
    }

    /**
     *
     * @return the symbolic variable of this expression
     */
    public SymbolicVariable getDeclaredSymbolicVar() {
        return declSymbVar;
    }

    /**
     *
     * @return the following node
     */
    public BooleanExpressionNode getFollowingExpNode() {
        return followingNode;
    }
}
