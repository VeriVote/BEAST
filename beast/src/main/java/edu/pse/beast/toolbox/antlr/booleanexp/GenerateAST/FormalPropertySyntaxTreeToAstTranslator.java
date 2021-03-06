/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST;

import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpConstant;
import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.datatypes.booleanExpAST.ComparisonSymbol;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BinaryRelationshipNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.EquivalencyNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ForAllNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ImplicationNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.IntegerComparisonNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.LogicalAndNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.LogicalOrNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.NotNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.QuantorNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ThereExistsNode;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.AtPosExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.ElectExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.TypeExpression;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.VoteExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.BinaryIntegerValuedNode;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.ConstantExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.IntegerNode;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.IntegerValuedExpression;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.VoteSumForCandExp;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionBaseListener;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.BooleanExpListContext;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.OutputType;

/**
 *
 * @author Holger-Desktop
 */
public class FormalPropertySyntaxTreeToAstTranslator extends FormalPropertyDescriptionBaseListener {

    private BooleanExpListNode generated;
    private InputType inputType;
    private OutputType resType;
    private int maxElectExp = 0;
    private int maxVoteExp = 0;
    private int currentHighestElect = 0;
    private BooleanExpScopehandler scopeHandler;

    //Stacks
    private Stack<BooleanExpressionNode> nodeStack;
    private Stack<TypeExpression> expStack;

    public BooleanExpListNode   generateFromSyntaxTree(
            BooleanExpListContext parseTree,
            InputType inputType,
            OutputType resType,
            BooleanExpScope declaredVars) {
        scopeHandler = new BooleanExpScopehandler();
        scopeHandler.enterNewScope(declaredVars);
        this.inputType = inputType;
        this.resType = resType;
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(this, parseTree);
        return generated;
    }


    @Override
    public void enterBooleanExpList(FormalPropertyDescriptionParser.BooleanExpListContext ctx) {
        generated = new BooleanExpListNode();
        maxElectExp = 0;
        maxVoteExp = 0;
        expStack = new Stack<>();
        nodeStack = new Stack<>();
    }

    @Override
    public void exitBooleanExpList(FormalPropertyDescriptionParser.BooleanExpListContext ctx) {
        generated.setMaxVoteLevel(maxVoteExp);
    }

    @Override
    public void enterBooleanExpListElement(FormalPropertyDescriptionParser.BooleanExpListElementContext ctx) {
        currentHighestElect = 0;
    }

    @Override
    public void exitBooleanExpListElement(FormalPropertyDescriptionParser.BooleanExpListElementContext ctx) {
        generated.addNode(nodeStack.pop(), currentHighestElect);
        if (currentHighestElect > maxElectExp) {
            maxElectExp = currentHighestElect;
        }
    }

    @Override
    public void enterBooleanExp(FormalPropertyDescriptionParser.BooleanExpContext ctx) {

    }

    @Override
    public void exitBooleanExp(FormalPropertyDescriptionParser.BooleanExpContext ctx) {

    }

    @Override
    public void enterBinaryRelationExp(FormalPropertyDescriptionParser.BinaryRelationExpContext ctx) {
    }

    @Override
    public void exitBinaryRelationExp(FormalPropertyDescriptionParser.BinaryRelationExpContext ctx) {
        String symbol = ctx.BinaryRelationSymbol().getText();
        BooleanExpressionNode rhs = nodeStack.pop();
        BooleanExpressionNode lhs = nodeStack.pop();

        BinaryRelationshipNode node = null;

        if (symbol.equals("&&")) {
            node = new LogicalAndNode(lhs, rhs);
        } else if (symbol.equals("||")) {
            node = new LogicalOrNode(lhs, rhs);
        } else if (symbol.equals("==>")) {
            node = new ImplicationNode(lhs, rhs);
        } else if (symbol.equals("<==>")) {
            node = new EquivalencyNode(lhs, rhs);
        }
        nodeStack.add(node);
    }

    @Override
    public void enterQuantorExp(FormalPropertyDescriptionParser.QuantorExpContext ctx) {
        String quantorTypeString = ctx.Quantor().getText();
        InternalTypeContainer varType = null;
        if (quantorTypeString.contains("VOTER")) {
            varType = new InternalTypeContainer(InternalTypeRep.VOTER);
        } else if (quantorTypeString.contains("CANDIDATE")) {
            varType = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
        } else if (quantorTypeString.contains("SEAT")) {
            varType = new InternalTypeContainer(InternalTypeRep.SEAT);
        }
        scopeHandler.enterNewScope();
        String id = ctx.passSymbVar().symbolicVarExp().Identifier().getText();
        scopeHandler.addVariable(id, varType);
    }

