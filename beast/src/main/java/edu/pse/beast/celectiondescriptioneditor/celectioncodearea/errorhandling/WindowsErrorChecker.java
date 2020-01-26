package edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling;

import static edu.pse.beast.electionsimulator.programaccess.WindowsCompilerAndRunner.AMPERSAND;
import static edu.pse.beast.electionsimulator.programaccess.WindowsCompilerAndRunner.CMD_EXE;
import static edu.pse.beast.electionsimulator.programaccess.WindowsCompilerAndRunner.SLASH_C;
import static edu.pse.beast.electionsimulator.programaccess.WindowsCompilerAndRunner.TWO_BACKSLASHES;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.WindowsOStoolbox;

/**
 * This is the windows specific implementation to check code. It uses cl.exe
 * from the C++ pack for visual studio to check the code for errors.
 *
 * @author Lukas Stapelbroek
 *
 */
public final class WindowsErrorChecker extends SystemSpecificErrorChecker {
    /** The Constant COLON. */
    private static final String COLON = ":";
    /** The Constant BLANK. */
    private static final String BLANK = " ";
    /** The Constant QUOTE. */
    private static final String QUOTE = "\"";

    /**
     * The Constant COMPILER_STRING: The compiler we use on windows, because it
     * is also needed by cbmc.
     */
    private static final String COMPILER_STRING =
            "cl" + FileLoader.EXE_FILE_ENDING;

    /**
     * The Constant ENABLE_USER_INCLUDE: Used to enable includes from the
     * user's own written classes.
     */
    private static final String ENABLE_USER_INCLUDE = "/I";

    /** The Constant USER_INCLUDE_FOLDER. */
    private static final String USER_INCLUDE_FOLDER = "/core/user_includes/";

    /**
     * The Constant COMPILE_ALL_INCLUDES_IN_FOLDER: We want to compile all
     * available c files, so the user does not need to specify anything.
     */
    private static final String COMPILE_ALL_INCLUDES_IN_FOLDER =
            "*" + FileLoader.C_FILE_ENDING;

    @Override
    public Process checkCodeFileForErrors(final File toCheck) {
        String vsCmd = null;
        Process startedProcess = null;
        final String userIncludeAndPath =
                ENABLE_USER_INCLUDE + QUOTE
                + SuperFolderFinder.getSuperFolder() + USER_INCLUDE_FOLDER
                + QUOTE;
        // We have to compile all includes that the user puts in that folder, in
        // case some of them are needed.
        final String compileAllIncludesInIncludePath =
                QUOTE
                + SuperFolderFinder.getSuperFolder() + USER_INCLUDE_FOLDER
                + COMPILE_ALL_INCLUDES_IN_FOLDER + QUOTE;

        // try to get the vsCMD
        try {
            vsCmd = WindowsOStoolbox.getVScmdPath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (vsCmd == null) {
            ErrorForUserDisplayer.displayError(
                    "The program \"VsDevCmd.bat\" could not be found. "
                            + "It is required to run this program, so "
                            + "please supply it with it. \n"
                            + " To do so, download the Visual Studio Community Version, "
                            + "install it (including the C++ pack). \n "
                            + "Then, search for the VsDevCmd.bat in it, and copy and paste "
                            + "it into the foler /windows/ in the BEAST installation folder.");
            return null;
        } else {
            // Since Windows is weird, the whole call that will get placed
            // inside VScmd has to be in one giant string. Put the created
            // file in the output directory, such that it can be deleted
            // afterwards.
            final String clExeCall =
                    QUOTE + vsCmd + QUOTE + BLANK
                    + AMPERSAND + BLANK + COMPILER_STRING
                    + BLANK + userIncludeAndPath + BLANK
                    + (QUOTE + toCheck.getAbsolutePath() + QUOTE) + BLANK
                    + (BLANK + "/Fo" + toCheck.getParent() + TWO_BACKSLASHES + BLANK)
                    + (BLANK + "/Fe" + toCheck.getParent() + TWO_BACKSLASHES + BLANK)
                    + compileAllIncludesInIncludePath;
            final List<String> callInList = new ArrayList<String>();
            callInList.add(clExeCall);
            final File batFile =
                    new File(toCheck.getParent() + TWO_BACKSLASHES
                            + toCheck.getName().replace(FileLoader.C_FILE_ENDING,
                                                        FileLoader.BAT_FILE_ENDING));
            FileSaver.writeStringLinesToFile(callInList, batFile);
            // This call starts a new VScmd instance and let's cl.exe (the
            // compiler) run in it.
            // ProcessBuilder prossBuild = new ProcessBuilder(CMD_EXE, SLASH_C,
            //                                                clExeCall);
            final ProcessBuilder prossBuild =
                    new ProcessBuilder(CMD_EXE, SLASH_C,
                                       batFile.getAbsolutePath());
            try {
                startedProcess = prossBuild.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return startedProcess;
    }

    @Override
    protected List<CodeError> parseError(final List<String> result,
                                         final List<String> errors,
                                         final int lineOffset) {
        final List<CodeError> codeErrors = new ArrayList<CodeError>();

        // errors are displayed like "(LINENUMBER)" where line number is a whole
        // number
        final Pattern lineExtractor = Pattern.compile("((.*)(\\([0-9]*\\))(.*))");

        // cl.exe prints out the results in the result list
        for (final Iterator<String> iterator = result.iterator();
                iterator.hasNext();) {
            final String line = iterator.next();
            final Matcher linesMatcher = lineExtractor.matcher(line);
            int lineNumber = -1;
            String varName = "";
            String message = "";
            if (linesMatcher.find()) {
                try {
                    // we want the first occurrence of such a "line number"
                    // identifier, so we do not need to worry
                    // about code injection from strings or such
                    // then we split at "(" and ")" to extract the number
                    lineNumber =
                            Integer.parseInt(linesMatcher.group(1).split("\\(")[1]
                                                .split("\\)")[0]) - lineOffset;
                    // get the error message here by splitting at a common
                    // (error/warning C[ERRORNUMBER]) identifier
                    final String[] varAndMessage = line.split("([a-zA-Z]+ C[0-9]+:)");
                    // String msg = line.substring(line.lastIndexOf(COLON));
                    // to prevent exceptions
                    if (varAndMessage.length > 1) {
                        final String toSplit = varAndMessage[1];
                        // the variable and compiler message is between ":"'s,
                        // so we split there.
                        if (toSplit.contains(COLON)) {
                            varName =
                                    toSplit.split(COLON)[0].replaceAll(QUOTE, "");
                            message = toSplit.split(COLON)[1];
                        } else {
                            message = toSplit;
                        }
                    }
                    codeErrors.add(CCodeErrorFactory.generateCompilerError(
                                    lineNumber, -1, varName, message)
                    );
                } catch (NumberFormatException e) {
                    ErrorLogger.log("cannot parse the current"
                                    + " error line from cl.exe");
                }
            } else if (line.contains(BLANK + COLON + BLANK + "error LNK")) {
                final String[] splittedArray = line.split(COLON);
                if (splittedArray.length >= 2) {
                    final String subString = splittedArray[2];
                    codeErrors.add(
                            CCodeErrorFactory.generateCompilerError(
                                    -1, -1, "", subString
                                    )
                    );
                }
            }
        }
        return codeErrors;
    }
}
