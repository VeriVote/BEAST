package edu.pse.beast.parametereditor;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

import org.junit.Test;

import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;

/**
 * The tests for TimeoutValueHandler.
 *
 * @author Jonas Wohnig
 */
public class TimeoutValueHandlerTest {
    /** Test string "s". */
    private static final String S = "s";
    /** Test string "m". */
    private static final String M = "m";
    /** Test string "h". */
    private static final String H = "h";
    /** Test string "d". */
    private static final String D = "d";

    /**
     * Test of class TimeoutValueHandler testing both input and output at different
     * configurations.
     */
    @Test
    public void testTimeout() {
        System.out.println("set/getTimeout");
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
        model.addElement(S);
        model.addElement(M);
        model.addElement(H);
        model.addElement(D);
        model.addElement("faultyEntry");
        JComboBox<String> comboBox = new JComboBox<String>();
        JSpinner spinner = new JSpinner();
        comboBox.setModel(model);
        TimeoutValueHandler handler = new TimeoutValueHandler(spinner, comboBox);
        handler.setReacts(true);
        spinner.setValue(1);
        comboBox.setSelectedIndex(2);
        spinner.setValue(0);
        comboBox.setSelectedIndex(0);
        handler.setValue(handler.getTimeout());
        TimeOut expResult = new TimeOut(TimeUnit.SECONDS, 0);
        TimeOut result = handler.getTimeout();
        final boolean minTest =
                expResult.getDuration() == result.getDuration()
                && expResult.getOrigUnit().equals(result.getOrigUnit()) && !result.isActive();
        spinner.setValue(3653);
        comboBox.setSelectedIndex(3);
        handler.setValue(handler.getTimeout());
        expResult = new TimeOut(TimeUnit.DAYS, 3653);
        result = handler.getTimeout();
        final boolean maxTest =
                expResult.getDuration() == result.getDuration()
                && expResult.getOrigUnit().equals(result.getOrigUnit()) && result.isActive();
        spinner.setValue(-1);
        handler.setValue(handler.getTimeout());
        expResult = new TimeOut(TimeUnit.DAYS, 0);
        result = handler.getTimeout();
        final boolean falseValueTest =
                expResult.getDuration() == result.getDuration()
                && expResult.getOrigUnit().equals(result.getOrigUnit()) && !result.isActive();
        spinner.setValue(42);
        comboBox.setSelectedIndex(4);
        handler.setValue(handler.getTimeout());
        expResult = new TimeOut(TimeUnit.SECONDS, 0);
        result = handler.getTimeout();
        // System.out.println(handler.hasChanged() + " max " +
        // handler.getTimeout().getDuration() + " " +
        // handler.getTimeout().getOrigUnit());
        // System.out.println(handler.hasChanged() + " max " + expResult.getDuration() +
        // " " + expResult.getOrigUnit());
        boolean falseUnitTest = expResult.getDuration() == result.getDuration()
                && expResult.getOrigUnit().equals(result.getOrigUnit()) && !result.isActive();
        assertTrue(minTest && maxTest && falseValueTest && falseUnitTest);
    }

    /**
     * Test of class TimeoutValueHandler's ability to toggle whether it reacts and
     * the log of whether it changed.
     */
    @Test
    public void testReactsAndHasChanged() {
        System.out.println("ReactsAndHasChanged");
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
        model.addElement(S);
        model.addElement(M);
        model.addElement(H);
        model.addElement(D);
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setModel(model);
        JSpinner spinner = new JSpinner();
        TimeoutValueHandler handler = new TimeoutValueHandler(spinner, comboBox);
        final boolean hasChangedBeforeTest = handler.hasChanged();
        handler.setReacts(true);
        spinner.setValue(11);
        comboBox.setSelectedIndex(2);
        handler.setValue(handler.getTimeout());
        final boolean hasChangedAfterInput = handler.hasChanged();
        handler.setHasChanged(false);
        handler.setReacts(false);
        spinner.setValue(27);
        comboBox.setSelectedIndex(1);
        boolean hasChangedAfterStopReact = handler.hasChanged();
        assertTrue(!hasChangedBeforeTest && hasChangedAfterInput && !hasChangedAfterStopReact);
    }

}
