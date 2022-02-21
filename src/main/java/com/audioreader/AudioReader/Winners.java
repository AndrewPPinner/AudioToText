package com.audioreader.AudioReader;

public class Winners {
    private String name;
    private int bet;
    private String profilePic;


    Winners(String name, int bet, String profilePic) {
        this.name = name;
        this.bet = bet;
        this.profilePic = profilePic;
    }


    public String getName() {
        return name;
    }

    public int getBet() {
        return bet;
    }

    public String getProfilePic() {
        return profilePic;
    }
}
