package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

public class VoteIntersectionNode extends ElectionTypeNode {
    private List<Integer> numbers = new ArrayList<>();

    @Override
    public String getTreeString(final int depth) {
        return "Intersect Votes";
    }

    public void addVoteNumber(final int number) {
        numbers.add(number);
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitVoteIntersectionNode(this);
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}
