package com.googlecode.instinct.internal.lang;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.test.InstinctTestCase;

// NOTE: Currently doesn't handle graphs, so recursive loops will kill it.

public final class IndentingToStringMasterAtomicTest extends InstinctTestCase {
    public void testToString() {
        checkToString("TestNoField[]", new TestNoField());
        checkToString(singleField(9), new TestSingleField(9));
        checkToString(twoFieldsResult(2, 4, 0), new TestTwoFields(2, 4));
        checkToString(twoFieldsResult(5, 7, 0), new TestTwoFields(5, 7));
    }

    public void testToStringWithNullFields() {
        final String expected = "TestNullFields[" + lfIndent("nullos=null") + lfIndent("oNullo=null") + lf("]");
        checkToString(expected, new TestNullFields());
    }

    public void testToStringMultiple() {
        checkToString(nestedFieldResult(), new TestNestedFields("Andy", new TestFixed()));
        checkToString(nullFieldResult(), new TestNullField());
        checkToString(stringArrayResult("A", "B"), new TestStringArrayField(new String[]{"A", "B"}));
        checkToString(stringArrayResult("C", "D"), new TestStringArrayField(new String[]{"C", "D"}));
        checkToString(intArrayResult(), new TestIntArrayField(new int[]{1, 2, 4}));
        checkToString(mixedArrayResult(), new TestMixedArrayField(new long[]{1L, 54L}, new String[]{"A", "Z"}));
        checkToString(multipleNestedFieldResult(), new TestMultipleNestedFields("multiple", new TestPreformattedTwoFields(2, 4)));
    }

    private String singleField(final int i) {
        return "TestSingleField[" + lfIndent("value=" + Integer.toString(i)) + lf("]");
    }

    private String nestedFieldResult() {
        return "TestNestedFields[" + lfIndent("name=Andy") + lfIndent("nested=FIXED") + lf("]");
    }

    private String nullFieldResult() {
        return "TestNullField[" + lfIndent("field=null") + lf("]");
    }

    private String multipleNestedFieldResult() {
        return "TestMultipleNestedFields[" + lfIndent("name=multiple") + lfIndent("two=") + twoFieldsResult(2, 4, 1) + lf("]");
    }

    private String mixedArrayResult() {
        return "TestMixedArrayField[" + lfIndent("longs={1,54}") + lfIndent("strings={A,Z}") + lf("]");
    }

    private String intArrayResult() {
        return "TestIntArrayField[" + lfIndent("ints={1,2,4}") + lf("]");
    }

    private String stringArrayResult(final String x, final String y) {
        return "TestStringArrayField[" + lfIndent("strings={" + x + "," + y + "}") + lf("]");
    }

    private String twoFieldsResult(final int x, final int y, final int depth) {
        return "TestTwoFields[" + lfIndent("x=" + x, depth + 1) + lfIndent("y=" + y, depth + 1) + lfIndent("]", depth);
    }

    private String lf(final String content) {
        return System.getProperty("line.separator") + content;
    }

    private String lfIndent(final String content) {
        return lfIndent(content, 1);
    }

    private String lfIndent(final String content, final int level) {
        return lf(indent(content, level));
    }

    private String indent(final String content, final int level) {
        String prefix = "";
        for (int i = 0; i < level; i++) {
            prefix += "    ";
        }
        return prefix + content;
    }

    private void checkToString(final String expected, final Object ref) {
        final ToStringMaster indentingToStringMaster = new IndentingToStringMaster();
        expect.that(indentingToStringMaster.getString(ref)).isEqualTo(expected);
    }
}
