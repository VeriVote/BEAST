package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.EquivalenceNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ForAllNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ImplicationNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.IntegerComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.IntersectTypeExpNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.LogicalAndNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.LogicalOrNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.NotNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.QuantifierNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ThereExistsNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.AccessValueNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.AtPosExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.ElectExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.VoteExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.BinaryIntegerValuedNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.ConstantExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.IntegerNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.VoteSumForCandExp;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ConcatenationExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectContentContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PermutationExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteEquivalentsContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VotingListChangeContentContext;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.CandidateListChangeExpNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VoteEquivalentsNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VotingListChangeExpNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VotingTupleChangeExpNode;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.cbmctypes.outputplugins.CandidateList;

/**
 * This is the visitor for the codeGeneration For every assert or assume it
 * should be called to visit the top node 1 time it will move down and generate
 * the code. to get the generated code, you have to use the method generateCode
 * You also have to set it either to Pre- or PostConditionMode.
 *
 * @author Niels Hanselmann
 */
public final class CBMCCodeGenerationVisitor implements BooleanExpNodeVisitor {
    /** The Constant NUM_TEMPLATE. */
    private static final String NUM_TEMPLATE = "NUM";
    /** The Constant NUMBER_TEMPLATE. */
    private static final String NUMBER_TEMPLATE = "NUMBER";
    /** The Constant VARNAME_TEMPLATE. */
    private static final String VARNAME_TEMPLATE = "VARNAME";

    /** The Constant EXISTS_TEMPLATE. */
    private static final String EXISTS_TEMPLATE = "THEREEXISTS";
    /** The Constant BOOL_TEMPLATE. */
    private static final String BOOL_TEMPLATE = "BOOL";
    /** The Constant VAR_TEMPLATE. */
    private static final String VAR_TEMPLATE = "VAR";
    /** The Constant FORALL_TEMPLATE. */
    private static final String FORALL_TEMPLATE = "FORALL";
    /** The Constant MAX_TEMPLATE. */
    private static final String MAX_TEMPLATE = "MAX";
    /** The Constant SYMBVAR_TEMPLATE. */
    private static final String SYMBVAR_TEMPLATE = "SYMBVAR";

    /** The Constant VOTES_TEMPLATE. */
    private static final String VOTES_TEMPLATE = "VOTES";
    /** The Constant CAND_TEMPLATE. */
    private static final String CAND_TEMPLATE = "CAND";
    /** The Constant "i". */
    private static final String I = "i";
    /** The Constant "j". */
    private static final String J = "j";

    /** The Constant C. */
    private static final String C = "C";
    /** The Constant V. */
    private static final String V = "V";

    /** The Constant VOTE. */
    private static final String VOTE = "vote";
    /** The Constant VOTES. */
    private static final String VOTES = "votes";
    /** The Constant ELECT. */
    private static final String ELECT = "elect";
    /** The Constant SIZE. */
    private static final String SIZE = "size";
    /** The Constant SPLIT. */
    private static final String SPLIT = "split";

    /** The Constant BLANK. */
    private static final String BLANK = " ";
    /** The Constant COMMA. */
    private static final String COMMA = ",";
    /** The Constant SEMICOLON. */
    private static final String SEMICOLON = ";";
    /** The Constant AMPERSAND. */
    private static final String AMPERSAND = "&";
    /** The Constant EQUALS_SIGN. */
    private static final String EQUALS_SIGN = "=";
    /** The Constant LT_SIGN. */
    private static final String LT_SIGN = "<";
    /** The Constant PIPE. */
    private static final String PIPE = "|";
    /** The Constant MINUS. */
    private static final String MINUS = "-";
    /** The Constant NOT. */
    private static final String NOT = "!";
    /** The Constant PLUS_PLUS. */
    private static final String PLUS_PLUS = "++";

    /** The Constant OPENING_PARENTHESES. */
    private static final String OPENING_PARENTHESES = "(";
    /** The Constant CLOSING_PARENTHESES. */
    private static final String CLOSING_PARENTHESES = ")";
    /** The Constant OPENING_BRACKETS. */
    private static final String OPENING_BRACKETS = "[";
    /** The Constant CLOSING_BRACKETS. */
    private static final String CLOSING_BRACKETS = "]";
    /** The Constant OPENING_BRACES. */
    private static final String OPENING_BRACES = "{";
    /** The Constant CLOSING_BRACES. */
    private static final String CLOSING_BRACES = "}";

    /** The Constant UNDERSCORE. */
    private static final String UNDERSCORE = "_";
    /** The Constant DOT_ARR. */
    private static final String DOT_ARR = ".arr";

    /** The Constant ZERO. */
    private static final String ZERO = "0";
    /** The Constant ONE. */
    private static final String ONE = "1";

    /**
     * This String must always be "assume" or "assert". If it is not set, it is
     * null.
     */
    private String assumeOrAssert;

    /*
     * The following attributes are the counters for the variables in the code.
     * They make the variable names unique.
     */

    /** The and node counter. */
    private int andNodeCounter;

    /** The or node counter. */
    private int orNodeCounter;

    /** The implication node counter. */
    private int implicationNodeCounter;

    /** The equivalence node counter. */
    private int equivalenceNodeCounter;

    /** The for all node counter. */
    private int forAllNodeCounter;

    /** The there exists node counter. */
    private int thereExistsNodeCounter;

    /** The not node counter. */
    private int notNodeCounter;

    /** The comparison node counter. */
    private int comparisonNodeCounter;

    /** The vote sum counter. */
    private int voteSumCounter;

    /** The listlvl. */
    private int listlvl;

    /** The amt by pos var. */
    private int amtByPosVar;

    /** The number vars. */
    private int numberVars;

    /**
     * Stack of the variable names.
     */
    private Stack<String> variableNames;

    /**
     * Object that handles the generated code.
     */
    private CodeArrayListBeautifier code;

    /** The tmp index. */
    private int tmpIndex;

    /** The election type container. */
    private final ElectionTypeContainer electionTypeContainer;

    /** The post. */
    private boolean post;

    /**
     * This creates the visitor. You should create one and only one visitor for
     * every c-file you want to make. You must set it to pre- or post-property
     * mode in order for it to function.
     *
     * @param elecTypeContainer
     *            the election type container.
     *
     */
    public CBMCCodeGenerationVisitor(final ElectionTypeContainer elecTypeContainer) {
        this.electionTypeContainer = elecTypeContainer;
        andNodeCounter = 0;
        orNodeCounter = 0;
        implicationNodeCounter = 0;
        equivalenceNodeCounter = 0;
        forAllNodeCounter = 0;
        thereExistsNodeCounter = 0;
        notNodeCounter = 0;
        comparisonNodeCounter = 0;
        voteSumCounter = 0;
        numberVars = 0;
        listlvl = 0;
        amtByPosVar = 0;
        code = new CodeArrayListBeautifier();
    }

    /**
     * Determines whether the string is neither null nor empty.
     *
     * @param string
     *            the string
     * @return true, if the string is not null and not empty
     */
    private static boolean notNullOrEmpty(final String string) {
        return string != null && !"".equals(string);
    }

