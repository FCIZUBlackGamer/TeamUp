package teamup.rivile.com.teamup.Uitls.APIModels;

public class AttachmentModel {
    public int Id ;
    public Boolean Type;
    public String Name ;
    public String Source ;
    public Integer RequirmentId;

    public AttachmentModel() {
    }

    public AttachmentModel(String displayName, String url, Boolean type) {
        Name = displayName;
        Source = url;
        Type = type;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setRequirmentId(Integer requirmentId) {
        RequirmentId = requirmentId;
    }

    public void setSource(String source) {
        Source = source;
    }

    public void setType(boolean type) {
        Type = type;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public Integer getRequirmentId() {
        return RequirmentId;
    }

    public Boolean getType() {
        return Type;
    }

    public String getSource() {
        return Source;
    }
}
