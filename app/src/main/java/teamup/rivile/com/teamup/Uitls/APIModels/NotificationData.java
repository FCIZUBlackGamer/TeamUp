package teamup.rivile.com.teamup.Uitls.APIModels;

public class NotificationData {
    private int targetUserId;
    private int notificationType;

    private String userName;
    private String projectName;

    public NotificationData() {
    }

    public NotificationData(int targetUserId, int notificationType, String userName, String projectName) {
        this.targetUserId = targetUserId;
        this.notificationType = notificationType;
        this.userName = userName;
        this.projectName = projectName;
    }

    public int getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(int targetUserId) {
        this.targetUserId = targetUserId;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
