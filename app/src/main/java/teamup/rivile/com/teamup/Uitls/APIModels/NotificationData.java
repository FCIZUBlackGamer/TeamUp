package teamup.rivile.com.teamup.Uitls.APIModels;

public class NotificationData {
    private int targetUserId;
    private int notificationType;
    private int projectId;
    private String userName;
    private String projectName;

    public NotificationData(int targetUserId, int notificationType, int projectId, String userName, String projectName) {
        this.targetUserId = targetUserId;
        this.notificationType = notificationType;
        this.projectId = projectId;
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

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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
