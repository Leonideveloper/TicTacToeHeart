package com.gmail.leonidandand.tictactoe.game.model.opponent;

public class OpponentFactory {
    public static Opponent createDefault() {
        return new StupidAIOpponent();
    }
}
