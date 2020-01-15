package edu.pse.beast.toolbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueArray;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueSingle;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;

public final class CBMCResultPresentationHelper {
    private CBMCResultPresentationHelper() { }

    private static String getWhiteSpaces(final int amount) {
        char[] spaces = new char[amount];
        Arrays.fill(spaces, ' ');
        return new String(spaces);
    }

    public static String printSingleElement(final CBMCResultValueSingle single,
                                            final int offset) {
        return getWhiteSpaces(offset) + single.getValue() + "\n";
    }

    public static String printOneDimResult(final CBMCResultValueArray array,
                                           final long size, final int offset) {
        String toReturn = getWhiteSpaces(offset);
        List<CBMCResultValueWrapper> arrayValues = array.getValues();

        final long s;
        if (size < 0) {
            s = arrayValues.size();
        } else {
            s = size;
        }

        for (int i = 0; i < s; i++) {
            CBMCResultValueSingle singleValue =
                    (CBMCResultValueSingle) arrayValues.get(i).getResultValue();
            toReturn = toReturn + singleValue.getValue() + " ";
        }
        return toReturn + "\n";
    }

    public static List<String> printTwoDimResult(final CBMCResultValueArray array,
                                                 final long size, final int offset) {
        List<String> toReturn = new ArrayList<String>();
        List<CBMCResultValueWrapper> arrayValues = array.getValues();

        final long s;
        if (size < 0) {
            s = arrayValues.size();
        } else {
            s = size;
        }

        for (int i = 0; i < s; i++) {
            CBMCResultValueArray currentArray =
                    (CBMCResultValueArray) arrayValues.get(i).getResultValue();
            toReturn.add(printOneDimResult(currentArray, -1, offset));
        }
        return toReturn;
    }
}
