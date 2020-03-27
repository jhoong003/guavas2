package com.example.guavas.data;

public class ItemList implements Comparable<ItemList>{
    private String text1;
    private String text2;

    public ItemList(String Itext1,String Itext2){
        this.text1 = Itext1;
        this.text2 = Itext2;
    }
    public String getText1(){
        return text1;
    }
    public String getText2(){
        return text2;
    }

    @Override
    public int compareTo(ItemList o) {
        return text1.compareTo(o.getText1());
    }
}
