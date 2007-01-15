package com.googlecode.instinct.internal.aggregate.locate;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Dummy;
import com.googlecode.instinct.core.annotate.Specification;

@SuppressWarnings({"UnusedDeclaration", "PackageVisibleField", "FieldCanBeLocal", "ProtectedField"})
@BehaviourContext
public class WithRuntimeAnnotations {
    @Dummy
    private final String string1;
    @Dummy
    final String string2;
    @Dummy
    protected final String string3;
    @Dummy
    public final String string4;
    public final String string5;

    public WithRuntimeAnnotations(final String string1, final String string2, final String string3, final String string4, final String string5) {
        this.string1 = string1;
        this.string2 = string2;
        this.string3 = string3;
        this.string4 = string4;
        this.string5 = string5;
    }

    @Override
    @Specification
    public String toString() {
        return super.toString();
    }

    @Override
    @Specification
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }
}
