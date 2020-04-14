package com.example.guavas.data.entity;

/**
 * This class holds the information of a sub menu.
 */
public class SubMenu {
    private int[] imageIds;
    private String[] titles;

    /**
     * The constructor of the class.
     *
     * @param subMenuTitles   the title of the sub menu.
     * @param subMenuImageIds the image id of the sub menu.
     */
    public SubMenu(String[] subMenuTitles, int[] subMenuImageIds) {
        titles = subMenuTitles;
        imageIds = subMenuImageIds;
    }

    /**
     * Gets the sub menu titles.
     *
     * @return the sub menu titles.
     */
    public String[] getTitles() {
        return titles;
    }

    /**
     * Gets the sub menu image ids.
     *
     * @return the sub menu image ids.
     */
    public int[] getImageIds() {
        return imageIds;
    }
}
