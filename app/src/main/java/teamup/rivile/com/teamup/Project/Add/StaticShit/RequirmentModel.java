package teamup.rivile.com.teamup.Project.Add.StaticShit;

import java.io.Serializable;

public class RequirmentModel implements Serializable {
    public static int Id ;
    public static boolean NeedPlaceStatus ;
    public static boolean NeedPlaceType ;
    public static boolean NeedPlace ;
    public static String PlaceAddress ;
    public static String PlaceDescriptions ;
    public static boolean NeedMoney ;
    public static int MoneyFrom ;
    public static int MoneyTo ;
    public static String MoneyDescriptions ;
    public static boolean NeedExperience ;
    public static int ExperienceFrom ;
    public static int ExperienceTo ;
    public static String ExperienceDescriptions ;
    public static int UserId ;
    public static int ExperienceTypeId ;

    public RequirmentModel() {
    }

    public static void setId(int id) {
        Id = id;
    }

    public static void setUserId(int userId) {
        UserId = userId;
    }

    public static void setExperienceDescriptions(String experienceDescriptions) {
        ExperienceDescriptions = experienceDescriptions;
    }

    public static void setExperienceFrom(int experienceFrom) {
        ExperienceFrom = experienceFrom;
    }

    public static void setExperienceTo(int experienceTo) {
        ExperienceTo = experienceTo;
    }

    public static void setExperienceTypeId(int experienceTypeId) {
        ExperienceTypeId = experienceTypeId;
    }

    public static void setMoneyDescriptions(String moneyDescriptions) {
        MoneyDescriptions = moneyDescriptions;
    }

    public static void setMoneyFrom(int moneyFrom) {
        MoneyFrom = moneyFrom;
    }

    public static void setMoneyTo(int moneyTo) {
        MoneyTo = moneyTo;
    }

    public static void setNeedExperience(boolean needExperience) {
        NeedExperience = needExperience;
    }

    public static void setNeedMoney(boolean needMoney) {
        NeedMoney = needMoney;
    }

    public static void setNeedPlace(boolean needPlace) {
        NeedPlace = needPlace;
    }

    public static void setNeedPlaceStatus(boolean needPlaceStatus) {
        NeedPlaceStatus = needPlaceStatus;
    }

    public static void setNeedPlaceType(boolean needPlaceType) {
        NeedPlaceType = needPlaceType;
    }

    public static void setPlaceAddress(String placeAddress) {
        PlaceAddress = placeAddress;
    }

    public static void setPlaceDescriptions(String placeDescriptions) {
        PlaceDescriptions = placeDescriptions;
    }

    public static int getId() {
        return Id;
    }

    public static int getUserId() {
        return UserId;
    }

    public static int getExperienceFrom() {
        return ExperienceFrom;
    }

    public static int getExperienceTo() {
        return ExperienceTo;
    }

    public static int getExperienceTypeId() {
        return ExperienceTypeId;
    }

    public static int getMoneyFrom() {
        return MoneyFrom;
    }

    public static int getMoneyTo() {
        return MoneyTo;
    }

    public static String getExperienceDescriptions() {
        return ExperienceDescriptions;
    }

    public static String getMoneyDescriptions() {
        return MoneyDescriptions;
    }

    public static String getPlaceAddress() {
        return PlaceAddress;
    }

    public static String getPlaceDescriptions() {
        return PlaceDescriptions;
    }

    public static boolean isNeedExperience() {
        return NeedExperience;
    }

    public static boolean isNeedMoney() {
        return NeedMoney;
    }

    public static boolean isNeedPlace() {
        return NeedPlace;
    }

    public static boolean isNeedPlaceStatus() {
        return NeedPlaceStatus;
    }

    public static boolean isNeedPlaceType() {
        return NeedPlaceType;
    }
}
