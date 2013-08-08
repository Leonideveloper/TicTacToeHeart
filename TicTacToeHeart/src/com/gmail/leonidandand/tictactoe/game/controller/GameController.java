package com.gmail.leonidandand.tictactoe.game.controller;

import com.gmail.leonidandand.matrix.Position;

public interface GameController {
    void onViewIsReadyToStartGame();
    void onPlayerMove(Position movePos);
}
