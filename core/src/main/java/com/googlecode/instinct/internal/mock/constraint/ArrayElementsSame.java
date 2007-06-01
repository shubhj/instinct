package com.googlecode.instinct.internal.mock.constraint;

import java.util.Arrays;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.core.Constraint;

@Suggest("Move to internal.expect.constraint.")
public final class ArrayElementsSame implements Constraint {
    private Object[] expectedArray;

    public ArrayElementsSame(final Object[] expectedArray) {
        checkNotNull(expectedArray);
        this.expectedArray = expectedArray;
    }

    @Suggest("Might need to come up with a better exception to throw on mock failure, a jMock one?")
    public boolean eval(final Object o) {
        checkNotNull(o);
        if (!(o instanceof Object[])) {
            throw new RuntimeException("I can only compare arrays!");
        }

        return checkArrayElements((Object[]) o);
    }

    public StringBuffer describeTo(final StringBuffer buffer) {
        checkNotNull(buffer);
        return buffer.append("sameElements(").append(Arrays.toString(expectedArray)).append(')');
    }

    private boolean checkArrayElements(final Object[] actualArray) {
        if (actualArray.length != expectedArray.length) {
            return false;
        }
        for (int i = 0; i < actualArray.length; i++) {
            if (actualArray[i] != expectedArray[i]) {
                return false;
            }
        }
        return true;
    }
}
