package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.symbolic_var;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;

public class SymbVarByPosExp extends SymbolicCBMCVarExp {

    private CBMCVarType variableType;
    private int accessingNumber;

    public SymbVarByPosExp(final CBMCVarType varType,
                           final int accNumber) {
        this.variableType = varType;
        this.accessingNumber = accNumber;
    }

    public CBMCVarType getVarType() {
        return variableType;
    }

    public int getAccessingNumber() {
        return accessingNumber;
    }

    @Override
    public void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitSymbVarByPosExp(this);
    }

    @Override
    public String getTreeString(final int depth) {
        // TODO Auto-generated method stub
        return null;
    }
}