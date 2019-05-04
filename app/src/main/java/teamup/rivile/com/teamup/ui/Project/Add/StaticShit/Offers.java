package teamup.rivile.com.teamup.ui.Project.Add.StaticShit;

import java.io.Serializable;
import java.util.List;

import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;

public class Offers implements Serializable {
    private static Integer Id;
    private static String Name;
    private static String Description;
    private static Integer CategoryId;
    private static String CategoryName;
    private static Integer ProfitType;
    private static Integer NumContributor;
    private static Integer GenderContributor;
    private static Integer UserId;
    private static Integer NumLiks;
    private static Integer NumJoinOffer;
    private static List<UserModel> Users;
    private static String Address;
    private static String Date;
    private static Float ProjectDuration;
    private static Integer Status;
    private static Integer LocationId;
    private static Integer MinExperience;
    private static Float ProfitMoney;
    private static Float InitialCost;
    private static Float DirectExpenses;
    private static Float IndectExpenses;
    public static boolean NeedPlace ;
    public static boolean IsJoin ;

    private static Integer InitialCostType;
    private static Integer DirectExpensesType;
    private static Integer IndectExpensesType;
    private static Integer ProjectDurationType;
    private static Integer ProjectType;
    private static boolean IsSuccess ;
    private static boolean IsCompleted ;

    public static boolean isIsSuccess() {
        return IsSuccess;
    }

    public static void setIsSuccess(boolean isSuccess) {
        IsSuccess = isSuccess;
    }

    public static boolean isIsCompleted() {
        return IsCompleted;
    }

    public static void setIsCompleted(boolean isCompleted) {
        IsCompleted = isCompleted;
    }

    public static Integer getProjectType() {
        return ProjectType;
    }

    public static void setProjectType(Integer projectType) {
        ProjectType = projectType;
    }

    public static Integer getId() {
        return Id;
    }

    public static Integer getInitialCostType() {

        return InitialCostType;
    }

    public static void setInitialCostType(Integer initialCostType) {
        InitialCostType = initialCostType;
    }

    public static Integer getDirectExpensesType() {
        return DirectExpensesType;
    }

    public static void setDirectExpensesType(Integer directExpensesType) {
        DirectExpensesType = directExpensesType;
    }

    public static Integer getIndectExpensesType() {
        return IndectExpensesType;
    }

    public static void setIndectExpensesType(Integer indectExpensesType) {
        IndectExpensesType = indectExpensesType;
    }

    public static Integer getProjectDurationType() {
        return ProjectDurationType;
    }

    public static void setProjectDurationType(Integer projectDurationType) {
        ProjectDurationType = projectDurationType;
    }

    public static boolean isSuccess() {
        return IsSuccess;
    }

    public static void setSuccess(boolean success) {
        IsSuccess = success;
    }

    public static boolean isCompleted() {
        return IsCompleted;
    }

    public static void setCompleted(boolean completed) {
        IsCompleted = completed;
    }

    public static Float getProfitMoney() {
        return ProfitMoney;
    }

    public static void setProfitMoney(Float profitMoney) {
        ProfitMoney = profitMoney;
    }

    public static void setId(Integer id) {
        Id = id;
    }

    public static String getName() {
        return Name;
    }

    public static void setName(String name) {
        Name = name;
    }

    public static String getDescription() {
        return Description;
    }

    public static void setDescription(String description) {
        Description = description;
    }

    public static Integer getCategoryId() {
        return CategoryId;
    }

    public static void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    public static String getCategoryName() {
        return CategoryName;
    }

    public static void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public static Integer getProfitType() {
        return ProfitType;
    }

    public static void setProfitType(Integer profitType) {
        ProfitType = profitType;
    }

    public static Integer getNumContributor() {
        return NumContributor;
    }

    public static void setNumContributor(Integer numContributor) {
        NumContributor = numContributor;
    }

    public static Integer getGenderContributor() {
        return GenderContributor;
    }

    public static void setGenderContributor(Integer genderContributor) {
        GenderContributor = genderContributor;
    }

    public static Integer getUserId() {
        return UserId;
    }

    public static void setUserId(Integer userId) {
        UserId = userId;
    }

    public static Integer getNumLiks() {
        return NumLiks;
    }

    public static void setNumLiks(Integer numLiks) {
        NumLiks = numLiks;
    }

    public static Integer getNumJoinOffer() {
        return NumJoinOffer;
    }

    public static void setNumJoinOffer(Integer numJoinOffer) {
        NumJoinOffer = numJoinOffer;
    }

    public static List<UserModel> getUsers() {
        return Users;
    }

    public static void setUsers(List<UserModel> users) {
        Users = users;
    }

    public static String getAddress() {
        return Address;
    }

    public static void setAddress(String address) {
        Address = address;
    }

    public static String getDate() {
        return Date;
    }

    public static void setDate(String date) {
        Date = date;
    }

    public static Float getProjectDuration() {
        return ProjectDuration;
    }

    public static void setProjectDuration(Float projectDuration) {
        ProjectDuration = projectDuration;
    }

    public static Integer getStatus() {
        return Status;
    }

    public static void setStatus(Integer status) {
        Status = status;
    }

    public static Integer getLocationId() {
        return LocationId;
    }

    public static void setLocationId(Integer locationId) {
        LocationId = locationId;
    }

    public static Integer getMinExperience() {
        return MinExperience;
    }

    public static void setMinExperience(Integer minExperience) {
        MinExperience = minExperience;
    }

    public static Float getInitialCost() {
        return InitialCost;
    }

    public static void setInitialCost(Float initialCost) {
        InitialCost = initialCost;
    }

    public static Float getDirectExpenses() {
        return DirectExpenses;
    }

    public static void setDirectExpenses(Float directExpenses) {
        DirectExpenses = directExpenses;
    }

    public static Float getIndectExpenses() {
        return IndectExpenses;
    }

    public static void setIndectExpenses(Float indectExpenses) {
        IndectExpenses = indectExpenses;
    }

    public static boolean isNeedPlace() {
        return NeedPlace;
    }

    public static void setNeedPlace(boolean needPlace) {
        NeedPlace = needPlace;
    }

    public static boolean isIsJoin() {
        return IsJoin;
    }

    public static void setIsJoin(boolean isJoin) {
        IsJoin = isJoin;
    }
}
