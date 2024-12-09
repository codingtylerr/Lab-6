import java.util.*;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private LinkedList<Entry<K, V>>[] table;
    private int size = 0;
    private float loadFactor;

    static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Entry) {
                Entry<?, ?> e = (Entry<?, ?>) o;
                return Objects.equals(key, e.key) && Objects.equals(value, e.value);
            }
            return false;
        }
    }

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity, float loadFactor) {
        this.table = new LinkedList[initialCapacity];
        this.loadFactor = loadFactor;
    }

    @Override
    public void clear() {
        size = 0;
        table = new LinkedList[table.length];
    }

    @Override
    public boolean containsKey(K key) {
        int index = hash(key.hashCode());
        if (table[index] != null) {
            for (Entry<K, V> entry : table[index]) {
                if (entry.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    if (entry.value.equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int index = hash(key.hashCode());
        if (table[index] != null) {
            for (Entry<K, V> entry : table[index]) {
                if (entry.key.equals(key)) {
                    return entry.value;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    set.add(entry.key);
                }
            }
        }
        return set;
    }

    @Override
    public V put(K key, V value) {
        if (size >= table.length * loadFactor) {
            rehash();
        }
        int index = hash(key.hashCode());
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }
        table[index].add(new Entry<>(key, value));
        size++;
        return null;
    }

    @Override
    public void remove(K key) {
        int index = hash(key.hashCode());
        if (table[index] != null) {
            table[index].removeIf(entry -> entry.key.equals(key));
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<V> values() {
        Set<V> set = new HashSet<>();
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    set.add(entry.value);
                }
            }
        }
        return set;
    }

    private int hash(int hashCode) {
        return Math.abs(hashCode) % table.length;
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        Set<Entry<K, V>> entries = new HashSet<>();
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                entries.addAll(bucket);
            }
        }
        table = new LinkedList[table.length * 2];
        size = 0;
        for (Entry<K, V> entry : entries) {
            put(entry.key, entry.value);
        }
    }
}