    /**
     * Determines whether there is at least one string in the list
     * and this string is neither null nor empty.
     *
     * @param strings
     *            the strings in a list
     * @return true, if the string is not null and not empty
     */
    private static boolean notNullOrEmpty(final List<String> strings) {
        return strings != null && !strings.isEmpty()
                && strings.get(0) != null && !"".equals(strings.get(0));
    }

    /**
     * Unsigned int variable.
     *
     * @param varName
     *            the var name
     * @return the string
     */
    private static String unsignedIntVar(final String varName) {
        return CCodeHelper.UNSIGNED + BLANK
                + CCodeHelper.INT + BLANK + varName;
    }

    /**
     * Unsigned int variable equals code.
     *
     * @param varName
     *            the var name
     * @return the string
     */
    private static String varEqualsCode(final String varName) {
        return unsignedIntVar(varName) + BLANK + EQUALS_SIGN + BLANK;
    }

    /**
     * Variable assignment code.
     *
     * @param varName
     *            the var name
     * @param rhsVar
     *            the rhs var
     * @return the string
     */
    private static String varAssignCode(final String varName,
                                        final String rhsVar) {
        return varName + BLANK + EQUALS_SIGN + BLANK + rhsVar;
    }

    /**
     * Variable subtract code.
     *
     * @param varPos
     *            the var pos
     * @param varNeg
     *            the var neg
     * @return the string
     */
    private static String varSubtractCode(final String varPos,
                                          final String varNeg) {
        return varPos + BLANK + MINUS + BLANK + varNeg;
    }

    /**
     * Generate function code.
     *
     * @param function
     *            the function
     * @param params
     *            the parameters
     * @return the string
     */
    private static String functionCode(final String function,
                                       final List<String> params) {
        String functionCode = "";
        if (notNullOrEmpty(function) && notNullOrEmpty(params)) {
            for (final String param : params) {
                if (notNullOrEmpty(param)
                        && !"".equals(functionCode)) {
                    functionCode += COMMA + BLANK;
                }
                functionCode += notNullOrEmpty(param) ? param : "";
            }
            functionCode = function + parenthesize(functionCode);
        }
        return functionCode;
    }

    /**
     * Generate function code.
     *
     * @param function
     *            the function
     * @param params
     *            the parameters
     * @return the string
     */
    private static String functionCode(final String function,
                                       final String... params) {
        List<String> paramList = new ArrayList<String>();
        for (final String param : params) {
            if (notNullOrEmpty(param)) {
                paramList.add(param);
            }
        }
        return functionCode(function, paramList);
    }

    /**
     * Generate code for split function.
     *
     * @param param1
     *            the first parameter
     * @param param2
     *            the second parameter
     * @param param3
     *            the third parameter
     * @return the string
     */
    private static String splitFunctionCode(final String param1,
                                            final String param2,
                                            final String param3) {
        return functionCode(SPLIT, param1, param2, param3);
    }

    /**
     * Dis- or conjunct if not null.
     *
     * @param toBeAppended
     *            the term to be appended
     * @param originalTerm
     *            the term which should be complemented
     * @param junctor
     *            the junctor symbol(s)
     * @return the string
     */
    private static String disOrConjunct(final String toBeAppended,
                                        final String originalTerm,
                                        final String junctor) {
        final String junction = BLANK + junctor + BLANK;
        final String disOrConjuncted = notNullOrEmpty(toBeAppended)
                                ? toBeAppended
                                        + (notNullOrEmpty(originalTerm)
                                                ? junction : "")
                                        : "";
        return disOrConjuncted + originalTerm;
    }

    /**
     * Conjunct if not null.
     *
     * @param toBeConjuncted
     *            the term to be conjuncted
     * @param originalTerm
     *            the term which should be complemented
     * @return the string
     */
    private static String conjunct(final String toBeConjuncted,
                                   final String originalTerm) {
        final String conjunctor = AMPERSAND + AMPERSAND;
        return disOrConjunct(toBeConjuncted, originalTerm, conjunctor);
    }

    /**
     * Disjunct if not null.
     *
     * @param toBeDisjuncted
     *            the term to be disjuncted
     * @param originalTerm
     *            the term which should be complemented
     * @return the string
     */
    private static String disjunct(final String toBeDisjuncted,
                                   final String originalTerm) {
        final String disjunctor = PIPE + PIPE;
        return disOrConjunct(toBeDisjuncted, originalTerm, disjunctor);
    }

    /**
     * Generate the loop header code of a for-loop given the name string of
     * the count variable, the string of the comparison symbol, and the name
     * string of the bound constant.
     *
     * @param countVar
     *            the name string of the counting variable
     * @param comparison
     *            the string of the comparison symbol
     * @param boundConst
     *            the name string of the loop bound constant
     * @param guardReinforce
     *            the guard reinforce
     * @return the code string of the for-loop header
     */
    private static String forLoopHeaderCode(final String countVar,
                                            final String comparison,
                                            final String boundConst,
                                            final String guardReinforce) {
        final String varEqualsZero =
                varEqualsCode(countVar) + ZERO + SEMICOLON;
        final String boundGuard =
                countVar + BLANK + comparison + BLANK + boundConst;
        final String loopGuard = conjunct(guardReinforce, boundGuard)
                                + SEMICOLON;
        final String incrCounter = countVar + PLUS_PLUS;
        return CCodeHelper.FOR + BLANK
                + parenthesize(varEqualsZero + BLANK
                                + loopGuard + BLANK
                                + incrCounter)
                + BLANK + OPENING_BRACES;
    }

    /**
     * Generate the loop header code of a for-loop given the name string of
     * the count variable, the string of the comparison symbol, and the name
     * string of the bound constant.
     *
     * @param countVar
     *            the name string of the counting variable
     * @param comparison
     *            the string of the comparison symbol
     * @param boundConst
     *            the name string of the loop bound constant
     * @return the code string of the for-loop header
     */
    private static String forLoopHeaderCode(final String countVar,
                                            final String comparison,
                                            final String boundConst) {
        return forLoopHeaderCode(countVar, comparison, boundConst, null);
    }

    /**
     * Parenthesize the expression.
     *
     * @param expression
     *            the expression
     * @return the string
     */
    private static String parenthesize(final String expression) {
        return notNullOrEmpty(expression)
                ? (OPENING_PARENTHESES + expression + CLOSING_PARENTHESES)
                        : expression;
    }

    /**
     * Array access.
     *
     * @param dim
     *            the dim
     * @return the string
     */
    private static String arrAcc(final String dim) {
        return OPENING_BRACKETS + dim + CLOSING_BRACKETS;
    }

    /**
     * Array access.
     *
     * @param dim
     *            the dim
     * @return the string
     */
    private static String arrAcc(final int dim) {
        return arrAcc(Integer.toString(dim));
    }

    /**
     * Dot arr struct access for all given dimensions.
     *
     * @param owner
     *            the owner
     * @param dims
     *            the dims
     * @return the string
     */
    private static String dotArrStructAccess(final String owner,
                                             final List<String> dims) {
        String arrAccess = notNullOrEmpty(owner) ? owner : "";
        if (notNullOrEmpty(owner) && notNullOrEmpty(dims)) {
            arrAccess += DOT_ARR;
            for (final String dim : dims) {
                arrAccess += notNullOrEmpty(dim) ? arrAcc(dim) : "";
            }
        }
        return arrAccess;
    }

