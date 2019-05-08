package teamup.rivile.com.teamup.Uitls.APIModels;

public class RefuseReason {
    private Integer Id;
    private String Name;

    public RefuseReason() {
    }

    public RefuseReason(int id, String name) {
        Id = id;
        Name = name;
    }

    public RefuseReason(String name) {
        Name = name;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
