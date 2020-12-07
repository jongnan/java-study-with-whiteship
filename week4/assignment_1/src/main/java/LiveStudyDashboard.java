import org.kohsuke.github.*;

import java.util.*;

public class LiveStudyDashboard {
    final static String TOKEN_PROP_PATH = System.getProperty("user.dir") + "/src/main/resources/token.properties";
    final static String REPO_OWNER_ID = "whiteship";
    final static String REPO_NAME = "live-study";
    final static int WEEK_NUMBER = 18;

    GitHub gitHub;
    GHRepository liveStudyRepo;

    LiveStudyDashboard() {
        try {
            this.gitHub = GitHubBuilder.fromPropertyFile(TOKEN_PROP_PATH).build();
            this.liveStudyRepo = gitHub.getRepository(REPO_OWNER_ID + "/" + REPO_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, Integer> takeAttendance() {
        final int INITIAL_COUNT_NUM = 1;

        Map<String, Integer> attendanceInfo = new HashMap<>();
        try{
            long s = System.nanoTime();
            List<GHIssue> issueList = liveStudyRepo.getIssues(GHIssueState.ALL);
            for(GHIssue issue : issueList) {
                if(!isOngoingAssignment(issue.getLabels())) continue;

                Set<String> curIssueUserList = new HashSet<>();

                for(GHIssueComment comment : issue.getComments()) {
                    String userId = comment.getUser().getLogin();
                    if(userId.equals(REPO_OWNER_ID)) continue;
                    if(curIssueUserList.contains(userId)) continue;
                    curIssueUserList.add(userId);

                    if(attendanceInfo.containsKey(userId)) {
                        attendanceInfo.put(userId, attendanceInfo.get(userId) + 1);
                        continue;
                    }
                    attendanceInfo.put(userId, INITIAL_COUNT_NUM);
                }
            }
            System.out.println((System.nanoTime() - s) / Math.pow(10, 9));
            return attendanceInfo;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isOngoingAssignment(Collection<GHLabel> labels) {
        final String DRAFT = "draft";

        for(GHLabel label : labels) {
            if(label.getName().equals(DRAFT)) {
                return false;
            }
        }
        return true;
    }

    public void getUserParticipationRate() {
        final int ONE_HUNDRED = 100;
        final String PARTICIPANTS_KOR = "참여자";
        final String PARTICIPANT_RATE_KOR = "참여율";

        Map<String, Integer> attendanceInfo = takeAttendance();
        System.out.printf("%-18s %-6s\n", PARTICIPANTS_KOR, PARTICIPANT_RATE_KOR);
        attendanceInfo.forEach((k, v) -> {
            double rate = ((double)v / WEEK_NUMBER ) * ONE_HUNDRED;
            System.out.printf("%-18s %6.2f%%\n",k, rate);
        });
    }
}
