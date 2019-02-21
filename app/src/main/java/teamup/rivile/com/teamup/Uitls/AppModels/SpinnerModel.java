package teamup.rivile.com.teamup.Uitls.AppModels;

import android.support.annotation.NonNull;

public class SpinnerModel {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public SpinnerModel() {
    }

    public SpinnerModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
