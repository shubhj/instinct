package com.googlecode.instinct.integrate.idea.ui.actions;

import com.intellij.util.config.AbstractProperty;
import com.googlecode.instinct.integrate.idea.ui.TestNGResults;
import com.googlecode.instinct.integrate.idea.model.TestNGConsoleProperties;
import com.googlecode.instinct.integrate.idea.model.TestNGPropertyListener;
import com.googlecode.instinct.integrate.idea.model.TestFilter;

public class TestNGActions
{
    public static void installFilterAction(final TestNGResults results)
    {
        final TestNGConsoleProperties properties = results.getConsoleProperties();
        TestNGPropertyListener junitpropertylistener = new TestNGPropertyListener() {

            public void onChanged(Object obj)
            {
                boolean flag = TestNGConsoleProperties.HIDE_PASSED_TESTS.value(properties);
                results.getTreeStructure().setFilter(flag ? TestFilter.NOT_PASSED : TestFilter.NO_FILTER);
                results.rebuildTree();
            }
        };
        addPropertyListener(TestNGConsoleProperties.HIDE_PASSED_TESTS, junitpropertylistener, results, true);
    }

    public static void addPropertyListener(AbstractProperty property, TestNGPropertyListener listener, TestNGResults results, boolean sendImmediately)
    {
        TestNGConsoleProperties props = results.getConsoleProperties();
        if(sendImmediately)
            props.addListenerAndSendValue(property, listener);
        else
            props.addListener(property, listener);
    }
}
