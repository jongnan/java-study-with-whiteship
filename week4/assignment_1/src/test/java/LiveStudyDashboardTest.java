import org.junit.jupiter.api.*;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueState;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Live study 대시보드")
public class LiveStudyDashboardTest {

    static LiveStudyDashboard liveStudyDashboard = new LiveStudyDashboard();

    @Nested
    @DisplayName("출석 체크 메소드는")
    class Describe_take_attendance {
        @Test
        @DisplayName("유저마다 출석 횟수를 리턴한다")
        void it_returns_a_user_attendance_count() {
            try {
                Method takeAttendance = liveStudyDashboard.getClass().getDeclaredMethod("takeAttendance");
                takeAttendance.setAccessible(true);
                Map<String, Integer> attendanceInfo = (Map<String, Integer>) takeAttendance.invoke(liveStudyDashboard);
                assertEquals(attendanceInfo.get("jongnan"), 3);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("과제 진행 여부 메소드는")
    class Describe_is_ongoing_assignment {

        List<GHIssue> issues;
        Method isOngoingAssignment;

        Boolean check_ongoing_assignment(int week) {
            try {
                return (boolean) isOngoingAssignment.invoke(liveStudyDashboard, issues.get(week).getLabels());
            } catch (IllegalAccessException
                    | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }

        @BeforeAll
        void prepare() {
            try {
                this.issues = liveStudyDashboard.liveStudyRepo.getIssues(GHIssueState.ALL);
                this.isOngoingAssignment = liveStudyDashboard.getClass().getDeclaredMethod("isOngoingAssignment", Collection.class);
                this.isOngoingAssignment.setAccessible(true);
            }catch (IOException |  NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        @Nested
        @DisplayName("1주차라면")
        class Context_with_first_week {
            final int FIRST_WEEK_IDX = 17;
            @Test
            @DisplayName("참을 리턴한다")
            void it_return_true() {
                assertTrue(check_ongoing_assignment(FIRST_WEEK_IDX));
            }
        }


        @Nested
        @DisplayName("2주차라면")
        class Context_with_second_week {
            final int SECOND_WEEK_IDX = 16;
            @Test
            @DisplayName("참을 리턴한다")
            void it_return_true() {
                assertTrue(check_ongoing_assignment(SECOND_WEEK_IDX));
            }
        }

        @Nested
        @DisplayName("18주차라면")
        class Context_with_18th_week {
            final int EIGHTEENTH_WEEK_IDX = 0;
            @Test
            @DisplayName("거짓을 리턴한다")
            void it_return_false() {
                assertFalse(check_ongoing_assignment(EIGHTEENTH_WEEK_IDX));
            }
        }
    }
}
