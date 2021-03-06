package edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;


/**
 * Created by holger on 13.03.17.
 */
public class AstTesterHelper {

    public static void areTheSame(BooleanExpListNode lhs, BooleanExpListNode rhs) {
        assertEquals(lhs.getHighestElect(), rhs.getHighestElect());
        assertEquals(lhs.getMaxVoteLevel(), rhs.getMaxVoteLevel());
        for (int i = 0; i <lhs.getBooleanExpressions().size(); i++) {
            List<BooleanExpressionNode> lhsList = lhs.getBooleanExpressions().get(i);
            List<BooleanExpressionNode> rhsList = rhs.getBooleanExpressions().get(i);
            areTheSame(lhsList, rhsList);
        }
    }

    private static void areTheSame(List<BooleanExpressionNode> lhsList, List<BooleanExpressionNode> rhsList) {
        assertEquals(lhsList.size(), rhsList.size());
        for (int i = 0; i <lhsList.size(); i++) {
            areTheSame(lhsList.get(i), rhsList.get(i));
        }
    }

    private static void areTheSame(BooleanExpressionNode lhsNode, BooleanExpressionNode rhsNode) {
        if(!lhsNode.equals(rhsNode)) {
            System.out.println("expected: " + lhsNode.toString() + " actual: " + rhsNode.toString());
        }
        assertTrue(lhsNode.equals(rhsNode));
    }
}

