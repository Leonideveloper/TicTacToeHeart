package com.gmail.leonidandand.tictactoe.view;

import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.controller.GameController;
import com.gmail.leonidandand.tictactoe.model.TicTacToeResult;
import com.gmail.leonidandand.tictactoe.model.GameModel;
import com.gmail.leonidandand.tictactoe.model.listeners.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.model.listeners.OnOpponentMoveListener;
import com.gmail.leonidandand.tictactoe.model.listeners.OnScoreChangedListener;
import com.gmail.leonidandand.tictactoe.model.Score;
import com.gmail.leonidandand.tictactoe.view.components.GameResultDisplay;
import com.gmail.leonidandand.tictactoe.view.components.GameScoreDisplay;
import com.gmail.leonidandand.tictactoe.view.components.OpponentMoveProgressBar;
import com.gmail.leonidandand.tictactoe.view.components.game_board.GameBoard;
import com.gmail.leonidandand.tictactoe.view.components.game_board.OnCellClickListener;

public abstract class GameViewImpl implements GameView, OnCellClickListener,
                        OnOpponentMoveListener, OnGameFinishedListener, OnScoreChangedListener {

    private final GameController controller;
    private final GameModel model;

    private boolean movesBlocked;
    private boolean gameFinished;

    public GameViewImpl(GameController controller, GameModel model) {
        this.controller = controller;
        this.model = model;
        model.addOnOpponentMoveListener(this);
        model.addOnGameFinishedListener(this);
        model.addOnScoreChangedListener(this);
        gameFinished = false;
        movesBlocked = false;
    }

    @Override
    public void blockMoves() {
        movesBlocked = true;
        opponentMoveProgressBar().show();
    }

    protected abstract OpponentMoveProgressBar opponentMoveProgressBar();

    @Override
    public void unblockMoves() {
        opponentMoveProgressBar().hide();
        movesBlocked = false;
    }

    @Override
    public boolean movesBlocked() {
        return movesBlocked;
    }

    @Override
    public void onCellClick(Position cellPos) {
        if (gameFinished) {
            gameFinished = false;
            gameBoard().clear();
            controller.onViewIsReadyToStartGame();
        } else if (model.emptyCell(cellPos) && !movesBlocked()) {
            gameBoard().showMove(cellPos);
            controller.onPlayerMove(cellPos);
        }
    }

    protected abstract GameBoard gameBoard();

    @Override
    public void onOpponentMove(Position movePos) {
        gameBoard().showMove(movePos);
    }

    @Override
    public void onGameFinished(TicTacToeResult result) {
        gameFinished = true;
        gameBoard().showFireLine(result.cellsOnFire());
        gameResultDisplay().show(result.gameState());
    }

    protected abstract GameResultDisplay gameResultDisplay();

    @Override
    public void onScoreChanged() {
        Score newScore = model.getScore();
        gameScoreDisplay().showScore(newScore);
    }

    protected abstract GameScoreDisplay gameScoreDisplay();

}
