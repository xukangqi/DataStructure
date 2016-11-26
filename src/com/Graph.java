package com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

/**
 * Created by Administrator on 2016/11/7.
 */
public class Graph {
    public ArrayList<ScenicSpot> scenicSpots;
    public ArrayList<Edge> edges;
    public int[][] matrix;
    public ArrayList<String> way;//存在里面的点的名字代表已经访问过
    public ArrayList<Integer> road;//记录下在遍历的过程中访问过的所有点的索引（有重复）
    public int nodenumber;//点的多少
    public Graph(){
        scenicSpots=new ArrayList<ScenicSpot>();
        edges=new ArrayList<Edge>();
        way=new ArrayList<String>();
        road=new ArrayList<Integer>();
    }
    public void  addscenicSpot(ScenicSpot scenicSpot){
        scenicSpots.add(scenicSpot);
    }
    public void  addedge(Edge edge){
        edges.add(edge);
    }
    public void CreatGraph(){//根据输入的点和边生成邻接表和邻接矩阵
        nodenumber=scenicSpots.size();
        ScenicSpot scenicSpot;
        Edge edge;
        int i;
        int index;
        matrix=new int[nodenumber][nodenumber];
        for(int k=0;k<nodenumber;k++) {//初始化邻接矩阵
            for (int j = 0; j < nodenumber; j++) {
                matrix[k][j] = 32767;
            }
        }
        for (i=0;i<edges.size();i++){//添加每一条边到相应的点的边链表中
             edge=edges.get(i);
             addtoNode(edge.getFirstnode(),edge);
             addtoNode(edge.getSecondnode(),edge);
         }

        for (i=0;i<nodenumber;i++){//根据添加好的点和边链表生成邻接矩阵
            scenicSpot=scenicSpots.get(i);
            edge=scenicSpot.getFirstEdge();
            while (edge!=null){
                if (edge.getFirstnode().equals(scenicSpot.getName())){
                    index=findindex(edge.getSecondnode());
                    matrix[i][index]=edge.getDistance();
                }else{
                    index=findindex(edge.getFirstnode());
                    matrix[i][index]=edge.getDistance();
                }
                edge=edge.getNext();
            }
            matrix[i][i]=0;//自身到自身距离为0
        }
    }

    public void addtoNode(String name, Edge newedge){//将与这个点有关的边加到这个点的边的链表中
        ScenicSpot scenicSpot;
        Edge edge;
            scenicSpot=scenicSpots.get(findindex(name));
            edge=scenicSpot.getFirstEdge();
            if (edge==null){
                scenicSpot.setFirstEdge(new Edge(newedge));
            }else{
                while (edge.getNext()!=null){
                    edge=edge.getNext();
                }
                edge.setNext(new Edge(newedge));
            }
    }

    public int findindex(String name){//根据景点名字查找景点索引
        int i;
        for (i=0;i<scenicSpots.size();i++){
            if (scenicSpots.get(i).getName().equals(name)){
                break;
            }
        }
            return i;

    }

    public boolean findway(int index){//采用递归的方法深度遍历所有点
        if (way.contains(scenicSpots.get(index).getName())){//判断是否访问过这个点
            return false;//访问过了
        }
        way.add(scenicSpots.get(index).getName());//将此次访问的点加入到way中
        road.add(index);
        for(int i=0;i<nodenumber;i++){
            if (matrix[index][i]!= 0 && matrix[index][i]!= 32767) {//如果跟这个点之间有通路
                    if (findway(i)){//跳到这个点进行遍历
                        if (way.size()!=scenicSpots.size()){//如果还有点没访问过，当所有点都访问过，就不再继续，防止最后退回到出发点的情况
                            road.add(index);
                        }
                    }
                }
        }
        return true;
    }

    public  Stack<Integer> findCircular(){//寻找回路
        int[][] DirectionMatrix=new int[nodenumber][nodenumber];
        int[] indegree=new int[nodenumber];//每个点的入度，用于拓扑排序
        int from;
        int to;
        for (int i=1;i<road.size();i++){//根据导游路线图计算入度
            indegree[road.get(i)]++;
        }
        Stack<Integer> deleteNode=findinDegree(indegree,0);
        return  deleteNode;
    }

    public Stack<Integer> findinDegree(int[] indegree,int firstnode){//根据入度进行拓扑排序
        Stack<Integer> deletenode=new Stack<Integer>();//用于压入入度为0的节点
        deletenode.add(firstnode);//由于本题的特殊性，所以先将起始点删除
        int index=firstnode;//用于表示上一个删除点的索引
        int count=0;
        while (true){
            for (int i=0;i<road.size()-1;i++){//遍历路线图，更正因删除点造成的入度变化
                if (road.get(i)==index){
                        indegree[road.get(i+1)]--;
                }
            }
            for (int i=0;i<nodenumber;i++){//寻找现在入度为0的点，并删除
                if (indegree[i]==0){
                    indegree[i]=-1;//将入度变成负数，用于表示已删除，用于区分
                    index=i;
                    deletenode.add(index);
                    break;
                }
                count++;
            }
            if (count==nodenumber){//如果遍历所有点，已经找不到入度为0的点，则结束循环
                break;
            }
            count=0;
        }
        return deletenode;//返回删除的点
    }

