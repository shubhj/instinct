package au.id.adams.instinct.core.naming;

public final class BeforeSpecificationNamingConvention implements NamingConvention {
    public String getPattern() {
        return "^setUp.*";
    }
}
