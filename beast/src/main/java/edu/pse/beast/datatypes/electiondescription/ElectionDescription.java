package edu.pse.beast.datatypes.electiondescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.NameInterface;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

/**
 * 
 * @author Lukas
 *
 */
public class ElectionDescription implements NameInterface {
    private String name;
    private List<String> code = new ArrayList<String>();
    private ElectionTypeContainer container;
    private int votingDeclLine;
    
    /**
     * 
     * @param name the name of the description
     * @param inputType the input type
     * @param outputType the output type
     * @param votingDeclLine the votingDeclerationLine
     */
    public ElectionDescription(String name, InputType inputType,
            OutputType outputType, int votingDeclLine) {
        this.name = name;
        this.container = new ElectionTypeContainer(inputType, outputType);
        this.votingDeclLine = votingDeclLine;
    }
    
    /**
     * 
     * @return code of this description;
     */
    public List<String> getCode() {
        return code;
    }
    

	public String getCodeAsString() {
		return String.join("\n", code);
	}
    
    /**
     * 
     * @return the name of the Description
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return the votingDescriptionLine of this description
     */
    public int getVotingDeclLine() {
        return votingDeclLine;
    }
    
    /**
     * 
     * @return the outputType of this description
     */
    public ElectionTypeContainer getContainer() {
    	return container;
    }
    
    /**
     * 
     * @param code of this description
     */
    public void setCode(List<String> code) {
        this.code = code;
    }
    
    /**
     * 
     * @param code of this description
     */
    public void setCode(String code) {
    	String[] split = code.split("\n");
    	
    	this.code = new ArrayList<>(Arrays.asList(split));
    }
    
    /**
     * 
     * @param name of this description
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @param votingDeclLine of this description
     */
    public void setVotingDeclLine(int votingDeclLine) {
        this.votingDeclLine = votingDeclLine;
    }
    
    /**
     * 
     * @param outputType of this description
     */
    public void setContainer(ElectionTypeContainer newContainer) {
        this.container = newContainer;
    }

    @Override
    public void setNewName(String newName) {
        setName(newName);
    }

	public void updateVotingDeclLine(String newVotingDeclLine) {
		this.code.set(votingDeclLine, newVotingDeclLine);
	}

	public ElectionDescription getDeepCopy() {
		ElectionDescription deepCopy = new ElectionDescription(name, container.getInputType(), container.getOutputType(), votingDeclLine);
		
		List<String> clonedCode = new ArrayList<String>();
		
		for (Iterator<String> iterator = code.iterator(); iterator.hasNext();) {
			String line = (String) iterator.next();
			clonedCode.add(line);
		}
		
		deepCopy.setCode(clonedCode);
		
		return deepCopy;
	}
}
