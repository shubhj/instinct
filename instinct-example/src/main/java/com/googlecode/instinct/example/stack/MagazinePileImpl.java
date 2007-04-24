package com.googlecode.instinct.example.stack;

final class MagazinePileImpl implements MagazinePile {
    private final Stack<Magazine> stack;

    MagazinePileImpl(final Stack<Magazine> stack) {
        this.stack = stack;
    }

    public void addToPile(final Magazine magazine) {
        stack.push(magazine);
    }
}