    @Override
    public void exitQuantorExp(FormalPropertyDescriptionParser.QuantorExpContext ctx) {
        String quantorType = ctx.Quantor().getText();

        QuantorNode node = null;

        if (quantorType.contains("FOR_ALL")) {
            node = new ForAllNode(((SymbolicVarExp) expStack.pop()).getSymbolicVar(), nodeStack.pop());
        } else if (quantorType.contains("EXISTS_ONE")) {
            node = new ThereExistsNode(((SymbolicVarExp) expStack.pop()).getSymbolicVar(), nodeStack.pop());
        }

        nodeStack.add(node);
        scopeHandler.exitScope();
    }

    @Override
    public void enterNotExp(FormalPropertyDescriptionParser.NotExpContext ctx) {

    }

    @Override
    public void exitNotExp(FormalPropertyDescriptionParser.NotExpContext ctx) {
        NotNode node = new NotNode(nodeStack.pop());
        nodeStack.add(node);
    }

    @Override
    public void enterComparisonExp(FormalPropertyDescriptionParser.ComparisonExpContext ctx) {
    }

    @Override
    public void exitComparisonExp(FormalPropertyDescriptionParser.ComparisonExpContext ctx) {
        String comparisonSymbolString = ctx.ComparisonSymbol().getText();
        ComparisonSymbol comparisonSymbol = new ComparisonSymbol(comparisonSymbolString);
        TypeExpression rhs = expStack.pop();
        TypeExpression lhs = expStack.pop();
        if(lhs.getInternalTypeContainer().getInternalType() == InternalTypeRep.INTEGER) {
            IntegerComparisonNode node = new IntegerComparisonNode(lhs, rhs, comparisonSymbol);
            nodeStack.add(node);
        }
        else {
            ComparisonNode node = new ComparisonNode(lhs, rhs, comparisonSymbol);
            nodeStack.add(node);
        }
    }

    @Override
    public void enterTypeExp(FormalPropertyDescriptionParser.TypeExpContext ctx) {

    }

    @Override
    public void exitTypeExp(FormalPropertyDescriptionParser.TypeExpContext ctx) {

    }

    @Override
    public void enterNumberExpression(FormalPropertyDescriptionParser.NumberExpressionContext ctx) {

    }

    @Override
    public void exitNumberExpression(FormalPropertyDescriptionParser.NumberExpressionContext ctx) {
        if(ctx.Mult() != null) {
            IntegerValuedExpression rhs = (IntegerValuedExpression) expStack.pop();
            IntegerValuedExpression lsh = (IntegerValuedExpression) expStack.pop();
            BinaryIntegerValuedNode expNode = new BinaryIntegerValuedNode(lsh, rhs, ctx.Mult().getText());
            expStack.push(expNode);
        } else if(ctx.Add() != null) {
            IntegerValuedExpression rhs = (IntegerValuedExpression) expStack.pop();
            IntegerValuedExpression lsh = (IntegerValuedExpression) expStack.pop();
            BinaryIntegerValuedNode expNode = new BinaryIntegerValuedNode(lsh, rhs, ctx.Add().getText());
            expStack.push(expNode);
        }
    }

