import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;


@DisplayName("ArrayQueue 클래스")
public class ArrayQueueTest {

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
            ArrayQueue queue;

            @BeforeEach
            void makeQueueStateFull() {
                queue = new ArrayQueue(QUEUE_SIZE);
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
            final int INITIAL_INDEX = 0;
            ArrayQueue queue;

            @BeforeEach
            void prepare() {
                queue = new ArrayQueue(QUEUE_SIZE);
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

            int getIndexFromQueue(String dir) {
                try {
                    Field idx = queue.getClass().getDeclaredField(dir);
                    idx.setAccessible(true);
                    return (int) idx.get(queue);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return -1;
            }

            @Test
            @DisplayName("front 인덱스는 그대로지만 rear 인덱스는 증가한다")
            void it_increased_rear_idx() {
                Assertions.assertAll(
                        () -> {
                            int frontIdx = getIndexFromQueue("front");
                            int rearIdx = getIndexFromQueue("rear");
                            Assertions.assertAll(
                                    () -> Assertions.assertEquals(frontIdx, INITIAL_INDEX),
                                    () -> Assertions.assertEquals(rearIdx, INITIAL_INDEX)
                            );
                        },
                        () -> {
                            queue.add(FIRST_DATA_VALUE);
                            int frontIdx = getIndexFromQueue("front");
                            int rearIdx = getIndexFromQueue("rear");
                            Assertions.assertAll(
                                    () -> Assertions.assertEquals(frontIdx, INITIAL_INDEX),
                                    () -> Assertions.assertEquals(rearIdx, INITIAL_INDEX + 1)
                            );
                        },
                        () -> {
                            queue.add(SECOND_DATA_VALUE);
                            int frontIdx = getIndexFromQueue("front");
                            int rearIdx = getIndexFromQueue("rear");
                            Assertions.assertAll(
                                    () -> Assertions.assertEquals(frontIdx, INITIAL_INDEX),
                                    () -> Assertions.assertEquals(rearIdx, INITIAL_INDEX + 2)
                            );
                        }
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
                ArrayQueue queue = new ArrayQueue();
                Assertions.assertThrows(NoSuchElementException.class, queue::remove);
            }
        }

        @Nested
        @DisplayName("여러개의 데이터가 존재했을 때 호출한다면")
        class Context_with_exist_multiple_data {
            final int FIRST_DATA_VALUE = 1;
            final int SECOND_DATA_VALUE = 2;
            final int THIRD_DATA_VALUE = 3;
            final int INITIAL_FRONT_INDEX = 0;
            final int INITIAL_REAR_INDEX = 3;
            ArrayQueue queue;

            @BeforeEach
            void prepare() {
                queue = new ArrayQueue();
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
                        }
                );
            }

            int getIndexFromQueue(String dir) {
                try {
                    Field idx = queue.getClass().getDeclaredField(dir);
                    idx.setAccessible(true);
                    return (int) idx.get(queue);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return -1;
            }

            @Test
            @DisplayName("rear 인덱스는 그대로지만 front 인덱스는 증가한다")
            void it_increased_front_idx() {
                Assertions.assertAll(
                        () -> {
                            queue.remove();
                            int frontIdx = getIndexFromQueue("front");
                            int rearIdx = getIndexFromQueue("rear");
                            Assertions.assertAll(
                                    () -> Assertions.assertEquals(frontIdx, INITIAL_FRONT_INDEX + 1),
                                    () -> Assertions.assertEquals(rearIdx, INITIAL_REAR_INDEX)
                            );
                        },
                        () -> {
                            queue.remove();
                            int frontIdx = getIndexFromQueue("front");
                            int rearIdx = getIndexFromQueue("rear");
                            Assertions.assertAll(
                                    () -> Assertions.assertEquals(frontIdx, INITIAL_FRONT_INDEX + 2),
                                    () -> Assertions.assertEquals(rearIdx, INITIAL_REAR_INDEX)
                            );
                        }
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
                ArrayQueue queue = new ArrayQueue();
                Assertions.assertThrows(NoSuchElementException.class, queue::element);
            }
        }

        @Nested
        @DisplayName("여러개의 데이터가 존재할 때 호출하면")
        class Context_with_exist_multiple_data {
            final int FIRST_DATA_VALUE = 1;
            final int SECOND_DATA_VALUE = 2;
            final int THIRD_DATA_VALUE = 3;

            @Test
            @DisplayName("중간에 삭제하지 않는 이상 가장 먼저 들어온 데이터를 반환한다")
            void it_returns_front_data_always_not_remove() {
                ArrayQueue queue = new ArrayQueue();
                Assertions.assertAll(
                        () -> {
                            queue.add(FIRST_DATA_VALUE);
                            Assertions.assertEquals(queue.element(), FIRST_DATA_VALUE);
                        },
                        () -> {
                            queue.add(SECOND_DATA_VALUE);
                            Assertions.assertEquals(queue.element(), FIRST_DATA_VALUE);
                        },
                        () -> {
                            queue.add(THIRD_DATA_VALUE);
                            Assertions.assertEquals(queue.element(), FIRST_DATA_VALUE);
                        }
                );
            }
        }
    }
}
