/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.internal.edge.org.hamcrest;

public interface StringFactory {
    String space();
    String space(int spaces);
    String newLine();
    String quoted(String value);
}
