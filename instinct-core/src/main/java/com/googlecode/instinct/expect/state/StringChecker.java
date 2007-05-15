package com.googlecode.instinct.expect.state;

public interface StringChecker extends ComparableChecker<String> {
    void equalsIgnoringCase(String string);

    void equalsIgnoringWhiteSpace(String string);

    void notEqualIgnoringCase(String string);

    void notEqualIgnoringWhiteSpace(String string);

    void containsString(String string);

    void notContainString(String string);

    void endsWith(String string);

    void notEndingWith(String string);

    void startsWith(String string);

    void notStartingWith(String string);

    void isEmpty();

    void hasLength(int length);
}
