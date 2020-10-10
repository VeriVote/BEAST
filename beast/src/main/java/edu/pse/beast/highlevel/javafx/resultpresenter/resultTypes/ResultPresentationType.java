package edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;

import edu.pse.beast.highlevel.javafx.AnalysisType;
import edu.pse.beast.highlevel.javafx.resultpresenter.ResultPresenterNEW;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueSingle;

/**
 * The Class ResultPresentationType.
 *
 * @author Lukas Stapelbroek
 */
public abstract class ResultPresentationType {

    /** The Constant STANDARD_SIZE. */
    static final int STANDARD_SIZE = 10;

    /** The menu item. */
    private MenuItem menuItem;

    /**
     * Present result.
     *
     * @param result
     *            the result
     * @return the node
     */
    public abstract Node presentResult(Result result);

    /**
     * Gets the name.
     *
     * @return the name
     */
    public abstract String getName();

    /**
     * Gets the tool tip description.
     *
     * @return the tool tip description
     */
    public abstract String getToolTipDescription();

    /**
     * Supports zoom.
     *
     * @return true, if the Node given by this Type support zooming
     */
    public abstract boolean supportsZoom();

    /**
     * Gets the implementations.
     *
     * @return all implementations of this class
     */
    public static List<ResultPresentationType> getImplementations() {
        final ServiceLoader<ResultPresentationType> loader =
                ServiceLoader.load(ResultPresentationType.class);
        final List<ResultPresentationType> implementations =
                new ArrayList<ResultPresentationType>();
        for (Iterator<ResultPresentationType> iterator = loader.iterator();
                iterator.hasNext();) {
            final ResultPresentationType implementation = iterator.next();
            implementations.add(implementation);
        }
        return implementations;
    }

    /**
     * Gets the menu item.
     *
     * @return the menu item
     */
    public MenuItem getMenuItem() {
        if (menuItem != null) {
            return menuItem;
        } else {
            final CustomMenuItem item = new CustomMenuItem(new Label(getName()));
            final Tooltip tip = new Tooltip(getToolTipDescription());
            Tooltip.install(item.getContent(), tip);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent event) {
                    ResultPresenterNEW.getInstance()
                            .setPresentationType(ResultPresentationType.this);
                }
            });
            this.menuItem = item;
            return item;
        }
    }

    /**
     * Zoom to.
     *
     * @param zoomValue
     *            the zoom value
     */
    public abstract void zoomTo(double zoomValue);

    /**
     * Supports.
     *
     * @param analysisType
     *            the analysis type
     * @return true, if successful
     */
    public abstract boolean supports(AnalysisType analysisType);

    /**
     * Extracts the value of single value objects. It is used to extract the
     * size of each vote.
     *
     * @param toExtract
     *            has be of the form: ValueWrapper -> {@link ResultValueSingle}
     * @return the all sizes
     */
    public Map<Integer, Long> getAllSizes(final List<ResultValueWrapper> toExtract) {
        final Map<Integer, Long> toReturn = new HashMap<Integer, Long>();
        Iterator<ResultValueWrapper> emptyIterator =
                new LinkedList<ResultValueWrapper>().iterator();
        for (final Iterator<ResultValueWrapper>
                iterator = toExtract != null ? toExtract.iterator() : emptyIterator;
             iterator.hasNext();) {
            final ResultValueWrapper currentWrapper = iterator.next();
            final int index = currentWrapper.getMainIndex();
            final CBMCResultValueSingle singleValue =
                    (CBMCResultValueSingle) currentWrapper.getResultValue();
            toReturn.put(index, (Long) singleValue.getNumberValue());
        }
        return toReturn;
    }

    /**
     * Indicates if this class should be normally used first to display a
     * result.
     *
     * @return true, if is default
     */
    public abstract boolean isDefault();
}
