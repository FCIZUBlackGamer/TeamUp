package teamup.rivile.com.teamup.Project.Add;

import java.util.List;

import teamup.rivile.com.teamup.Uitls.APIModels.CapitalModel;
import teamup.rivile.com.teamup.Uitls.APIModels.ExperienceTypeModel;

public class CapTagCat {
    List<CapitalModel> Category;
    List<ExperienceTypeModel> Tags;
    List<CapitalModel> Capital;
    List<ExperienceTypeModel> ExperienceType;

    public CapTagCat(List<CapitalModel> category, List<ExperienceTypeModel> tags, List<CapitalModel> capital, List<ExperienceTypeModel> experienceType) {
        Category = category;
        Tags = tags;
        Capital = capital;
        ExperienceType = experienceType;
    }

    public CapTagCat() {
    }

    public List<CapitalModel> getCategory() {
        return Category;
    }

    public void setCategory(List<CapitalModel> category) {
        Category = category;
    }

    public List<ExperienceTypeModel> getTags() {
        return Tags;
    }

    public void setTags(List<ExperienceTypeModel> tags) {
        Tags = tags;
    }

    public List<CapitalModel> getCapital() {
        return Capital;
    }

    public void setCapital(List<CapitalModel> capital) {
        Capital = capital;
    }

    public List<ExperienceTypeModel> getExperienceType() {
        return ExperienceType;
    }

    public void setExperienceType(List<ExperienceTypeModel> experienceType) {
        ExperienceType = experienceType;
    }
}
