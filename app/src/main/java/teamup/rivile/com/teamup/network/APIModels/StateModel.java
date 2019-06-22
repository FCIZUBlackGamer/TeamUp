package teamup.rivile.com.teamup.network.APIModels;

public class StateModel {
    public Integer Id ;
    public String Name ;

    public StateModel() {
    }

    public String getName() {
        return Name;
    }

    public Integer getId() {
        return Id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
