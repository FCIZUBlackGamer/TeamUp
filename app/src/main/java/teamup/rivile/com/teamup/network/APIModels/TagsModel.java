package teamup.rivile.com.teamup.network.APIModels;

public class TagsModel {
    public int Id ;
    public int CategoryId;
    public String Name ;
    public int Counter ;

    public TagsModel() {
    }

    public void setId(int id) {
        Id = id;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getId() {
        return Id;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setCounter(int counter) {
        Counter = counter;
    }

    public int getCounter() {
        return Counter;
    }
}
