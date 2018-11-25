package com.example.administrator.jihye.DataStructure;

import java.util.Date;

public class Travel {
    private int id;
    private String Name;
    private String startDay;
    private String finishDay;
    private String Country;

    public Travel(String Name,String startDay,String finishDay, String Country){
        this.Name=Name;
        this.startDay=startDay;
        this.finishDay=finishDay;
        this.Country=Country;
    }

    public Travel() {

    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getFinishDay() {
        return finishDay;
    }

    public void setFinishDay(String finishDay) {
        this.finishDay = finishDay;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
