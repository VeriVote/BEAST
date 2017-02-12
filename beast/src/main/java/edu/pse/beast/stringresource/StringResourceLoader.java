/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

import edu.pse.beast.toolbox.ErrorLogger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author Niels
 */
public class StringResourceLoader {
    
    private final HashMap<String, String> idsToString;

    /**
     *
     * @param stringRes a Linked List with the correct format. Id : string
     * @throws ArrayIndexOutOfBoundsException if the list is not correctly
     * formatted
     */
    public StringResourceLoader(LinkedList<String> stringRes) throws ArrayIndexOutOfBoundsException {
        idsToString = new HashMap<>();
        String line;
        String[] split;
        String id;
        String displayedText;
        while (!stringRes.isEmpty()) {
            line = stringRes.pop();
            if (line.length() != 0) {
                split = line.split(":", 2);
                id = split[0].trim();
                displayedText = split[1].trim();
                idsToString.put(id, displayedText);
            }
        }
    }

    /**
     *
     * @param id Id of the String you want to load
     * @return the String with the id
     */
    public String getStringFromID(String id) {
        String get = idsToString.get(id);
        if (get == null) {
            ErrorLogger.log("this Id was not found in a Stringfile + " + id);
        }
        return get;
    }
    
    public String getIdForString(String s) {
        for (Map.Entry<String, String> entry : idsToString.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(value.equals(s)) return key;
        }
        return null;
    }
}
