package dev.swaleha.bookmysalonappointment.modal;

public class UserResponseTO {
    private String userName;
    private String role;

    // Constructor
    public UserResponseTO(String userName, String role) {
        this.userName = userName;
        this.role = role;
    }

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

