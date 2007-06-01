package com.googlecode.instinct.example.stack;

import java.util.ArrayList;
import java.util.List;
import com.googlecode.instinct.internal.util.Suggest;

public final class StackImpl<T> implements Stack<T> {
    private final List<T> objects = new ArrayList<T>();

    public boolean isEmpty() {
        return objects.isEmpty();
    }

    public void push(final T t) {
        objects.add(t);
    }

    @Suggest("Fix this.")
    public T pop() {
//        objects.remove(0);
        return objects.remove(0);
//        return null;
    }
}
