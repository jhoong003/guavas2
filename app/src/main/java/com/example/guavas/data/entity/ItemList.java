package com.example.guavas.data.entity;

/**
 * This class holds the information of an item.
 */
public class ItemList implements Comparable<ItemList> {
    private String text1;
    private String text2;

    /**
     * Constructs the class.
     *
     * @param Itext1 the first text.
     * @param Itext2 the second text.
     */
    public ItemList(String Itext1, String Itext2) {
        this.text1 = Itext1;
        this.text2 = Itext2;
    }

    /**
     * Gets the first text.
     *
     * @return the first text.
     */
    public String getText1() {
        return text1;
    }

    /**
     * Gets the second text.
     *
     * @return the second text.
     */
    public String getText2() {
        return text2;
    }

    /**
     * Items are compared based on the first text.
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(ItemList o) {
        return text1.compareTo(o.getText1());
    }
}
