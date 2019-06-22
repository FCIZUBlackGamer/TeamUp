package teamup.rivile.com.teamup.network.APIModels;

public class CertificationModel {
    public int Id ;
    public int UserId ;
    public String Name ;
    public String Source ;
    public String Date ;
    public boolean Type ;

    public CertificationModel() {
    }

    public void setId(int id) {
        Id = id;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getId() {
        return Id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public String getSource() {
        return Source;
    }

    public void setType(boolean type) {
        Type = type;
    }

    public void setSource(String source) {
        Source = source;
    }

    public boolean isType() {
        return Type;
    }

    public void setDate(String dateOfGraduation) {
        Date = dateOfGraduation;
    }

    public String getDate() {
        return Date;
    }
}
