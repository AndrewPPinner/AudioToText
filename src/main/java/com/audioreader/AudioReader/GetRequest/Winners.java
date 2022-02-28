package com.audioreader.AudioReader.GetRequest;

public class Winners {
    private String name;
    private int bet;
    private int winningBet;
    private String profilePic;
    private int timesWon;


    Winners(String name, Integer bet, int winningBet, String profilePic, Integer timesWon) {
        this.name = name;
        if (bet == null) {
            this.bet = 0;
        } else {
            this.bet = Integer.parseInt(String.valueOf(bet));
        }
        this.winningBet = winningBet;
        this.profilePic = profilePic;
        if (timesWon == null) {
            this.timesWon = 0;
        } else {
            this.timesWon = Integer.parseInt(String.valueOf(timesWon));
        }
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

    public int getTimesWon() {
        return timesWon;
    }
}
