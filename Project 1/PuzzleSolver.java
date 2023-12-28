import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class PuzzleSolver {
    
    private PuzzleBoard rootBoard;
    private String config;
    private int stepsTaken;
    private int nodesGenerated;

    // constructor set defaults
    public PuzzleSolver(String config) {
        //this.rootBoard = new PuzzleBoard(config);
        this.config = config;
        stepsTaken = 0;
        nodesGenerated = 0;
    }

    public void solve(int heuristic) {

        Comparator<PuzzleBoard> puzzleComparator = new PuzzleComparator(); // means of comparing the boards
        PriorityQueue<PuzzleBoard> queue = new PriorityQueue<>(10, puzzleComparator); // priority queue to order boards by A*

        HashMap<String, PuzzleBoard> hashMap = new HashMap<>(10); // keep track of visited nodes/states

        this.rootBoard = new PuzzleBoard(this.config, heuristic);
        queue.add(rootBoard);
        nodesGenerated = 0;

        while (queue.size() > 0) { // while there are still nodes to be looked at

            PuzzleBoard board = queue.remove(); // get the current board off of the queue
            //System.out.println("Current board with a h(n) of " + board.getAStarValue() + ":\n" + board.toString());
            //System.out.println(board.toString());

            if (board.isGoalState()) {
                System.out.println("Solved!\n"); // if found goal state, go back and print all states
                int steps = -1;
                Stack<PuzzleBoard> boardStack = new Stack<>(); // stack to store board states

                while (board != null) {
                    steps++;
                    boardStack.push(board); // push all the board states onto the stack in order backwards
                    board = board.getParent();
                }

                //System.out.println("Amount of steps taken = " + steps);
                while (boardStack.size() > 1 ) {
                    System.out.println(boardStack.pop().toString()); // now print out all the moves in order from the top of the stack
                    System.out.println("--------------");
                }
                System.out.println(boardStack.pop().toString());
                this.stepsTaken = steps;
                //System.out.println("Final steps = " + stepsTaken);
                return;

            }

            // if blank can move up, generate that board and add it to the queue
            if (board.canMoveUp()) {
                PuzzleBoard boardUp = board.getUpMove();
                nodesGenerated++;
                if (!hashMap.containsKey(boardUp.toString())) {
                    queue.add(boardUp);
                    //System.out.println("Added up move!");
                }
            }
            // if blank can move down, generate that board and add it to the queue
            if (board.canMoveDown()) {
                PuzzleBoard boardDown = board.getDownMove();
                nodesGenerated++;
                if (!hashMap.containsKey(boardDown.toString())) {
                    queue.add(boardDown);
                    //System.out.println("Added down move!");
                }
            }
            // if blank can move left, generate that board and add it to the queue
            if (board.canMoveLeft()) {
                PuzzleBoard boardLeft = board.getLeftMove();
                nodesGenerated++;
                if (!hashMap.containsKey(boardLeft.toString())) {
                    queue.add(boardLeft);
                    //System.out.println("Added left move!");
                }
            }
            // if blank can move right, generate that board and add it to the queue
            if (board.canMoveRight()) {
                PuzzleBoard boardRight = board.getRightMove();
                nodesGenerated++;
                if (!hashMap.containsKey(boardRight.toString())) {
                    queue.add(boardRight);
                    //System.out.println("Added right move!");
                }
            }

            hashMap.put(board.toString(), board); // now that we're done with it, add this board to the hashmap of seen boards

        }
        
        System.out.println("Search failed"); // print failure
        System.exit(0);
    }

    public int getNodesGenerated() {
        return this.nodesGenerated;
    }

    public int getStepsTaken() {
        return this.stepsTaken;
    }
    
}
