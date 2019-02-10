package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;


public class OfferDataBase extends RealmObject {
    public int Id ;
    public String Name ;
    public String Description ;
    public int CategoryId ;
    public String CategoryName ;
    public int ProfitType ;
    public int ProfitFrom ;
    public int ProfitTo ;
    public int NumContributorFrom ;
    public int NumContributorTo ;
    public int AgeRequiredFrom ;
    public int AgeRequiredTo ;
    public int GenderContributor ;
    public int EducationContributorLevel ;
    public int UserId ;
    public int NumLiks ;
    public int NumJoinOffer ;
    public String Address ;
    public int Status ;
    public RealmList<RequirementDataBase> Requirments ;
    public RealmList<CapitalDataBase> Capitals ;
    public RealmList<UserDataBase> Users ;
    public int ProjectStatus ; /** 0: mine, 1: favourite */


    public OfferDataBase() {
    }

    public void setStatus(int status) {
        Status = status;
    }

    public void setProjectStatus(int projectStatus) {
        ProjectStatus = projectStatus;
    }

    public int getProjectStatus() {
        return ProjectStatus;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setCapitals(RealmList<CapitalDataBase> capitals) {
        Capitals = capitals;
    }

    public void setRequirments(RealmList<RequirementDataBase> requirments) {
        Requirments = requirments;
    }

    public String getAddress() {
        return Address;
    }

    public int getStatus() {
        return Status;
    }

    public List<CapitalDataBase> getCapitals() {
        return Capitals;
    }

    public List<RequirementDataBase> getRequirments() {
        return Requirments;
    }

    public void setNumJoinOffer(int numJoinOffer) {
        NumJoinOffer = numJoinOffer;
    }

    public void setNumLiks(int numLiks) {
        NumLiks = numLiks;
    }

    public void setUsers(RealmList<UserDataBase> users) {
        Users = users;
    }

    public int getNumJoinOffer() {
        return NumJoinOffer;
    }

    public int getNumLiks() {
        return NumLiks;
    }

    public List<UserDataBase> getUsers() {
        return Users;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public void setAgeRequiredFrom(int ageRequiredFrom) {
        AgeRequiredFrom = ageRequiredFrom;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;

    }

    public void setAgeRequiredTo(int ageRequiredTo) {
        AgeRequiredTo = ageRequiredTo;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setEducationContributorLevel(int educationContributorLevel) {
        EducationContributorLevel = educationContributorLevel;
    }

    public void setGenderContributor(int genderContributor) {
        GenderContributor = genderContributor;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNumContributorFrom(int numContributorFrom) {
        NumContributorFrom = numContributorFrom;
    }

    public void setNumContributorTo(int numContributorTo) {
        NumContributorTo = numContributorTo;
    }

    public void setProfitFrom(int profitFrom) {
        ProfitFrom = profitFrom;
    }

    public void setProfitTo(int profitTo) {
        ProfitTo = profitTo;
    }

    public void setProfitType(int profitType) {
        ProfitType = profitType;
    }

    public int getAgeRequiredFrom() {
        return AgeRequiredFrom;
    }

    public int getAgeRequiredTo() {
        return AgeRequiredTo;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public int getEducationContributorLevel() {
        return EducationContributorLevel;
    }

    public int getGenderContributor() {
        return GenderContributor;
    }

    public int getId() {
        return Id;
    }

    public int getNumContributorFrom() {
        return NumContributorFrom;
    }

    public int getNumContributorTo() {
        return NumContributorTo;
    }

    public int getProfitFrom() {
        return ProfitFrom;
    }

    public int getProfitTo() {
        return ProfitTo;
    }

    public int getProfitType() {
        return ProfitType;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public String getDescription() {
        return Description;
    }

    public String getName() {
        return Name;
    }
}
