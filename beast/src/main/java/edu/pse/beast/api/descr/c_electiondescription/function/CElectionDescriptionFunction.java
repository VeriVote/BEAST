package edu.pse.beast.api.descr.c_electiondescription.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public abstract class CElectionDescriptionFunction {
    private static final String LINE_BREAK = "\n";

    private String name;
    private List<String> code = new ArrayList<>();
    private List<ExtractedCLoop> extractedLoops = new ArrayList<>();

    public CElectionDescriptionFunction(final String nameString) {
        this.name = nameString;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return String.join(LINE_BREAK, code);
    }

    public void setCode(final String codeString) {
        this.code = Arrays.asList(codeString.split(LINE_BREAK));
    }

    public void setName(final String nameString) {
        this.name = nameString;
    }

    public List<String> getCodeAsList() {
        return code;
    }

    public abstract String getDeclCString(CodeGenOptions codeGenOptions);

    @Override
    public String toString() {
        return name;
    }

    public List<ExtractedCLoop> getExtractedLoops() {
        return extractedLoops;
    }

    public void setExtractedLoops(final List<ExtractedCLoop> extractedLoopList) {
        this.extractedLoops = extractedLoopList;
    }

    public boolean allLoopsDescribed() {
        boolean res;
        if (extractedLoops.isEmpty()) {
            res = AntlrCLoopParser.getAmtLoops(getCode()) == 0;
        } else {
            res = true;
        }
        for (final ExtractedCLoop l : extractedLoops) {
            if (l.getParsedLoopBoundType() == LoopBoundType.MANUALLY_ENTERED_INTEGER
                    && l.getManualInteger() == null) {
                res = false;
            }
        }
        return res;
    }

    public abstract String getReturnText(CodeGenOptions codeGenOptions);
}