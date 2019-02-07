package teamup.rivile.com.teamup.Uitls.APIModels;

public class AttachmentModel {
    public int Id ;
    public int Type ;
    public String Name ;
    public String Source ;
    public Integer RequirmentId;

    public AttachmentModel() {
    }

    public AttachmentModel(String displayName, String url) {
        Name = displayName;
        Source = url;
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

    public void setType(int type) {
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

    public int getType() {
        return Type;
    }

    public String getSource() {
        return Source;
    }
}