    /**
     * Dot arr struct access for all given dimensions.
     *
     * @param owner
     *            the owner
     * @param dims
     *            the dims
     * @return the string
     */
    private static String dotArrStructAccess(final String owner,
                                             final String... dims) {
        List<String> dimList = new ArrayList<String>();
        for (final String dim : dims) {
            if (notNullOrEmpty(dim)) {
                dimList.add(dim);
            }
        }
        return dotArrStructAccess(owner, dimList);
    }

    /**
     * Gets the max string.
     *
     * @param node
     *            the node
     * @return the max string
     */
    private static String getMaxString(final QuantifierNode node) {
        final String max;
        final InternalTypeRep typeRep =
                node.getDeclaredSymbolicVar()
                .getInternalTypeContainer()
                .getInternalType();
        switch (typeRep) {
        case VOTER:
            max = UnifiedNameContainer.getVoter();
            break;
        case CANDIDATE:
            max = UnifiedNameContainer.getCandidate();
            break;
        case SEAT:
            max = UnifiedNameContainer.getSeats();
            break;
        default:
            throw new AssertionError(typeRep.name());
        }
        return max;
    }

    /**
     * Gets the tmp index.
     *
     * @return the tmp index
     */
    private synchronized int getTmpIndex() {
        int tmp = tmpIndex;
        tmpIndex++;
        return tmp;
    }

    /**
     * Create new temp variable string.
     *
     * @param name
     *            the name
     * @return the string
     */
    private String newTempVarString(final String name) {
        final String newVarString =
                "tmp" + (notNullOrEmpty(name)
                        ? UNDERSCORE + name
                                : "");
        return newVarString + getTmpIndex();
    }

    /**
     * Create new temp variable string.
     *
     * @return the string
     */
    private String newTempVarString() {
        return newTempVarString(null);
    }

    /**
     * Sets the visitor to preConditionMode. That means the top node will be
     * assumed in the code.
     */
    public void setToPreConditionMode() {
        assumeOrAssert = "assume";
        this.post = true; // refactor this later
    }

    /**
     * Sets the visitor to postConditionMode. That means the top node will be
     * asserted in the code.
     */
    public void setToPostConditionMode() {
        assumeOrAssert = "assert";
        this.post = true;
    }

    /**
     * This generates the c-code for a property description-statement.
     *
     * @param node
     *            this should be the top node of a statement
     * @return the generated code
     */
    public ArrayList<String> generateCode(final BooleanExpressionNode node) {
        code = new CodeArrayListBeautifier();
        variableNames = new Stack<String>();
        node.getVisited(this);
        return code.getCodeArrayList();
    }

    @Override
    public void visitAndNode(final LogicalAndNode node) {
        String varName = "and_" + andNodeCounter;
        andNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        code.add(varEqualsCode(varName)
                + parenthesize(
                        conjunct(
                                parenthesize(variableNames.pop()),
                                parenthesize(variableNames.pop())
                                )
                        )
                + SEMICOLON);
        testIfLast();
    }

    @Override
    public void visitOrNode(final LogicalOrNode node) {
        String varName = "or_" + orNodeCounter;
        orNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        code.add(varEqualsCode(varName)
                + parenthesize(
                        disjunct(parenthesize(variableNames.pop()),
                                parenthesize(variableNames.pop()))
                        )
                + SEMICOLON);
        testIfLast();
    }

    @Override
    public void visitImplicationNode(final ImplicationNode node) {
        String varName = "implication_" + implicationNodeCounter;
        implicationNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        String rhsName = variableNames.pop();
        String lhsName = variableNames.pop();
        code.add(varEqualsCode(varName)
                + parenthesize(
                        disjunct(NOT + parenthesize(lhsName),
                                parenthesize(rhsName))
                        )
                + SEMICOLON);
        testIfLast();
    }

    @Override
    public void visitEquivalenceNode(final EquivalenceNode node) {
        String varName = "equivalence_" + equivalenceNodeCounter;
        equivalenceNodeCounter++;
        variableNames.push(varName);
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        String rhs = variableNames.pop();
        String lhs = variableNames.pop();
        code.add(varEqualsCode(varName)
                + parenthesize(
                        disjunct(
                                parenthesize(
                                        conjunct(parenthesize(lhs),
                                                parenthesize(rhs))
                                        ),
                                parenthesize(
                                        conjunct(NOT + parenthesize(lhs),
                                                parenthesize(NOT + rhs))
                                        )
                                )
                        )
                + SEMICOLON);
        testIfLast();
    }

    @Override
    public void visitForAllNode(final ForAllNode node) {
        String varName = "forAll_" + forAllNodeCounter;
        forAllNodeCounter++;
        variableNames.push(varName);
        code.add(varEqualsCode(varName) + ONE + SEMICOLON);
        final String max = getMaxString(node);
        String tempString =
                forLoopHeaderCode(SYMBVAR_TEMPLATE, LT_SIGN, MAX_TEMPLATE, FORALL_TEMPLATE);
        tempString = tempString.replaceAll(SYMBVAR_TEMPLATE,
                                           node.getDeclaredSymbolicVar().getId());
        tempString = tempString.replace(MAX_TEMPLATE, max);
        tempString = tempString.replace(FORALL_TEMPLATE, varName);
        code.add(tempString);
        code.addTab();
        node.getFollowingExpNode().getVisited(this);
        code.add(varAssignCode(varName, variableNames.pop()) + SEMICOLON);
        code.deleteTab();
        code.add(CLOSING_BRACES);
        testIfLast();
    }

    /**
     * Compute counter.
     *
     * @param varName
     *            the var name
     * @param maxListLevel
     *            the max list level
     * @param cont
     *            the cont
     * @return the array list
     */
    private ArrayList<String> computeCounter(final String varName,
                                             final int maxListLevel,
                                             final InternalTypeContainer cont) {
        InternalTypeContainer container = cont;
        ArrayList<String> counter = new ArrayList<String>();
        // Generate the for loops used to test the two types.
        for (int i = 0; i < maxListLevel; ++i) {
            String max = "";
            String countingVar = "count_" + i;
            counter.add(countingVar);
            if (null != container.getAccessTypeIfList()) {
                switch (container.getAccessTypeIfList()) {
                case VOTER:
                    max = UnifiedNameContainer.getVoter();
                    break;
                case CANDIDATE:
                    max = UnifiedNameContainer.getCandidate();
                    break;
                case SEAT:
                    max = UnifiedNameContainer.getSeats();
                    break;
                default:
                    break;
                }
            }
            // TODO This is just a hotfix because it used V when it should have
            //      used C for candidate lists.
            // TODO Maybe extract this to the voting types.
            if (electionTypeContainer
                    .getOutputType() instanceof CandidateList) {
                max = UnifiedNameContainer.getCandidate();
            }

            String loop = forLoopHeaderCode(VAR_TEMPLATE, LT_SIGN,
                                            MAX_TEMPLATE, BOOL_TEMPLATE);
            loop = loop.replaceAll(VAR_TEMPLATE, countingVar);
            loop = loop.replaceAll(MAX_TEMPLATE, max);
            loop = loop.replaceAll(BOOL_TEMPLATE, varName);
            code.add(loop);
            container = container.getListedType();
        }
        return counter;
    }

