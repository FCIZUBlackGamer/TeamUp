package teamup.rivile.com.teamup.Project.Details;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import teamup.rivile.com.teamup.Uitls.APIModels.CapitalModel;
import teamup.rivile.com.teamup.Uitls.APIModels.ExperienceTypeModel;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;

public class OfferDetails implements Serializable {
    private Integer Id;
    private String Name;
    private String Description;
    private Integer CategoryId;
    private String CategoryName;
    private Integer ProfitType;
    private Integer ProfitFrom;
    private Integer ProfitTo;
    private Integer NumContributorFrom;
    private Integer NumContributorTo;
    private Integer AgeRequiredFrom;
    private Integer AgeRequiredTo;
    private Integer GenderContributor;
    private Integer EducationContributorLevel;
    private Integer UserId;
    private Integer NumLiks;
    private Integer NumJoinOffer;
    private List<UserModel> Users;
    private String Address;
    private String Date;
    private Integer Status;
    private Boolean Block;
    private Boolean IsDelete;
    private List<OfferDetailsRequirment> Requirments;
    @SerializedName("State")
    private List<CapitalModel> Capitals;
    private List<ExperienceTypeModel> Tags;


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

    public List<ExperienceTypeModel> getTags() {
        return Tags;
    }

    public void setTags(List<ExperienceTypeModel> tags) {
        Tags = tags;
    }

    public OfferDetails() {
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

    public void setCapitals(List<CapitalModel> capitals) {
        Capitals = capitals;
    }

    public void setRequirments(List<OfferDetailsRequirment> requirments) {
        Requirments = requirments;
    }

    public String getAddress() {
        return Address;
    }

    public Integer getStatus() {
        return Status;
    }

    public List<CapitalModel> getCapitals() {
        return Capitals;
    }

    public List<OfferDetailsRequirment> getRequirments() {
        return Requirments;
    }

    public void setNumJoinOffer(Integer numJoinOffer) {
        NumJoinOffer = numJoinOffer;
    }

    public void setNumLiks(Integer numLiks) {
        NumLiks = numLiks;
    }

    public void setUsers(List<UserModel> users) {
        Users = users;
    }

    public Integer getNumJoinOffer() {
        return NumJoinOffer;
    }

    public Integer getNumLiks() {
        return NumLiks;
    }

    public List<UserModel> getUsers() {
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
