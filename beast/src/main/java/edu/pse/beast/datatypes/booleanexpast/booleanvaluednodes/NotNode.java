package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 *
 * @author Lukas Stapelbroek
 *
 */
public class NotNode extends BooleanExpressionNode {
    private BooleanExpressionNode followingNode;

    /**
     * Creates a new NotNode.
     *
     * @param followingNode the node that follows this node (the node that gets
     *                      negated)
     */
    public NotNode(BooleanExpressionNode followingNode) {
        this.followingNode = followingNode;
    }

    /**
     *
     * @return the negated expression node
     */
    public BooleanExpressionNode getNegatedExpNode() {
        return followingNode;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitNotNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        String tabs = "\t\t\t\t\t\t\t\t\t\t\t\t".substring(0, depth + 1);
        return "NOT\n" + tabs + "following: " + followingNode.getTreeString(depth + 1);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((followingNode == null)
                        ? 0 : followingNode.hashCode());
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
        NotNode notNode = (NotNode) o;
        return followingNode != null
                ? followingNode.equals(notNode.followingNode)
                        : notNode.followingNode == null;
    }
}