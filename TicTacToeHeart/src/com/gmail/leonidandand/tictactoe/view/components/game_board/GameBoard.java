package com.gmail.leonidandand.tictactoe.view.components.game_board;


import com.gmail.leonidandand.matrix.Position;

import java.util.Collection;

public interface GameBoard {
    void setOnCellClickListener(OnCellClickListener onCellClickListener);
    void showMove(Position pos);
    void showFireLine(Collection<Position> positions);
    void clear();
}
