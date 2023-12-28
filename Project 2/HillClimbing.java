import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HillClimbing {

    public static int cost = 0;

    // algorithm returns a QueenBoard
    public static QueenBoard solveHillClimbing(QueenBoard board) {

        if (board.getCrosses() == 0)
            return board;
        
        // queue that will hold neighbors
        Comparator<QueenBoard> QueenBoardComparator = new QueenBoardComparator();
        PriorityQueue<QueenBoard> queue = new PriorityQueue<>(10, QueenBoardComparator);

        // while the solution hasn't been found, keep going
        while (board.getCrosses() != 0) {
            queue = neighbors(board);
            QueenBoard bestNeighbor = queue.poll(); // retrieve the BEST neighbor state
            if (board.getCrosses() > bestNeighbor.getCrosses()) {
                board = bestNeighbor; // if it is an improvement, take it
            }
            else
                return board; // otherwise return
        }
        return board;
        
    }

    // method generates all 56 neighbors (8 pieces * 7 positions per piece)
    public static PriorityQueue<QueenBoard> neighbors(QueenBoard current) {
        
        Comparator<QueenBoard> QueenBoardComparator = new QueenBoardComparator();
        PriorityQueue<QueenBoard> queue = new PriorityQueue<>(10, QueenBoardComparator);

        int[] queens = current.getQueens();
        int newPos = 0;

        // for each queen, generate all the other positions it could be in, and add that to the queue
        for (int row = 0; row < 8; row++) {
            for (int adder = 1; adder <= 7; adder++) {
                newPos = queens[row] + adder;
                if (newPos > 7)
                    newPos -= 8;
                int[] newQueens = Arrays.copyOf(queens, queens.length);
                newQueens[row] = newPos;
                QueenBoard newBoard = new QueenBoard(newQueens);
                queue.add(newBoard);
                cost++; // update cost
            }
        }
        return queue;

    }

    public int getCost() {
        return cost;
    }

}