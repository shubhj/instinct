package au.id.adams.instinct.test;

import au.net.netstorm.boost.nursery.reflect.checker.DefaultConstructorEmptyStringChecker;

public final class ConstructorTestChecker {
        

    public void test() {
        new DefaultConstructorEmptyStringChecker().checkPublicConstructorsRejectEmptyString();
    }
}
