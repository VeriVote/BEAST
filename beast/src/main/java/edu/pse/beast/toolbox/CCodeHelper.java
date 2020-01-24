package edu.pse.beast.toolbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.celectiondescriptioneditor.electiontemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 * This class contains functionality to generate C Code from internal data
 * structures.
 *
 * @author Holger Klein
 */
public final class CCodeHelper {
    /** The Constant AUTO. */
    public static final String AUTO = "auto";
    /** The Constant BREAK. */
    public static final String BREAK = "break";
    /** The Constant CASE. */
    public static final String CASE = "case";
    /** The Constant CHAR. */
    public static final String CHAR = "char";
    /** The Constant CONST. */
    public static final String CONST = "const";
    /** The Constant CONTINUE. */
    public static final String CONTINUE = "continue";
    /** The Constant DEFAULT. */
    public static final String DEFAULT = "default";
    /** The Constant DEFINE. */
    public static final String DEFINE = "#define";
    /** The Constant DO. */
    public static final String DO = "do";
    /** The Constant DOUBLE. */
    public static final String DOUBLE = "double";
    /** The Constant ELSE. */
    public static final String ELSE = "else";
    /** The Constant ENUM. */
    public static final String ENUM = "enum";
    /** The Constant EXTERN. */
    public static final String EXTERN = "extern";
    /** The Constant FLOAT. */
    public static final String FLOAT = "float";
    /** The Constant FOR. */
    public static final String FOR = "for";
    /** The Constant GOTO. */
    public static final String GOTO = "goto";
    /** The Constant IF. */
    public static final String IF = "if";
    /** The Constant INCLUDE. */
    public static final String INCLUDE = "#include";
    /** The Constant INLINE. */
    public static final String INLINE = "inline";
    /** The Constant INT. */
    public static final String INT = "int";
    /** The Constant LONG. */
    public static final String LONG = "long";
    /** The Constant REGISTER. */
    public static final String REGISTER = "register";
    /** The Constant RESTRICT. */
    public static final String RESTRICT = "restrict";
    /** The Constant RETURN. */
    public static final String RETURN = "return";
    /** The Constant SHORT. */
    public static final String SHORT = "short";
    /** The Constant SIGNED. */
    public static final String SIGNED = "signed";
    /** The Constant SIZE_OF. */
    public static final String SIZE_OF = "sizeof";
    /** The Constant STATIC. */
    public static final String STATIC = "static";
    /** The Constant STRUCT. */
    public static final String STRUCT = "struct";
    /** The Constant SWITCH. */
    public static final String SWITCH = "switch";
    /** The Constant TYPE_OF. */
    public static final String TYPE_DEF = "typedef";
    /** The Constant UNION. */
    public static final String UNION = "union";
    /** The Constant UNSIGNED. */
    public static final String UNSIGNED = "unsigned";
    /** The Constant VOID. */
    public static final String VOID = "void";
    /** The Constant VOLATILE. */
    public static final String VOLATILE = "volatile";
    /** The Constant WHILE. */
    public static final String WHILE = "while";
    /** The Constant ALIGN_AS. */
    public static final String ALIGN_AS = "_Alignas";
    /** The Constant ALIGN_OF. */
    public static final String ALIGN_OF = "_Alignof";
    /** The Constant ATOMIC. */
    public static final String ATOMIC = "_Atomic";
    /** The Constant BOOL. */
    public static final String BOOL = "_Bool";
    /** The Constant COMPLEX. */
    public static final String COMPLEX = "_Complex";
    /** The Constant GENERIC. */
    public static final String GENERIC = "_Generic";
    /** The Constant IMAGINARY. */
    public static final String IMAGINARY = "_Imaginary";
    /** The Constant NO_RETURN. */
    public static final String NO_RETURN = "_Noreturn";
    /** The Constant STATIC_ASSERT. */
    public static final String STATIC_ASSERT = "_Static_assert";
    /** The Constant THREAD_LOCAL. */
    public static final String THREAD_LOCAL = "_Thread_local";

