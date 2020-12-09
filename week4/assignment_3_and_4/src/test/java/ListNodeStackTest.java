import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ListNodeStack 클래스")
public class ListNodeStackTest {

    @Nested
    @DisplayName("생성자는")
    class Describe_constructor {
        final int DEFAULT_STACK_SIZE = 10;
        final int RANDOM_STACK_SIZE = 5;

        private int getMaxSizeFromStack(ListNodeStack listNodeStack) {
            try {
                Field maxSize = listNodeStack.getClass().getDeclaredField("maxSize");
                maxSize.setAccessible(true);
                return (int) maxSize.get(listNodeStack);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return -1;
        }

        @Nested
        @DisplayName("인자를 아무것도 주지 않았을 시에")
        class Context_with_no_param {
            @Test
            @DisplayName("최대 크기가 10인 스택이 생성된다")
            void it_created_max_size_ten() {
                ListNodeStack listNodeStack = new ListNodeStack();
                int maxSize = getMaxSizeFromStack(listNodeStack);
                assertEquals(maxSize, DEFAULT_STACK_SIZE);
            }
        }

        @Nested
        @DisplayName("인자를 주었을 시에")
        class Context_with_param {
            @Test
            @DisplayName("받은 인자만큼 크기의 스택이 생성된다")
            void it_created_max_size_param() {
                ListNodeStack listNodeStack = new ListNodeStack(RANDOM_STACK_SIZE);
                int maxSize = getMaxSizeFromStack(listNodeStack);
                assertEquals(maxSize, RANDOM_STACK_SIZE);
            }
        }
    }

    @Nested
    @DisplayName("push 메소드는")
    class Describe_push {
        @Nested
        @DisplayName("스택이 꽉 찬 상태라면")
        class Context_with_stack_full {
            final int STACK_SIZE = 5;
            final int RANDOM_DATA = 10;
            ListNodeStack stack;

            @BeforeEach
            void prepare() {
                stack = new ListNodeStack(STACK_SIZE);
                for(int i = 1; i <= STACK_SIZE; i++) {
                    stack.push(i);
                }
            }

            @Test
            @DisplayName("RuntimeException이 발생한다")
            void it_occurs_runtime_exception() {
                assertThrows(RuntimeException.class, () -> stack.push(RANDOM_DATA));
            }
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        @DisplayName("3개의 데이터가 들어갔을 때")
        class Context_with_three_data_push {
            final int FIRST_DATA = 3;
            final int SECOND_DATA = 10;
            final int THIRD_DATA = -20;
            final int STACK_SIZE = 3;
            ListNodeStack stack;

            @BeforeAll
            void prepare() {
                stack = new ListNodeStack();
                stack.push(FIRST_DATA);
                stack.push(SECOND_DATA);
                stack.push(THIRD_DATA);
            }

            @Test
            @DisplayName("최상위 데이터는 세번째 데이터와 같다")
            void it_top_data_same_third_data() {
                try {
                    Field head = stack.getClass().getDeclaredField("head");
                    head.setAccessible(true);
                    Field size = stack.getClass().getDeclaredField("size");
                    size.setAccessible(true);

                    ListNode cur = (ListNode) head.get(stack);
                    while(cur.getNext() != null) {
                        cur = cur.getNext();
                    }
                    int topData = cur.getData();
                    assertEquals(topData, THIRD_DATA);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            @Test
            @DisplayName("스택 크기는 3이다")
            void it_stack_size_three() {
                try {
                    Field size = stack.getClass().getDeclaredField("size");
                    size.setAccessible(true);
                    assertEquals(size.get(stack), STACK_SIZE);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Nested
    @DisplayName("pop 메소드는")
    class Describe_pop {
        @Nested
        @DisplayName("스택이 비워져 있을 때")
        class Context_with_stack_empty {
            @Test
            @DisplayName("RuntimeException이 발생한다")
            void it_occurs_runtime_exception() {
                ListNodeStack stack = new ListNodeStack();
                assertThrows(RuntimeException.class, stack::pop);
            }
        }

        @Nested
        @DisplayName("3개의 데이터가 존재할 때")
        class Context_with_exist_three_data {
            final int FIRST_DATA = 3;
            final int SECOND_DATA = 10;
            final int THIRD_DATA = -20;
            ListNodeStack stack;

            @BeforeEach
            void prepare() {
                stack = new ListNodeStack();
                stack.push(FIRST_DATA);
                stack.push(SECOND_DATA);
                stack.push(THIRD_DATA);
            }

            @Test
            @DisplayName("리턴 값과 마지막으로 넣은 데이터가 같다")
            void it_returns_same_last_push_data() {
                int popped = stack.pop();
                assertEquals(popped, THIRD_DATA);
            }

            @Test
            @DisplayName("최상위 데이터는 두번째로 넣은 값과 같다")
            void it_top_data_same_second_data() {
                stack.pop();
                try {
                    Field head = stack.getClass().getDeclaredField("head");
                    head.setAccessible(true);
                    Field size = stack.getClass().getDeclaredField("size");
                    size.setAccessible(true);

                    ListNode cur = (ListNode) head.get(stack);
                    while(cur.getNext() != null) {
                        cur = cur.getNext();
                    }
                    int topData = cur.getData();
                    assertEquals(topData,SECOND_DATA);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
