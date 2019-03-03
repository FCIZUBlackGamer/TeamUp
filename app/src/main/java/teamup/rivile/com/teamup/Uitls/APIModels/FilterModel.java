package teamup.rivile.com.teamup.Uitls.APIModels;

import java.util.List;

public class FilterModel {
    public String Name ;
    public int EducationContributorLevel ;
    public int GenderContributor ;
    public List<Integer> State ;
    public List<Integer> CategoryId ;
    public int NumContributorFrom ;
    public int NumContributorTo ;
    public int ExperienceFrom ;
    public int ExperienceTo ;
    public int AgeRequiredFrom ;
    public int AgeRequiredTo ;
    public int MoneyFrom ;
    public int MoneyTo ;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getEducationContributorLevel() {
        return EducationContributorLevel;
    }

    public void setEducationContributorLevel(int educationContributorLevel) {
        EducationContributorLevel = educationContributorLevel;
    }

    public int getGenderContributor() {
        return GenderContributor;
    }

    public void setGenderContributor(int genderContributor) {
        GenderContributor = genderContributor;
    }

    public List<Integer> getAddress() {
        return State;
    }

    public void setAddress(List<Integer> address) {
        State = address;
    }

    public List<Integer> getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(List<Integer> categoryId) {
        CategoryId = categoryId;
    }

    public int getNumContributorFrom() {
        return NumContributorFrom;
    }

    public void setNumContributorFrom(int numContributorFrom) {
        NumContributorFrom = numContributorFrom;
    }

    public int getNumContributorTo() {
        return NumContributorTo;
    }

    public void setNumContributorTo(int numContributorTo) {
        NumContributorTo = numContributorTo;
    }

    public int getExperienceFrom() {
        return ExperienceFrom;
    }

    public void setExperienceFrom(int experienceFrom) {
        ExperienceFrom = experienceFrom;
    }

    public int getExperienceTo() {
        return ExperienceTo;
    }

    public void setExperienceTo(int experienceTo) {
        ExperienceTo = experienceTo;
    }

    public int getAgeRequiredFrom() {
        return AgeRequiredFrom;
    }

    public void setAgeRequiredFrom(int ageRequiredFrom) {
        AgeRequiredFrom = ageRequiredFrom;
    }

    public int getAgeRequiredTo() {
        return AgeRequiredTo;
    }

    public void setAgeRequiredTo(int ageRequiredTo) {
        AgeRequiredTo = ageRequiredTo;
    }

    public int getMoneyFrom() {
        return MoneyFrom;
    }

    public void setMoneyFrom(int moneyFrom) {
        MoneyFrom = moneyFrom;
    }

    public int getMoneyTo() {
        return MoneyTo;
    }

    public void setMoneyTo(int moneyTo) {
        MoneyTo = moneyTo;
    }
}
