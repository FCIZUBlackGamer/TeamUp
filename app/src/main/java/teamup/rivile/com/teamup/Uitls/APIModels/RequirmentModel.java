package teamup.rivile.com.teamup.Uitls.APIModels;

import java.io.Serializable;

public class RequirmentModel implements Serializable {
    public Integer Id ;
    public boolean NeedPlaceStatus ;
    public boolean NeedPlaceType ;
    public boolean NeedPlace ;
    public String PlaceAddress ;
    public String PlaceDescriptions ;
    public boolean NeedMoney ;
    public Integer MoneyFrom ;
    public Integer MoneyTo ;
    public String MoneyDescriptions ;
    public boolean NeedExperience ;
    public Integer ExperienceFrom ;
    public Integer ExperienceTo ;
    public String ExperienceDescriptions ;
    public String Date ;
    public Integer UserId ;
    public Integer ExperienceTypeId;

    public RequirmentModel() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public void setExperienceDescriptions(String experienceDescriptions) {
        ExperienceDescriptions = experienceDescriptions;
    }

    public void setExperienceFrom(Integer experienceFrom) {
        ExperienceFrom = experienceFrom;
    }

    public void setExperienceTo(Integer experienceTo) {
        ExperienceTo = experienceTo;
    }

    public void setExperienceTypeId(Integer experienceTypeId) {
        ExperienceTypeId = experienceTypeId;
    }

    public void setMoneyDescriptions(String moneyDescriptions) {
        MoneyDescriptions = moneyDescriptions;
    }

    public void setMoneyFrom(Integer moneyFrom) {
        MoneyFrom = moneyFrom;
    }

    public void setMoneyTo(Integer moneyTo) {
        MoneyTo = moneyTo;
    }

    public void setNeedExperience(boolean needExperience) {
        NeedExperience = needExperience;
    }

    public void setNeedMoney(boolean needMoney) {
        NeedMoney = needMoney;
    }

    public void setNeedPlace(boolean needPlace) {
        NeedPlace = needPlace;
    }

    public void setNeedPlaceStatus(boolean needPlaceStatus) {
        NeedPlaceStatus = needPlaceStatus;
    }

    public void setNeedPlaceType(boolean needPlaceType) {
        NeedPlaceType = needPlaceType;
    }

    public void setPlaceAddress(String placeAddress) {
        PlaceAddress = placeAddress;
    }

    public void setPlaceDescriptions(String placeDescriptions) {
        PlaceDescriptions = placeDescriptions;
    }

    public Integer getId() {
        return Id;
    }

    public Integer getUserId() {
        return UserId;
    }

    public Integer getExperienceFrom() {
        return ExperienceFrom;
    }

    public Integer getExperienceTo() {
        return ExperienceTo;
    }

    public Integer getExperienceTypeId() {
        return ExperienceTypeId;
    }

    public Integer getMoneyFrom() {
        return MoneyFrom;
    }

    public Integer getMoneyTo() {
        return MoneyTo;
    }

    public String getExperienceDescriptions() {
        return ExperienceDescriptions;
    }

    public String getMoneyDescriptions() {
        return MoneyDescriptions;
    }

    public String getPlaceAddress() {
        return PlaceAddress;
    }

    public String getPlaceDescriptions() {
        return PlaceDescriptions;
    }

    public boolean isNeedExperience() {
        return NeedExperience;
    }

    public boolean isNeedMoney() {
        return NeedMoney;
    }

    public boolean isNeedPlace() {
        return NeedPlace;
    }

    public boolean isNeedPlaceStatus() {
        return NeedPlaceStatus;
    }

    public boolean isNeedPlaceType() {
        return NeedPlaceType;
    }
}
