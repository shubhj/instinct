package com.googlecode.instinct.example.stack;

final class MagazineImpl implements Magazine {
    private final String title;

    MagazineImpl(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
