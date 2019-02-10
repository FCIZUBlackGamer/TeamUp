package teamup.rivile.com.teamup.Project.Details;

import java.io.Serializable;
import java.util.List;

import teamup.rivile.com.teamup.Uitls.APIModels.CapitalModel;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;

public class OfferDetails implements Serializable {
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
    public List<UserModel> Users ;
    public String Address ;
    public int Status ;
    public List<OfferDetailsRequirment> Requirments ;
    public List<CapitalModel> Capitals ;


    public OfferDetails() {
    }

    public void setStatus(int status) {
        Status = status;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setCapitals(List<CapitalModel> capitals) {
        Capitals = capitals;
    }

    public void setRequirments(List<OfferDetailsRequirment> requirments) {
        Requirments = requirments;
    }

    public String getAddress() {
        return Address;
    }

    public int getStatus() {
        return Status;
    }

    public List<CapitalModel> getCapitals() {
        return Capitals;
    }

    public List<OfferDetailsRequirment> getRequirments() {
        return Requirments;
    }

    public void setNumJoinOffer(int numJoinOffer) {
        NumJoinOffer = numJoinOffer;
    }

    public void setNumLiks(int numLiks) {
        NumLiks = numLiks;
    }

    public void setUsers(List<UserModel> users) {
        Users = users;
    }

    public int getNumJoinOffer() {
        return NumJoinOffer;
    }

    public int getNumLiks() {
        return NumLiks;
    }

    public List<UserModel> getUsers() {
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
