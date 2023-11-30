package capstone.schedulemanager.model;

public class UserTracker {

    int userId;
    String userName;
    String userReportTime;
    String userReportType;

    public UserTracker(int userId, String userName, String userReportTime, String userReportType) {
        this.userId = userId;
        this.userName = userName;
        this.userReportTime = userReportTime;
        this.userReportType = userReportType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserReportTime() {
        return userReportTime;
    }

    public void setUserReportTime(String userReportTime) {
        this.userReportTime = userReportTime;
    }

    public String getUserReportType() {
        return userReportType;
    }

    public void setUserReportType(String userReportType) {
        this.userReportType = userReportType;
    }
}
