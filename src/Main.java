import java.util.Scanner;

/**
 * Created by Cody on 6/4/2016.
 */


public class Main {
    public static void main(String[] args) {
        int noCaptureLimit = 35;
        boolean staleMate = false;
        int score = 0, tmpScore = 0;
        int unchangedScore = 0;
        Scanner scan = new Scanner(System.in);

        Const.kPAWN = Float.valueOf(args[0]);
        Const.kKNIGHT = Float.valueOf(args[1]);
        Const.kBISHOP = Float.valueOf(args[2]);
        Const.kROOK = Float.valueOf(args[3]);
        Const.kQUEEN = Float.valueOf(args[4]);
        Board b1 = new Board();
        b1.kMOBILITY = Float.valueOf(args[5]);
        AI aiB = new AI(b1, 'b', 4);

        Const.kPAWN = Float.valueOf(args[6]);
        Const.kKNIGHT = Float.valueOf(args[7]);
        Const.kBISHOP = Float.valueOf(args[8]);
        Const.kROOK = Float.valueOf(args[9]);
        Const.kQUEEN = Float.valueOf(args[10]);
        Board b2 = new Board();
        b2.kMOBILITY = Float.valueOf(args[11]);
        // read in the rest of the values
        AI aiW = new AI(b2, 'w', 4);
        b1.printBoard();

        boolean valid;
        boolean whiteTurn = false;
        game:
        while (!b1.gameOver() && !staleMate) {
            valid = false;
            whiteTurn = !whiteTurn;
            while (!valid) {
                if (!whiteTurn) {
                    String bMove = aiB.aiMove();
                    b1.parseMove(bMove);
                    b2.parseMove(bMove);
                    valid = true;

                    tmpScore = b1.whitePieces.size() + b1.blackPieces.size();
                    if (score == tmpScore) {
                        unchangedScore++;
                    } else {
                        unchangedScore = 0;
                        score = tmpScore;
                    }
                    if (unchangedScore >= noCaptureLimit) {
                        break game;
                    }
                } else {
                    String wMove = aiW.aiMove();
                    b1.parseMove(wMove);
                    b2.parseMove(wMove);
                    valid = true;

                    tmpScore = b1.whitePieces.size() + b1.blackPieces.size();
                    if (score == tmpScore) {
                        unchangedScore++;
                    } else {
                        unchangedScore = 0;
                        score = tmpScore;
                    }
                    if (unchangedScore >= noCaptureLimit) {
                        break game;
                    }
                    /*System.out.println("Please enter your move: ");
                    String candidateMove = scan.nextLine();

                    Move m = new Move(candidateMove.charAt(0) + "" + candidateMove.charAt(1));
                    //can't move opponent's pieces
                    if (b1.blackPieces.contains(b1.gameBoard[m.row()][m.col()])) {
                        valid = false;
                    }
                    //if so try and do the move
                    else {
                        valid = b1.parseMove(candidateMove);
                    }*/
                }
            }

            System.out.println("Nodes evaluated: " + Stats.nodesEval);
            System.out.println("Black's Board evaluation: " + String.format("%8.3f", b1.getValue()));
            System.out.println("White's Board evaluation: " + String.format("%8.3f", b2.getValue()));
            /*if (Stats.sortAmt != 0) {
                long aiMoveTimeRaw = (Stats.aiMoveEnd - Stats.aiMoveStart);
                long aiMoveTime = aiMoveTimeRaw / 1000000;
                System.out.println("AIMove time: " + aiMoveTime + "ms");
                System.out.println(String.format("%2.3f", ((double) Stats.sortTot) * 100 / aiMoveTimeRaw) + "% of time spent on sort");
                System.out.println(String.format("%2.3f", ((double) Stats.cloneTime) * 100 / aiMoveTimeRaw) + "% of time spent on clone");
                System.out.println(Stats.cloneTime / 1000000 + "ms spent cloning");
                System.out.println(String.format("%2.3f", ((double) Stats.getMove) * 100 / aiMoveTimeRaw) + "% of time spent on getMoves");
                System.out.println(String.format("%2.3f", ((double) Stats.getValueTime) * 100 / aiMoveTimeRaw) + "% of time spent on getValue");
                System.out.println(Stats.hashTableHits + " hash table hits");
                System.out.println(Stats.hashTableSuccess + " hash table successes");
            }
            Stats.reset();*/
            b1.printBoard();
        }

    }
}
