package ru.andreymarkelov.atlas.plugins;

public class Pair<V, E> implements Comparable<E> {
    private V key;

    private E value;

    public Pair(V key, E value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(E o) {
        if (o == null && value == null) return 0;
        if (o == null) return 1;
        if (value == null) return -1;

        return value.toString().compareTo(o.toString());
    }

    public V getKey() {
        return key;
    }

    public E getValue() {
        return value;
    }

    public void setKey(V key) {
        this.key = key;
    }

    public void setValue(E value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Pair[key=" + key + ", value=" + value + "]";
    }
}
