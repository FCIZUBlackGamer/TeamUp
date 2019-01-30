package teamup.rivile.com.teamup.Uitls.APIModels;

public class UserModel {
    public int Id ;
    public String FullName ;
    public String Password ;
    public boolean Gender ;
    public String DateOfBirth ;
    public String Phone ;
    public String Address ;
    public String Image ;
    public String Jobtitle ;
    public int Status ;
    public String Bio ;
    public String Mail ;
    public int NumProject ;
    public int CapitalId ;

    public UserModel() {
    }

    public void setId(int id) {
        Id = id;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public void setCapitalId(int capitalId) {
        CapitalId = capitalId;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setGender(boolean gender) {
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

    public void setNumProject(int numProject) {
        NumProject = numProject;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getId() {
        return Id;
    }

    public int getCapitalId() {
        return CapitalId;
    }

    public int getNumProject() {
        return NumProject;
    }

    public int getStatus() {
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
