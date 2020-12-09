public class Pair<T, S> {
    T first;
    S second;

    private Pair(T first, S second) {
        this.first = first;
        this.second = second;
    }

    static <T, S> Pair<T, S> add(T first, S second) {
        return new Pair<>(first, second);
    }
}
