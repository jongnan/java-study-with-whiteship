import org.kohsuke.github.GHIssue;

import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        LiveStudyDashboard liveStudyDashboard = new LiveStudyDashboard();
        liveStudyDashboard.getUserParticipationRate();
    }
}
