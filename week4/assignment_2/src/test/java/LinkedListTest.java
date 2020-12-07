import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LinkedList 구현")
public class LinkedListTest {

    @Nested
    @DisplayName("add 메소드는")
    class Describe_add{
        @Nested
        @DisplayName("잘못된 position이 주어졌다면")
        class Context_with_invalid_position {
            final int FIRST_NODE_VALUE = 10;
            final int INVALID_POSITION_INDEX = -1;

            @Test
            @DisplayName("null을 리턴한다")
            void it_returns_null() {
                LinkedList linkedList = new LinkedList();
                ListNode addedNode = linkedList.add(new ListNode(FIRST_NODE_VALUE), INVALID_POSITION_INDEX);
                assertNull(addedNode);
            }
        }

        @Nested
        @DisplayName("유효한 position이 주어졌다면")
        class Context_with_valid_position {

            @Nested
            @DisplayName("맨 앞의 경우")
            class Sub_context_with_front {
                final int FIRST_NODE_VALUE = 10;
                final int FIRST_POSITION_INDEX = 1;

                @Test
                @DisplayName("리턴값이 head와 같다")
                void it_returns_list_node_same_head() {
                    try {
                        LinkedList linkedList = new LinkedList();
                        ListNode frontNode = linkedList.add(new ListNode(FIRST_NODE_VALUE), FIRST_POSITION_INDEX);
                        Field headField = linkedList.getClass().getDeclaredField("head");
                        headField.setAccessible(true);
                        assertEquals(frontNode, headField.get(linkedList));
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Nested
            @TestInstance(TestInstance.Lifecycle.PER_CLASS)
            @DisplayName("첫번째 노드와 두번째 노드 사이에 넣을 경우")
            class Sub_context_with_between_first_second {
                final int FIRST_NODE_VALUE = 3;
                final int SECOND_NODE_VALUE = 1;
                final int BETWEEN_NODE_VALUE = 10;
                final int FIRST_POSITION_INDEX = 1;
                final int SECOND_POSITION_INDEX = 2;

                LinkedList linkedList;
                ListNode first, second, between, head;

                @BeforeAll
                void prepare_add_test() {
                    try {
                        linkedList = new LinkedList();
                        first = linkedList.add(new ListNode(FIRST_NODE_VALUE), FIRST_POSITION_INDEX);
                        second = linkedList.add(new ListNode(SECOND_NODE_VALUE), SECOND_POSITION_INDEX);
                        between = linkedList.add(new ListNode(BETWEEN_NODE_VALUE), SECOND_POSITION_INDEX);

                        Field headField = linkedList.getClass().getDeclaredField("head");
                        headField.setAccessible(true);
                        head = (ListNode) headField.get(linkedList);

                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }

                @Test
                @DisplayName("리턴 값이 head의 다음 노드와 같다")
                void it_returns_same_head_next() {
                    assertAll(
                            () -> assertEquals(head, first),
                            () -> assertNotEquals(second, head.getNext()),
                            () -> assertEquals(between, head.getNext())
                    );
                }

                @Test
                @DisplayName("리턴 값의 다음 노드는 두번째 삽입한 노드와 같다")
                void it_returns_same_second_add_node() {
                    assertEquals(between.getNext(), second);
                }
            }
        }
    }

    @Nested
    @DisplayName("remove 메소드는")
    class Describe_remove {
        @Nested
        @DisplayName("잘못된 position이 주어졌다면")
        class Context_with_invalid_position {
            final int FIRST_NODE_VALUE = 10;
            final int INVALID_POSITION_INDEX = -1;

            @Test
            @DisplayName("null을 리턴한다")
            void it_returns_null() {
                LinkedList linkedList = new LinkedList();
                ListNode addedNode = linkedList.add(new ListNode(FIRST_NODE_VALUE), INVALID_POSITION_INDEX);
                assertNull(addedNode);
            }
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        @DisplayName("유효한 position이 주어졌다면")
        class Context_with_valid_position {
            final int FIRST_NODE_VALUE = 3;
            final int SECOND_NODE_VALUE = 1;
            final int THIRD_NODE_VALUE = 10;
            final int FIRST_POSITION_INDEX = 1;
            final int SECOND_POSITION_INDEX = 2;
            final int THIRD_POSITION_INDEX = 3;

           @Nested
            @TestInstance(TestInstance.Lifecycle.PER_CLASS)
            @DisplayName("두개의 노드가 존재하고 맨 앞을 삭제했을 경우")
            class Sub_context_with_front_remove {

               ListNode first, second, removed, head;

                @BeforeAll
                void prepare_remove_front_node() {
                    try {
                        LinkedList linkedList = new LinkedList();
                        first = linkedList.add(new ListNode(FIRST_NODE_VALUE), FIRST_POSITION_INDEX);
                        second = linkedList.add(new ListNode(SECOND_NODE_VALUE), SECOND_POSITION_INDEX);
                        removed = linkedList.remove(FIRST_POSITION_INDEX);

                        Field headField = linkedList.getClass().getDeclaredField("head");
                        headField.setAccessible(true);
                        head = (ListNode) headField.get(linkedList);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }

                @Test
                @DisplayName("리턴 값은 첫번째 삽입한 노드와 같다")
                void it_returns_same_first_add_node() {
                    assertEquals(first, removed);
                }

                @Test
                @DisplayName("두번째 삽입한 노드와 head와 같다")
                void it_second_add_node_same_head() {
                    assertEquals(second, head);
                }
            }

            @Nested
            @TestInstance(TestInstance.Lifecycle.PER_CLASS)
            @DisplayName("세개의 노드가 존재하고 중간을 삭제했을 경우")
            class Sub_context_with_between_first_to_second_remove {

                ListNode first, second, third, removed;

                @BeforeAll
                void prepare_remove_between_node() {
                    LinkedList linkedList = new LinkedList();
                    first = linkedList.add(new ListNode(FIRST_NODE_VALUE), FIRST_POSITION_INDEX);
                    second = linkedList.add(new ListNode(SECOND_NODE_VALUE), SECOND_POSITION_INDEX);
                    third = linkedList.add(new ListNode(THIRD_NODE_VALUE), THIRD_POSITION_INDEX);
                    removed = linkedList.remove(SECOND_POSITION_INDEX);
                }

                @Test
                @DisplayName("리턴 값은 두번째 삽입 노드와 같다")
                void it_return_second_node() {
                    assertEquals(removed, second);
                }

                @Test
                @DisplayName("리턴(삭제된) 값은 더 이상 다음 노드를 가지지 않는다")
                void it_returns_remove_next_node() {
                    assertNotEquals(removed.getNext(), third);
                }

                @Test
                @DisplayName("첫번째 삽입 노드의 다음은 세번째 삽입 노드와 같다")
                void it_first_node_same_second_node() {
                    assertAll(
                            () -> assertEquals(first.getNext(), third),
                            () -> assertNotEquals(first.getNext(), second)
                    );
                }
            }
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("contains 메소드는")
    class Describe_contains {
        final int FIRST_NODE_VALUE = 3;
        final int SECOND_NODE_VALUE = 1;
        final int THIRD_NODE_VALUE = 10;
        final int NOT_CONTAINS_NODE_VALUE = -3;
        final int FIRST_POSITION_INDEX = 1;
        final int SECOND_POSITION_INDEX = 2;
        final int THIRD_POSITION_INDEX = 3;

        LinkedList linkedList;
        ListNode first, second, third, contains, notContains;

        @BeforeAll
        void prepare_contains_test() {
            linkedList = new LinkedList();
            first = linkedList.add(new ListNode(FIRST_NODE_VALUE), FIRST_POSITION_INDEX);
            second = linkedList.add(new ListNode(SECOND_NODE_VALUE), SECOND_POSITION_INDEX);
            third = linkedList.add(new ListNode(THIRD_NODE_VALUE), THIRD_POSITION_INDEX);
            contains = first;
            notContains = new ListNode(NOT_CONTAINS_NODE_VALUE);
        }

        @Nested
        @DisplayName("연결 리스트에 존재하는 노드가 주어질 경우")
        class Context_with_contains {
            @Test
            @DisplayName("참을 리턴한다")
            void it_returns_true() {
                boolean isContains = linkedList.contains(contains);
                assertTrue(isContains);
            }
        }

        @Nested
        @DisplayName("연결 리스트에 존재하지 않는 노드가 주어질 경우")
        class Context_with_not_contains {
            @Test
            @DisplayName("거짓을 리턴한다")
            void it_returns_false() {
                boolean isNotContains = linkedList.contains(notContains);
                assertFalse(isNotContains);
            }
        }
    }
}
