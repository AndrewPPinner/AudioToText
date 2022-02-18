package com.audioreader.AudioReader;

public class Winners {
    private String name;
    private int bet;


    Winners(String name, int bet) {
        this.name = name;
        this.bet = bet;
    }


    public String getName() {
        return name;
    }

    public int getBet() {
        return bet;
    }
}
