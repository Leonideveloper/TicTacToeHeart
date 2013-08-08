package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.matrix.ArrayMatrix;
import com.gmail.leonidandand.matrix.Dimension;
import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.OnEachHandler;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.GameJudge;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.GameJudgeImpl;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnOpponentMoveListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnScoreChangedListener;
import com.gmail.leonidandand.tictactoe.game.model.opponent.Opponent;
import com.gmail.leonidandand.tictactoe.game.model.opponent.OpponentFactory;

import java.util.ArrayList;
import java.util.List;

public class GameModelImpl implements GameModel {

    private final Dimension dimension;
    private final GameJudge gameJudge;
    private final List<OnOpponentMoveListener> onOpponentMoveListeners;
    private final List<OnGameFinishedListener> onGameFinishedListeners;
    private final List<OnScoreChangedListener> onScoreChangedListeners;
    private final Matrix<Cell> gameBoard;
    private final Score score;

    private boolean opponentMovesFirst;
    private Opponent opponent;

    public GameModelImpl(Dimension gameBoardDimension) {
        dimension = gameBoardDimension;
        gameBoard = new ArrayMatrix<Cell>(gameBoardDimension);
        initByEmpty(gameBoard);
        gameJudge = new GameJudgeImpl(gameBoard);
        onOpponentMoveListeners = new ArrayList<OnOpponentMoveListener>();
        onGameFinishedListeners = new ArrayList<OnGameFinishedListener>();
        onScoreChangedListeners = new ArrayList<OnScoreChangedListener>();
        score = new Score();
        opponentMovesFirst = false;
        setOpponent(OpponentFactory.createDefault());
    }

    private void initByEmpty(final Matrix<Cell> gameBoard) {
        gameBoard.forEach(new OnEachHandler<Cell>() {
            @Override
            public void handle(Position pos, Cell elem) {
                gameBoard.set(pos, Cell.EMPTY);
            }
        });
    }

    @Override
    public void setOpponent(Opponent opponent) {
        opponent.setGameBoard(gameBoard);
        this.opponent = opponent;
    }

    @Override
    public boolean emptyCell(Position pos) {
        return gameBoard.get(pos) == Cell.EMPTY;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public void addOnOpponentMoveListener(OnOpponentMoveListener listener) {
        onOpponentMoveListeners.add(listener);
    }

    @Override
    public void addOnGameFinishedListener(OnGameFinishedListener listener) {
        onGameFinishedListeners.add(listener);
    }

    @Override
    public void addOnScoreChangedListener(OnScoreChangedListener listener) {
        onScoreChangedListeners.add(listener);
    }

    @Override
    public void onPlayerMove(Position movePosition) {
        gameBoard.set(movePosition, Cell.PLAYER);
        TicTacToeResult result = gameJudge.getResult();
        if (!result.isKnown()) {
            opponentMove();
            result = gameJudge.getResult();
        }
        if (result.isKnown()) {
            onGameFinished(result);
        }
    }

    private void opponentMove() {
        Position opponentMovePos = opponent.positionToMove();
        gameBoard.set(opponentMovePos, Cell.OPPONENT);
        notifyOnOpponentMoveListeners(opponentMovePos);
    }

    private void notifyOnOpponentMoveListeners(Position opponentMovePos) {
        for (OnOpponentMoveListener each : onOpponentMoveListeners) {
            each.onOpponentMove(opponentMovePos);
        }
    }

    private void onGameFinished(TicTacToeResult result) {
        GameState gameState = result.gameState();
        opponentMovesFirst = defineOpponentMovesFirst(gameState);
        notifyOnGameFinishedListeners(result);
        if (gameState != GameState.DRAW) {
            changeScore(gameState);
            notifyOnScoreChangedListeners();
        }
        initByEmpty(gameBoard);
    }

    private boolean defineOpponentMovesFirst(GameState gameState) {
        return (gameState == GameState.OPPONENT_WINS) ||
               (opponentMovesFirst && (gameState == GameState.DRAW));
    }

    private void notifyOnGameFinishedListeners(TicTacToeResult result) {
        for (OnGameFinishedListener each : onGameFinishedListeners) {
            each.onGameFinished(result);
        }
    }

    private void changeScore(GameState gameState) {
        if (gameState == GameState.PLAYER_WINS) {
            score.increasePlayerScore();
        } else if (gameState == GameState.OPPONENT_WINS) {
            score.increaseOpponentScore();
        }
    }

    private void notifyOnScoreChangedListeners() {
        for (OnScoreChangedListener each : onScoreChangedListeners) {
            each.onScoreChanged();
        }
    }

    @Override
    public void onViewIsReadyToStartGame() {
        if (opponentMovesFirst) {
            opponentMove();
        }
    }
}
