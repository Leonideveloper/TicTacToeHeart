package com.gmail.leonidandand.tictactoe.model.opponent;

public class OpponentFactory {
    public static Opponent createDefault() {
        return new StupidAIOpponent();
    }
}
