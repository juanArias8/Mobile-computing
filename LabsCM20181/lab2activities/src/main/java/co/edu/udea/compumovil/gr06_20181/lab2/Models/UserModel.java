package co.edu.udea.compumovil.gr06_20181.lab2.Models;

public class UserModel {
    private String name;
    private String email;
    private String password;
    private String state;
    private byte[] photo;

    public UserModel(String name, String email, String password, String state, byte[] photo) {
        this.photo = photo;
        this.name = name;
        this.email = email;
        this.password = password;
        this.state = state;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto( byte[] photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
