package teamup.rivile.com.teamup.Uitls.InternalDatabase;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class OfferDetailsDataBase extends RealmObject {
    public Integer Id ;
    public String Name ;
    public String Description ;
    public Integer CategoryId ;
    public String CategoryName ;
    public Integer ProfitType ;
    public Integer ProfitFrom ;
    public Integer ProfitTo ;
    public Integer NumContributorFrom ;
    public Integer NumContributorTo ;
    public Integer AgeRequiredFrom ;
    public Integer AgeRequiredTo ;
    public Integer GenderContributor ;
    public Integer EducationContributorLevel ;
    public Integer UserId ;
    public Integer NumLiks ;
    public Integer NumJoinOffer ;
    public RealmList<UserDataBase> Users ;
    public String Address ;
    public String Date ;
    public Integer Status ;
    public Boolean Block;
    public Boolean IsDelete;
    public RealmList<OfferDetailsRequirmentDataBase> Requirments ;
    public RealmList<CapitalModelDataBase> Capitals ;


    public OfferDetailsDataBase() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Boolean getBlock() {
        return Block;
    }

    public Boolean getDelete() {
        return IsDelete;
    }

    public void setBlock(Boolean block) {
        Block = block;
    }

    public void setDelete(Boolean delete) {
        IsDelete = delete;
    }

    public Boolean isBlock() {
        return Block;
    }

    public Boolean isDelete() {
        return IsDelete;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setCapitals(RealmList<CapitalModelDataBase> capitals) {
        Capitals = capitals;
    }

    public void setRequirments(RealmList<OfferDetailsRequirmentDataBase> requirments) {
        Requirments = requirments;
    }

    public String getAddress() {
        return Address;
    }

    public Integer getStatus() {
        return Status;
    }

    public List<CapitalModelDataBase> getCapitals() {
        return Capitals;
    }

    public List<OfferDetailsRequirmentDataBase> getRequirments() {
        return Requirments;
    }

    public void setNumJoinOffer(Integer numJoinOffer) {
        NumJoinOffer = numJoinOffer;
    }

    public void setNumLiks(Integer numLiks) {
        NumLiks = numLiks;
    }

    public void setUsers(RealmList<UserDataBase> users) {
        Users = users;
    }

    public Integer getNumJoinOffer() {
        return NumJoinOffer;
    }

    public Integer getNumLiks() {
        return NumLiks;
    }

    public List<UserDataBase> getUsers() {
        return Users;
    }

    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    public void setAgeRequiredFrom(Integer ageRequiredFrom) {
        AgeRequiredFrom = ageRequiredFrom;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;

    }

    public void setAgeRequiredTo(Integer ageRequiredTo) {
        AgeRequiredTo = ageRequiredTo;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setEducationContributorLevel(Integer educationContributorLevel) {
        EducationContributorLevel = educationContributorLevel;
    }

    public void setGenderContributor(Integer genderContributor) {
        GenderContributor = genderContributor;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNumContributorFrom(Integer numContributorFrom) {
        NumContributorFrom = numContributorFrom;
    }

    public void setNumContributorTo(Integer numContributorTo) {
        NumContributorTo = numContributorTo;
    }

    public void setProfitFrom(Integer profitFrom) {
        ProfitFrom = profitFrom;
    }

    public void setProfitTo(Integer profitTo) {
        ProfitTo = profitTo;
    }

    public void setProfitType(Integer profitType) {
        ProfitType = profitType;
    }

    public Integer getAgeRequiredFrom() {
        return AgeRequiredFrom;
    }

    public Integer getAgeRequiredTo() {
        return AgeRequiredTo;
    }

    public Integer getCategoryId() {
        return CategoryId;
    }

    public Integer getEducationContributorLevel() {
        return EducationContributorLevel;
    }

    public Integer getGenderContributor() {
        return GenderContributor;
    }

    public Integer getId() {
        return Id;
    }

    public Integer getNumContributorFrom() {
        return NumContributorFrom;
    }

    public Integer getNumContributorTo() {
        return NumContributorTo;
    }

    public Integer getProfitFrom() {
        return ProfitFrom;
    }

    public Integer getProfitTo() {
        return ProfitTo;
    }

    public Integer getProfitType() {
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
