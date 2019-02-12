package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import io.realm.RealmObject;

public class CapitalModelDataBase extends RealmObject {
    public int Id ;
    public String Name ;

    public CapitalModelDataBase() {
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
