package com.audioreader.AudioReader.GetRequest;

public class Winners {
    private String name;
    private int bet;
    private int winningBet;
    private String profilePic;


    Winners(String name, int bet, int winningBet, String profilePic) {
        this.name = name;
        this.bet = bet;
        this.winningBet = winningBet;
        this.profilePic = profilePic;
    }


    public String getName() {
        return name;
    }

    public int getBet() {
        return bet;
    }

    public int getWinningBet() {
        return winningBet;
    }

    public String getProfilePic() {
        return profilePic;
    }
}
