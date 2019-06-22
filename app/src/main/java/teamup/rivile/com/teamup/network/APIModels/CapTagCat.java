package teamup.rivile.com.teamup.network.APIModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CapTagCat {
    List<StateModel> Category;
    List<TagsModel> Tags;
    @SerializedName("State")
    List<StateModel> Capital;
    List<ExperienceTypeModel> ExperienceType;

    public CapTagCat(List<StateModel> category, List<TagsModel> tags, List<StateModel> capital, List<ExperienceTypeModel> experienceType) {
        Category = category;
        Tags = tags;
        Capital = capital;
        ExperienceType = experienceType;
    }

    public CapTagCat() {
    }

    public List<StateModel> getCategory() {
        return Category;
    }

    public void setCategory(List<StateModel> category) {
        Category = category;
    }

    public List<TagsModel> getTags() {
        return Tags;
    }

    public void setTags(List<TagsModel> tags) {
        Tags = tags;
    }

    public List<StateModel> getCapital() {
        return Capital;
    }

    public void setCapital(List<StateModel> capital) {
        Capital = capital;
    }

    public List<ExperienceTypeModel> getExperienceType() {
        return ExperienceType;
    }

    public void setExperienceType(List<ExperienceTypeModel> experienceType) {
        ExperienceType = experienceType;
    }
}
