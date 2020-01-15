package edu.pse.beast.codearea.useractions;

import edu.pse.beast.codearea.actionlist.ActionList;
import edu.pse.beast.toolbox.UserAction;

/**
 * This useraction asks the given actionlist to undo the last action.
 *
 * @author Holger Klein
 */
public class UndoLastUserAction extends UserAction {
    private ActionList list;

    public UndoLastUserAction(final ActionList actList) {
        super("undo");
        this.list = actList;
    }

    @Override
    public void perform() {
        list.undoLast();
    }
}
