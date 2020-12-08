import java.util.Stack;

public class ArrayStack {
    private final int[] store;
    private final int maxSize;
    private int size;

    ArrayStack() {
        final int DEFAULT_SIZE = 10;
        this.store = new int[DEFAULT_SIZE];
        this.maxSize = DEFAULT_SIZE;
        this.size = 0;
    }

    ArrayStack(int maxSize) {
        this.store = new int[maxSize];
        this.maxSize = maxSize;
        this.size = 0;
    }

    public void push(int data) {
        if (isFull()) {
            throw new RuntimeException("Stack is full...");
        }
        this.store[this.size++] = data;
    }

    public int pop() {
        if(isEmpty()) {
            throw new RuntimeException("Stack is Empty...");
        }
        return this.store[--this.size];
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return maxSize == size;
    }
}
