public class ListNode {
    private int data;
    private ListNode next;

    ListNode(int data) {
        this.data = data;
    }

    public int getData() {
        return this.data;
    }

    public ListNode getNext() {
        return this.next;
    }

    public void setNext(ListNode nextNode) {
        this.next = nextNode;
    }
}
