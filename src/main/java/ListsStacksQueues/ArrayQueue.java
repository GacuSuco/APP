package ListsStacksQueues;

public class ArrayQueue<T> {
    public static final int DEFAULT_CAPACITY = 10;

    private T[] items;
    private int currentSize;
    private int front;
    private int back;

    public ArrayQueue (){
        this.items = (T[]) new Object[DEFAULT_CAPACITY];
        this.makeEmpty();
    }

    private void makeEmpty() {
        this.currentSize = 0;
        this.front = 0;
        this.back = -1;
    }

    public void enqueue(T x){
        if(currentSize == items.length){
            doubleQueue();
        }
        back = increment(back);
        items[back] = x;
        currentSize++;
    }

    private void doubleQueue() {
        T[] newArray;
        newArray = (T[]) new Object[items.length * 2];

        for (int i = 0; i < currentSize; i++, front = increment(front)) {
            newArray[i] = items[front];
        }
        items = newArray;
        front = 0;
        back = currentSize -1;
    }

    private int increment(int x) {
        if(++x == this.items.length){
            x = 0;
        }
        return x;
    }

    public T dequeue(){
        if(isEmpty()){
            throw new ArrayIndexOutOfBoundsException();
        }
        currentSize--;

        T returnValue = items[front];
        front = increment(front);
        return returnValue;
    }
    public T getFront(){
        if(isEmpty()){
            throw new ArrayIndexOutOfBoundsException();
        }
        return items[front];
    }

    public boolean isEmpty() {
        return this.currentSize == 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(T x : items)
            result.append(x).append(" ");
        return result.toString();
    }
}
