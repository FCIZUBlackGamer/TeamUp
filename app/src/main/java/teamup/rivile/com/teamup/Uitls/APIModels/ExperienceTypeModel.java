package teamup.rivile.com.teamup.Uitls.APIModels;

public class ExperienceTypeModel {
    public int Id ;
    public String Name ;

    public ExperienceTypeModel() {
    }

    public String getName() {
        return Name;
    }

    public int getId() {
        return Id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setId(int id) {
        Id = id;
    }
}
