package com;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/11/13.
 */
public class Parkinglot {
    public newStack parking;//停车场
    public newStack out;//用于移出车辆
    public Car end;//当前停在便道的最后一辆辆车
    public Car firstIn;//当前停在便道的第一辆车
    public int size;//停车场大小
    public Parkinglot(int size){
        parking=new newStack();
        out=new newStack();
        this.size=size;
    }

    public String arrive(Car car){//有车辆到达
        ArrayList<String> numbers=new ArrayList<>();
        for(int i=0;i<parking.size();i++){
            numbers.add(parking.getCar(i).getNumber());
        }
        if(numbers.contains(car.getNumber())){
            String str="该车已在停车场";
            return  str;
        }else{
            if (parking.size()==size){//如果停车场已满
                if (end==null){//当前没有车停在便道
                    end=car;
                    firstIn=end;//当前在便道的第一辆车和最后一辆车为同一辆
                    String str=car.getNumber()+" 停在了便道";
                    return  str;
                }else{//当前已经有车在便道上，变更新到达的车辆为最后一辆
                    end.setNextcoming(car);
                    end=end.getNextcoming();
                    String str=car.getNumber()+" 停在了便道";
                    return  str;
                }
            }else{//停车场没有满，直接进入
                parking.push(car);
                String str=car.getNumber()+" 停在了 "+parking.size() +" 车道";
                return  str;
            }
        }
    }
    public String leave(String number,int hour,int minute){//有车辆离开
        String str;
        int currentsize=parking.size();
        if (parking.findcar(number)==null){
            str="没有找到这辆车";
            return str;
        }else{
            Car car=parking.findcar(number);//找到要离开的车辆
            if(car.getHour()>hour){//如果离开时间比到达时间早
                str="离开时间早于到达时间！！" ;
                return str;
            }else if (car.getHour()==hour&&car.getMinute()>minute){//如果离开时间比到达时间早
                str="离开时间早于到达时间！！" ;
                return str;
            }else {
                int index=parking.findindex(number);
                for (int i=index+1;i<currentsize;i++){//从离开的车辆开始，挪开后面所有车辆，放在stack中
                    out.push(parking.getCar(i));
                }
                int time=(hour-car.getHour())*60+minute-car.getMinute();//计算停车时间
                str=car.getNumber()+" 离开了 "+" 需要付 "+0.5*time+"  ";//默认每分钟0.5元
                parking.remove(index);
                for (int i=0;i<out.size();i++){//将挪开的车辆加入到停车场
                    parking.setCar(out.getCar(i),index);
                    index++;
                }
                out.removeAll();//清空挪开的stack，方便下次操作
                if (firstIn!=null){//如果便道有车辆在等待，新到达一辆车
                    str+=arrive(firstIn);
                    firstIn.setHour(hour);
                    firstIn.setMinute(minute);
                    firstIn=firstIn.getNextcoming();//便道最先的一辆车变更
                }
                return str;
            }
        }
    }
    public  String  list(){//列出当前在停车场的所有车
        String str="";
        if (parking.size()!=0){
            for (int i=0;i<parking.size();i++){
                str+=parking.getCar(i).getNumber()+" 停在了 "+(i+1)+"车道  ";
            }
        }
        return str;
    }
    private class newStack {//私有类，作为栈来使用
        private ArrayList<Car> carlist;
        public newStack(){
             carlist=new ArrayList<Car>();
        }
        private Car peek(){
            return carlist.get(carlist.size()-1);
        }
        private void push(Car car){
            carlist.add(car);
        }
        private void pop(){
            carlist.remove(carlist.size()-1);
        }
        private void  remove(int index){
            carlist.remove(index);
        }
        private  void  removeAll(){
            carlist.clear();
        }
        private  int size(){
            return carlist.size();
        }
        private Car findcar(String number){
            for (int i=0;i<carlist.size();i++){
                if (carlist.get(i).getNumber().equals(number)){
                    return carlist.get(i);
                }
            }
            return null;
        }
        private int  findindex(String number){//更具车牌号查找车辆的索引
            for (int i=0;i<carlist.size();i++){
                if (carlist.get(i).getNumber().equals(number)){
                    return i;
                }
            }
            return -1;
        }
        private Car getCar(int index){
            return  carlist.get(index);
        }
        private void setCar(Car car, int index){
            carlist.set(index,car);
        }
    }
}
