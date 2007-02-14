package com.googlecode.instinct.example.techtalk;

public interface Stack<T> {
    boolean isEmpty();

    void push(T t);

    T pop();
}