    @Override
    public void visitThereExistsNode(final ThereExistsNode node) {
        String varName = "thereExists_" + thereExistsNodeCounter;
        thereExistsNodeCounter++;
        variableNames.push(varName);
        code.add(varEqualsCode(varName) + ZERO + SEMICOLON);
        String max = getMaxString(node);

        String tempString = forLoopHeaderCode(SYMBVAR_TEMPLATE, LT_SIGN,
                                              MAX_TEMPLATE, NOT + EXISTS_TEMPLATE);
        tempString = tempString.replaceAll(SYMBVAR_TEMPLATE,
                node.getDeclaredSymbolicVar().getId());
        tempString = tempString.replaceAll(MAX_TEMPLATE, max);
        tempString = tempString.replaceAll(EXISTS_TEMPLATE, varName);
        code.add(tempString);
        code.addTab();
        node.getFollowingExpNode().getVisited(this);
        code.add(varAssignCode(varName, variableNames.pop()) + SEMICOLON);
        code.deleteTab();
        code.add(CLOSING_BRACES);
        testIfLast();
    }

    @Override
    public void visitNotNode(final NotNode node) {
        if (node.getNegatedExpNode() instanceof NotNode) {
            NotNode node2 = (NotNode) node.getNegatedExpNode();
            node2.getNegatedExpNode().getVisited(this);
            return;
        }
        String varName = "not_" + notNodeCounter;
        notNodeCounter++;
        variableNames.push(varName);
        node.getNegatedExpNode().getVisited(this);
        code.add(varEqualsCode(varName)
                + NOT + parenthesize(variableNames.pop())
                + SEMICOLON);
        testIfLast();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Generates the code for the comparison of 2 types which are not integers.
     * These types can be lists which may have different depth and might be
     * accessed by variables.
     */
    @Override
    public void visitComparisonNode(final ComparisonNode node) {
        String varName = "comparison_" + comparisonNodeCounter;
        comparisonNodeCounter++;
        variableNames.push(varName);
        // Let the visitor generate the code for the left value
        // which will be compared. This is also used to find out
        // how many list levels need to be compared, meaning how
        // many for loops need to be generated
        listlvl = 0;
        for (InternalTypeContainer cont = node.getLHSBooleanExpNode()
                .getInternalTypeContainer(); cont
                        .isList(); cont = cont.getListedType()) {
            listlvl++;
        }
        code.add("// Comparison left side:");
        node.getLHSBooleanExpNode().getVisited(this);
        final int lhslistLevel = listlvl;
        listlvl = 0;
        for (InternalTypeContainer cont =
                node.getRHSBooleanExpNode().getInternalTypeContainer();
                cont.isList();
                cont = cont.getListedType()) {
            listlvl++;
        }
        code.add("// Comparison right side:");
        node.getRHSBooleanExpNode().getVisited(this);
        int rhslistLevel = listlvl;
        int maxListLevel = lhslistLevel > rhslistLevel
                ? lhslistLevel : rhslistLevel;
        InternalTypeContainer cont = lhslistLevel > rhslistLevel
                ? node.getLHSBooleanExpNode().getInternalTypeContainer()
                : node.getRHSBooleanExpNode().getInternalTypeContainer();
        while (cont.getListLvl() != maxListLevel) {
            cont = cont.getListedType();
        }
        String internCode = varEqualsCode(BOOL_TEMPLATE) + ONE + SEMICOLON;
        internCode = internCode.replace(BOOL_TEMPLATE, varName);
        code.add(internCode);
        ArrayList<String> counter = computeCounter(varName, maxListLevel, cont);
        String lhs;
        String rhs;
        // if (node.getRHSBooleanExpNode() instanceof IntersectTypeExpNode) {
        //     IntersectTypeExpNode rightNode =
        //         (IntersectTypeExpNode)
        //             node.getRHSBooleanExpNode();
        //     IntersectExpContext context = rightNode.context;
        //
        //     String voteInputOne = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + voteInputOne + ";"); // Create the var
        //     String voteInputTwo = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + voteInputTwo + ";"); // Create the var
        //     rhs = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + rhs + ";"); // Create the var
        //     intersectHelpMethod(context.intersectContent(0), voteInputOne,
        //                         context.intersectContent(1), voteInputTwo,
        //                         rhs);
        //     rhs = rhs + ".arr";
        // } else {
        //     rhs = variableNames.pop();
        // }
        //
        // if (node.getLHSBooleanExpNode() instanceof IntersectTypeExpNode) {
        //     IntersectTypeExpNode leftNode =
        //         (IntersectTypeExpNode)
        //             node.getLHSBooleanExpNode();
        //     IntersectExpContext context = leftNode.context;
        //
        //     String voteInputOne = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + voteInputOne + ";"); // Create the var
        //     String voteInputTwo = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + voteInputTwo + ";"); // Create the var
        //     lhs = "tmp_elect" + getTmpIndex();
        //     code.add(electionTypeContainer.getOutputType().getOutputString()
        //         + " " + lhs + ";"); // Create the var
        //     intersectHelpMethod(context.intersectContent(0), voteInputOne,
        //                         context.intersectContent(1), voteInputTwo,
        //                         lhs);
        //     lhs = lhs + ".arr";
        // } else {
        //     lhs = variableNames.pop();
        // }
        rhs = variableNames.pop();
        lhs = variableNames.pop();
        // setToPostConditionMode();
        internCode = varAssignCode(varName, lhs);
        // TODO Use unified name container, or defer to the Types
        final String prefix = post ? DOT_ARR : "";
        String preFixCopy = prefix;
        for (int i = 0; i < lhslistLevel; ++i) {
            internCode += preFixCopy
                    + arrAcc(VAR_TEMPLATE).replace(VAR_TEMPLATE, counter.get(i));
            // TODO Rewrite this with using input and output types
            preFixCopy = "";
        }
        internCode += BLANK + node.getComparisonSymbol().getCStringRep() + BLANK;
        internCode += rhs;

        preFixCopy = prefix;
        for (int i = 0; i < rhslistLevel; ++i) {
            internCode += preFixCopy
                    + arrAcc(VAR_TEMPLATE).replace(VAR_TEMPLATE, counter.get(i));
            // TODO Rewrite this with using input and output types
            preFixCopy = "";
        }
        code.add(internCode + SEMICOLON);
        for (int i = 0; i < maxListLevel; ++i) {
            code.add(CLOSING_BRACES);
        }
        testIfLast();
    }

    @Override
    public void visitSymbVarExp(final SymbolicVarExp exp) {
        variableNames.push(exp.getSymbolicVar().getId());
    }

    @Override
    public void visitConstExp(final ConstantExp exp) {
        variableNames.push(exp.getConstant());
    }

    @Override
    public void visitElectExp(final ElectExp exp) {
        visitAccessingNodesReverseOrder(exp);
        String tempCode = (ELECT + NUMBER_TEMPLATE).replace(NUMBER_TEMPLATE,
                String.valueOf(exp.getCount()));
        for (int i = 0; i < exp.getAccessingVars().length; ++i) {
            tempCode += arrAcc(VAR_TEMPLATE).replace(VAR_TEMPLATE, variableNames.pop());
            listlvl--;
        }
        variableNames.push(tempCode);
    }

    @Override
    public void visitVoteExp(final VoteExp exp) {
        visitAccessingNodesReverseOrder(exp);
        String tempCode = (VOTES + NUMBER_TEMPLATE).replace(NUMBER_TEMPLATE,
                String.valueOf(exp.getCount()));
        for (int i = 0; i < exp.getAccessingVars().length; ++i) {
            tempCode += arrAcc(VAR_TEMPLATE).replace(VAR_TEMPLATE, variableNames.pop());
            listlvl--;
        }
        variableNames.push(tempCode);
    }

    /**
     * Visit accessing nodes reverse order.
     *
     * @param exp
     *            the exp
     */
    private void visitAccessingNodesReverseOrder(final AccessValueNode exp) {
        for (int i = exp.getAccessingVars().length - 1; i >= 0; i--) {
            exp.getAccessingVars()[i].getVisited(this);
        }
    }

    @Override
    public void visitVoteSumExp(final VoteSumForCandExp exp,
                                final boolean unique) {
        exp.getAccessingVariable().getVisited(this);
        String funcCallTemplate =
                varEqualsCode(VARNAME_TEMPLATE)
                + functionCode("voteSumForCandidate" + (unique ? "Unique" : ""),
                               VOTES + NUM_TEMPLATE,
                               UnifiedNameContainer.getVoter() + voteSumCounter,
                               CAND_TEMPLATE)
                + SEMICOLON;
        String counter = "voteSumExp_" + voteSumCounter;
        voteSumCounter++;
        funcCallTemplate =
                funcCallTemplate.replaceAll(VARNAME_TEMPLATE, counter);
        funcCallTemplate =
                funcCallTemplate.replaceAll(NUM_TEMPLATE,
                                            String.valueOf(exp.getVoteNumber()));
        funcCallTemplate =
                funcCallTemplate.replaceAll(CAND_TEMPLATE, variableNames.pop());
        code.add(funcCallTemplate);
        variableNames.push(counter);
    }

    @Override
    public void visitIntegerNode(final IntegerNode integerNode) {
        String varName = getNewNumberVariableName();
        code.add(varEqualsCode(varName)
                + integerNode.getHeldInteger() + SEMICOLON);
        variableNames.push(varName);
    }

    /**
     * Gets the new number variable name.
     *
     * @return the new number variable name
     */
    private String getNewNumberVariableName() {
        String newNumberVariable = "integerVar_" + numberVars;
        numberVars++;
        return newNumberVariable;
    }

    @Override
    public void visitIntegerComparisonNode(final IntegerComparisonNode listComparisonNode) {
        listComparisonNode.getLHSBooleanExpNode().getVisited(this);
        listComparisonNode.getRHSBooleanExpNode().getVisited(this);
        String varNameDecl = "integer_comp_" + comparisonNodeCounter;
        comparisonNodeCounter++;
        String rhs = variableNames.pop();
        String lhs = variableNames.pop();
        String comparisonString = varEqualsCode(varNameDecl) + lhs
                + BLANK + listComparisonNode.getComparisonSymbol().getCStringRep()
                + BLANK + rhs + SEMICOLON;
        code.add(comparisonString);
        variableNames.push(varNameDecl);
        testIfLast();
    }

    @Override
    public void visitBinaryIntegerValuedNode(final BinaryIntegerValuedNode
                                                        binaryIntegerValuedNode) {
        binaryIntegerValuedNode.getLhs().getVisited(this);
        binaryIntegerValuedNode.getRhs().getVisited(this);
        String rhs = variableNames.pop();
        String lhs = variableNames.pop();
        String varname = getNewNumberVariableName();
        code.add(varEqualsCode(varname) + lhs + BLANK
                + binaryIntegerValuedNode.getRelationSymbol()
                + BLANK + rhs + SEMICOLON);
        variableNames.push(varname);
    }

    @Override
    public void visitAtPosNode(final AtPosExp atPosExp) {
        atPosExp.getIntegerValuedExpression().getVisited(this);
        String varName = getAtPosVarName(atPosExp);
        String template = varEqualsCode(VAR_TEMPLATE) + NUMBER_TEMPLATE + SEMICOLON;
        template = template.replace(VAR_TEMPLATE, varName);
        template = template.replace(NUMBER_TEMPLATE, variableNames.pop());
        code.add(template);
        variableNames.push(varName);
    }

    /**
     * Gets the at pos var name.
     *
     * @param atPosExp
     *            the at pos exp
     * @return the at pos var name
     */
    private String getAtPosVarName(final AtPosExp atPosExp) {
        int amtByPos = amtByPosVar;
        amtByPosVar++;
        return atPosExp.getInternalTypeContainer().getInternalType().toString()
                .toLowerCase() + "AtPos_" + amtByPos;
    }

    /**
     * Test if last.
     */
    private void testIfLast() {
        if (variableNames.size() == 1) {
            if (assumeOrAssert != null) {
                code.add(assumeOrAssert
                        + parenthesize(variableNames.pop())
                        + SEMICOLON);
            } else {
                ErrorLogger.log(
                        "The CodeGeneration Visitor was not set to a proper mode.");
            }
        }
    }

    @Override
    public void visitVotingTupleChangeNode(final VotingTupleChangeExpNode node) {
        List<String> tupleVotes = new ArrayList<String>();
        // Add the first element to the list
        tupleVotes.add(node.getTuple().getChild(1).getText());

        // Extract the vote elements one by one. The terminal symbol is saved in
        // child(1). If there is another nonterminal, it is saved in child(2),
        // else, that will be null.
        ParseTree subNode = node.getTuple().getChild(2);
        do {
            tupleVotes.add(subNode.getChild(1).getText());
            subNode = subNode.getChild(2);
        } while (subNode != null);
        // There is only one child
        ParseTree voteEquivalent = node.getSplitExp().getChild(2);
        String voteInput = "";
        String voteInputSize = "";
        // We have to go deeper
        if (!(voteEquivalent instanceof TerminalNodeImpl)) {
            voteInput = newTempVarString(VOTE);
            // Create the variable
            code.add(electionTypeContainer.getInputStruct().getStructAccess()
                    + BLANK + voteInput + SEMICOLON);
            voteInputSize = newTempVarString(SIZE);
            code.add(unsignedIntVar(voteInputSize) + SEMICOLON);
            VoteEquivalentsNode equiNode = new VoteEquivalentsNode(
                    node.getSplitExp().voteEquivalents(), voteInput,
                    voteInputSize);
            equiNode.getVisited(this);
        } else { // We only have one vote to split
            voteInput = node.getSplitExp().voteEquivalents().Vote().getText()
                    .toLowerCase();
            voteInputSize = V + voteInput.substring(VOTES.length());
        }
        // We now have only one voting array to care about, and its name is
        // written in voteInput;
        String splits = "splits" + getTmpIndex();
        // Determine how many splits we need
        code.add(varEqualsCode(splits) + (tupleVotes.size() - 1)
                + SEMICOLON);
        String tmpLines = newTempVarString("Lines");
        code.add(unsignedIntVar("*" + tmpLines) + SEMICOLON);
        // TODO Maybe make this non-dynamic
        code.add(varAssignCode(tmpLines,
                               functionCode("getRandomSplitLines",
                                            splits, voteInputSize))
                + SEMICOLON);
        String splitLines = "splitLines" + getTmpIndex();
        code.add(unsignedIntVar(splitLines) + arrAcc(splits) + SEMICOLON);
        code.add(forLoopHeaderCode(I, LT_SIGN + EQUALS_SIGN, splits));
        code.add(varAssignCode(splitLines + arrAcc(I),
                               tmpLines + arrAcc(I))
                + SEMICOLON);
        code.add(CLOSING_BRACES);
        int tupleSize = tupleVotes.size();
        String lastSplit = "last_split" + getTmpIndex();

        final int dim = electionTypeContainer.getInputType()
                .getAmountOfDimensions();
        code.add(varEqualsCode(lastSplit) + ZERO + SEMICOLON);
        // Split the array and extract the votes for all but one tuple element
        String voteStruct = electionTypeContainer.getInputStruct()
                .getStructAccess();

        // -1, as the last one has to take the rest
        for (int i = 0; i < tupleSize - 1; i++) {
            String tmp = newTempVarString();
            code.add(voteStruct + BLANK
                    + varAssignCode(tmp,
                                    splitFunctionCode(voteInput, lastSplit,
                                                      splitLines + arrAcc(i)))
                    + SEMICOLON);
            code.add(forLoopHeaderCode(I, LT_SIGN, V));
            String secDim = "";
            if (2 <= dim) {
                code.add(BLANK + BLANK + forLoopHeaderCode(J, LT_SIGN, C));
                secDim = arrAcc(J);
            }
            code.add(BLANK + BLANK
                    + varAssignCode(
                            dotArrStructAccess(tupleVotes.get(i).toLowerCase(), I)
                                                + secDim,
                                               dotArrStructAccess(tmp, I) + secDim)
                    + SEMICOLON);
            code.add((dim < 2 ? "" : BLANK + BLANK + CLOSING_BRACES) + "");
            code.add(CLOSING_BRACES);

            // Set the size value for this array to its new size:
            code.add(varAssignCode(V + tupleVotes.get(i).substring(VOTES.length()),
                        varSubtractCode(splitLines + arrAcc(i), lastSplit))
                    + SEMICOLON);
            code.add(varAssignCode(lastSplit, splitLines + arrAcc(i))
                    + SEMICOLON);
        }
        String tmp = newTempVarString();
        String votingStruct = electionTypeContainer.getInputStruct()
                .getStructAccess();
        code.add(votingStruct + BLANK
                + varAssignCode(tmp,
                                splitFunctionCode(voteInput, lastSplit,
                                                  voteInputSize))
                + SEMICOLON);
        // TODO Is V correct here?
        code.add(forLoopHeaderCode(I, LT_SIGN, V));
        if (2 <= dim) {
            // TODO Change to dimensions
            code.add(forLoopHeaderCode(J, LT_SIGN, C));
        }
        code.add(varAssignCode(dotArrStructAccess(tupleVotes.get(tupleSize - 1)
                                                    .toLowerCase(),
                                                  I)
                                    + (2 <= dim ? arrAcc(J) : ""),
                                dotArrStructAccess(tmp, I) + ""
                                    + (2 <= dim ? arrAcc(J) : ""))
                + SEMICOLON);
        if (2 <= dim) {
            code.add(BLANK + BLANK + CLOSING_BRACES);
        }
        code.add(CLOSING_BRACES);
        int index = tupleSize - 1;
        code.add(varAssignCode(V + tupleVotes.get(index).substring(VOTES.length()),
                                varSubtractCode(voteInputSize, lastSplit))
                + SEMICOLON);
        // Finished the split
    }

    @Override
    public void visitVotingListChangeNode(final VotingListChangeExpNode node) {
        String voteToSaveInto = node.getVote().getText().toLowerCase();
        VotingListChangeContentContext contentContext = node
                .getVotingListChangeContent();
        // We save the result of the sub computation in this variable
        String voteInput = newTempVarString(VOTE);
        // Create the variable

        code.add(electionTypeContainer.getInputStruct().getStructAccess()
                + BLANK + voteInput + SEMICOLON);
        String voteInputSize = newTempVarString(SIZE);
        code.add(unsignedIntVar(voteInputSize) + SEMICOLON);
        if (contentContext.getChild(0) instanceof ConcatenationExpContext) {
            // We have a concatenation
            ConcatenationExpNode concNode = new ConcatenationExpNode(
                    contentContext.concatenationExp(), voteInput,
                    voteInputSize);
            concNode.getVisited(this);
        } else {
            // We have a permutation
            PermutationExpNode permNode = new PermutationExpNode(
                    contentContext.permutationExp(), voteInput, voteInputSize);
            permNode.getVisited(this);
        }
        // The result of the sub computation is now saved in "voteInput"

        // TODO Extract this logic into InputType
        if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
            code.add(forLoopHeaderCode(I, LT_SIGN, V));
            code.add(varAssignCode(dotArrStructAccess(voteToSaveInto, I),
                                   dotArrStructAccess(voteInput, I))
                    + SEMICOLON);
            code.add(CLOSING_BRACES);
        } else { // We have two dimensions
            code.add(forLoopHeaderCode(I, LT_SIGN, V));
            code.add(BLANK + BLANK + forLoopHeaderCode(J, LT_SIGN, C));
            code.add(varAssignCode(dotArrStructAccess(voteToSaveInto, I, J),
                                   dotArrStructAccess(voteInput, I, J))
                    + SEMICOLON);
            code.add(BLANK + BLANK + CLOSING_BRACES);
            code.add(CLOSING_BRACES);
        }
    }

