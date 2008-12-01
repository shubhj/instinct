package com.googlecode.instinct.runner;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Specification;
import java.util.Collection;
import java.util.HashSet;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class TheExitCodeEnum {

    @Specification
    public void mustNotContainMembersWithTheSamePrimitiveCode() {
        final Collection<Integer> codes = new HashSet<Integer>(ExitCode.values().length);
        for (final ExitCode exitCode : ExitCode.values()) {
            expect.that(codes.contains(exitCode.getCode())).isFalse();
            codes.add(exitCode.getCode());
        }
    }

    @Specification
    public void mustReturnNullWhenAskedForTheMemberWithAPrimitiveValueThatIsNotAssigned() {
        expect.that(ExitCode.fromPrimitive(Integer.MAX_VALUE)).isNull();
    }

    @Specification
    public void mustAlwaysAssignZeroToSuccess() {
        expect.that(ExitCode.fromPrimitive(0)).isEqualTo(ExitCode.SUCCESS);
    }

    @Specification
    public void mustReturnTheCorrectMemberForTheGivenPrimitiveCode() {
        for (final ExitCode exitCode : ExitCode.values()) {
            expect.that(ExitCode.fromPrimitive(exitCode.getCode())).isEqualTo(exitCode);
        }
    }
}
