package com.av.jerb.Data;

/**
 * Created by Maiada on 10/16/2017.
 */

public class Plans {


    int id;
    String planName;
    String location;
    String memberNumber;
    String budget;

    public Plans() {


    }

    public Plans(int id, String planName, String location, String memberNumber, String budget) {
        this.id = id;
        this.planName = planName;
        this.location = location;
        this.memberNumber = memberNumber;
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }
}
