package dz.esi.pfe.pfe_app.DAL.Model;

import java.io.Serializable;
import java.sql.Date;

import dz.esi.pfe.pfe_app.DAL.Model.Enum.BloodGroup;
import dz.esi.pfe.pfe_app.DAL.Model.Enum.Gender;

/**
 * Created by DUALCOMPUTER on 3/27/2017.
 */
public class User implements Serializable {
    String userID;
    String name;
    Gender gender;
    Date birthday;
    BloodGroup bloodGroup;

    public User(String userID) {
        this.userID = userID;
    }

    public User(String userID, String name, Gender gender, Date birthday, BloodGroup bloodGroup) {
        this.userID = userID;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.bloodGroup = bloodGroup;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}