    @Override
    public void enterElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
    }

    @Override
    public void exitElectExp(FormalPropertyDescriptionParser.ElectExpContext ctx) {
        //get number
        String numberString = ctx.Elect().getText().substring("ELECT".length());
        int number = Integer.valueOf(numberString);
        if (currentHighestElect < number) {
            currentHighestElect= number;
        }
        int amtAcessingTypes = ctx.passType().size();
        TypeExpression[] accessingVars = new TypeExpression[amtAcessingTypes];

        for (int i = 0; i < amtAcessingTypes; ++i) {
            accessingVars[amtAcessingTypes - i - 1] = expStack.pop();
        }

        ElectExp expNode = new ElectExp(resType, accessingVars, number);
        expStack.push(expNode);
    }

    @Override
    public void enterVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx) {

    }

    @Override
    public void exitVoteExp(FormalPropertyDescriptionParser.VoteExpContext ctx) {
        String numberString = ctx.Vote().getText().substring("VOTES".length());
        int number = Integer.valueOf(numberString);
        if (maxVoteExp < number) {
            maxVoteExp = number;
        }
        int amtAcessingTypes = ctx.passType().size();
        TypeExpression[] accessingVars = new TypeExpression[amtAcessingTypes];

        for (int i = 0; i < amtAcessingTypes; ++i) {
            accessingVars[amtAcessingTypes - i - 1] = expStack.pop();
        }

        VoteExp expNode = new VoteExp(inputType, accessingVars, number);
        expStack.push(expNode);
    }

    @Override
    public void enterConstantExp(FormalPropertyDescriptionParser.ConstantExpContext ctx) {

    }

    @Override
    public void exitConstantExp(FormalPropertyDescriptionParser.ConstantExpContext ctx) {
        String constString = ctx.getText();
        ConstantExp expNode = null;
        if (constString.equals(BooleanExpConstant.getConstForVoterAmt())) {
            expNode = new ConstantExp(constString);
        } else if (constString.equals(BooleanExpConstant.getConstForCandidateAmt())) {
            expNode = new ConstantExp(constString);
        } else if (constString.equals(BooleanExpConstant.getConstForSeatAmt())) {
            expNode = new ConstantExp(constString);
        }
        expStack.push(expNode);
    }

    @Override
    public void enterVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx) {

    }

    private void exitVoteSum(final String exprStr, final TerminalNode tn, final boolean unique) {
        String numberString = tn.getText().substring(exprStr.length());
        int number = Integer.valueOf(numberString);
        if (number > maxVoteExp) {
            maxVoteExp = number;
        }
        VoteSumForCandExp expNode = new VoteSumForCandExp(number, expStack.pop(), unique);
        expStack.push(expNode);
    }

    @Override
    public void exitVoteSumExp(FormalPropertyDescriptionParser.VoteSumExpContext ctx) {
        final String exprStr = "VOTE_SUM_FOR_CANDIDATE";
        final TerminalNode tn = ctx.Votesum();
        exitVoteSum(exprStr, tn, false);
    }

    @Override
    public void enterVoteSumUniqueExp(FormalPropertyDescriptionParser.VoteSumUniqueExpContext ctx) {

    }

    @Override
    public void exitVoteSumUniqueExp(FormalPropertyDescriptionParser.VoteSumUniqueExpContext ctx) {
        final String exprStr = "VOTE_SUM_FOR_UNIQUE_CANDIDATE";
        final TerminalNode tn = ctx.VotesumUnique();
        exitVoteSum(exprStr, tn, true);
    }

    @Override
    public void enterPassSymbVar(FormalPropertyDescriptionParser.PassSymbVarContext ctx) {

    }

    @Override
    public void exitPassSymbVar(FormalPropertyDescriptionParser.PassSymbVarContext ctx) {

    }

    @Override
    public void enterSymbolicVarExp(FormalPropertyDescriptionParser.SymbolicVarExpContext ctx) {

    }

    @Override
    public void exitSymbolicVarExp(FormalPropertyDescriptionParser.SymbolicVarExpContext ctx) {
        String name = ctx.getText();
        InternalTypeContainer type = scopeHandler.getTypeForVariable(name);
        SymbolicVarExp expNode = new SymbolicVarExp(type, new SymbolicVariable(name, type));
        expStack.push(expNode);
    }

    @Override
    public void exitInteger(FormalPropertyDescriptionParser.IntegerContext ctx) {
        String integerString = ctx.getText();
        int heldInteger = Integer.valueOf(integerString);
        IntegerNode integerNode = new IntegerNode(heldInteger);
        expStack.push(integerNode);
    }

    private void pushAtPosNode(InternalTypeRep rep) {
        expStack.push(
                new AtPosExp(new InternalTypeContainer(rep) ,
                        (IntegerValuedExpression) expStack.pop()));
    }

    @Override
    public void exitVoterByPosExp(FormalPropertyDescriptionParser.VoterByPosExpContext ctx) {
        pushAtPosNode(InternalTypeRep.VOTER);
    }

    @Override
    public void exitCandByPosExp(FormalPropertyDescriptionParser.CandByPosExpContext ctx) {
        pushAtPosNode(InternalTypeRep.CANDIDATE);
    }

    @Override
    public void exitSeatByPosExp(FormalPropertyDescriptionParser.SeatByPosExpContext ctx) {
        pushAtPosNode(InternalTypeRep.SEAT);
    }

    @Override
    public void visitTerminal(TerminalNode tn) {

    }

    @Override
    public void visitErrorNode(ErrorNode en) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext prc) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext prc) {

    }

}
