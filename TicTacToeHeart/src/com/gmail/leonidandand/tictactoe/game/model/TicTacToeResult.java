package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.matrix.Position;

import java.util.ArrayList;
import java.util.Collection;

public class TicTacToeResult {
    private final GameState gameState;
    private final Collection<Position> cellsOnFire;

    public static TicTacToeResult unknownResult() {
        return new TicTacToeResult(GameState.UNKNOWN, new ArrayList<Position>());
    }

    public static TicTacToeResult drawResult() {
        return new TicTacToeResult(GameState.DRAW, new ArrayList<Position>());
    }

    public TicTacToeResult(GameState gameState, Collection<Position> cellsOnFire) {
        this.gameState = gameState;
        this.cellsOnFire = cellsOnFire;
    }

    public GameState gameState() {
        return gameState;
    }

    public Collection<Position> cellsOnFire() {
        return cellsOnFire;
    }

    public boolean isKnown() {
        return gameState != GameState.UNKNOWN;
    }
}
