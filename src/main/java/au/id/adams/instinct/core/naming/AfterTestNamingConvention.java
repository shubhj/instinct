package au.id.adams.instinct.core.naming;

public final class AfterTestNamingConvention implements NamingConvention {
    public String getPattern() {
        return "^tearDown.*";
    }
}
