package br.com.carrental.model;

public class User {
    private String ID;
    private String name;
    private String email;
    private String address;
    private String birthDate; //type to define, probably Calendar...

    public User(String ID, String name, String email, String address, String birthDate) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getID() {
        return ID;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    //only two setter are necessary, because they could have to be modified
    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