    @Override
    public void visitCandidateListChangeExpNode(final CandidateListChangeExpNode node) {
        if (electionTypeContainer.getOutputType() instanceof CandidateList) {
            String voteInput =  newTempVarString(ELECT);
            // Create the variable
            code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                    + BLANK + voteInput + SEMICOLON);
            IntersectExpNode intersectNode = new IntersectExpNode(
                    node.getIntersectExp(), voteInput);
            intersectNode.getVisited(this);
            // The value the new array shall have is now in voteInput
            String toSaveInto = node.getElect().getText().toLowerCase();
            code.add(forLoopHeaderCode(I, LT_SIGN,
                                       UnifiedNameContainer.getCandidate()));
            String structVarAcc = "."
                    + UnifiedNameContainer.getStructValueName()
                    + arrAcc(I);
            code.add(toSaveInto + BLANK
                    + varAssignCode(structVarAcc,
                                    voteInput + structVarAcc)
                    + SEMICOLON);
            code.add(CLOSING_BRACES);
        } else {
            GUIController.setErrorText(
                    "So far only candidate lists can be intersected!");
        }
    }

    @Override
    public void visitIntersectExpNode(final IntersectExpNode node) {
        IntersectExpContext context = node.getIntersectExpContext();
        String voteInputOne = newTempVarString(ELECT);
        // Create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                + BLANK + voteInputOne + SEMICOLON);
        String voteInputTwo = newTempVarString(ELECT);
        // Create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                + BLANK + voteInputTwo + SEMICOLON);
        intersectHelpMethod(context.intersectContent(0), voteInputOne,
                context.intersectContent(1), voteInputTwo,
                node.getVoteOutput());
        // We now have both inputs saved in their variables.
    }

    @Override
    public void visitIntersectTypeExpNode(final IntersectTypeExpNode node) {
        String voteInputOne = newTempVarString(ELECT);
        // Create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                + BLANK + voteInputOne + SEMICOLON);
        String voteInputTwo = newTempVarString(ELECT);
        // Create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                + BLANK + voteInputTwo + SEMICOLON);
        String toOutputTo = newTempVarString("output");
        // Create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                + BLANK + toOutputTo + SEMICOLON);
        intersectHelpMethod(node.getContext().intersectContent(0), voteInputOne,
                            node.getContext().intersectContent(1), voteInputTwo,
                            toOutputTo);
        variableNames.push(toOutputTo);
    }

    /**
     * Intersect help method.
     *
     * @param contextOne
     *            the context one
     * @param voteInputOne
     *            the vote input one
     * @param contextTwo
     *            the context two
     * @param voteInputTwo
     *            the vote input two
     * @param toOutputTo
     *            the to output to
     */
    private void intersectHelpMethod(final IntersectContentContext contextOne,
                                     final String voteInputOne,
                                     final IntersectContentContext contextTwo,
                                     final String voteInputTwo,
                                     final String toOutputTo) {
        IntersectContentNode intersectContentNodeOne =
                new IntersectContentNode(contextOne, voteInputOne);
        intersectContentNodeOne.getVisited(this);
        IntersectContentNode intersectContentNodeTwo =
                new IntersectContentNode(contextTwo, voteInputTwo);
        intersectContentNodeTwo.getVisited(this);
        code.add(varAssignCode(toOutputTo,
                functionCode("intersect", voteInputOne, voteInputTwo))
                + SEMICOLON);
    }

    @Override
    public void visitIntersectContentNode(final IntersectContentNode node) {
        String voteInput = newTempVarString(ELECT);
        // Create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                + BLANK + voteInput + SEMICOLON);
        if (node.getIntersectContentContext().Elect() != null) {
            code.add(varAssignCode(voteInput,
                                   node.getIntersectContentContext().Elect()
                                       .getText().toLowerCase())
                    + SEMICOLON);
        } else {
            // We have an intersect expression
            IntersectExpNode intersectNode =
                    new IntersectExpNode(node.getIntersectContentContext().intersectExp(),
                                         voteInput);
            intersectNode.getVisited(this);
        }
        // We now have the result standing in
        String toOutputTo = node.getVoteOutput();
        code.add(forLoopHeaderCode(I, LT_SIGN, C));
        code.add(varAssignCode(dotArrStructAccess(toOutputTo, I),
                               dotArrStructAccess(voteInput, I))
                + SEMICOLON);
        code.add(CLOSING_BRACES);
    }

    @Override
    public void visitVoteEquivalentsNode(final VoteEquivalentsNode node) {
        String voteInput = "";
        String voteInputSize = "";
        if (node.getVoteEquivalentsContext()
                .getChild(0) instanceof PermutationExpContext) {
            // We have a permutation
            voteInput = newTempVarString(VOTE);
            // Create the variable
            code.add(electionTypeContainer.getInputStruct().getStructAccess()
                    + BLANK + voteInput + SEMICOLON);
            voteInputSize = newTempVarString(SIZE);
            code.add(unsignedIntVar(voteInputSize) + SEMICOLON);
            PermutationExpNode permNode = new PermutationExpNode(
                    node.getVoteEquivalentsContext().permutationExp(),
                    voteInput, voteInputSize);
            permNode.getVisited(this);
        } else if (node.getVoteEquivalentsContext()
                .getChild(0) instanceof ConcatenationExpContext) {
            // We have a concatenation
            voteInput = electionTypeContainer.getInputStruct().getStructAccess()
                    + BLANK + newTempVarString(VOTE);
            ConcatenationExpNode concNode = new ConcatenationExpNode(
                    node.getVoteEquivalentsContext().concatenationExp(),
                    voteInput, voteInputSize);
            concNode.getVisited(this);
        } else {
            // We just have a simple vote
            voteInput = node.getVoteEquivalentsContext().Vote().getText()
                    .toLowerCase();
            voteInputSize = V + node.getVoteEquivalentsContext().Vote()
                    .getText().substring(VOTES_TEMPLATE.length());
        }
        // Now a created voting array is saved in "voteInput"
        String voteToSaveInto = node.getToOutput();
        String voteOutputLength = node.getVoteOutputLength();
        // TODO Extract this logic into InputType
        if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
            code.add(forLoopHeaderCode(I, LT_SIGN, V));
            code.add(varAssignCode(dotArrStructAccess(voteToSaveInto, I),
                                   dotArrStructAccess(voteInput, I))
                    + SEMICOLON);
            code.add(CLOSING_BRACES);
        } else { // We have two dimensions
            code.add(forLoopHeaderCode(I, LT_SIGN, V));
            code.add(BLANK + BLANK + forLoopHeaderCode(J, LT_SIGN, C));
            code.add(varAssignCode(dotArrStructAccess(voteToSaveInto, I, J),
                                   dotArrStructAccess(voteInput, I, J))
                    + SEMICOLON);
            code.add(BLANK + BLANK + CLOSING_BRACES);
            code.add(CLOSING_BRACES);
        }
        code.add(varAssignCode(voteOutputLength, voteInputSize) + SEMICOLON);
        // We fulfilled the task of writing the result of the lower variable
        // into the given output variable.
    }

    @Override
    public void visitConcatenationExpNode(final ConcatenationExpNode node) {
        int offset = 0;
        if (node.getConcatenationExpContext().getChild(0).getText()
                .equals(OPENING_PARENTHESES)) {
            // We have to skip one child if we start with "("
            offset = 1; // TODO Wieso?
            // System.out.println("Hat eine Klammer!!");
        }
        ConcatenationExpContext context = node.getConcatenationExpContext();
        ParseTree firstChild = context.getChild(0 + offset);
        String voteInputOne = newTempVarString(VOTE);
        // Create the variable.
        code.add(electionTypeContainer.getInputStruct().getStructAccess()
                + BLANK + voteInputOne + SEMICOLON);
        String voteInputOneSize = newTempVarString(SIZE);
        // We have a vote equivalent.
        if (firstChild instanceof VoteEquivalentsContext) {
            code.add(unsignedIntVar(voteInputOneSize) + SEMICOLON);
            VoteEquivalentsNode voteEqNode = new VoteEquivalentsNode(
                    context.voteEquivalents(0), voteInputOne, voteInputOneSize);
            voteEqNode.getVisited(this);
        } else if (firstChild instanceof PermutationExpContext) {
            // We have a permutation
            code.add(unsignedIntVar(voteInputOneSize) + SEMICOLON);
            PermutationExpNode permNode = new PermutationExpNode(
                    context.permutationExp(), voteInputOne, voteInputOneSize);
            permNode.getVisited(this);
        } else {
            // We have just a single vote.
            voteInputOne = context.Vote().getText().toLowerCase();
        }
        // Do the same thing for the other non terminal
        String voteInputTwo = newTempVarString(VOTE);
        // Create the variable
        code.add(electionTypeContainer.getInputStruct().getStructAccess()
                + BLANK + voteInputTwo + SEMICOLON);
        String voteInputSecondSize = newTempVarString(SIZE);
        code.add(unsignedIntVar(voteInputSecondSize) + SEMICOLON);
        VoteEquivalentsNode voteEqNodeTwo =
                new VoteEquivalentsNode(context.voteEquivalents(offset),
                                        voteInputTwo, voteInputSecondSize);
        voteEqNodeTwo.getVisited(this);
        // We now have both sides ready in voteInputOne and voteInputTwo
        String toSaveInto = node.getVoteOutput();
        String tmp = newTempVarString();
        // TODO Extract into input type
        String outputStructDesc = electionTypeContainer.getOutputStruct()
                .getStructAccess();

        if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
            code.add(outputStructDesc + BLANK
                    + varAssignCode(tmp,
                                    functionCode("concatOne",
                                                 voteInputOne,
                                                 voteInputOneSize,
                                                 voteInputTwo,
                                                 voteInputSecondSize)
                            )
                    + SEMICOLON);
            // Result is now in tmp
            code.add(forLoopHeaderCode(I, LT_SIGN, V));
            code.add(varAssignCode(dotArrStructAccess(toSaveInto, I),
                                   dotArrStructAccess(tmp, I))
                    + SEMICOLON);
            code.add(CLOSING_BRACES);
        } else {
            // We have 2 dim
            code.add(outputStructDesc + BLANK
                    + varAssignCode(tmp,
                                    functionCode("concatTwo",
                                                 voteInputOne,
                                                 voteInputOneSize,
                                                 voteInputTwo,
                                                 voteInputSecondSize)
                            )
                    + SEMICOLON);
            // Result is now in tmp //TODO Redo all of this later on
            code.add(forLoopHeaderCode(I, LT_SIGN, V));
            code.add(forLoopHeaderCode(J, LT_SIGN, C));
            code.add(varAssignCode(dotArrStructAccess(toSaveInto, I, J),
                                   dotArrStructAccess(tmp, I, J))
                    + SEMICOLON);
            code.add(CLOSING_BRACES);
            code.add(CLOSING_BRACES);
        }
        // The result is now saved in "toSaveInto"
    }

    @Override
    public void visitPermutationExpNode(final PermutationExpNode node) {
        String voteInput = newTempVarString(VOTE);
        // Create the variable
        code.add(electionTypeContainer.getInputStruct().getStructAccess()
                + BLANK + voteInput + SEMICOLON);
        String voteInputSize = newTempVarString(SIZE);
        code.add(unsignedIntVar(voteInputSize) + SEMICOLON);
        VoteEquivalentsNode voteEqNode = new VoteEquivalentsNode(
                node.getPermutationExpContext().voteEquivalents(), voteInput,
                voteInputSize);
        // voteInput now holds the vote to permute.
        // voteInputSize now is set to the size of the returned array
        voteEqNode.getVisited(this);
        String toSaveInto = node.getVoteOutput();
        String toSaveSizeInto = node.getVoteOutputLength();
        String tmp = newTempVarString();
        // TODO Extract to input type

        String voteStruct = electionTypeContainer.getInputStruct()
                .getStructAccess();
        if (electionTypeContainer.getInputType().getAmountOfDimensions() == 1) {
            code.add(voteStruct + BLANK
                    + varAssignCode(tmp,
                                    functionCode("permutateOne",
                                                 voteInput,
                                                 voteInputSize))
                    + SEMICOLON);
            // Result is now in tmp
            code.add(forLoopHeaderCode(I, LT_SIGN, V));
            code.add(varAssignCode(dotArrStructAccess(toSaveInto, I),
                                   dotArrStructAccess(tmp, I))
                    + SEMICOLON);
            code.add(CLOSING_BRACES);
            code.add(varAssignCode(toSaveSizeInto, voteInputSize) + SEMICOLON);
        } else {
            code.add(voteStruct + BLANK
                    + varAssignCode(tmp,
                                    functionCode("permutateTwo",
                                                 voteInput,
                                                 voteInputSize)
                            )
                    + SEMICOLON);
            // Result is now in tmp
            code.add(forLoopHeaderCode(I, LT_SIGN, V));
            code.add(forLoopHeaderCode(J, LT_SIGN, C));
            code.add(varAssignCode(dotArrStructAccess(toSaveInto, I, J),
                                   dotArrStructAccess(tmp, I, J))
                    + SEMICOLON);
            code.add(CLOSING_BRACES);
            code.add(CLOSING_BRACES);
            code.add(varAssignCode(toSaveSizeInto, voteInputSize) + SEMICOLON);
        }
    }

    @Override
    public void visitNotEmptyExpNode(final NotEmptyExpressionNode node) {
        String notEmpty = "not_empty" + getTmpIndex();
        code.add(unsignedIntVar(notEmpty) + SEMICOLON);
        // We will save the result in this variable.
        // 0 for false, everything else for true
        variableNames.push(notEmpty);
        String subResult = newTempVarString("bool");
        code.add(varEqualsCode(subResult) + ZERO + SEMICOLON);
        NotEmptyContentNode notEmptyNode = new NotEmptyContentNode(
                node.getContext().notEmptyContent(), subResult);
        notEmptyNode.getVisited(this);
        // The result is now saved in votingInput
        code.add(varAssignCode(notEmpty, subResult) + SEMICOLON);
        if (node.isTop()) {
            code.add(functionCode(assumeOrAssert, notEmpty) + SEMICOLON);
        }
    }

    @Override
    public void visitNotEmptyContentNode(final NotEmptyContentNode node) {
        String subResult = newTempVarString(ELECT);
        // Create the variable
        code.add(electionTypeContainer.getOutputStruct().getStructAccess()
                + BLANK + subResult + SEMICOLON);
        // We have an intersect beneath
        if (node.getContext().intersectExp() != null) {
            IntersectExpNode intersectNode =
                    new IntersectExpNode(node.getContext().intersectExp(),
                                         subResult);
            intersectNode.getVisited(this);
        } else {
            // We only have an elect
            code.add(varAssignCode(subResult,
                                   node.getContext().Elect().getText().toLowerCase())
                    + SEMICOLON);
        }
        String toReturn = node.getVotingOutput();
        // The value is now saved in subResult
        code.add(forLoopHeaderCode(I, LT_SIGN, C));
        code.add(toReturn + BLANK + "+" + EQUALS_SIGN + BLANK
                + dotArrStructAccess(subResult, I) + SEMICOLON);
        code.add(CLOSING_BRACES);
    }
}
