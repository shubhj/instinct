package com.googlecode.instinct.expect.check;

import org.hamcrest.Matcher;
import org.w3c.dom.Node;

public interface NodeChecker<T extends Node> extends ObjectChecker<T> {
    void hasXPath(String path, Matcher<String> matcher);

    void hasXPath(String path);

    void notHaveXPath(String path, Matcher<String> matcher);

    void notHaveXPath(String path);
}
