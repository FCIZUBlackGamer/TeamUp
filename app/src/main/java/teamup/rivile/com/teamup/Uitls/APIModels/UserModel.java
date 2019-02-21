package teamup.rivile.com.teamup.Uitls.APIModels;

public class UserModel {
    public Integer Id ;
    public String FullName ;
    public String Password ;
    public Boolean Gender ;
    public String DateOfBirth ;
    public String Phone ;
    public String Address ;
    public String Image ;
    public String Jobtitle ;
    public Integer Status ;
    public String Bio ;
    public String Mail ;
    public Integer NumProject ;
    public Integer CapitalId ;
    public String IdentityNum ;
    public String SocialId ;
    public String IdentityImage ;
    public Boolean IsCoded ;
    public String Code ;

    public Boolean getCoded() {
        return IsCoded;
    }

    public void setCoded(Boolean coded) {
        IsCoded = coded;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public Boolean getGender() {
        return Gender;
    }

    public String getIdentityNum() {
        return IdentityNum;
    }

    public void setIdentityNum(String identityNum) {
        IdentityNum = identityNum;
    }

    public String getSocialId() {
        return SocialId;
    }

    public void setSocialId(String socialId) {
        SocialId = socialId;
    }

    public String getIdentityImage() {
        return IdentityImage;
    }

    public void setIdentityImage(String identityImage) {
        IdentityImage = identityImage;
    }

    public UserModel() {
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public void setCapitalId(Integer capitalId) {
        CapitalId = capitalId;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setGender(Boolean gender) {
        Gender = gender;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setJobtitle(String jobtitle) {
        Jobtitle = jobtitle;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public void setNumProject(Integer numProject) {
        NumProject = numProject;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Integer getId() {
        return Id;
    }

    public Integer getCapitalId() {
        return CapitalId;
    }

    public Integer getNumProject() {
        return NumProject;
    }

    public Integer getStatus() {
        return Status;
    }

    public String getAddress() {
        return Address;
    }

    public String getBio() {
        return Bio;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public String getFullName() {
        return FullName;
    }

    public String getImage() {
        return Image;
    }

    public String getJobtitle() {
        return Jobtitle;
    }

    public String getMail() {
        return Mail;
    }

    public String getPassword() {
        return Password;
    }

    public String getPhone() {
        return Phone;
    }
}
