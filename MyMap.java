import java.util.Set;

public interface MyMap<K, V> {
    void clear();
    boolean containsKey(K key);
    boolean containsValue(V value);
    V get(K key);
    boolean isEmpty();
    Set<K> keySet();
    V put(K key, V value);
    void remove(K key);
    int size();
    Set<V> values();
}