    public String findMiniDistance(String origin,String end){//迪杰斯特拉算法实现
        String result;
        int originindex=findindex(origin);
        int endindex=findindex(end);
        boolean[] findshortest=new boolean[nodenumber];//是否找到了最短路径
        int[] frontNode=new int[nodenumber];//最短路径中这个点的上一个节点
        int[] shortestDistance=new int[nodenumber];//到起始点的距离
        for (int i=0;i<nodenumber;i++){//初始化数组
            findshortest[i]=false;
            frontNode[i]=originindex;
            shortestDistance[i]=matrix[originindex][i];
        }

        findshortest[originindex]=true;//自身到自身已经找到最短路程
        shortestDistance[originindex]=0;//自身到自身距离为0

        int k=0;
        int min;
        for (int i=1;i<nodenumber;i++){//每次发现一个新的最短路程的节点，一共为number-1个
            min=32767;
            for (int j=0;j<nodenumber;j++){//找到目前节点中离起点最近的点
                if (findshortest[j]==false&&shortestDistance[j]<min){
                    min=shortestDistance[j];
                    k=j;
                }
            }
            findshortest[k]=true;//该节点到起始点距离已经找到
            int temp;
            int j;
            for (j=0;j<nodenumber;j++){//更正节点，从新发现的节点出发，重新计算最短路程
                if (matrix[k][j]==32767) {//如果到新发现的节点之间没有通路，表明通过新节点到起始点没通路
                    temp = 32767;
                }else {//到起始点距离等于到新节点距离加新节点到起始点距离
                    temp=min+matrix[k][j];
                }
                if (findshortest[j]==false && (temp<shortestDistance[j]) ) {//如果该节点还没有找到最短距离并且新发现的通路比之前的要短
                    shortestDistance[j] = temp;//更新最短距离
                    frontNode[j] = k;//更新前置节点
                }
            }
        }
        result="最短距离： "+shortestDistance[endindex]+" 最短路径 ：";
        while (frontNode[endindex]!=originindex){//输出结果
            result+=scenicSpots.get(endindex).getName()+"<--";
            endindex=frontNode[endindex];
        }
        result+=scenicSpots.get(endindex).getName()+"<--"+scenicSpots.get(frontNode[endindex]).getName();
        return result;
    }

