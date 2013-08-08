package com.gmail.leonidandand.tictactoe.model;

import com.gmail.leonidandand.matrix.Dimension;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.model.listeners.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.model.listeners.OnOpponentMoveListener;
import com.gmail.leonidandand.tictactoe.model.listeners.OnScoreChangedListener;
import com.gmail.leonidandand.tictactoe.model.opponent.Opponent;

public interface GameModel {
    boolean emptyCell(Position pos);
    Dimension getDimension();
    Score getScore();
    void addOnOpponentMoveListener(OnOpponentMoveListener listener);
    void addOnGameFinishedListener(OnGameFinishedListener listener);
    void addOnScoreChangedListener(OnScoreChangedListener listener);
    void setOpponent(Opponent opponent);
    void onPlayerMove(Position movePosition);
    void onViewIsReadyToStartGame();
}
