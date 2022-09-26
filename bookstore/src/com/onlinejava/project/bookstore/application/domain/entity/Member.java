package com.onlinejava.project.bookstore.application.domain.entity;

import java.util.Objects;

public class Member extends Entity {
    private String userName;
    private String email;
    private String address;

    private int totalPoint;

    private Grade grade;
    private boolean active;

    public Member() {
    }

    public Member(String userName, String email, String address, int totalPoint, Grade grade) {
        this.userName = userName;
        this.email = email;
        this.address = address;
        this.totalPoint = totalPoint;
        this.grade = grade;
        this.active = true;
    }

    public int getTotalPoint() {
        return totalPoint;
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
    public Grade getGrade() {
        return grade;
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
    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    public void addPoint(int point) {
        this.totalPoint += point;
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
                ", totalPoint=" + totalPoint +
                ", grade=" + grade +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return totalPoint == member.totalPoint
                && active == member.active
                && Objects.equals(userName, member.userName)
                && Objects.equals(email, member.email)
                && Objects.equals(address, member.address)
                && grade == member.grade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, email, address, totalPoint, grade, active);
    }
}
