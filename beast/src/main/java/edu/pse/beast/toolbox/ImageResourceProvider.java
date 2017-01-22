/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Holger-Desktop
 */
public class ImageResourceProvider {

    private static ImageResourceProvider toolbar;
    public static ImageResourceProvider getToolbarImages() {
        if(toolbar == null) {
            String[] ids = {"new", "save", "save_as", 
                "load", "copy", "cut", "paste", "undo", "redo",
            "start", "stop"};
            toolbar = 
                    new ImageResourceProvider("src/resources/images/toolbar/", 
                    ids);
        }
        return toolbar;
    }
    
    private HashMap<String, Image> images = new HashMap<>();
    private String folder;
    private String[] ids;
    
    public ImageResourceProvider(String folder, String[] ids) {
        this.folder = folder;
        this.ids = ids;
        for(String s : ids) {
            Image img = FileLoader.loadFileAsImage(new File(folder + s + ".png"));
            images.put(s, img);
        }
    }
    
    public Image getImageById(String id) {
        return images.get(id);
    }
}