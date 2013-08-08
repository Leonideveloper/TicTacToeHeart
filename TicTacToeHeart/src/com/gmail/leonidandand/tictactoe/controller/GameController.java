package com.gmail.leonidandand.tictactoe.controller;

import com.gmail.leonidandand.matrix.Position;

public interface GameController {
    void onViewIsReadyToStartGame();
    void onPlayerMove(Position movePos);
}
