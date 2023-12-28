public class QueenBoard {
    
    private int[] queens;

    // constructor takes in an int array
    public QueenBoard(int[] layout) {
        queens = new int[8];
        for (int i = 0; i < 8; i++) {
            queens[i] = layout[i];
        }
    }

    // returns number of pairs of queens crossing each other
    private int findCrosses() {
        int crossCount = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = i + 1; j < 8; j++) {
                if (isAttacking(i, queens[i], j, queens[j]))
                    crossCount++;
            }
        }
        return crossCount;
    }

    // private helper method to get crosses
    private boolean isAttacking(int r1, int c1, int r2, int c2) {
        if (r1 == r2 || c1 == c2)
            return true;
        
        if (Math.abs(r1 - r2) == Math.abs(c1 - c2))
            return true;

        return false;
    }

    public int getCrosses() {
        return findCrosses();
    }

    public int[] getQueens() {
        return this.queens;
    }

    // generates random layout
    public static int[] getRandomLayout() {
        int[] placements = new int[8];
        for (int i = 0; i < 8; i++) {
            placements[i] = (int) (Math.random() * 8);
        }
        return placements;
    }

    // string representation
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < 8; i++) {
            sb.append(" " + queens[i] + " ");
        }
        sb.append("]");
        return sb.toString();
    }
}
