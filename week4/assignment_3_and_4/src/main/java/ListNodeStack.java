public class ListNodeStack {
    private ListNode head;
    private int maxSize;
    private int size;

    ListNodeStack() {
        this.head = null;
        this.maxSize = 10;
        this.size = 0;
    }

    ListNodeStack(int maxSize) {
        this.head = null;
        this.maxSize = maxSize;
        this.size = 0;
    }

    public void push(int data) {
        if(isFull()) throw new RuntimeException("Stack is full...");
        ListNode added = new ListNode(data);
        if(this.head == null) {
            this.head = added;
        }else {
            this.head.add(this.head, added, this.size);
        }
        this.size++;
    }

    public int pop() {
        if(isEmpty()) throw new RuntimeException("Stack is Empty...");
        int removed;
        if(this.size == 1) {
            removed = this.head.getData();
            this.head = null;
            this.size--;
        }else {
            removed = this.head.remove(this.head, --this.size).getData();
        }
        return removed;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return maxSize == size;
    }
}
