package teamup.rivile.com.teamup.Uitls.AppModels;

import java.io.Serializable;
import java.util.List;

import teamup.rivile.com.teamup.network.APIModels.AttachmentModel;
import teamup.rivile.com.teamup.network.APIModels.StateModel;
import teamup.rivile.com.teamup.network.APIModels.TagsModel;
import teamup.rivile.com.teamup.network.APIModels.UserModel;

public class OfferDetails implements Serializable {
    private Integer Id;
    private String Name;
    private String Description;
    private Integer CategoryId;
    private String CategoryName;
    private Integer NumContributor;
    private Integer GenderContributor;
    private Integer UserId;
    private Integer NumLiks;
    private Integer NumJoinOffer;
    private List<UserModel> Users;
    private String Address;
    private String Date;
    private Integer Status;
    private Boolean Block;
    private Boolean IsDelete;
    private List<StateModel> States;

    private Float ProjectDuration;
    private List<TagsModel> Tags;
    private Integer LocationId;
    private Integer MinExperience;
    private Float ProfitMoney;
    private Float InitialCost;
    private Float DirectExpenses;
    private Float IndectExpenses;
    public boolean NeedPlace ;
    public boolean IsJoin ;
    public List<AttachmentModel> Attachments;

    private Integer InitialCostType;
    private Integer DirectExpensesType;
    private Integer IndectExpensesType;
    private Integer ProjectDurationType;
    private Integer ProjectType;
    public boolean IsSuccess ;
    public boolean IsCompleted ;

    public Integer getProjectType() {
        return ProjectType;
    }

    public void setProjectType(Integer projectType) {
        ProjectType = projectType;
    }

    public Integer getId() {
        return Id == null ? 0 : Id;
    }

    public Integer getInitialCostType() {
        return InitialCostType;
    }

    public void setInitialCostType(Integer initialCostType) {
        InitialCostType = initialCostType;
    }

    public Integer getDirectExpensesType() {
        return DirectExpensesType;
    }

    public void setDirectExpensesType(Integer directExpensesType) {
        DirectExpensesType = directExpensesType;
    }

    public Integer getIndectExpensesType() {
        return IndectExpensesType;
    }

    public void setIndectExpensesType(Integer indectExpensesType) {
        IndectExpensesType = indectExpensesType;
    }

    public Integer getProjectDurationType() {
        return ProjectDurationType;
    }

    public void setProjectDurationType(Integer projectDurationType) {
        ProjectDurationType = projectDurationType;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }

    public boolean isCompleted() {
        return IsCompleted;
    }

    public void setCompleted(boolean completed) {
        IsCompleted = completed;
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

    public Integer getNumContributor() {
        return NumContributor;
    }

    public void setNumContributor(Integer numContributor) {
        NumContributor = numContributor;
    }

    public Integer getGenderContributor() {
        return GenderContributor;
    }

    public void setGenderContributor(Integer genderContributor) {
        GenderContributor = genderContributor;
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

    public Boolean getBlock() {
        return Block;
    }

    public void setBlock(Boolean block) {
        Block = block;
    }

    public Boolean getDelete() {
        return IsDelete;
    }

    public void setDelete(Boolean delete) {
        IsDelete = delete;
    }

    public List<StateModel> getStates() {
        return States;
    }

    public void setStates(List<StateModel> states) {
        States = states;
    }

    public Float getProjectDuration() {
        return ProjectDuration;
    }

    public void setProjectDuration(Float projectDuration) {
        ProjectDuration = projectDuration;
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

    public Integer getMinExperience() {
        return MinExperience;
    }

    public void setMinExperience(Integer minExperience) {
        MinExperience = minExperience;
    }

    public Float getProfitMoney() {
        return ProfitMoney;
    }

    public void setProfitMoney(Float profitMoney) {
        ProfitMoney = profitMoney;
    }

    public Float getInitialCost() {
        return InitialCost;
    }

    public void setInitialCost(Float initialCost) {
        InitialCost = initialCost;
    }

    public Float getDirectExpenses() {
        return DirectExpenses;
    }

    public void setDirectExpenses(Float directExpenses) {
        DirectExpenses = directExpenses;
    }

    public Float getIndectExpenses() {
        return IndectExpenses;
    }

    public void setIndectExpenses(Float indectExpenses) {
        IndectExpenses = indectExpenses;
    }

    public boolean isNeedPlace() {
        return NeedPlace;
    }

    public void setNeedPlace(boolean needPlace) {
        NeedPlace = needPlace;
    }

    public boolean isJoin() {
        return IsJoin;
    }

    public void setJoin(boolean join) {
        IsJoin = join;
    }

    public List<AttachmentModel> getAttachments() {
        return Attachments;
    }

    public void setAttachments(List<AttachmentModel> attachments) {
        Attachments = attachments;
    }
}
