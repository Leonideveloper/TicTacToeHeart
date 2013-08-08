package com.gmail.leonidandand.tictactoe.game.model;

public class Score {
    private int playerScore;
    private int opponentScore;

    public Score() {
        playerScore = 0;
        opponentScore = 0;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public void increasePlayerScore() {
        ++playerScore;
    }

    public void increaseOpponentScore() {
        ++opponentScore;
    }
}
