package com.onlinejava.project.bookstore;

public class Member {
    private String userName;
    private String email;
    private String address;
    private boolean active;

    public Member(String userName, String email, String address) {
        this.userName = userName;
        this.email = email;
        this.address = address;
        this.active = true;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public boolean isActive() {
        return active;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Member{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", active=" + active +
                '}';
    }


    public String toCsvString() {
        return String.join(",", userName, email, address, String.valueOf(active));
    }
}