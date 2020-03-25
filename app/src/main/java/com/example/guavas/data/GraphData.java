package com.example.guavas.data;

public class GraphData {
    private long count;
    private double total;

    public GraphData(){
        count = 0;
        total = 0;
    }

    public void addCount(long addValue){
        count+=addValue;
    }

    public void addTotal(double addValue){
        total+=addValue;
    }

    public double getAverage(){return total/count;}

    public long getCount() { //TODO: remove debug
        return count;
    }
}
