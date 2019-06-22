package teamup.rivile.com.teamup.network.APIModels;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Offers implements Serializable {
    private Integer Id;
    private String Name;
    private String Description;
    private Integer CategoryId;
    private String CategoryName;
    //    private Integer ProfitType;
    private Integer NumContributor;
    private Integer GenderContributor;
    private Integer UserId;
    private Integer NumLiks;
    private Integer NumJoinOffer;
    private List<UserModel> Users;
    private String Address;
    //    private String Date;
    private Date StringDate;
    private Float ProjectDuration;
    private Integer Status;
    //    private List<StateModel> States;
    private List<TagsModel> Tags;
    private Integer LocationId;
    //    private Integer InitialCostType;
//    private Integer DirectExpensesType;
//    private Integer IndectExpensesType;
//    private Integer ProjectDurationType;
//    private Integer MinExperience;
    private Float ProfitMoney;
    private Float InitialCost;
    private Float DirectExpenses;
    private Float IndectExpenses;

    //    private Integer ProjectType;
    public boolean NeedPlace;
    public boolean IsJoin;
    public boolean IsSuccess;
    public boolean IsCompleted;
    public List<AttachmentModel> Attachments;

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

    public Date getStringDate() {
        return StringDate;
    }

    public void setStringDate(Date stringDate) {
        StringDate = stringDate;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
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

    public Float getProfitMoney() {
        return ProfitMoney;
    }

    public void setProfitMoney(Float profitMoney) {
        ProfitMoney = profitMoney;
    }

    public Float getInitialCost() {
        return InitialCost == null ? 0 : InitialCost;
    }

    public void setInitialCost(Float initialCost) {
        InitialCost = initialCost;
    }

    public Float getDirectExpenses() {
        return DirectExpenses == null ? 0 : DirectExpenses;
    }

    public void setDirectExpenses(Float directExpenses) {
        DirectExpenses = directExpenses;
    }

    public Float getIndectExpenses() {
        return IndectExpenses == null ? 0 : IndectExpenses;
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

    public List<AttachmentModel> getAttachments() {
        return Attachments;
    }

    public void setAttachments(List<AttachmentModel> attachments) {
        Attachments = attachments;
    }

    public Float getProjectDuration() {
        return ProjectDuration;
    }

    public void setProjectDuration(Float projectDuration) {
        ProjectDuration = projectDuration;
    }
}
