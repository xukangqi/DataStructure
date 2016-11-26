package com;

/**
 * Created by Administrator on 2016/11/13.
 */
public class Car {
    public String number;//车牌
    public int hour;//到达小时
    public int minute;//到达分钟
    public Car Nextcoming;

    public Car(String number, int hour, int minute) {
        this.number = number;
        this.hour = hour;
        this.minute = minute;
    }

    public Car(Car newcar){
        number=newcar.getNumber();
        hour=newcar.getHour();
        minute=newcar.getMinute();
    }
    public void setNumber(String number) {
        this.number = number;
    }


    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Car getNextcoming() {
        return Nextcoming;
    }

    public void setNextcoming(Car nextcoming) {
        Nextcoming = nextcoming;
    }

    public String getNumber() {

        return number;

    }
}
