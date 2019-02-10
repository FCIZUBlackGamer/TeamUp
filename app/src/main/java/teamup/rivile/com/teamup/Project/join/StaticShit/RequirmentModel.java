package teamup.rivile.com.teamup.Project.join.StaticShit;

import java.io.Serializable;

public class RequirmentModel implements Serializable {
    public static int Id;
    public static boolean NeedPlaceStatus;
    public static boolean NeedPlaceType;
    public static boolean NeedPlace;
    public static String PlaceAddress;
    public static String PlaceDescriptions;
    public static boolean NeedMoney;
    public static int MoneyFrom;
    public static int MoneyTo;
    public static String MoneyDescriptions;
    public static boolean NeedExperience;
    public static int ExperienceFrom;
    public static int ExperienceTo;
    public static String ExperienceDescriptions;
    public static int UserId;
    public static Integer ExperienceTypeId;

    public RequirmentModel() {
    }

    public static int getId() {
        return Id;
    }

    public static void setId(int id) {
        Id = id;
    }

    public static boolean isNeedPlaceStatus() {
        return NeedPlaceStatus;
    }

    public static void setNeedPlaceStatus(boolean needPlaceStatus) {
        NeedPlaceStatus = needPlaceStatus;
    }

    public static boolean isNeedPlaceType() {
        return NeedPlaceType;
    }

    public static void setNeedPlaceType(boolean needPlaceType) {
        NeedPlaceType = needPlaceType;
    }

    public static boolean isNeedPlace() {
        return NeedPlace;
    }

    public static void setNeedPlace(boolean needPlace) {
        NeedPlace = needPlace;
    }

    public static String getPlaceAddress() {
        return PlaceAddress;
    }

    public static void setPlaceAddress(String placeAddress) {
        PlaceAddress = placeAddress;
    }

    public static String getPlaceDescriptions() {
        return PlaceDescriptions;
    }

    public static void setPlaceDescriptions(String placeDescriptions) {
        PlaceDescriptions = placeDescriptions;
    }

    public static boolean isNeedMoney() {
        return NeedMoney;
    }

    public static void setNeedMoney(boolean needMoney) {
        NeedMoney = needMoney;
    }

    public static int getMoneyFrom() {
        return MoneyFrom;
    }

    public static void setMoneyFrom(int moneyFrom) {
        MoneyFrom = moneyFrom;
    }

    public static int getMoneyTo() {
        return MoneyTo;
    }

    public static void setMoneyTo(int moneyTo) {
        MoneyTo = moneyTo;
    }

    public static String getMoneyDescriptions() {
        return MoneyDescriptions;
    }

    public static void setMoneyDescriptions(String moneyDescriptions) {
        MoneyDescriptions = moneyDescriptions;
    }

    public static boolean isNeedExperience() {
        return NeedExperience;
    }

    public static void setNeedExperience(boolean needExperience) {
        NeedExperience = needExperience;
    }

    public static int getExperienceFrom() {
        return ExperienceFrom;
    }

    public static void setExperienceFrom(int experienceFrom) {
        ExperienceFrom = experienceFrom;
    }

    public static int getExperienceTo() {
        return ExperienceTo;
    }

    public static void setExperienceTo(int experienceTo) {
        ExperienceTo = experienceTo;
    }

    public static String getExperienceDescriptions() {
        return ExperienceDescriptions;
    }

    public static void setExperienceDescriptions(String experienceDescriptions) {
        ExperienceDescriptions = experienceDescriptions;
    }

    public static int getUserId() {
        return UserId;
    }

    public static void setUserId(int userId) {
        UserId = userId;
    }

    public static Integer getExperienceTypeId() {
        return ExperienceTypeId;
    }

    public static void setExperienceTypeId(Integer experienceTypeId) {
        ExperienceTypeId = experienceTypeId;
    }
}
