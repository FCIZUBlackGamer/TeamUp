package teamup.rivile.com.teamup.Uitls.APIModels;

public class UserModel {
    private Integer Id ;
    private String FullName ;
    private String Password ;
    private Boolean Gender ;
    private String DateOfBirth ;
    private String Phone ;
    private String Address ;
    private String Image ;
    private String Jobtitle ;
    private Integer Status ;
    private String Bio ;
    private String Mail ;
    private Integer NumProject ;
    private Integer CapitalId ;
    private String IdentityNum ;
    private String SocialId ;
    private String IdentityImage ;
    private Boolean IsCoded ;
    private String Code ;
    private Integer LocationId;

    public UserModel() {
    }

    public UserModel(Integer id, String fullName, String password, Boolean gender, String dateOfBirth, String phone, String address, String image, String jobtitle, Integer status, String bio, String mail, Integer numProject, Integer capitalId, String identityNum, String socialId, String identityImage, Boolean isCoded, String code, Integer locationId) {
        Id = id;
        FullName = fullName;
        Password = password;
        Gender = gender;
        DateOfBirth = dateOfBirth;
        Phone = phone;
        Address = address;
        Image = image;
        Jobtitle = jobtitle;
        Status = status;
        Bio = bio;
        Mail = mail;
        NumProject = numProject;
        CapitalId = capitalId;
        IdentityNum = identityNum;
        SocialId = socialId;
        IdentityImage = identityImage;
        IsCoded = isCoded;
        Code = code;
        LocationId = locationId;
    }

    public UserModel(String fullName, String password, Boolean gender, String dateOfBirth, String phone, String address, String image, String jobtitle, Integer status, String bio, String mail, Integer numProject, Integer capitalId, String identityNum, String socialId, String identityImage, Boolean isCoded, String code, Integer locationId) {
        FullName = fullName;
        Password = password;
        Gender = gender;
        DateOfBirth = dateOfBirth;
        Phone = phone;
        Address = address;
        Image = image;
        Jobtitle = jobtitle;
        Status = status;
        Bio = bio;
        Mail = mail;
        NumProject = numProject;
        CapitalId = capitalId;
        IdentityNum = identityNum;
        SocialId = socialId;
        IdentityImage = identityImage;
        IsCoded = isCoded;
        Code = code;
        LocationId = locationId;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Boolean getGender() {
        return Gender;
    }

    public void setGender(Boolean gender) {
        Gender = gender;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getJobtitle() {
        return Jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        Jobtitle = jobtitle;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public Integer getNumProject() {
        return NumProject;
    }

    public void setNumProject(Integer numProject) {
        NumProject = numProject;
    }

    public Integer getCapitalId() {
        return CapitalId;
    }

    public void setCapitalId(Integer capitalId) {
        CapitalId = capitalId;
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

    public Integer getLocationId() {
        return LocationId;
    }

    public void setLocationId(Integer locationId) {
        LocationId = locationId;
    }
}
