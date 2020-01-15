package edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder;

import java.util.ArrayList;
import java.util.BitSet;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import edu.pse.beast.booleanexpeditor.booleanexpcodearea.BooleanExpANTLRHandler;
import edu.pse.beast.codearea.errorhandling.CodeError;

/**
 * Class that finds errors which conflict with the grammar in
 * BooleanExpGrammar.g in the BooleanExpression(s) of the CodeArea this class is
 * an attribute of.
 *
 * @author Nikolai Schnell
 */
public final class BooleanExpEditorGrammarErrorFinder implements ANTLRErrorListener {
    private static BooleanExpEditorGrammarErrorFinder finder;
    private final ArrayList<CodeError> errors = new ArrayList<>();

    private BooleanExpEditorGrammarErrorFinder(final BooleanExpANTLRHandler antlrHandler) {
        antlrHandler.getParser().addErrorListener(this);
    }

    public static ArrayList<CodeError> getErrors(final BooleanExpANTLRHandler antlrHandler) {
        finder = new BooleanExpEditorGrammarErrorFinder(antlrHandler);
        antlrHandler.getParseTree();
        return finder.getErrors();
    }

    public void clearErrors() {
        errors.clear();
    }

    public ArrayList<CodeError> getErrors() {
        return errors;
    }

    @Override
    public void syntaxError(final Recognizer<?, ?> rcgnzr,
                            final Object o,
                            final int line,
                            final int charInline,
                            final String msg,
                            final RecognitionException re) {
        errors.add(BooleanExpErrorFactory.createAntlrError(line, charInline, msg));
    }

    @Override
    public void reportAmbiguity(final Parser parser, final DFA dfa,
                                final int i, final int i1, final boolean bln,
                                final BitSet bitset,
                                final ATNConfigSet atncs) {
    }

    @Override
    public void reportAttemptingFullContext(final Parser parser, final DFA dfa,
                                            final int i, final int i1,
                                            final BitSet bitset,
                                            final ATNConfigSet atncs) {
    }

    @Override
    public void reportContextSensitivity(final Parser parser, final DFA dfa,
                                         final int i, final int i1, final int i2,
                                         final ATNConfigSet atncs) {
    }
}
