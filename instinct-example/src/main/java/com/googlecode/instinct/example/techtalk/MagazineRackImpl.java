package com.googlecode.instinct.example.techtalk;

final class MagazineRackImpl implements MagazineRack {
    private final Stack<Magazine> stack;

    MagazineRackImpl(final Stack<Magazine> stack) {
        this.stack = stack;
    }

    public void addToPile(final Magazine magazine) {
        stack.push(magazine);
    }
}
