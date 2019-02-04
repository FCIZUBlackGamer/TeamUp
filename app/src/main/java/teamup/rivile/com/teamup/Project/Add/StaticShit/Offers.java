package teamup.rivile.com.teamup.Project.Add.StaticShit;

import java.io.Serializable;
import java.util.List;

import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;

public class Offers implements Serializable {
    public static int Id ;
    public static String Name ;
    public static String Description ;
    public static int CategoryId ;
    public static String CategoryName ;
    public static int ProfitType ;
    public static int ProfitFrom ;
    public static int ProfitTo ;
    public static int NumContributorFrom ;
    public static int NumContributorTo ;
    public static int AgeRequiredFrom ;
    public static int AgeRequiredTo ;
    public static int GenderContributor ;
    public static int EducationContributorLevel ;
    public static int UserId ;
    public static int NumLiks ;
    public static int NumJoinOffer ;
    public static List<UserModel> Users ;


    public Offers() {
    }

    public static void setNumJoinOffer(int numJoinOffer) {
        NumJoinOffer = numJoinOffer;
    }

    public static void setNumLiks(int numLiks) {
        NumLiks = numLiks;
    }

    public static void setUsers(List<UserModel> users) {
        Users = users;
    }

    public static int getNumJoinOffer() {
        return NumJoinOffer;
    }

    public static int getNumLiks() {
        return NumLiks;
    }

    public static List<UserModel> getUsers() {
        return Users;
    }

    public static void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public static void setAgeRequiredFrom(int ageRequiredFrom) {
        AgeRequiredFrom = ageRequiredFrom;
    }

    public static void setCategoryName(String categoryName) {
        CategoryName = categoryName;

    }

    public static void setAgeRequiredTo(int ageRequiredTo) {
        AgeRequiredTo = ageRequiredTo;
    }

    public static int getUserId() {
        return UserId;
    }

    public static void setUserId(int userId) {
        UserId = userId;
    }

    public static void setDescription(String description) {
        Description = description;
    }

    public static void setEducationContributorLevel(int educationContributorLevel) {
        EducationContributorLevel = educationContributorLevel;
    }

    public static void setGenderContributor(int genderContributor) {
        GenderContributor = genderContributor;
    }

    public static void setId(int id) {
        Id = id;
    }

    public static void setName(String name) {
        Name = name;
    }

    public static void setNumContributorFrom(int numContributorFrom) {
        NumContributorFrom = numContributorFrom;
    }

    public static void setNumContributorTo(int numContributorTo) {
        NumContributorTo = numContributorTo;
    }

    public static void setProfitFrom(int profitFrom) {
        ProfitFrom = profitFrom;
    }

    public static void setProfitTo(int profitTo) {
        ProfitTo = profitTo;
    }

    public static void setProfitType(int profitType) {
        ProfitType = profitType;
    }

    public static int getAgeRequiredFrom() {
        return AgeRequiredFrom;
    }

    public static int getAgeRequiredTo() {
        return AgeRequiredTo;
    }

    public static int getCategoryId() {
        return CategoryId;
    }

    public static int getEducationContributorLevel() {
        return EducationContributorLevel;
    }

    public static int getGenderContributor() {
        return GenderContributor;
    }

    public static int getId() {
        return Id;
    }

    public static int getNumContributorFrom() {
        return NumContributorFrom;
    }

    public static int getNumContributorTo() {
        return NumContributorTo;
    }

    public static int getProfitFrom() {
        return ProfitFrom;
    }

    public static int getProfitTo() {
        return ProfitTo;
    }

    public static int getProfitType() {
        return ProfitType;
    }

    public static String getCategoryName() {
        return CategoryName;
    }

    public static String getDescription() {
        return Description;
    }

    public static String getName() {
        return Name;
    }
}
