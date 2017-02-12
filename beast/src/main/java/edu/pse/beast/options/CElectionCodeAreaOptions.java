package edu.pse.beast.options;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeArea;

public class CElectionCodeAreaOptions extends Options {
    private CElectionCodeArea cElecCodeArea;
    private final CodeAreaOptions codeAreaOptions;
    
    /**
     * 
     * @param cElecCodeArea the cCodeArea
     * @param codeAreaOptions the options
     */
    public CElectionCodeAreaOptions(CElectionCodeArea cElecCodeArea,
            CodeAreaOptions codeAreaOptions) {
        super("ccodearea_opts");
        this.cElecCodeArea = cElecCodeArea;
        this.codeAreaOptions = codeAreaOptions;
    }

    CElectionCodeAreaOptions(CElectionCodeArea codeArea) {
        super("ccodearea_opts");
        this.cElecCodeArea = codeArea;
        this.codeAreaOptions = new CodeAreaOptions(codeArea);
        subOptions.add(codeAreaOptions);
    }
    /**
     * 
     * @return the options
     */
    public CodeAreaOptions getCodeAreaOptions() {
        return codeAreaOptions;
    }

    @Override
    protected void reapplySpecialized() {
        
    }

    public void setCodeArea(CElectionCodeArea codeArea) {
        this.cElecCodeArea = codeArea;
        this.codeAreaOptions.setCodeArea(codeArea);
    }
    
}
