import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

public class BinaryTree {
    public void bfs(Node node) {
        if(node == null) throw new NoSuchElementException("Not exist nodes..");
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(node);

        while(!queue.isEmpty()) {
            Node curNode = queue.remove();
            Node left = curNode.getLeft();
            Node right = curNode.getRight();
            System.out.print(curNode.getValue() + " ");
            if(left != null) queue.add(left);
            if(right != null) queue.add(right);
        }
        System.out.println();
    }

    public void dfs(Node node) {
        if(node == null) throw new NoSuchElementException("Not exist nodes..");
        Node left = node.getLeft();
        if(left != null) dfs(left);
        System.out.print(node.getValue() + " ");
        Node right = node.getRight();
        if(right != null) dfs(right);
    }
}
