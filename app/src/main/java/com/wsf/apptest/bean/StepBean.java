package com.wsf.apptest.bean;

public class StepBean {

    public static final int UNDO=-1;
    public static final int CURRENT=0;
    public static final int COMPLETED=1;


    private int state;

    private int number;

    public StepBean(int state, int number) {
        this.state = state;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
