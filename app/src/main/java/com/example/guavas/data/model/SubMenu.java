package com.example.guavas.data.model;

public class SubMenu {
    private int[] imageIds;
    private String[] titles;

    public SubMenu(String[] subMenuTitles, int[] subMenuImageIds){
        titles = subMenuTitles;
        imageIds = subMenuImageIds;
    }

    public String[] getTitles() {
        return titles;
    }

    public int[] getImageIds() {
        return imageIds;
    }
}
