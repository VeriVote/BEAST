package edu.pse.beast.datatypes.booleanexpast.othervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.IntegerValuedExpression;
import edu.pse.beast.types.InternalTypeContainer;

/**
 * @author Holger Klein
 */
public class AtPosExp extends TypeExpression {
    private final IntegerValuedExpression integerValuedExpression;

    public AtPosExp(InternalTypeContainer container,
                    IntegerValuedExpression integerValuedExpression) {
        super(container);
        this.integerValuedExpression = integerValuedExpression;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitAtPosNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        return "atpos\n" + "\t\t\t\t\t\t\t\t\t\t".substring(0, depth + 1) + "int "
                + integerValuedExpression.getTreeString(depth + 1);
    }

    public IntegerValuedExpression getIntegerValuedExpression() {
        return integerValuedExpression;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((integerValuedExpression == null)
                        ? 0 : integerValuedExpression.hashCode());
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
        AtPosExp atPosExp = (AtPosExp) o;
        return integerValuedExpression != null
                ? integerValuedExpression.equals(atPosExp.integerValuedExpression)
                : atPosExp.integerValuedExpression == null;
    }
}