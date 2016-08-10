import java.util.ArrayList;

/**
 * Created by Cody on 6/4/2016.
 */
public class Pawn extends Piece {
    private Piece[][] board;
    private Board classBoard;
    private char name;
    private Move location;
    private int value = 1;
    char color;
    boolean hasMoved = false;
    boolean enPassantPoss = false;

    public Pawn(Board board, char color, Move location) {
        super(board, color, location);
        this.location = location;
        classBoard = board;
        this.board = board.gameBoard; //please get better at naming things in advance
        this.color = color;
        pickColor(color);
    }

    private void pickColor(char color) {
        if(color == 'w')
            name = 'P';
        else name = 'p';
    }
    public boolean legalMove(Move move) {
        // |          same column     |                      nothing in front of it          |     only moving up one or two if it hasn't moved
        if(move.col() == location.col() && board[move.row()][move.col()].toChar() == '-' &&
                (((location.row() - move.row() == 1 || (location.row() - move.row() == 2 && !hasMoved)) && color == 'b') || //black
                ((location.row() - move.row() == -1 || (location.row() - move.row() == -2 && !hasMoved)) && color == 'w'))) { //white
            return true;
        }
        // |           move up one           |         column shifts one
        char colorDest = board[move.row()][move.col()].getColor();
        if((location.row() - move.row() == 1 && Math.abs(move.col() - location.col()) == 1 && color == 'b' && colorDest == 'w') || (location.row() - move.row() == -1 && Math.abs(move.col() - location.col()) == 1 && color == 'w' && colorDest == 'b')){
            return true;
        }
        return false;
    }

    public boolean validLegalMove(Move move) {
        return validMove(move) && legalMove(move) && !classBoard.endangersKing(color, move, this);
    }

    public ArrayList<Move> genMovesScoring() {
        ArrayList<Move> tentativeList = new ArrayList<>();
        int offset;
        if(color == 'w')
            offset = 1;
        else offset = -1;
        //captures only
        if(location.col() + 1 <= 7 && location.row() + offset >= 0 && location.row() + offset <= 7)
            tentativeList.add(new Move("" + (location.row() + offset) + "" + (location.col() + 1)));
        if(location.col() - 1 >= 0 && location.row() + offset >= 0 && location.row() + offset <= 7)
            tentativeList.add(new Move("" + (location.row() + offset) + "" + (location.col() - 1)));
        return tentativeList;
    }

    public ArrayList<Move> genMoves() {
        ArrayList<Move> moveList = new ArrayList<>();
        ArrayList<Move> tentativeList = new ArrayList<>();
        int offset;
        if(color == 'w')
            offset = 1;
        else offset = -1;
        //captures evaluated first
        tentativeList.add(new Move("" + (location.row() + offset) + "" + (location.col() + 1)));
        tentativeList.add(new Move("" + (location.row() + offset) + "" + (location.col() - 1)));
        //2*offset is for the pawn's first move
        tentativeList.add(new Move("" + (location.row() + offset) + "" + location.col()));
        tentativeList.add(new Move("" + (location.row() + 2*offset) + "" + location.col()));

        for(Move m : tentativeList) {
            if(validMove(m) && legalMove(m))
                moveList.add(m);
        }
        return moveList;
    }

    public void move(Move move) {

            super.move(move);
            //
            //NOTE: NEED TO DO EN PASSANT CHECKING IN MAIN AS WELL, YOU LOSE YOUR CHANCE AFTER ONE MOVE
            //
            if(Math.abs(move.col() - location.col()) == 2)
                enPassantPoss = true;
            updateLocation(move);
            hasMoved = true;

            if(color == 'w' && location.row() == 7) {
                Queen newQ = new Queen(classBoard, 'w', location);
                board[location.row()][location.col()] = newQ;
                classBoard.remove(this);
                classBoard.whitePieces.add(newQ);
            }

            if(color == 'b' && location.row() == 0) {
                Queen newQ = new Queen(classBoard, 'b', location);
                board[location.row()][location.col()] = newQ;
                classBoard.remove(this);
                classBoard.whitePieces.add(newQ);
            }

    }


    public void updateLocation(Move move) {
        super.updateLocation(move);
        location = move;
    }

    public Move getLocation() {
        return location;
    }

    public int getValue() {
        return value;
    }

    public char toChar(){
        return name;
    }

    public Piece clone(Board newBoard) {
        return new Pawn(newBoard, color, location);
    }

}
