package com.gmail.leonidandand.tictactoe.game.model.game_judge;

import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.Cell;
import com.gmail.leonidandand.tictactoe.game.model.TicTacToeResult;
import com.gmail.leonidandand.tictactoe.game.model.GameState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GameJudgeImpl implements GameJudge {
    private final Matrix<Cell> gameBoard;
    private final int gameBoardDimension;

    public GameJudgeImpl(Matrix<Cell> gameBoard) {
        this.gameBoard = gameBoard;
        this.gameBoardDimension = gameBoard.getDimension().rows;
    }

    @Override
    public TicTacToeResult getResult() {
        TicTacToeResult result = checkBoard();
        if (result.isKnown()) {
            return result;
        }
        if (gameBoard.contains(Cell.EMPTY)) {
            return TicTacToeResult.unknownResult();
        }
        return TicTacToeResult.drawResult();
    }

    private TicTacToeResult checkBoard() {
        TicTacToeResult result = combineFireLines(checkRows(), checkColumns(), checkDiagonals());
        // max number of fire-lines: 4 Fire Lines (need to combine):
        // -----------------
        //   X O O X O O X
        //   O X O X O X O
        //   O O X X X O O
        //   X X X X X X X
        //   O O X X X O O
        //   O X O X O X O
        //   X O O X O O X
        return result;
    }

    private TicTacToeResult combineFireLines(TicTacToeResult res1,
                        TicTacToeResult res2, TicTacToeResult res3) {
        List<TicTacToeResult> results = matchDecidedResults(res1, res2, res3);
        if (results.isEmpty()) {
            return TicTacToeResult.unknownResult();
        } else {
            GameState state = results.get(0).gameState();
            Set<Position> totalCellsOnFire = new HashSet<Position>();
            for (TicTacToeResult each : results) {
                totalCellsOnFire.addAll(each.cellsOnFire());
            }
            return new TicTacToeResult(state, totalCellsOnFire);
        }
    }

    private List<TicTacToeResult> matchDecidedResults(TicTacToeResult res1,
                                TicTacToeResult res2, TicTacToeResult res3) {
        List<TicTacToeResult> results = new LinkedList<TicTacToeResult>();
        if (res1.isKnown()) {
            results.add(res1);
        }
        if (res2.isKnown()) {
            results.add(res2);
        }
        if (res3.isKnown()) {
            results.add(res3);
        }
        return results;
    }

    private TicTacToeResult checkRows() {
        for (int i = 0; i < gameBoardDimension; ++i) {
            TicTacToeResult result = checkRow(i);
            if (result.isKnown()) {
                return result;
            }
        }
        return TicTacToeResult.unknownResult();
    }

    private TicTacToeResult checkRow(int row) {
        return resultByCellsPositions(cellsPositionsOnRow(row));
    }

    private List<Position> cellsPositionsOnRow(int row) {
        List<Position> cells = new ArrayList<Position>(gameBoardDimension);
        for (int column = 0; column < gameBoardDimension; ++column) {
            cells.add(new Position(row, column));
        }
        return cells;
    }

    private TicTacToeResult resultByCellsPositions(List<Position> cellsPositions) {
        Position firstCellOnLinePosition = cellsPositions.get(0);
        Cell firstCellOnLine = gameBoard.get(firstCellOnLinePosition);
        if (firstCellOnLine == Cell.EMPTY) {
            return TicTacToeResult.unknownResult();
        }
        for (int i = 1; i < gameBoardDimension; ++i) {
            Position currentPosition = cellsPositions.get(i);
            Cell currentCell = gameBoard.get(currentPosition);
            if (firstCellOnLine != currentCell) {
                return TicTacToeResult.unknownResult();
            }
        }
        return new TicTacToeResult(stateByCell(firstCellOnLine), cellsPositions);
    }

    private GameState stateByCell(Cell cell) {
        if (cell == Cell.PLAYER) {
            return GameState.PLAYER_WINS;
        } else if (cell == Cell.OPPONENT) {
            return GameState.OPPONENT_WINS;
        }
        throw new IllegalArgumentException("Input cell must be not empty!");
    }

    private TicTacToeResult checkColumns() {
        for (int i = 0; i < gameBoardDimension; ++i) {
            TicTacToeResult result = checkColumn(i);
            if (result.isKnown()) {
                return result;
            }
        }
        return TicTacToeResult.unknownResult();
    }

    private TicTacToeResult checkColumn(int column) {
        return resultByCellsPositions(cellsPositionsOnColumn(column));
    }

    private List<Position> cellsPositionsOnColumn(int column) {
        List<Position> cells = new ArrayList<Position>(gameBoardDimension);
        for (int row = 0; row < gameBoardDimension; ++row) {
            cells.add(new Position(row, column));
        }
        return cells;
    }

    private TicTacToeResult checkDiagonals() {
        TicTacToeResult result = checkLeftUpperDiagonal();
        if (result.isKnown()) {
            return result;
        } else {
            return checkRightUpperDiagonal();
        }
    }

    private TicTacToeResult checkLeftUpperDiagonal() {
        return resultByCellsPositions(cellsPositionsOnLeftUpperDiagonal());
    }

    private List<Position> cellsPositionsOnLeftUpperDiagonal() {
        List<Position> positions = new ArrayList<Position>(gameBoardDimension);
        for (int i = 0; i < gameBoardDimension; ++i) {
            positions.add(new Position(i, i));
        }
        return positions;
    }

    private TicTacToeResult checkRightUpperDiagonal() {
        return resultByCellsPositions(cellsPositionsOnRightUpperDiagonal());
    }

    private List<Position> cellsPositionsOnRightUpperDiagonal() {
        List<Position> positions = new ArrayList<Position>(gameBoardDimension);
        for (int i = 0; i < gameBoardDimension; ++i) {
            positions.add(new Position(i, gameBoardDimension - i - 1));
        }
        return positions;
    }
}
