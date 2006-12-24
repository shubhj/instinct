package au.id.adams.instinct.core.naming;

public final class AfterSpecificationNamingConvention implements NamingConvention {
    public String getPattern() {
        return "^tearDown.*";
    }
}
