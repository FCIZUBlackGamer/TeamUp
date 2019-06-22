package teamup.rivile.com.teamup.network.APIModels;

import java.io.Serializable;

public class Department implements Serializable {
    int Id;
    String Name;
    String Icon;
//
//
//    public Department(int id, String Icon, String name) {
//        Id = id;
//        this.Icon = Icon;
//        Name = name;
//    }
//
    public Department(int id, String name) {
        Id = id;
        Name = name;
    }

    public Department() {

    }

    public void setName(String name) {
        Name = name;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public String getIcon() {
        return Icon;
    }

    public String getName() {
        return Name;
    }
}
