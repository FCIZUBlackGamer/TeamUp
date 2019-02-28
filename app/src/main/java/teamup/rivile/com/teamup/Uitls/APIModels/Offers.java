package teamup.rivile.com.teamup.Uitls.APIModels;

import java.io.Serializable;
import java.util.List;

public class Offers implements Serializable {
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
    private List<RequirmentModel> Requirments;
    private List<CapitalModel> Capitals;
    private List<TagsModel> Tags;
    private Integer LocationId;

    public Offers() {
    }

    public Offers(String name, String description, Integer categoryId, String categoryName, Integer profitType, Integer profitFrom, Integer profitTo, Integer numContributorFrom, Integer numContributorTo, Integer ageRequiredFrom, Integer ageRequiredTo, Integer genderContributor, Integer educationContributorLevel, Integer userId, Integer numLiks, Integer numJoinOffer, List<UserModel> users, String address, String date, Integer status, List<RequirmentModel> requirments, List<CapitalModel> capitals, List<TagsModel> tags, Integer locationId) {
        Name = name;
        Description = description;
        CategoryId = categoryId;
        CategoryName = categoryName;
        ProfitType = profitType;
        ProfitFrom = profitFrom;
        ProfitTo = profitTo;
        NumContributorFrom = numContributorFrom;
        NumContributorTo = numContributorTo;
        AgeRequiredFrom = ageRequiredFrom;
        AgeRequiredTo = ageRequiredTo;
        GenderContributor = genderContributor;
        EducationContributorLevel = educationContributorLevel;
        UserId = userId;
        NumLiks = numLiks;
        NumJoinOffer = numJoinOffer;
        Users = users;
        Address = address;
        Date = date;
        Status = status;
        Requirments = requirments;
        Capitals = capitals;
        Tags = tags;
        LocationId = locationId;
    }

    public Offers(Integer id, String name, String description, Integer categoryId, String categoryName, Integer profitType, Integer profitFrom, Integer profitTo, Integer numContributorFrom, Integer numContributorTo, Integer ageRequiredFrom, Integer ageRequiredTo, Integer genderContributor, Integer educationContributorLevel, Integer userId, Integer numLiks, Integer numJoinOffer, List<UserModel> users, String address, String date, Integer status, List<RequirmentModel> requirments, List<CapitalModel> capitals, List<TagsModel> tags, Integer locationId) {


        Id = id;
        Name = name;
        Description = description;
        CategoryId = categoryId;
        CategoryName = categoryName;
        ProfitType = profitType;
        ProfitFrom = profitFrom;
        ProfitTo = profitTo;
        NumContributorFrom = numContributorFrom;
        NumContributorTo = numContributorTo;
        AgeRequiredFrom = ageRequiredFrom;
        AgeRequiredTo = ageRequiredTo;
        GenderContributor = genderContributor;
        EducationContributorLevel = educationContributorLevel;
        UserId = userId;
        NumLiks = numLiks;
        NumJoinOffer = numJoinOffer;
        Users = users;
        Address = address;
        Date = date;
        Status = status;
        Requirments = requirments;
        Capitals = capitals;
        Tags = tags;
        LocationId = locationId;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public Integer getProfitType() {
        return ProfitType;
    }

    public void setProfitType(Integer profitType) {
        ProfitType = profitType;
    }

    public Integer getProfitFrom() {
        return ProfitFrom;
    }

    public void setProfitFrom(Integer profitFrom) {
        ProfitFrom = profitFrom;
    }

    public Integer getProfitTo() {
        return ProfitTo;
    }

    public void setProfitTo(Integer profitTo) {
        ProfitTo = profitTo;
    }

    public Integer getNumContributorFrom() {
        return NumContributorFrom;
    }

    public void setNumContributorFrom(Integer numContributorFrom) {
        NumContributorFrom = numContributorFrom;
    }

    public Integer getNumContributorTo() {
        return NumContributorTo;
    }

    public void setNumContributorTo(Integer numContributorTo) {
        NumContributorTo = numContributorTo;
    }

    public Integer getAgeRequiredFrom() {
        return AgeRequiredFrom;
    }

    public void setAgeRequiredFrom(Integer ageRequiredFrom) {
        AgeRequiredFrom = ageRequiredFrom;
    }

    public Integer getAgeRequiredTo() {
        return AgeRequiredTo;
    }

    public void setAgeRequiredTo(Integer ageRequiredTo) {
        AgeRequiredTo = ageRequiredTo;
    }

    public Integer getGenderContributor() {
        return GenderContributor;
    }

    public void setGenderContributor(Integer genderContributor) {
        GenderContributor = genderContributor;
    }

    public Integer getEducationContributorLevel() {
        return EducationContributorLevel;
    }

    public void setEducationContributorLevel(Integer educationContributorLevel) {
        EducationContributorLevel = educationContributorLevel;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getNumLiks() {
        return NumLiks;
    }

    public void setNumLiks(Integer numLiks) {
        NumLiks = numLiks;
    }

    public Integer getNumJoinOffer() {
        return NumJoinOffer;
    }

    public void setNumJoinOffer(Integer numJoinOffer) {
        NumJoinOffer = numJoinOffer;
    }

    public List<UserModel> getUsers() {
        return Users;
    }

    public void setUsers(List<UserModel> users) {
        Users = users;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public List<RequirmentModel> getRequirments() {
        return Requirments;
    }

    public void setRequirments(List<RequirmentModel> requirments) {
        Requirments = requirments;
    }

    public List<CapitalModel> getCapitals() {
        return Capitals;
    }

    public void setCapitals(List<CapitalModel> capitals) {
        Capitals = capitals;
    }

    public List<TagsModel> getTags() {
        return Tags;
    }

    public void setTags(List<TagsModel> tags) {
        Tags = tags;
    }

    public Integer getLocationId() {
        return LocationId;
    }

    public void setLocationId(Integer locationId) {
        LocationId = locationId;
    }
}
