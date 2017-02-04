package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;

/**
 * @author NikolaiLMS
 */
public class ShowHidePropertyList extends UserAction {
    private JFrame propertyListWindow;

    public ShowHidePropertyList(JFrame propertyListWindow) {
        super("showPropertyList");
        this.propertyListWindow = propertyListWindow;
    }

    @Override
    public void perform() {
        if (propertyListWindow.isVisible()) {
            propertyListWindow.setVisible(false);
        } else {
            propertyListWindow.setVisible(true);
        }
    }
}
