package com.googlecode.instinct.example.stack;

public interface Stack<T> {
    boolean isEmpty();

    void push(T t);

    T pop();

    T peek();
}
