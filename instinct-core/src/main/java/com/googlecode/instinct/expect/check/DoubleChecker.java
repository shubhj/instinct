package com.googlecode.instinct.expect.check;

public interface DoubleChecker extends ComparableChecker<Double> {
    void closeTo(double value, double delta);

    void notCloseTo(double value, double delta);
}
