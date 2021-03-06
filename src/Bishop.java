import java.util.ArrayList;

/**
 * Created by Cody on 6/4/2016.
 */
public class Bishop extends Piece {
    private Piece[][] board;
    private Board classBoard;
    private char name;
    private float value = 3;
    char color;

    public Bishop(Board board, char color, Move location) {
        super(board, color, location);
        classBoard = board;
        this.board = board.gameBoard; //please get better at naming things in advance
        this.color = color;
        pickColor(color);
        value = Const.kBISHOP;
    }

    public Bishop(Board board, char color, Move location, float val) {
        super(board, color, location);
        classBoard = board;
        this.board = board.gameBoard; //please get better at naming things in advance
        this.color = color;
        pickColor(color);
        value = val;
    }

    private void pickColor(char color) {
        if (color == 'w')
            name = 'B';
        else name = 'b';
    }

    //legal as far as the piece's movement is concerned
    public boolean legalMove(Move move) {
        if (Math.abs(move.row() - location.row()) == Math.abs(move.col() - location.col())) { //move up/down and left/right the same amount
            return true;
        }
        return false;
    }

    //Required because checking endangersKing() in any of the other methods causes infinite loop
    public boolean finalMoveCheck(Move move) {
        return validMove(move) && legalMove(move) && !classBoard.endangersKing(color, move, this);
    }

    //Used to find which pieces it can attack or defend
    public ArrayList<Move> genMovesScoring() {
        ArrayList<Move> moveList = new ArrayList<>();
        ArrayList<Move> tentativeList = new ArrayList<>();
        int offsetCol = 1;
        int offsetRow = 1;
        while (location.col() + offsetCol < 8 || location.row() + offsetRow < 8 || location.col() - offsetCol >= 0 || location.row() - offsetRow >= 0) {
            tentativeList.add(new Move("" + (offsetRow + location.row()) + "" + (offsetCol + location.col())));
            tentativeList.add(new Move("" + (location.row() - offsetRow) + "" + (location.col() - offsetCol)));
            tentativeList.add(new Move("" + (location.row() + offsetRow) + "" + (location.col() - offsetCol)));
            tentativeList.add(new Move("" + (location.row() - offsetRow) + "" + (location.col() + offsetCol)));
            offsetCol++;
            offsetRow++;
        }
        for (Move move : tentativeList) {
            if (move.row() >= 0 && move.row() <= 7 && move.col() >= 0 && move.col() <= 7 && !jump(move)) {
                moveList.add(move);
            }
        }
        return moveList;
    }

    public ArrayList<Move> genMoves() {
        ArrayList<Move> legalList = new ArrayList<>();
        ArrayList<Move> tentativeList = new ArrayList<>();
        int offsetCol = 1;
        int offsetRow = 1;
        while (location.col() + offsetCol < 8 || location.row() + offsetRow < 8 || location.col() - offsetCol >= 0 || location.row() - offsetRow >= 0) {
            if (offsetRow + location.row() < 8 && location.col() + offsetCol < 8) {
                tentativeList.add(new Move("" + (location.row() + offsetRow) + "" + (location.col() + offsetCol)));
            }
            if (location.row() - offsetRow > 0 && location.col() - offsetCol > 0) {
                tentativeList.add(new Move("" + (location.row() - offsetRow) + "" + (location.col() - offsetCol)));
            }
            if (location.row() + offsetRow < 8 && location.col() - offsetCol > 0) {
                tentativeList.add(new Move("" + (location.row() + offsetRow) + "" + (location.col() - offsetCol)));
            }
            if (location.row() - offsetRow > 0 && location.col() + offsetCol < 8) {
                tentativeList.add(new Move("" + (location.row() - offsetRow) + "" + (location.col() + offsetCol)));
            }

            offsetCol++;
            offsetRow++;
        }
        for (Move m : tentativeList) {
            if (validMove(m)) {
                legalList.add(new Move(m.row() + "" + m.col(), location));
            }
        }
        return legalList;
    }

    public float getValue() {
        return value;
    }

    public char toChar() {
        return name;
    }

    public Piece clone(Board newBoard) {
        return new Bishop(newBoard, color, location, value);
    }
}