    /** The BLANK symbol. */
    public static final String BLANK = " ";
    /** The Constant SEMICOLON. */
    public static final String SEMICOLON = ";";
    /** The Constant EQUALS_SIGN. */
    public static final String EQUALS_SIGN = "=";
    /** The Constant LT_SIGN. */
    public static final String LT_SIGN = "<";
    /** The Constant GT_SIGN. */
    public static final String GT_SIGN = ">";

    /** The Constant OPENING_PARENTHESES. */
    public static final String OPENING_PARENTHESES = "(";
    /** The Constant CLOSING_PARENTHESES. */
    public static final String CLOSING_PARENTHESES = ")";

    /** The Constant OPENING_BRACES. */
    public static final String OPENING_BRACES = "{";
    /** The Constant CLOSING_BRACES. */
    public static final String CLOSING_BRACES = "}";
    /** The comma symbol. */
    public static final String COMMA = ",";
    /** The colon symbol. */
    public static final String COLON = ":";
    /** The Constant DOT. */
    private static final String DOT = ".";

    /** The Constant AMPERSAND. */
    private static final String AMPERSAND = "&";
    /** The Constant PIPE. */
    private static final String PIPE = "|";
    /** The Constant MINUS. */
    private static final String MINUS = "-";
    /** The Constant NOT. */
    private static final String NOT = "!";
    /** The Constant PLUS_PLUS. */
    private static final String PLUS = "+";
    /** The Constant PLUS_PLUS. */
    private static final String PLUS_PLUS = PLUS + PLUS;

    /** The Constant OPENING_BRACKETS. */
    private static final String OPENING_BRACKETS = "[";
    /** The Constant CLOSING_BRACKETS. */
    private static final String CLOSING_BRACKETS = "]";

    /** The Constant UNDERSCORE. */
    private static final String UNDERSCORE = "_";
    /** The Constant DOT_ARR. */
    private static final String DOT_ARR = DOT + "arr";

    /** The Constant ZERO. */
    private static final String ZERO = "0";
    /** The Constant ONE. */
    private static final String ONE = "1";

    /** The line comment symbol(s). */
    private static final String LINE_COMMENT = "//";
    /** The right parenthesis and left brace symbol. */
    private static final String PAREN_R_BRACE_L =
            CLOSING_PARENTHESES + BLANK + OPENING_BRACES;
    /** The uint loop start string. */
    private static final String UINT_LOOP_START =
            OPENING_PARENTHESES + UNSIGNED + BLANK + INT + BLANK;

    /** The RESULT constant. */
    private static final String RESULT = "RESULT";
    /** The VOTES constant. */
    private static final String VOTES = "VOTES";
    /** The "amountVotes" constant. */
    private static final String AMOUNT_VOTES = "amountVotes";
    /** The "_exp" string. */
    private static final String EXP = "_exp";

    // String that only allows string in valid C format (they can still contain
    // identifiers)

    /** The character regex. */
    private static String characterRegex = "[_a-zA-Z][_a-zA-Z0-9]{0,30}";

    /** The reserved words. */
    private static String[] reservedWords =
        {
        AUTO, BREAK, CASE, CHAR,
        CONST, CONTINUE, DEFAULT,
        DO, DOUBLE, ELSE, ENUM,
        EXTERN, FLOAT, FOR, GOTO,
        IF, INLINE, INT, LONG,
        REGISTER, RESTRICT, RETURN,
        SHORT, SIGNED, SIZE_OF,
        STATIC, STRUCT, SWITCH,
        TYPE_DEF, UNION, UNSIGNED,
        VOID, VOLATILE, WHILE,
        ALIGN_AS, ALIGN_OF, ATOMIC,
        BOOL, COMPLEX, GENERIC,
        IMAGINARY, NO_RETURN,
        STATIC_ASSERT, THREAD_LOCAL
        };

    /** The c reserved words. */
    private static List<String> cReservedWords =
            new ArrayList<String>(Arrays.asList(reservedWords));

