package ListsStacksQueues;

import ListsStacksQueues.ListNodes.DoubleListNode;
import ListsStacksQueues.ListNodes.SingleListNode;

import java.util.NoSuchElementException;

public class DoubleLinkedList<T> {
    private DoubleListNode<T> header;

    public DoubleLinkedList() {
        header = new DoubleListNode<T>();
    }

    public boolean isEmpty() {
        return header.next == null;
    }

    public void makeEmpty() {
        header.next = null;
    }

    public int ListSize() {
        DoubleListNode<T> tmp = header.next;
        int size = 0;
        while (tmp.next != null) {
            size++;
            tmp = tmp.next;
        }
        return size;
    }

    public void addFirst(T value) {
        header.next = new DoubleListNode<T>(value,header, header.next);
    }

    public void removeFirst() {
        header.next = header.next.next;
        header.next.prev = header;
    }

    public void insert(int index, T value) {
        if (index == 0) {
            addFirst(value);
        } else {
            if (ListSize() < index) {
                throw new IndexOutOfBoundsException();
            }
            DoubleListNode<T> tmp = header.next;
            for (int i = 0; i < index - 1; i++) {
                tmp = tmp.next;
            }
            tmp.next = new DoubleListNode<T>(value,tmp, tmp.next);
        }
    }

    public void delete(int index) {
        if (index == 0) {
            removeFirst();
        } else {
            if (ListSize()  < index) {
                throw new IndexOutOfBoundsException();
            }
            DoubleListNode<T> tmp = header.next;
            for (int i = 0; i < index - 1; i++) {
                tmp = tmp.next;
            }
            tmp.next = tmp.next.next;
            tmp.next.prev = tmp;
        }

    }

    public T get(int index) {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            DoubleListNode<T> tmp = header.next;
            for (int i = 0; i < index; i++) {
                tmp = tmp.next;
            }
            if (tmp == null) {
                throw new IndexOutOfBoundsException();
            }
            return tmp.element;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        DoubleListNode<T> tmp = header.next;

        while (tmp != null) {
            result.append(tmp.element).append(" ");
            tmp = tmp.next;
        }
        return result.toString();
    }
}
