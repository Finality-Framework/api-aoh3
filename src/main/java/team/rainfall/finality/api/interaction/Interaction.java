package team.rainfall.finality.api.interaction;
@FunctionalInterface
public interface Interaction<T> {
    T run(Object[] objects);
}
