package com;

/**
 * Created by Administrator on 2016/11/7.
 */
public class ScenicSpot {
    public String name;
    public String introduce;
    public String picture;
    public int popularity;
    public String Toilet;
    public String Restarea;
    public Edge firstEdge;
    public ScenicSpot(){
        name="";
        introduce="";
        picture="";
        popularity=0;
        Toilet="无";
        Restarea="无";
    }
    public  ScenicSpot(String name){
        this.name=name;
        introduce="";
        picture="";
        popularity=0;
        Toilet="无";
        Restarea="无";
    }

    public ScenicSpot(String name, String introduce, int popularity, String picture, String toilet, String restarea) {
        this.name = name;
        this.introduce = introduce;
        this.picture = picture;
        this.popularity = popularity;
        Toilet = toilet;
        Restarea = restarea;
    }

    public String getName() {
        return name;
    }

    public void setFirstEdge(Edge firstEdge) {
        this.firstEdge = firstEdge;
    }

    public Edge getFirstEdge() {
        return firstEdge;

    }

    public String getIntroduce() {
        return introduce;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getToilet() {
        return Toilet;
    }

    public void setToilet(String toilet) {
        Toilet = toilet;
    }

    public String getRestarea() {
        return Restarea;
    }

    public void setRestarea(String restarea) {
        Restarea = restarea;
    }
}
