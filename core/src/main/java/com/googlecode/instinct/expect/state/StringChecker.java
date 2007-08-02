package com.googlecode.instinct.expect.state;

public interface StringChecker extends ComparableChecker<String> {
    void equalToIgnoringCase(String string);

    void equalToIgnoringWhiteSpace(String string);

    void notEqualToIgnoringCase(String string);

    void notEqualToIgnoringWhiteSpace(String string);

    void containsString(String string);

    void notContainString(String string);

    void endsWith(String string);

    void notEndingWith(String string);

    void startsWith(String string);

    void notStartingWith(String string);

    void isEmpty();

    void notEmpty();

    void hasLength(int length);
}
