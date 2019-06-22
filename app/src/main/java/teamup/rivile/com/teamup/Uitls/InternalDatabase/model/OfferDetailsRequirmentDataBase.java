package teamup.rivile.com.teamup.Uitls.InternalDatabase.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class OfferDetailsRequirmentDataBase extends RealmObject {
    public int Id ;
    public boolean NeedPlaceStatus ;
    public boolean NeedPlaceType ;
    public boolean NeedPlace ;
    public String PlaceAddress ;
    public String PlaceDescriptions ;
    public boolean NeedMoney ;
    public int MoneyFrom ;
    public int MoneyTo ;
    public String MoneyDescriptions ;
    public boolean NeedExperience ;
    public int ExperienceFrom ;
    public int ExperienceTo ;
    public String ExperienceDescriptions ;
    public String Date ;
    public int UserId ;
    public Integer ExperienceTypeId;
    public RealmList<AttachmentModelDataBase> Attachments;

    public OfferDetailsRequirmentDataBase() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public RealmList<AttachmentModelDataBase> getAttachments() {
        return Attachments;
    }

    public void setAttachments(RealmList<AttachmentModelDataBase> attachments) {
        Attachments = attachments;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setExperienceDescriptions(String experienceDescriptions) {
        ExperienceDescriptions = experienceDescriptions;
    }

    public void setAttachmentModels(RealmList<AttachmentModelDataBase> attachmentModels) {
        this.Attachments = attachmentModels;
    }

    public List<AttachmentModelDataBase> getAttachmentModels() {
        return Attachments;
    }

    public void setExperienceFrom(int experienceFrom) {
        ExperienceFrom = experienceFrom;
    }

    public void setExperienceTo(int experienceTo) {
        ExperienceTo = experienceTo;
    }

    public void setExperienceTypeId(Integer experienceTypeId) {
        ExperienceTypeId = experienceTypeId;
    }

    public void setMoneyDescriptions(String moneyDescriptions) {
        MoneyDescriptions = moneyDescriptions;
    }

    public void setMoneyFrom(int moneyFrom) {
        MoneyFrom = moneyFrom;
    }

    public void setMoneyTo(int moneyTo) {
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

    public int getId() {
        return Id;
    }

    public int getUserId() {
        return UserId;
    }

    public int getExperienceFrom() {
        return ExperienceFrom;
    }

    public int getExperienceTo() {
        return ExperienceTo;
    }

    public int getExperienceTypeId() {
        return ExperienceTypeId;
    }

    public int getMoneyFrom() {
        return MoneyFrom;
    }

    public int getMoneyTo() {
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

