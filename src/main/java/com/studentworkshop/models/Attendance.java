package com.studentworkshop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long workshopId;
    private String workshopName;
    private String username;
    private String email;
    private LocalDate date;   
    private LocalTime time;   
    private int isPresent;    

    public Attendance() {}

    public Attendance(Long workshopId, String workshopName, String username, String email, LocalDate date, LocalTime time, int isPresent) {
        this.workshopId = workshopId;
        this.workshopName = workshopName;
        this.username = username;
        this.email = email;
        this.date = date;
        this.time = time;
        this.isPresent = isPresent; 
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Long workshopId) {
        this.workshopId = workshopId;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getIsPresent() {
        return isPresent; // Get as integer
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent; 
    }

    public boolean isPresent() {
        return isPresent == 1; 
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", workshopId=" + workshopId +
                ", workshopName='" + workshopName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", isPresent=" + (isPresent == 1) + 
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attendance)) return false;
        Attendance that = (Attendance) o;
        return isPresent == that.isPresent &&
                workshopId.equals(that.workshopId) &&
                workshopName.equals(that.workshopName) &&
                username.equals(that.username) &&
                email.equals(that.email) &&
                date.equals(that.date) &&
                time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workshopId, workshopName, username, email, date, time, isPresent);
    }
}
