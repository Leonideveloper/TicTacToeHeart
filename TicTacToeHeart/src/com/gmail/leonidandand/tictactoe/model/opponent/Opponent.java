package com.gmail.leonidandand.tictactoe.model.opponent;

import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.model.Cell;

public interface Opponent {
    void setGameBoard(Matrix<Cell> gameBoard);
    Position positionToMove();
}
