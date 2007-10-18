package com.googlecode.instinct.example.stack;

import java.util.ArrayList;
import java.util.List;

public final class StackImpl<T> implements Stack<T> {
    private final List<T> objects = new ArrayList<T>();

    public boolean isEmpty() {
        return objects.isEmpty();
    }

    public void push(final T t) {
        objects.add(t);
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop an empty stack");
        }
        return objects.remove(0);
    }
}