    public String Floyd(String origin,String end){//弗洛伊德算法实现
        String str="";
        int originindex=findindex(origin);
        int endindex=findindex(end);
        int[][] distance=new int[nodenumber][nodenumber];//用于更新最短距离
        int[][] frontNode=new int[nodenumber][nodenumber];
        for (int i=0;i<nodenumber;i++){//将matrix的数据取出，防止因计算最短距离对原矩阵的破坏
            for (int j=0;j<nodenumber;j++){
                distance[i][j]=matrix[i][j];
                frontNode[i][j]=-1;
            }
        }
        for (int i=0;i<nodenumber;i++){//第i行
            for (int j=0;j<nodenumber;j++){//第j列
                if (distance[i][j]!=32767){//如果i到j之间有路
                    for (int k=0;k<nodenumber;k++){//去找k到j之间的路
                        if (distance[i][k]==32767&&distance[j][k]!=32767){//如果i到k之间没有通路，j到k之间有通路
                             distance[i][k]=distance[j][k]+distance[i][j];//更新i到k之间通路为i->j->k,赋值
                             frontNode[i][k]=j;//i到k之间的通路通过j
                        }else if ((distance[i][j]+distance[j][k])<distance[i][k]){//如果i到k之间有通路，但是还是i->j->k短
                            distance[i][k]=distance[i][j]+distance[j][k];//更新i到k之间通路为i->j->k,赋值
                            frontNode[i][k]=j;//i到k之间的通路通过j
                        }
                    }
                }
            }
        }
        str="最短距离："+distance[endindex][originindex];
        str+=" 最短路径："+scenicSpots.get(findindex(end)).getName()+"<--";
        while (frontNode[endindex][originindex]!=-1){//输出结果
            str+=scenicSpots.get(frontNode[endindex][originindex]).getName()+"<--";//查看起始点和结束点的之间的最短通路通过那个点实现
            endindex=frontNode[endindex][originindex];//继续寻找起始点和新发现点之间的通路
        }
        str+=scenicSpots.get(originindex).getName();
        return str;

    }
    public Edge[] prim(){//最小生成树的prim算法实现，逻辑与迪杰斯特拉算法一致，
        // 都是通过已经找到最短路径的点去发现其他节点存在的最短路径，直到所有点都发现最短路径
        Edge[] bulidedges=new Edge[edges.size()];
        int index=0;
        int originindex=0;
        boolean[] findshortest=new boolean[nodenumber];//是否找到了最短路径
        int[] frontNode=new int[nodenumber];//最短路径这个点的上一个节点
        int[] shortestway=new int[nodenumber];//到起始点的距离
        for (int i=0;i<nodenumber;i++){
            findshortest[i]=false;
            frontNode[i]=originindex;
            shortestway[i]=matrix[originindex][i];
        }

        findshortest[originindex]=true;
        shortestway[originindex]=0;

        int k=0;
        for (int i=1;i<nodenumber;i++){//每次发现一个新的最短路程的节点，一共为number-1个
            int min=32767;
            for (int j=0;j<nodenumber;j++){//找到目前节点中离起点最近的点
                if (findshortest[j]==false&&shortestway[j]<min){
                    min=shortestway[j];
                    k=j;
                }
            }
            findshortest[k]=true;
            int temporary;
            for (int j=0;j<nodenumber;j++){//更正节点，从新发现的节点出发，重新计算最短路程
                if (matrix[k][j]==32767) {
                    temporary = 32767;
                }else {
                    temporary=min+matrix[k][j];
                }
                if (findshortest[j]==false && (temporary<shortestway[j]) ) {
                    shortestway[j] = temporary;
                    frontNode[j] = k;
                }
            }
        }
        for (int i=0;i<nodenumber;i++){//因为每个点到前置节点的路都是最短的，所以只需要输出到前置节点的路就是所有要修的路
            if (i==originindex){
                continue;
            }else {
                for (int j=0;j<edges.size();j++){//根据节点匹配边，如果两个节点都正确，则加入这条边
                    if (edges.get(j).getFirstnode().equals(scenicSpots.get(i).getName())
                            &&edges.get(j).getSecondnode().equals(scenicSpots.get(frontNode[i]).getName())){
                        bulidedges[index]=edges.get(j);
                        index++;
                    }else if(edges.get(j).getSecondnode().equals(scenicSpots.get(i).getName())
                            &&edges.get(j).getFirstnode().equals(scenicSpots.get(frontNode[i]).getName())){
                        bulidedges[index]=edges.get(j);
                        index++;
                    }
                }

            }
        }
        return bulidedges;
    }
    public Edge[] kruskal(){//最小生成树的kruskal算法
        int number=edges.size();
        int[] endNode=new int[number];//存放每一个点在生成树中的下一个节点
        Edge[] treeedge=new Edge[number];
        int index=0;
        ArrayList<Edge> sortedge=new ArrayList<Edge>();//保存按照权排序过后的边
        for (int i=0;i<number;i++){
            sortedge.add(edges.get(i));
        }
        Collections.sort(sortedge,new SortbyDistance());//调用方法进行排序
        for (int i=0;i<number;i++){//根据距离从小到大取出所有边
            int begin=findindex(sortedge.get(i).getFirstnode());//获取该边起始节点索引
            int end=findindex(sortedge.get(i).getSecondnode());//获取该边结束节点索引

            int exist1=findend(endNode,begin);//调用方法查到终点
            int exist2=findend(endNode,end);
            if (exist1!=exist2){//如果两个点的终点不一致，说明两个点之间并没有存在通路，所以需要这条边
                endNode[exist1]=exist2;//设置exsit1的下一个节点为exist2，两个节点现在存在通路
                treeedge[index]=sortedge.get(i);//添加这条边
                index++;
            }
        }

        return treeedge;

    }
    public  int findend(int[] endNode,int i){//一直找到该节点在生成树中的终点
        while (endNode[i] != 0){
            i = endNode[i];
        }
        return i;
    }

    public int[] SortbyPopularity(){//用冒泡排序对景点欢迎度进行排序
      int[] index=new int[nodenumber];//用于存点的索引
      int[] popularity=new int[nodenumber];//用于存点的欢迎度
        for (int i=0;i<nodenumber;i++){
            index[i]=i;
            popularity[i]=scenicSpots.get(i).getPopularity();
        }
        int temp1;
        int temp2;
        for (int i=0;i<nodenumber-1;i++){//对欢迎度进行冒泡排序，同时对索引进行相同的位置调换
            for (int j=0;j<nodenumber-1-i;j++){
                  if (popularity[j]>popularity[j+1]){
                      temp1=popularity[j];
                      popularity[j]=popularity[j+1];
                      popularity[j+1]=temp1;
                      temp2=index[j];
                      index[j]=index[j+1];
                      index[j+1]=temp2;
                  }
            }
        }
        return index;//返回排序后的索引数组
    }
    private class SortbyDistance implements Comparator{//比较两条边的权
        public int compare(Object o1, Object o2) {
            Edge e1=(Edge)o1;
            Edge e2=(Edge)o2;
            if (e1.getDistance()>e2.getDistance() ){
                return 1;
            }
            return -1;
        }
    }

    public ArrayList<ScenicSpot> getScenicSpots() {
        return scenicSpots;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Integer> getRoad() {
        return road;
    }

    public void setRoad(ArrayList<Integer> road) {
        this.road = road;
    }

    public int[][] getMatrix() {

        return matrix;
    }

    public ArrayList<String> getWay() {
        return way;
    }

    public void setScenicSpots(ArrayList<ScenicSpot> scenicSpots) {
        this.scenicSpots = scenicSpots;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public void setWay(ArrayList<String> way) {
        this.way = way;
    }

}
