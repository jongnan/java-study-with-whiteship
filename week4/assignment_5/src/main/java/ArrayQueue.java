import java.util.NoSuchElementException;
import java.util.Queue;

public class ArrayQueue {
    private int[] entries;
    private int maxSize;
    private int front;
    private int rear;

    ArrayQueue() {
        final int DEFAULT_QUEUE_SIZE = 11;
        this.entries = new int[DEFAULT_QUEUE_SIZE];
        this.maxSize = DEFAULT_QUEUE_SIZE;
        this.front = 0;
        this.rear = 0;
    }

    ArrayQueue(int size) {
        this.entries = new int[size + 1];
        this.maxSize = size + 1;
        this.front = 0;
        this.rear = 0;
    }

    public boolean add(int data) {
        if(isFull()) throw new IllegalStateException("Queue is full...");
        this.rear = (this.rear + 1) % this.maxSize;
        this.entries[this.rear] = data;
        return true;
    }

    public int remove() {
        if(isEmpty()) throw new NoSuchElementException("Queue is empty...");
        int removed = element();
        this.front = (this.front + 1) % this.maxSize;
        return removed;
    }

    public int element() {
        if(isEmpty()) throw new NoSuchElementException("Queue is empty...");
        return this.entries[(this.front + 1) % this.maxSize];
    }

    private boolean isFull() {
        return (this.rear + 1) % this.maxSize == this.front;
    }

    private boolean isEmpty() {
        return this.front == this.rear;
    }
}
