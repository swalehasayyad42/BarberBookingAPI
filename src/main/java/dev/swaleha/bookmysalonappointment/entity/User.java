package dev.swaleha.bookmysalonappointment.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.ToString;

import java.util.List;

@ToString
@Document(collection = "user")
public class User {
    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("contact")
    private String contact;
    @Field("userName")
    private String userName;
    @Field("password")
    private String password;
    @Field("role")
    private String role;  // Can be 'customer' or 'barber'
    @Field("experties")
    private List<String> expertise;
    @DBRef
    private Shop shop; // Only for barbers, null for customers

    public User(Shop shop, String role, String password, String userName, String contact, String name, String id) {
        this.shop = shop;
        this.role = role;
        this.password = password;
        this.userName = userName;
        this.contact = contact;
        this.name = name;
        this.id = id;
    }

    public User() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<String> getExpertise() {
        return expertise;
    }

    public void setExperties(List<String> expertise) {
        this.expertise = expertise;
    }
}
