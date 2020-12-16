import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;

@DisplayName("BinaryTree 클래스")
public class BinaryTreeTest {
    @Nested
    @DisplayName("bfs 메소드는")
    class DescribeBfs {
        @Nested
        @DisplayName("아무런 노드가 존재하지 않았을 때")
        class ContextWithNotExistNodes {
            @Test
            @DisplayName("NoSuchElementException을 발생시킨다")
            void itThrowsNoSuchElementException() {
                BinaryTree binaryTree = new BinaryTree();
                Assertions.assertThrows(NoSuchElementException.class, () -> binaryTree.bfs(null));
            }
        }

        @Nested
        @DisplayName("각 케이스의 이진 트리가 주어졌을 때")
        class ContextWithSpecificBinaryTree {

            private void testBfsSearch(String result, Node root) throws Exception {
                BinaryTree binaryTree = new BinaryTree();
                String outputBfs = tapSystemOut(() -> binaryTree.bfs(root));
                Assertions.assertEquals(result, outputBfs);
            }

            private List<Node> createNodeList() {
                List<Node> nodes = new ArrayList<>();
                for(int i = 1; i <= 5; i++) {
                    nodes.add(new Node(i));
                }
                return nodes;
            }

            private Node setBinaryTreeCase1() {
                List<Node> nodeList = createNodeList();
                nodeList.get(0).setLeft(nodeList.get(1));
                nodeList.get(0).setRight(nodeList.get(2));
                nodeList.get(1).setLeft(nodeList.get(3));
                nodeList.get(2).setRight(nodeList.get(4));
                return nodeList.get(0);
            }

            private Node setBinaryTreeCase2() {
                List<Node> nodeList = createNodeList();
                nodeList.get(4).setLeft(nodeList.get(1));
                nodeList.get(1).setLeft(nodeList.get(0));
                nodeList.get(1).setRight(nodeList.get(2));
                nodeList.get(2).setRight(nodeList.get(3));
                return nodeList.get(4);
            }

            @Test
            @DisplayName("너비를 우선으로 탐색을 한다")
            void itSearchWidthFirst() {
                Assertions.assertAll(
                        () -> {
                            final String CASE_1_BFS_RESULT = "1 2 3 4 5 \n";
                            testBfsSearch(CASE_1_BFS_RESULT, setBinaryTreeCase1());
                        },
                        () -> {
                            final String CASE_2_BFS_RESULT = "5 2 1 3 4 \n";
                            testBfsSearch(CASE_2_BFS_RESULT, setBinaryTreeCase2());
                        }
                );
            }
        }
    }

    @Nested
    @DisplayName("dfs 메소드는")
    class DescribeDfs {
        @Nested
        @DisplayName("아무런 노드가 존재하지 않았을 때")
        class ContextWithNotExistNodes {
            @Test
            @DisplayName("NoSuchElementException을 발생시킨다")
            void itThrowsNoSuchElementException() {
                BinaryTree binaryTree = new BinaryTree();
                Assertions.assertThrows(NoSuchElementException.class, () -> binaryTree.dfs(null));
            }
        }

        @Nested
        @DisplayName("각 케이스의 이진 트리가 주어졌을 때")
        class ContextWithSpecificBinaryTree {

            private void testDfsSearch(String result, Node root) throws Exception {
                BinaryTree binaryTree = new BinaryTree();
                String outputDfs = tapSystemOut(() -> {
                    binaryTree.dfs(root);
                    System.out.println();
                });
                Assertions.assertEquals(result, outputDfs);
            }

            private List<Node> createNodeList() {
                List<Node> nodes = new ArrayList<>();
                for(int i = 1; i <= 5; i++) {
                    nodes.add(new Node(i));
                }
                return nodes;
            }

            private Node setBinaryTreeCase1() {
                List<Node> nodeList = createNodeList();
                nodeList.get(0).setLeft(nodeList.get(1));
                nodeList.get(0).setRight(nodeList.get(2));
                nodeList.get(1).setLeft(nodeList.get(3));
                nodeList.get(2).setRight(nodeList.get(4));
                return nodeList.get(0);
            }

            private Node setBinaryTreeCase2() {
                List<Node> nodeList = createNodeList();
                nodeList.get(4).setLeft(nodeList.get(1));
                nodeList.get(1).setLeft(nodeList.get(0));
                nodeList.get(1).setRight(nodeList.get(2));
                nodeList.get(2).setRight(nodeList.get(3));
                return nodeList.get(4);
            }

            @Test
            @DisplayName("너비를 우선으로 탐색을 한다")
            void itSearchWidthFirst() {
                Assertions.assertAll(
                        () -> {
                            final String CASE_1_BFS_RESULT = "4 2 1 3 5 \n";
                            testDfsSearch(CASE_1_BFS_RESULT, setBinaryTreeCase1());
                        },
                        () -> {
                            final String CASE_2_BFS_RESULT = "1 2 3 4 5 \n";
                            testDfsSearch(CASE_2_BFS_RESULT, setBinaryTreeCase2());
                        }
                );
            }
        }
    }
}
