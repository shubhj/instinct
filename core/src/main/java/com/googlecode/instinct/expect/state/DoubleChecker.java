package com.googlecode.instinct.expect.state;

public interface DoubleChecker extends ComparableChecker<Double> {
    void closeTo(double value, double delta);

    void notCloseTo(double value, double delta);
}
