import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;

@DisplayName("ListNodeQueue 클래스")
public class ListNodeQueueTest {
    @Nested
    @DisplayName("add 메소드는")
    class Describe_add {
        @Nested
        @DisplayName("큐가 꽉 차있을 때 호출하면")
        class Context_with_queue_full {
            final int QUEUE_SIZE = 3;
            final int FIRST_DATA = 1;
            final int SECOND_DATA = 2;
            final int THIRD_DATA = 3;
            final int OVER_DATA = 4;
            ListNodeQueue queue;

            @BeforeEach
            void makeQueueStateFull() {
                queue = new ListNodeQueue(QUEUE_SIZE);
                queue.add(FIRST_DATA);
                queue.add(SECOND_DATA);
                queue.add(THIRD_DATA);
            }

            @Test
            @DisplayName("IllegalStateException이 발생한다")
            void it_throws_illegal_state_exception() {
                Assertions.assertThrows(IllegalStateException.class, () -> queue.add(OVER_DATA));
            }
        }

        @Nested
        @DisplayName("여러개의 데이터를 삽입한다면")
        class Context_with_insert_multiple_data {
            final int FIRST_DATA_VALUE = 1;
            final int SECOND_DATA_VALUE = 2;
            final int THIRD_DATA_VALUE = 3;
            final int QUEUE_SIZE = 3;
            ListNodeQueue queue;

            @BeforeEach
            void prepare() {
                queue = new ListNodeQueue(QUEUE_SIZE);
            }

            @Test
            @DisplayName("각각 참을 반환한다")
            void it_returns_true() {
                Assertions.assertAll(
                        () -> Assertions.assertTrue(queue.add(FIRST_DATA_VALUE)),
                        () -> Assertions.assertTrue(queue.add(SECOND_DATA_VALUE)),
                        () -> Assertions.assertTrue(queue.add(THIRD_DATA_VALUE))
                );
            }

            ListNode getNodeFromQueue(String dir) {
                try {
                    Field node = queue.getClass().getDeclaredField(dir);
                    node.setAccessible(true);
                    return (ListNode) node.get(queue);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return null;
            }

            void compareBeforeAfterToAdd(int data) {
                ListNode beforeFront = getNodeFromQueue("front");
                ListNode beforeRear = getNodeFromQueue("rear");
                queue.add(data);
                ListNode afterFront = getNodeFromQueue("front");
                ListNode afterRear = getNodeFromQueue("rear");
                Assertions.assertAll(
                        () -> Assertions.assertEquals(beforeFront, afterFront),
                        () -> Assertions.assertNotEquals(beforeRear, afterRear)
                );
            }

            @Test
            @DisplayName("front 노드는 변하지 않지만, rear 노드는 넣는 노드로 계속해서 변경된다")
            void it_changed_rear_node() {
                queue.add(FIRST_DATA_VALUE);
                Assertions.assertAll(
                        () -> compareBeforeAfterToAdd(SECOND_DATA_VALUE),
                        () -> compareBeforeAfterToAdd(THIRD_DATA_VALUE)
                );
            }
        }
    }

    @Nested
    @DisplayName("remove 메소드는")
    class Describe_remove {
        @Nested
        @DisplayName("큐가 비어있을 때 호출하면")
        class Context_with_queue_empty {
            @Test
            @DisplayName("NoSuchElementException가 발생한다")
            void it_throws_no_such_element_exception() {
                ListNodeQueue queue = new ListNodeQueue();
                Assertions.assertThrows(NoSuchElementException.class, queue::remove);
            }
        }

        @Nested
        @DisplayName("여러개의 데이터가 존재했을 때 호출한다면")
        class Context_with_exist_multiple_data {
            final int FIRST_DATA_VALUE = 1;
            final int SECOND_DATA_VALUE = 2;
            final int THIRD_DATA_VALUE = 3;
            ListNodeQueue queue;

            @BeforeEach
            void prepare() {
                queue = new ListNodeQueue();
                queue.add(FIRST_DATA_VALUE);
                queue.add(SECOND_DATA_VALUE);
                queue.add(THIRD_DATA_VALUE);
            }

            @Test
            @DisplayName("가장 앞에 있는 데이터를 반환한다")
            void it_returns_first_insert_data() {
                Assertions.assertAll(
                        () -> {
                            int removed = queue.remove();
                            Assertions.assertEquals(removed, FIRST_DATA_VALUE);
                        },
                        () -> {
                            int removed = queue.remove();
                            Assertions.assertEquals(removed, SECOND_DATA_VALUE);
                        },
                        () -> {
                            int removed = queue.remove();
                            Assertions.assertEquals(removed, THIRD_DATA_VALUE);
                        }
                );
            }

            ListNode getNodeFromQueue(String dir) {
                try {
                    Field node = queue.getClass().getDeclaredField(dir);
                    node.setAccessible(true);
                    return (ListNode) node.get(queue);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return null;
            }

            void compareBeforeAfterToRemove() {
                ListNode beforeFront = getNodeFromQueue("front");
                ListNode beforeRear = getNodeFromQueue("rear");
                queue.remove();
                ListNode afterFront = getNodeFromQueue("front");
                ListNode afterRear = getNodeFromQueue("rear");
                Assertions.assertAll(
                        () -> Assertions.assertNotEquals(beforeFront, afterFront),
                        () -> Assertions.assertEquals(beforeRear, afterRear)
                );
            }

            @Test
            @DisplayName("rear 노드는 그대로지만 front 노드는 계속해서 변한다")
            void it_increased_front_idx() {
                Assertions.assertAll(
                        this::compareBeforeAfterToRemove,
                        this::compareBeforeAfterToRemove
                );
            }
        }
    }

    @Nested
    @DisplayName("element 메소드는")
    class Describe_element {
        @Nested
        @DisplayName("큐가 비어있을 때 호출하면")
        class Context_with_queue_empty {
            @Test
            @DisplayName("NoSuchElementException가 발생한다")
            void it_throws_no_such_element_exception() {
                ListNodeQueue queue = new ListNodeQueue();
                Assertions.assertThrows(NoSuchElementException.class, queue::element);
            }
        }

        @Nested
        @DisplayName("여러개의 데이터가 존재할 때 호출하면")
        class Context_with_exist_multiple_data {
            final int FIRST_DATA_VALUE = 1;
            final int SECOND_DATA_VALUE = 2;
            final int THIRD_DATA_VALUE = 3;
            ListNodeQueue queue = new ListNodeQueue();

            void checkReturnsElementMethod(int data) {
                queue.add(data);
                Assertions.assertEquals(queue.element(), FIRST_DATA_VALUE);
            }

            @Test
            @DisplayName("중간에 삭제하지 않는 이상 가장 먼저 들어온 데이터를 반환한다")
            void it_returns_front_data_always_not_remove() {
                Assertions.assertAll(
                        () -> checkReturnsElementMethod(FIRST_DATA_VALUE),
                        () -> checkReturnsElementMethod(SECOND_DATA_VALUE),
                        () -> checkReturnsElementMethod(SECOND_DATA_VALUE)
                );
            }
        }
    }
}
