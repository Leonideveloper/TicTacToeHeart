package com.gmail.leonidandand.tictactoe.model.opponent;

import com.gmail.leonidandand.matrix.Dimension;
import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.model.Cell;

public class StupidAIOpponent implements Opponent {

    private Matrix<Cell> gameBoard;

    @Override
    public void setGameBoard(Matrix<Cell> gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public Position positionToMove() {
        Dimension dim = gameBoard.getDimension();
        for (int row = 0; row < dim.rows; ++row) {
            for (int column = 0; column < dim.columns; ++column) {
                Position pos = new Position(row, column);
                if (gameBoard.get(pos) == Cell.EMPTY) {
                    return pos;
                }
            }
        }
        throw new RuntimeException("There is not empty cells!");
    }
}
