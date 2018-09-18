package ListsStacksQueues;

import java.util.Collection;

public class ArrayList<T> {
    private static int DEFAULT_SIZE = 10;

    private T[] items;
    private int size = 0;


    public ArrayList() {
        this.clear();
    }
    public ArrayList(Collection<? extends T> values) {
        this.clear();
        for (T obj : values) {
            this.add(obj);
        }
    }

    public boolean add(T value) {
        if (items.length == getSize()) {
            T[] old = items;
            items = (T[]) new Object[items.length * 2 + 1];
            System.arraycopy(old, 0, items, 0, size);
        }
        items[size++] = value;
        return true;
    }

    public T get(int index) {
        return items[index];
    }

    public void set(int index, T value) {
        this.items[index] = value;
    }

    private void clear() {
        this.size = 0;
        this.items = (T[]) new Object[DEFAULT_SIZE];
    }

    private int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(T x : items)
            result.append(x).append(" ");
        return result.toString();
    }
}
