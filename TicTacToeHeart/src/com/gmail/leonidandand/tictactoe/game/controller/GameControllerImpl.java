package com.gmail.leonidandand.tictactoe.game.controller;

import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.view.GameView;

public abstract class GameControllerImpl implements GameController {
    private final GameModel model;

    public GameControllerImpl(GameModel model) {
        this.model = model;
    }

    protected abstract GameView getGameView();
    
    @Override
    public void onViewIsReadyToStartGame() {
        model.onViewIsReadyToStartGame();
    }

    @Override
    public void onPlayerMove(Position movePos) {
        getGameView().blockMoves();
        model.onPlayerMove(movePos);
        getGameView().unblockMoves();
    }
}