    /**
     * Instantiates a new c code helper.
     */
    private CCodeHelper() { }

    /**
     * Determines whether the string is neither null nor empty.
     *
     * @param string
     *            the string
     * @return true, if the string is not null and not empty
     */
    public static boolean notNullOrEmpty(final String string) {
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
     * Generate negated expression.
     *
     * @param expr
     *            the expr
     * @return the string
     */
    public static String not(final String expr) {
        return NOT + expr;
    }

    /**
     * Generate underscored expression.
     *
     * @param expr
     *            the expr
     * @return the string
     */
    public static String underscore(final String expr) {
        return UNDERSCORE + expr;
    }

    /**
     * Generate increment expression.
     *
     * @param expr
     *            the expr
     * @return the string
     */
    public static String plusPlus(final String expr) {
        return expr + PLUS_PLUS;
    }

    /**
     * Generate lhs expression plus rhs expression.
     *
     * @param lhsExp
     *            the lhsExp
     * @param rhsExp
     *            the rhsExp
     * @return the string
     */
    public static String plus(final String lhsExp,
                              final String rhsExp) {
        return lhsExp + BLANK + PLUS + BLANK + rhsExp;
    }

    /**
     * Generate lhs expression increment by rhs expression.
     *
     * @param lhsExp
     *            the lhsExp
     * @param rhsExp
     *            the rhsExp
     * @return the string
     */
    public static String plusEquals(final String lhsExp,
                                    final String rhsExp) {
        return lhsExp + BLANK + PLUS + EQUALS_SIGN + BLANK + rhsExp;
    }

    /**
     * Generate code for zero.
     *
     * @return the string
     */
    public static String zero() {
        return ZERO;
    }

    /**
     * Generate code for one.
     *
     * @return the string
     */
    public static String one() {
        return ONE;
    }

    /**
     * Generate ".arr" code.
     *
     * @return the string
     */
    public static String dotArr() {
        return DOT_ARR;
    }

    /**
     * Generate code for unsigned int variable.
     *
     * @param varName
     *            the var name
     * @return the string
     */
    public static String unsignedIntVar(final String varName) {
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
    public static String varEqualsCode(final String varName) {
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
    public static String varAssignCode(final String varName,
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
    public static String varSubtractCode(final String varPos,
                                          final String varNeg) {
        return varPos + BLANK + MINUS + BLANK + varNeg;
    }

    /**
     * Variable add code.
     *
     * @param varOne
     *            the first variable
     * @param varTwo
     *            the second variable
     * @return the string
     */
    public static String varAddCode(final String varOne,
                                    final String varTwo) {
        return varOne + BLANK + MINUS + BLANK + varTwo;
    }

    /**
     * Variable less code.
     *
     * @param lhsExpr
     *            the lhs expression
     * @param rhsExpr
     *            the rhs expression
     * @return the string
     */
    public static String lt(final String lhsExpr,
                            final String rhsExpr) {
        return lhsExpr + BLANK + LT_SIGN + BLANK + rhsExpr;
    }

    /**
     * Variable less-equal code.
     *
     * @param lhsExpr
     *            the lhs expression
     * @param rhsExpr
     *            the rhs expression
     * @return the string
     */
    public static String leq(final String lhsExpr,
                             final String rhsExpr) {
        return lhsExpr + BLANK + LT_SIGN + EQUALS_SIGN + BLANK + rhsExpr;
    }

    /**
     * Variable equal code.
     *
     * @param lhsExpr
     *            the lhs expression
     * @param rhsExpr
     *            the rhs expression
     * @return the string
     */
    public static String eq(final String lhsExpr,
                            final String rhsExpr) {
        return lhsExpr + BLANK + EQUALS_SIGN + EQUALS_SIGN + BLANK + rhsExpr;
    }

    /**
     * Variable not-equal code.
     *
     * @param lhsExpr
     *            the lhs expression
     * @param rhsExpr
     *            the rhs expression
     * @return the string
     */
    public static String neq(final String lhsExpr,
                             final String rhsExpr) {
        return lhsExpr + BLANK + not(EQUALS_SIGN) + BLANK + rhsExpr;
    }

    /**
     * Parenthesize the expression.
     *
     * @param expression
     *            the expression
     * @return the string
     */
    public static String parenthesize(final String expression) {
        return notNullOrEmpty(expression)
                ? (OPENING_PARENTHESES + expression + CLOSING_PARENTHESES)
                        : expression;
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
    public static String functionCode(final String function,
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
        }
        return notNullOrEmpty(function)
                ? function + parenthesize(functionCode)
                : function;
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
    public static String functionCode(final String function,
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
    public static String conjunct(final String toBeConjuncted,
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
    public static String disjunct(final String toBeDisjuncted,
                                  final String originalTerm) {
        final String disjunctor = PIPE + PIPE;
        return disOrConjunct(toBeDisjuncted, originalTerm, disjunctor);
    }

    /**
     * Generate package-include statement.
     *
     * @param pkgName the pkg name
     * @return the string
     */
    public static String include(final String pkgName) {
        return CCodeHelper.INCLUDE + CCodeHelper.BLANK
                + LT_SIGN + pkgName + FileLoader.H_FILE_ENDING + GT_SIGN;
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
    public static String forLoopHeaderCode(final String countVar,
                                           final String comparison,
                                           final String boundConst,
                                           final String guardReinforce) {
        final String varEqualsZero =
                varEqualsCode(countVar) + zero() + SEMICOLON;
        final String boundGuard =
                countVar + BLANK + comparison + BLANK + boundConst;
        final String loopGuard = conjunct(guardReinforce, boundGuard)
                                + SEMICOLON;
        final String incrCounter = plusPlus(countVar);
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
    public static String forLoopHeaderCode(final String countVar,
                                           final String comparison,
                                           final String boundConst) {
        return forLoopHeaderCode(countVar, comparison, boundConst, null);
    }

    /**
     * Array access.
     *
     * @param dim
     *            the dim
     * @return the string
     */
    public static String arrAcc(final String dim) {
        return OPENING_BRACKETS + dim + CLOSING_BRACKETS;
    }

    /**
     * Array access.
     *
     * @param dim
     *            the dim
     * @return the string
     */
    public static String arrAcc(final int dim) {
        return arrAcc(Integer.toString(dim));
    }

    /**
     * Array access for all given dimensions.
     *
     * @param arr
     *            the array
     * @param dims
     *            the dims
     * @return the string
     */
    private static String arrAccess(final String arr,
                                    final List<String> dims) {
        String arrAccess = notNullOrEmpty(arr) ? arr : "";
        if (notNullOrEmpty(arr) && notNullOrEmpty(dims)) {
            arrAccess += arr;
            for (final String dim : dims) {
                arrAccess += notNullOrEmpty(dim) ? arrAcc(dim) : "";
            }
        }
        return arrAccess;
    }

    /**
     * Array access for all given dimensions.
     *
     * @param arr
     *            the array
     * @param dims
     *            the dims
     * @return the string
     */
    public static String arrAccess(final String arr,
                                   final String... dims) {
        List<String> dimList = new ArrayList<String>();
        for (final String dim : dims) {
            if (notNullOrEmpty(dim)) {
                dimList.add(dim);
            }
        }
        return arrAccess(arr, dimList);
    }

    /**
     * Dot arr struct access for all given dimensions.
     *
     * @param owner
     *            the owner
     * @param structName
     *            the struct name
     * @param dims
     *            the dims
     * @return the string
     */
    private static String dotStructAccess(final String owner,
                                          final String structName,
                                          final List<String> dims) {
        String arrAccess = notNullOrEmpty(owner) ? owner : "";
        if (notNullOrEmpty(owner) && notNullOrEmpty(dims)) {
            arrAccess += notNullOrEmpty(structName)
                    ? (DOT + structName) : dotArr();
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
     * @param structName
     *            the struct name
     * @param dims
     *            the dims
     * @return the string
     */
    public static String dotStructAccess(final String owner,
                                         final String structName,
                                         final String... dims) {
        List<String> dimList = new ArrayList<String>();
        for (final String dim : dims) {
            if (notNullOrEmpty(dim)) {
                dimList.add(dim);
            }
        }
        return dotStructAccess(owner, structName, dimList);
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
    public static String dotArrStructAccess(final String owner,
                                             final String... dims) {
        List<String> dimList = new ArrayList<String>();
        for (final String dim : dims) {
            if (notNullOrEmpty(dim)) {
                dimList.add(dim);
            }
        }
        return dotStructAccess(owner, null, dimList);
    }

    /**
     * Returns the C constant which is the max amount of elements in a given
     * list container.
     *
     * @param cont
     *            the type container representing the list
     * @return the size of the container in C Code
     */
    public static String getListSize(final InternalTypeContainer cont) {
        final String size;
        if (cont.getAccessTypeIfList() == InternalTypeRep.CANDIDATE) {
            size = UnifiedNameContainer.getCandidate();
        } else if (cont.getAccessTypeIfList() == InternalTypeRep.VOTER) {
            size = UnifiedNameContainer.getVoter();
        } else if (cont.getAccessTypeIfList() == InternalTypeRep.SEAT) {
            size = UnifiedNameContainer.getSeats();
        } else {
            size = "";
        }
        if ("".equals(size)) {
            ErrorLogger.log("");
        }
        return size;
    }
    //
    // /**
    // * Creates the C-Type text representation of the given internal type
    // * container.
    // * Arrays are created as arrays: "unsigned int votes[" +
    // * UnifiedNameContainer.getVoter() + "][" +
    // * UnifiedNameContainer.getCandidate()
    // * + "]", for example
    // *
    // * @param electionContainer the container for which the C type should be
    // *                          created
    // * @param name the name of the variable
    // * @return the c type
    // */
    // public static String getCType(ElectionTypeContainer electionContainer,
    //                               String name) {
    //     String decl = "unsigned int " + name;
    //     decl = decl + electionContainer.getInputType().getSimpleType(true);
    //     return decl;
    // }
    //
    // /**
    // * Creates the C-Type text representation of the given
    // * Internaltypecontainer.
    // * Arrays are created as pointers: "unsigned int *", for example.
    // *
    // * @param electionContainer the container for which the C type should be
    // *                          created
    // * @return the c type
    // */
    // public static String getCTypePointer(ElectionTypeContainer
    //                                          electionContainer) {
    //     String decl = electionContainer.getOutputType().getSimpleType(true);
    //     return decl;
    // }

    /**
     * If the given InternaltypeContainer represents a list, it generates the
     * String representing a corresponding C-Array. Ex return: "[" +
     * UnifiedNameContainer.getCandidate() + "]"
     *
     * @param cont
     *            the container for which the C type should be created
     * @return the amount of square brackets and length constants needed
     */
    public static String getCArrayType(final InternalTypeContainer cont) {
        InternalTypeContainer currentContainer = cont;
        String decl = "";
        while (currentContainer.isList()) {
            decl += OPENING_BRACKETS
                    + getListSize(currentContainer)
                    + CLOSING_BRACKETS;
            currentContainer = currentContainer.getListedType();
        }
        return decl;
    }

    /**
     * Generates the declaration String for a voting function depending on its
     * input and result type. This is the voting method which will be presented
     * to the user, so it should not contain structs, but just simple data types
     * (except if it cannot be helped).
     *
     * @param container
     *            the input format of the voting array passed to the function
     * @return the voting function declaration line
     */
    public static String generateSimpleDeclString(final ElectionTypeContainer container) {
        String decl = RESULT + BLANK
                + UnifiedNameContainer.getVotingMethod()
                + UINT_LOOP_START + AMOUNT_VOTES + COMMA + BLANK + VOTES + PAREN_R_BRACE_L;

        String[] sizeOfDimensions =
                container.getInputType().getSizeOfDimensions();

        if (sizeOfDimensions.length > 0) {
            sizeOfDimensions[0] = AMOUNT_VOTES;
        }

        decl = decl.replace(RESULT,
                container.getInputType().getDataTypeAndSign() + container
                        .getOutputType().getDimensionDescriptor(true));
        decl = decl.replace(VOTES,
                container.getInputType().getDataTypeAndSign() + BLANK
                        + UnifiedNameContainer.getVotingArray()
                        + container.getInputType()
                                .getDimensionDescriptor(sizeOfDimensions));

        return decl;
    }

    /**
     * Generates the declaration String for a voting function depending on its
     * input and result type.
     *
     * @param container
     *            the input format of the voting array passed to the function
     * @param voteStructName
     *            the vote struct name
     * @return the voting function declaration line
     */
    public static String generateStructDeclString(final ElectionTypeContainer container,
                                                  final String voteStructName) {
        String decl = RESULT + BLANK
                + UnifiedNameContainer.getVotingMethod()
                + UINT_LOOP_START + AMOUNT_VOTES + COMMA + BLANK + VOTES + PAREN_R_BRACE_L;

        decl = decl.replace(RESULT,
                container.getOutputStruct().getStructAccess());
        decl = decl.replace(VOTES,
                container.getInputStruct().getStructAccess() + BLANK
                        + voteStructName);

        return decl;
    }

    /**
     * Generates the complete function block which is placed in the C editor if
     * the user creates a new election description. It adds the explanatory
     * comments and the closing curly bracket
     *
     * @param container
     *            the input format of the voting array passed to the function
     * @param name
     *            the name of the election
     * @param templateHandler
     *            the Template handler which generated input and result types
     * @param stringResourceLoader
     *            the string resource loader currently used
     * @return the complete voting function
     */
    public static ElectionDescription
            generateElectionDescription(final ElectionTypeContainer container,
                                        final String name,
                                        final ElectionTemplateHandler templateHandler,
                                        final StringResourceLoader stringResourceLoader) {
        ArrayList<String> code = new ArrayList<String>();
        final String inputIdInFile = container.getInputType().getInputIDinFile();
        final String outputIdInFile = container.getOutputType().getOutputIDinFile();

        code.add(LINE_COMMENT + stringResourceLoader.getStringFromID(inputIdInFile)
                + COLON + BLANK
                + stringResourceLoader.getStringFromID(inputIdInFile + EXP));
        code.add(LINE_COMMENT + stringResourceLoader.getStringFromID(outputIdInFile)
                + COLON + BLANK + stringResourceLoader
                        .getStringFromID(outputIdInFile + EXP));
        code.add(generateSimpleDeclString(container));
        code.add(CLOSING_BRACES + BLANK);
        final ElectionDescription description =
                new ElectionDescription(name,
                                        container.getInputType(),
                                        container.getOutputType(),
                                        0, 0, 0, true);
        description.setCode(code);
        return description;
    }

    /**
     * Returns the max value an element of the given ElectionTypeContainer can
     * have.
     *
     * @param container
     *            the list whose elements max value needs to be determined
     * @return max value an element of the given ElectionTypeContainer can have
     */
    public static String getMax(final ElectionTypeContainer container) {
        return container.getInputType().getMaximalValue();
    }

    /**
     * Returns the minimum value an element of the given ElectionTypeContainer
     * can have.
     *
     * @param container
     *            the list whose elements min value needs to be determined
     * @return minimum value an element of the given ElectionTypeContainer can
     *         have
     */
    public static String getMin(final ElectionTypeContainer container) {
        return container.getInputType().getMinimalValue();
    }

    /**
     * Checks if is valid C name.
     *
     * @param name
     *            the name
     * @return true, if is valid C name
     */
    public static boolean isValidCName(final String name) {
        if (name.matches(characterRegex)) {
            if (!cReservedWords.stream().anyMatch(str -> str.equals(name))) {
                // it is not a reserved word
                return true;
            }
        }
        System.out.println("The given symbolic variable name"
                            + " is not valid in C.");
        return false;
    }
}
