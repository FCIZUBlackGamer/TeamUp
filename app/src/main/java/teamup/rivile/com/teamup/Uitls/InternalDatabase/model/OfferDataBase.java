package teamup.rivile.com.teamup.Uitls.InternalDatabase.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;


public class OfferDataBase extends RealmObject {
    private Integer Id;
    private String Name;
    private String Description;
    private Integer CategoryId;
    private String CategoryName;
    private Integer ProfitType;
    private Integer NumContributor;
    private Integer GenderContributor;
    private Integer UserId;
    private Integer NumLiks;
    private Integer NumJoinOffer;
    private RealmList<UserDataBase> Users;
    private String Address;
    private String Date;
    private Float ProjectDuration;
    private Integer Status;
    private RealmList<CapitalDataBase> Tags;
    private Integer LocationId;
    private Integer InitialCostType;
    private Integer DirectExpensesType;
    private Integer IndectExpensesType;
    private Integer ProjectDurationType;
    private Integer MinExperience;
    private Float ProfitMoney;
    private Float InitialCost;
    private Float DirectExpenses;
    private Float IndectExpenses;

    private Integer ProjectType;
    public boolean NeedPlace ;
    public boolean IsJoin ;
    public boolean IsDelete ;
    public boolean Block ;
    public boolean IsSuccess ;
    public boolean IsCompleted ;
    public int ProjectStatus ;

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public boolean isBlock() {
        return Block;
    }

    public void setBlock(boolean block) {
        Block = block;
    }

    /**0: mine, 1: favourite*/

    public void setId(Integer id) {
        Id = id;
    }

    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    public void setProfitType(Integer profitType) {
        ProfitType = profitType;
    }

    public Integer getNumContributor() {
        return NumContributor;
    }

    public void setNumContributor(Integer numContributor) {
        NumContributor = numContributor;
    }

    public void setGenderContributor(Integer genderContributor) {
        GenderContributor = genderContributor;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public void setNumLiks(Integer numLiks) {
        NumLiks = numLiks;
    }

    public void setNumJoinOffer(Integer numJoinOffer) {
        NumJoinOffer = numJoinOffer;
    }

    public void setUsers(RealmList<UserDataBase> users) {
        Users = users;
    }

    public Float getProjectDuration() {
        return ProjectDuration;
    }

    public void setProjectDuration(Float projectDuration) {
        ProjectDuration = projectDuration;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public List<CapitalDataBase> getTags() {
        return Tags;
    }

    public void setTags(RealmList<CapitalDataBase> tags) {
        Tags = tags;
    }

    public Integer getLocationId() {
        return LocationId;
    }

    public void setLocationId(Integer locationId) {
        LocationId = locationId;
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

    public Integer getProjectType() {
        return ProjectType;
    }

    public void setProjectType(Integer projectType) {
        ProjectType = projectType;
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

    /** 0: mine, 1: favourite */


    public OfferDataBase() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
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
        Tags = capitals;
    }

    public String getAddress() {
        return Address;
    }

    public int getStatus() {
        return Status;
    }

    public RealmList<CapitalDataBase> getCapitals() {
        return Tags;
    }

    public void setNumJoinOffer(int numJoinOffer) {
        NumJoinOffer = numJoinOffer;
    }

    public void setNumLiks(int numLiks) {
        NumLiks = numLiks;
    }

    public int getNumJoinOffer() {
        return NumJoinOffer;
    }

    public int getNumLiks() {
        return NumLiks;
    }

    public RealmList<UserDataBase> getUsers() {
        return Users;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;

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

    public void setGenderContributor(int genderContributor) {
        GenderContributor = genderContributor;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNumContributor(int numContributor) {
        NumContributor = numContributor;
    }

    public void setProfitType(int profitType) {
        ProfitType = profitType;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public int getGenderContributor() {
        return GenderContributor;
    }

    public int getId() {
        return Id;
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
