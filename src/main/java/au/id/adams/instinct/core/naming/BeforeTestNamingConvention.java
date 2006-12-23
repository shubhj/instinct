package au.id.adams.instinct.core.naming;

public final class BeforeTestNamingConvention implements NamingConvention {
    public String getPattern() {
        return "^setUp.*";
    }
}
