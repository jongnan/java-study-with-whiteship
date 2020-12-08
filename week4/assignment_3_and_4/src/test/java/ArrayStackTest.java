import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ArrayStack 클래스")
public class ArrayStackTest {
    @Nested
    @DisplayName("생성자는")
    class Describe_constructor {

        private int getMaxSizeFromStack(ArrayStack stack) {
            try{
                Field maxSize = stack.getClass().getDeclaredField("maxSize");
                maxSize.setAccessible(true);
                return (int) maxSize.get(stack);
            }catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return -1;
        }

        @Nested
        @DisplayName("아무 인자를 주지 않았다면")
        class Context_with_no_param {
            @Test
            @DisplayName("크기가 10인 스택이 생성된다")
            void it_created_stack_size_ten() {
                final int DEFAULT_STACK_SIZE = 10;
                ArrayStack stack = new ArrayStack();
                int maxSize = getMaxSizeFromStack(stack);
                assertEquals(maxSize, DEFAULT_STACK_SIZE);
            }
        }

        @Nested
        @DisplayName("인자를 주었다면")
        class Context_with_param {
            @Test
            @DisplayName("준 인자 크기의 스택이 생성된다")
            void it_created_stack_size_ten() {
                final int MAX_STACK_SIZE = 20;
                ArrayStack stack = new ArrayStack(MAX_STACK_SIZE);
                int maxSize = getMaxSizeFromStack(stack);
                assertEquals(maxSize, MAX_STACK_SIZE);
            }
        }
    }

    @Nested
    @DisplayName("push 메소드는")
    class Describe_push {

        @Nested
        @DisplayName("스택이 꽉 찬 상태라면")
        class Context_with_stack_full {
            ArrayStack stack;

            @BeforeEach
            void prepare() {
                final int STACK_SIZE = 5;
                stack = new ArrayStack(STACK_SIZE);
                for(int i = 1; i <= STACK_SIZE; i++) {
                    stack.push(i);
                }
            }

            @Test
            @DisplayName("RuntimeException이 발생한다")
            void it_occurs_runtime_exception() {
                final int RANDOM_DATA = 10;
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
            ArrayStack stack;

            @BeforeAll
            void prepare() {
                stack = new ArrayStack();
                stack.push(FIRST_DATA);
                stack.push(SECOND_DATA);
                stack.push(THIRD_DATA);
            }

            @Test
            @DisplayName("최상위 데이터는 세번째 데이터와 같다")
            void it_top_data_same_third_data() {
                try {
                    Field store = stack.getClass().getDeclaredField("store");
                    store.setAccessible(true);
                    Field size = stack.getClass().getDeclaredField("size");
                    size.setAccessible(true);
                    int topData = ((int[]) store.get(stack))[(int) size.get(stack) - 1];
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
                ArrayStack stack = new ArrayStack();
                assertThrows(RuntimeException.class, stack::pop);
            }
        }

        @Nested
        @DisplayName("3개의 데이터가 존재할 때")
        class Context_with_exist_three_data {
            final int FIRST_DATA = 3;
            final int SECOND_DATA = 10;
            final int THIRD_DATA = -20;
            ArrayStack stack;

            @BeforeEach
            void prepare() {
                stack = new ArrayStack();
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
                    Field store = stack.getClass().getDeclaredField("store");
                    store.setAccessible(true);
                    Field size = stack.getClass().getDeclaredField("size");
                    size.setAccessible(true);
                    int topData = ((int[])store.get(stack))[(int)size.get(stack) - 1];
                    assertEquals(topData,SECOND_DATA);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
