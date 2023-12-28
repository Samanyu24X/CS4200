import java.util.Arrays;

public class PuzzleBoard {

    private int[] board = new int[9];
    private int h1Value, h2Value;
    private int zeroPosition;
    private int heuristicType;
    private int depth;
    private PuzzleBoard parent;
    private int AStarValue;

    // public constructor
    public PuzzleBoard(String config, int hType) {
        this(config, hType, 0, null);
    }

    // private constructor
    private PuzzleBoard(String config, int hType, int depth, PuzzleBoard parent) {
        this.zeroPosition = config.indexOf("0");
        for (int i = 0; i < 9; i++) {
            this.board[i] = Character.getNumericValue(config.charAt(i)); // convert string to int array
        }
        this.heuristicType = hType;
        this.depth = depth;
        this.parent = parent;
        if (heuristicType == 1) // calculate f(n) = g(n) + h(n) based on heuristic type
            AStarValue = findH1();
        else
            AStarValue = findH2();
        AStarValue += depth;
    }

    private int findH1() { // calculate h(n)
        int score = 0;
        for (int i = 0; i < 9; i++) {
            if (board[i] != i)
                score++;
        }
        return score;
    }

    private int findH2() { // calculate h(n)
        int score = 0;
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0)
                continue;
            score += Math.abs(i / 3 - board[i] / 3) + Math.abs(i % 3 - board[i] % 3);
        }
        return score;
    }

    public int getH1() {
        return h1Value;
    }

    public int getH2() {
        return h2Value;
    }

    public int[] board() {
        return board;
    }

    public int getZero() {
        return zeroPosition;
    }

    // in the case that there is a parent board, it can be accessed here
    public PuzzleBoard getParent() {
        return parent;
    }

    // check if the board is in the goal state
    public boolean isGoalState() {
        for (int i = 0; i < 9; i++) {
            if (board[i] != i)
                return false;
        }
        return true;
    }

    public int getAStarValue() {
        return AStarValue;
    }

    // String represntation of board for visual aid
    @Override
    public String toString() {
        String result = "";
        result += "[ " + board[0] + " " + board[1] + " " + board[2] + " ]\n";
        result += "[ " + board[3] + " " + board[4] + " " + board[5] + " ]\n";
        result += "[ " + board[6] + " " + board[7] + " " + board[8] + " ]\n";
        return result;
    }

    // boolean functions that determine if moves can be made
    public boolean canMoveUp() {
        return (zeroPosition >= 3);
    }
    
    public boolean canMoveDown() {
        return (zeroPosition <= 5);
    }

    public boolean canMoveLeft() {
        return (zeroPosition != 0 && zeroPosition != 3 && zeroPosition != 6);
    }

    public boolean canMoveRight() {
        return (zeroPosition != 2 && zeroPosition != 5 && zeroPosition != 8);
    }

    // function returning board with the blank space moved up
    public PuzzleBoard getUpMove() {
        int[] newBoard = Arrays.copyOf(board, 9);
        int temp = newBoard[this.zeroPosition - 3];
        newBoard[this.zeroPosition - 3] = 0;
        newBoard[this.zeroPosition] = temp; // swap positions
        //zeroPosition -= 3;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            result.append(newBoard[i]); // create new string representation
        }
        //System.out.println("Upmove: " + result.toString());
        return new PuzzleBoard(result.toString(), this.heuristicType, this.depth + 1, this);
    }

    // function returning board with the blank space moved down
    public PuzzleBoard getDownMove() {
        int[] newBoard = Arrays.copyOf(board, 9);
        int temp = newBoard[this.zeroPosition + 3];
        newBoard[this.zeroPosition + 3] = 0;
        newBoard[this.zeroPosition] = temp; // swap positions
        //zeroPosition += 3;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            result.append(newBoard[i]); // create new string representation
        }
        //System.out.println("Downmove: " + result.toString());

        return new PuzzleBoard(result.toString(), this.heuristicType, this.depth + 1, this);
    }

    // function returning board with the blank space moved left
    public PuzzleBoard getLeftMove() {
        int[] newBoard = Arrays.copyOf(board, 9);
        int temp = newBoard[this.zeroPosition - 1];
        newBoard[this.zeroPosition - 1] = 0;
        newBoard[this.zeroPosition] = temp; // swap positions
        //zeroPosition -= 1;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            result.append(newBoard[i]); // create new string representation
        }
        //System.out.println("Leftmove: " + result.toString());

        return new PuzzleBoard(result.toString(), this.heuristicType, this.depth + 1, this);
    }

    // function returning board with the blank space moved right
    public PuzzleBoard getRightMove() {
        int[] newBoard = Arrays.copyOf(board, 9);
        int temp = newBoard[this.zeroPosition + 1];
        newBoard[this.zeroPosition + 1] = 0;
        newBoard[this.zeroPosition] = temp; // swap positions

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            result.append(newBoard[i]); // create new string representation
        }
        //System.out.println("Rightmove: " + result.toString());

        return new PuzzleBoard(result.toString(), this.heuristicType, this.depth + 1, this);
    }
}