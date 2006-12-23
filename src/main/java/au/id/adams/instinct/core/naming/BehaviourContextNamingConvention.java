package au.id.adams.instinct.core.naming;

public final class BehaviourContextNamingConvention implements NamingConvention {
    public String getPattern() {
        return ".*BehaviourContext$";
    }
}
