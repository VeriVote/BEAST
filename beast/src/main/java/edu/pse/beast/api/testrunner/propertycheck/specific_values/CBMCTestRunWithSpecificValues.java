package edu.pse.beast.api.testrunner.propertycheck.specific_values;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.cbmc_run_with_specific_values.VotingParameters;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.paths.PathHandler;
import edu.pse.beast.api.testrunner.code_files.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonMessage;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonRunningDataExtractor;

/**
 * This class can be expanded to implemented functionality to generate test runs
 * with specific parameters.
 *
 * @author Holger Klein
 */
public class CBMCTestRunWithSpecificValues implements CBMCTestCallback {
    private CBMCCodeFileData cbmcCodeFile;
    private VotingParameters parameters;

    private CBMCJsonRunningDataExtractor cbmcJsonRunningDataExtractor;
    private CBMCPropertyCheckWorkUnit workUnit;

    private String loopBounds;
    private CodeGenOptions codeGenerationOptions;

    private int v;
    private int s;
    private int c;

    private CElectionDescription description;
    private PreAndPostConditionsDescription propertyDescription;

    public CBMCTestRunWithSpecificValues(final CBMCCodeFileData codeFile,
                                         final VotingParameters params,
                                         final String loopBoundList,
                                         final CodeGenOptions codeGenOptions,
                                         final CElectionDescription descr) {
        this.cbmcCodeFile = codeFile;
        this.parameters = params;
        this.loopBounds = loopBoundList;
        this.codeGenerationOptions = codeGenOptions;
        this.description = descr;
        v = params.getV();
        c = params.getC();
        s = params.getS();
        propertyDescription = new PreAndPostConditionsDescription("false");
        propertyDescription.getPreConditionsDescription().setCode("");
        propertyDescription.getPostConditionsDescription().setCode("FALSE;");

        cbmcJsonRunningDataExtractor =
                new CBMCJsonRunningDataExtractor(descr,
                                                 propertyDescription, s, c, v,
                                                 codeFile.getCodeInfo());
    }

    public void setAndInitializeWorkUnit(final CBMCPropertyCheckWorkUnit cbmcWorkUnit,
                                         final PathHandler pathHandler) {
        if (cbmcWorkUnit.getProcessStarterSource() == null) {
            return;
        }
        cbmcWorkUnit.initialize(v, s, c, codeGenerationOptions, loopBounds,
                cbmcCodeFile, description, propertyDescription, this, pathHandler);
        this.workUnit = cbmcWorkUnit;
    }

    @Override
    public void onPropertyTestRawOutput(final String sessionUUID,
                                        final CElectionDescription descr,
                                        final PreAndPostConditionsDescription propertyDescr,
                                        final int seatAmount,
                                        final int candidateAmount,
                                        final int voteAmount,
                                        final String uuid,
                                        final String output) {
        final CBMCJsonMessage msg = cbmcJsonRunningDataExtractor.appendOutput(output);
        System.out.println(msg);
    }

}
