package com.googlecode.instinct.example.techtalk;

import java.util.ArrayList;
import java.util.List;

public final class StackImpl implements Stack {
    private final List<Object> objects = new ArrayList<Object>();

    public boolean isEmpty() {
        return objects.isEmpty();
    }

    public void push(final Object o) {
        objects.add(o);
    }

    public Object pop() {
        return null;
    }
}
