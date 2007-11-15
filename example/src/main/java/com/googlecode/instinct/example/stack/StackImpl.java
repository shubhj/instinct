package com.googlecode.instinct.example.stack;

import java.util.ArrayList;
import java.util.List;

public final class StackImpl<T> implements Stack<T> {
    private final List<T> contents = new ArrayList<T>();

    public boolean isEmpty() {
        return contents.isEmpty();
    }

    public void push(final T t) {
        if (t == null) {
            throw new IllegalArgumentException();
        } else {
            contents.add(t);
        }
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop an empty stack");
        }
        return contents.remove(contents.size() - 1);
    }

    public T peek() {
        return contents.get(contents.size() - 1);
    }
}
