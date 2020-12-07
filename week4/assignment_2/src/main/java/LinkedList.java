public class LinkedList {
    private ListNode head;
    private int size;

    LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public ListNode add(ListNode nodeToAdd, int position) {
        this.size++;
        if(validatePositionRange(position)) {
            printPositionErr();
            return null;
        }

        ListNode pre = null, cur = this.head;
        while(--position > 0) {
            pre = cur;
            cur = cur.getNext();
        }

        if(pre == null) {
            nodeToAdd.setNext(cur);
            this.head = nodeToAdd;
            return this.head;
        }

        pre.setNext(nodeToAdd);
        nodeToAdd.setNext(cur);
        return nodeToAdd;
    }

    public ListNode remove(int positionToRemove) {
        if(validatePositionRange(positionToRemove)) {
            printPositionErr();
            return null;
        }

        ListNode pre = null, cur = this.head;
        while(--positionToRemove > 0) {
            pre = cur;
            cur = cur.getNext();
        }

        this.size--;

        if(pre == null) {
            this.head = cur.getNext();
            return cur;
        }

        pre.setNext(cur.getNext());
        return cur;
    }

    public boolean contains(ListNode nodeToCheck) {
        ListNode cur = this.head;
        while(cur != null) {
            if(cur == nodeToCheck) {
                return true;
            }
            cur = cur.getNext();
        }
        return false;
    }

    public void print() {
        ListNode cur = this.head;
        while(cur != null) {
            System.out.println(cur.getData());
            cur = cur.getNext();
        }
    }

    private boolean validatePositionRange(int position) {
        return position <= 0 || position > this.size;
    }

    private void printPositionErr() {
        System.out.println("Position parameter is wrong...");
        System.out.println("Position is greater than 0 and less than or equal to " + this.size);
    }
}
