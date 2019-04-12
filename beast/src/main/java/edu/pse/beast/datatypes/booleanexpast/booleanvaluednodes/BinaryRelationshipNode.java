package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

/**
 *
 * @author Lukas Stapelbroek
 *
 */
public abstract class BinaryRelationshipNode extends BooleanExpressionNode {
    private final BooleanExpressionNode lhsExpNode;
    private final BooleanExpressionNode rhsExpNode;

    /**
     *
     * @param lhsExpNode the lhs node
     * @param rhsExpNode the rhs node
     */
    public BinaryRelationshipNode(BooleanExpressionNode lhsExpNode,
                                  BooleanExpressionNode rhsExpNode) {
        this.lhsExpNode = lhsExpNode;
        this.rhsExpNode = rhsExpNode;
    }

    /**
     *
     * @return the LHSNode
     */
    public BooleanExpressionNode getLHSBooleanExpNode() {
        return lhsExpNode;
    }

    /**
     *
     * @return the RHSNode
     */
    public BooleanExpressionNode getRHSBooleanExpNode() {
        return rhsExpNode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((lhsExpNode == null)
                        ? 0 : lhsExpNode.hashCode());
        result = prime * result
                + ((rhsExpNode == null)
                        ? 0 : rhsExpNode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BinaryRelationshipNode that = (BinaryRelationshipNode) o;
        if (lhsExpNode != null
                ? !lhsExpNode.equals(that.lhsExpNode)
                        : that.lhsExpNode != null) {
            return false;
        }
        return rhsExpNode != null
                ? rhsExpNode.equals(that.rhsExpNode)
                        : that.rhsExpNode == null;
    }
}