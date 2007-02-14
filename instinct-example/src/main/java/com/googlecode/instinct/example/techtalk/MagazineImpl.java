package com.googlecode.instinct.example.techtalk;

final class MagazineImpl implements Magazine {
    private final String title;

    MagazineImpl(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
