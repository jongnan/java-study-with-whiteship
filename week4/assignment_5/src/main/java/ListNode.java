public class ListNode {
    final static int POSITIVE_NUM_STAND = 0;

    private int data;
    private ListNode next;

    ListNode(int data) {
        this.data = data;
        this.next = null;
    }

    public int getData() {
        return this.data;
    }

    public ListNode getNext() {
        return this.next;
    }

    public ListNode add(ListNode head, ListNode nodeToAdd, int position) {
        Pair<ListNode, ListNode> foundNodes = findListNodePosition(head, position);
        ListNode pre = foundNodes.first, cur = foundNodes.second;

        pre.next = nodeToAdd;
        nodeToAdd.next = cur;
        return nodeToAdd;
    }

    public ListNode remove(ListNode head, int positionToRemove) {
        Pair<ListNode, ListNode> foundNodes = findListNodePosition(head, positionToRemove);
        ListNode pre = foundNodes.first, cur = foundNodes.second;

        pre.next = cur.next;
        cur.next = null;
        return cur;
    }

    public boolean contains(ListNode head, ListNode nodeToCheck) {
        ListNode cur = head;
        while(cur != null) {
            if(cur == nodeToCheck) return true;
            cur = cur.next;
        }
        return false;
    }

    private Pair<ListNode, ListNode> findListNodePosition(ListNode head, int position) {
        if(position <= POSITIVE_NUM_STAND) throw new RuntimeException("Position is positive number");

        ListNode pre = null, cur = head;
        while(position-- > 0) {
            try {
                pre = cur;
                cur = cur.next;
            } catch (NullPointerException e) {
                throw new RuntimeException("Position too big");
            }
        }
        return Pair.add(pre, cur);
    }
}

