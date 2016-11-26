package com;

/**
 * Created by Administrator on 2016/11/7.
 */
public class Edge {
    public String firstnode;
    public String secondnode;
    public int distance;
    public Edge next;
    public Edge(){
    }

    public Edge(String firstnode, String secondnode, int distance){
        this.firstnode=firstnode;
        this.secondnode=secondnode;
        this.distance=distance;
    }
    public Edge(Edge edge){
        this.firstnode = edge.firstnode;
        this.secondnode = edge.secondnode;
        this.distance = edge.distance;
    }

    public String getFirstnode() {
        return firstnode;
    }

    public String getSecondnode() {
        return secondnode;
    }

    public int getDistance() {

        return distance;
    }

    public void setFirstnode(String firstnode) {
        this.firstnode = firstnode;
    }

    public void setSecondnode(String secondnode) {
        this.secondnode = secondnode;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setNext(Edge next) {
        this.next = next;
    }

    public Edge getNext() {
        return next;
    }
}
