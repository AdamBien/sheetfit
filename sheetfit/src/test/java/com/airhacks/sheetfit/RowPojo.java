package com.airhacks.sheetfit;

/**
 *
 * @author airhacks.com
 */
public class RowPojo {

    private String first;
    private double second;
    private long third;

    public RowPojo(String first, double second, long third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public String getFirst() {
        return first;
    }

    public double getSecond() {
        return second;
    }

    public long getThird() {
        return third;
    }

}
