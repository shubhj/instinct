package au.id.adams.instinct.internal.runner;

public interface BehaviourContextRunner {
    <T> void run(final Class<T> behaviourContextClass);
}

