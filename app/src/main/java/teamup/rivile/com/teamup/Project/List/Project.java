package teamup.rivile.com.teamup.Project.List;

import java.util.List;

public class Project {
    int Id;
    int userId;
    String projectName;
    String location;
    String userImage;
    float userRate;
    int numLikes;
    int numContributers;
    List<String> contributersImages;

    public Project() {
    }

    public Project(int id, int userId, String projectName, String location, String userImage, float userRate, int numLikes, int numContributers, List<String> contributersImages) {
        Id = id;
        this.userId = userId;
        this.projectName = projectName;
        this.location = location;
        this.userImage = userImage;
        this.userRate = userRate;
        this.numLikes = numLikes;
        this.numContributers = numContributers;
        this.contributersImages = contributersImages;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContributersImages(List<String> contributersImages) {
        this.contributersImages = contributersImages;
    }

    public void setNumContributers(int numContributers) {
        this.numContributers = numContributers;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public void setUserRate(float userRate) {
        this.userRate = userRate;
    }

    public int getId() {
        return Id;
    }

    public String getLocation() {
        return location;
    }

    public float getUserRate() {
        return userRate;
    }

    public int getNumContributers() {
        return numContributers;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public int getUserId() {
        return userId;
    }

    public List<String> getContributersImages() {
        return contributersImages;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getUserImage() {
        return userImage;
    }
}

