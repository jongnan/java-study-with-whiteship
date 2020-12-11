import java.util.NoSuchElementException;

public class ListNodeQueue {
    private ListNode front;
    private ListNode rear;
    private final int maxSize;
    private int size;

    ListNodeQueue() {
        final int DEFAULT_QUEUE_SIZE = 10;
        this.front = null;
        this.rear = null;
        this.maxSize = DEFAULT_QUEUE_SIZE;
        this.size = 0;
    }

    ListNodeQueue(int maxSize) {
        this.front = null;
        this.rear = null;
        this.maxSize = maxSize;
        this.size = 0;
    }

    public boolean add(int data) {
        if(isFull()) throw new IllegalStateException("Queue is full...");
        ListNode addedNode = new ListNode(data);
        if(this.front == null) {
            this.front = addedNode;
        }else {
            this.rear.add(this.rear, addedNode, 1);
        }
        this.rear = addedNode;
        this.size++;
        return true;
    }

    public int remove() {
        if(isEmpty()) throw new NoSuchElementException("Queue is empty...");
        ListNode removedNode = this.front;
        if(this.front == this.rear) {
            this.front = null;
            this.rear = null;
            this.size = 0;
            return removedNode.getData();
        }
        this.front = removedNode.getNext();
        this.size--;
        return removedNode.getData();
    }

    public int element() {
        if(isEmpty()) throw new NoSuchElementException("Queue is empty...");
        return this.front.getData();
    }

    private boolean isFull() {
        return this.size == this.maxSize;
    }

    private boolean isEmpty() {
        return front == null;
    }
}
