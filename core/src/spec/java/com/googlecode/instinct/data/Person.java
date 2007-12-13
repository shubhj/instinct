/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.data;

public class Person {

    private String name;
    private double height;

    public Person(final String name, final double height) {
        this.height = height;
        this.name = name;
    }

    public Person(final String name) {
        this.name = name;
        height = 6.2;
    }

    public String getName() {
        return name;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Person[ name=" + name + ", height=" + height + " ]";
    }
}
